package dirtybits;

import java.util.Date;

public class LogMessage {
	private LogLevel level;
	private String message;
	private LogClient client;
	private Date date;

	public LogMessage(LogLevel level, String message, LogClient client, Date date) {
		this.level = level;
		this.message = message;
		this.client = client;
		this.date = date;
	}

	public LogLevel getLevel() {
		return level;
	}

	public String getMessage() {
		return message;
	}

	public LogClient getClient() {
		return client;
	}

	public Date getDate() {
		return date;
	}
}
