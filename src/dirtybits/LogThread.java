package dirtybits;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LogThread implements Runnable {

	private Thread thread;
	private ConcurrentLinkedQueue<LogMessage> messageQueue = new ConcurrentLinkedQueue<>();
	private PrintWriter writer;
	private String fileName;
	private LogServerConfig config;
	private boolean running;
	private boolean somethingToFlush = false;
	private int fileCounter;
	private long currentFileSize;
	private String filePath = null;

	public LogThread() {
		this.config = LogServerConfig.getConfig();
	}

	public LogThread(String fileName) {
		this();
		this.fileName = fileName;
	}

	private void writeMessageToFile(LogMessage log) throws IOException {
		String logLine = "[" + log.getDate() + "]" + "[" + log.getClient() + "]" + "[" + log.getLevel() + "]" + log.getMessage();

		if (this.currentFileSize + logLine.length() >= config.getMaxLogFileSize()) {
			this.rotateFileLog();
		}
		checkAndCreateWriter();

		this.currentFileSize += logLine.length() + System.getProperty("line.separator").length();

		writer.println(logLine);
		somethingToFlush = true;

	}

	public void enqueueMessage(LogMessage log) {
		if (this.messageQueue != null) {
			messageQueue.add(log);
		}
	}

	public void run() {
		long threadId = Thread.currentThread().getId();
		System.out.println("[LogThread][start][" + threadId + "]");
		this.running = true;
		while (running) {
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
		if (this.running) {
			return;
		}
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
		if (this.fileCounter == 0) {
			this.loadFileCounter();
			filePath = config.getDirectoryPath() + "/" + fileName + ".log";
		}
		System.out.println("Open file for write: " + filePath);
		try {
			File file = new File(filePath);
			this.currentFileSize = file.length();
			file.getParentFile().mkdirs();
			FileWriter fw = new FileWriter(file, true);

			BufferedWriter bw = new BufferedWriter(fw);
			this.writer = new PrintWriter(bw);
		} catch (IOException e) {
			System.out.println("Couldn't open file for write: " + filePath);
			throw e;
		}
	}

	private void rotateFileLog() {
		this.fileCounter++;
		this.writer.flush();
		this.writer.close();
		this.writer = null;
		String rotatedFilePath = filePath.substring(0, filePath.length() - 4) + "-" + this.fileCounter + ".log";
		try {
			Files.move(Paths.get(filePath), Paths.get(rotatedFilePath));
			System.out.println("Rotated " + filePath + " to " + rotatedFilePath);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void loadFileCounter() {
		this.fileCounter = 0;
		File directory = new File(config.getDirectoryPath());
		File[] files = directory.listFiles();
		if (files != null) {
			Arrays.sort(files);
			Pattern patternMatcher = Pattern.compile(fileName + "-[0-9]*.log");
			Pattern patternExtractor = Pattern.compile((fileName + "-([0-9]*?).log").replace(".", "\\."));
			for (File file : files) {
				Matcher matcher = patternMatcher.matcher(file.getName());
				boolean matches = matcher.matches();
				if (matches) {
					int currentNumber = Auxiliary.getMatchedNumber(file.getName(), patternExtractor);
					if (currentNumber > this.fileCounter) {
						this.fileCounter = currentNumber;
					}
				}
			}
		}
//		System.out.println("found lastFile nr: " + this.fileCounter);
	}
}
