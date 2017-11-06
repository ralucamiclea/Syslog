package dirtybits;

public class LogServerConfig {
	private boolean includeLevel;
	private boolean includeClientName;
	private int maxClients;
	
	public LogServerConfig() {
		this.includeLevel = true;
		this.includeClientName = false;
		this.maxClients = 10;
	}
	
	public static LogServerConfig getConfig() {
		return new LogServerConfig();
	}
	
	public int getMaxClients() {
		return maxClients;
	}
}
