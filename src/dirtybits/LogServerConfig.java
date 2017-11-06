package dirtybits;

public class LogServerConfig {
	private boolean includeLevel;
	private boolean includeClientName;
	private int maxClients;
	private String directoryPath;
	
	public LogServerConfig() {
		this.includeLevel = true;
		this.includeClientName = false;
		this.maxClients = 10;
		this.directoryPath = "C:/sdfsd/sdfdsf";
	}
	
	public static LogServerConfig getConfig() {
		return new LogServerConfig();
	}
	
	public int getMaxClients() {
		return maxClients;
	}

	public boolean isIncludeClientName() {
		return includeClientName;
	}

	public boolean isIncludeLevel() {
		return includeLevel;
	}

	public String getDirectoryPath() {
		return directoryPath;
	}
}
