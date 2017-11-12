package dirtybits;

import java.io.*;
import java.util.concurrent.ConcurrentLinkedQueue;

public class LogThread implements Runnable {

	private Thread thread;
	private ConcurrentLinkedQueue<LogMessage> messageQueue = new ConcurrentLinkedQueue<>();
	private PrintWriter writer;
	private String fileName;
	private LogServerConfig config;
	private boolean running;
	private boolean somethingToFlush = false;

	public LogThread() {
		this.config = LogServerConfig.getConfig();
	}

	public LogThread(String fileName) {
		this();
		this.fileName = fileName;
	}

	private void writeMessageToFile(LogMessage log) throws IOException {
		checkAndCreateWriter();

		String logLine = "[" + log.getDate() + "]" + "[" + log.getClient() + "]" + "[" + log.getLevel() + "]" + log.getMessage();
		writer.println(logLine);
		somethingToFlush = true;
	}

	public void enqueueMessage(LogMessage log) {
//        System.out.println("[LogThread][enqueueMessage]:" + log.getMessage());
		if (this.messageQueue != null) {
			messageQueue.add(log);
		}
	}

	public void run() {
		long threadId = Thread.currentThread().getId();
		System.out.println("[LogThread][start][" + threadId + "]");
		this.running = true;
		while (running) {
//            System.out.println("[LogThread][while][" + threadId + "]");
			if (messageQueue.isEmpty()) {
				if (somethingToFlush) {
					somethingToFlush = false;
					writer.flush();
				}
				try {
					Thread.sleep(200);
				} catch (InterruptedException exc) {
					System.out.println("InterruptedException thrown while Thread.sleep.");
					break;
				}
				continue;
			}
			LogMessage log = messageQueue.poll();
			if (log == null) {
				continue;
			}
			try {
				this.writeMessageToFile(log);
			} catch (IOException e) {
				e.printStackTrace();
				return;
			}
		}
		running = false;
		System.out.println("[LogThread][stop][" + threadId + "]");
	}

	public void start() {
		this.thread = (new Thread(this));
		this.thread.setName("Thread for: " + this.fileName);
		this.thread.start();
	}

	public void stop() {
		this.running = false;
		if (this.writer != null) {
			this.writer.flush();
			this.writer.close();
			this.writer = null;
		}
		if (this.thread != null) {
			this.thread.interrupt();
			this.thread = null;
		}
		this.messageQueue = null;
	}


	private void checkAndCreateWriter() throws IOException {
		if (this.writer != null) {
			return;
		}
		String filePath = config.getDirectoryPath() + "/" + fileName;
		System.out.println("Writing to file: " + filePath);
		try {
			File file = new File(filePath);
			file.getParentFile().mkdirs();
			FileWriter fw = new FileWriter(file, true);
			BufferedWriter bw = new BufferedWriter(fw);
			this.writer = new PrintWriter(bw);
		} catch (IOException e) {
			System.out.println("Couldn't open file for write: " + filePath);
			throw e;
		}
	}
}
