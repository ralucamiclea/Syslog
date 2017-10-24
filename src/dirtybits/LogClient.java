package dirtybits;

import java.util.Date;

public class LogClient {
	
	private String clientName;
	public Object server;
	
	public LogClient(String name, Object server) {
		this.clientName = name;
		this.server = server;
	}

	
	public void sendLog(String message, LogLevel level, Date date) {
		LogMessage msg = new LogMessage(level, message, this.clientName, date);
	}
	
	private void register() {
		//server.registerClient(this);
	}
	
	public boolean isRegistered() {
		return true;
	}
}
