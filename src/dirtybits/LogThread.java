package dirtybits;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class LogThread implements Runnable {

    private Thread thread;
    private ConcurrentLinkedQueue<LogMessage> messageQueue = new ConcurrentLinkedQueue<>();
    private PrintWriter writer;
    private String fileName;
    private LogServerConfig config;

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
        writer.flush();
    }

    public void enqueueMessage(LogMessage log) {
        System.out.println("[LogThread][enqueueMessage]:" + log.getMessage());
        messageQueue.add(log);
    }

    public void run() {
        System.out.println("[LogThread][run]");
        while (true) {
            System.out.println("[LogThread][run][while]");
            if (messageQueue.isEmpty()) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException exc) {
                    System.out.println("InterruptedException thrown when Thread.sleep.");
                    return;
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
    }

    public void start() {
        this.thread = (new Thread(this));
        this.thread.start();
    }

    public void stop() {
        if (this.writer != null) {
            this.writer.flush();
            this.writer.close();
            this.writer = null;
        }
        if (this.thread != null) {
            this.thread.interrupt();
            this.thread = null;
        }
    }


    private void checkAndCreateWriter() throws IOException {
        if (this.writer != null) {
            return;
        }
        String filePath = System.getProperty("user.dir") + "\\" + config.getDirectoryPath() + "\\" + fileName;
        System.out.println("Writing to file: " + filePath);
        try {
        	File file = new File(filePath);
        	file.getParentFile().mkdirs();
        	FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            this.writer = new PrintWriter(bw);
        } catch (IOException e) {
            System.out.println("Couldn't open file for write: " + filePath);
            throw e;
        }
    }
}
