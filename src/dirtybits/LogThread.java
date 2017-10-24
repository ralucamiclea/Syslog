package dirtybits;

import java.io.FileWriter;
import java.util.Queue;

public class LogThread implements Runnable{
	
	private Thread thread;
	private Queue<LogMessage> messageQueue;
	private FileWriter writer;

	public LogThread() {
		//TODO
	}
	
	private void writeMessageToFile(LogMessage message) {
		//TODO
	}
	
	public void enqueueMessage(LogMessage message) {
		//TODO
	}
	
	public void run() {
		//TODO
	}
	
	public void start() {
		//TODO
	}
}
