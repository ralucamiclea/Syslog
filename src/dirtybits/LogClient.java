package dirtybits;

import java.util.Date;

public class LogClient {
	
	private String clientName;
	private LogServer server;
	private boolean isRegistered;
	
	public LogClient(String name, LogServer server) {
		this.clientName = name;
		this.server = server;
		this.isRegistered = false;
		this.register();
	}

	public boolean sendLog(String message, LogLevel level, Date date) {
		if(this.isRegistered) {
			LogMessage msg = new LogMessage(level, message, this.clientName, date);
			server.registerLog(this, msg);
			return true;
		} else {
			System.out.println("Log cannot be sent - client is not registered on server.");
			return false;
		}
	}
	
	private boolean register() {
		if (server.registerClient(this)) {
			this.isRegistered = true;
			return true;
		} else {
			System.out.println("Client was not registered on server.");
			return false;
		}
	}

	public boolean isRegistered() {
		return isRegistered;
	}

	public String getName() {
		return this.clientName;
	}
}
