package dirtybits;

import java.util.Date;

public class LogMessage {
	private LogLevel level;
	private String message;
	private String client;
	private Date date;

	public LogMessage(LogLevel level, String message, String client, Date date) {
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

	public void setMessage(String message) {
		this.message = message;
	}

	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
	}

	public Date getDate() {
		return date;
	}
}
