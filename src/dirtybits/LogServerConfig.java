package dirtybits;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

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
		this.readConfigFile();
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

	private void readConfigFile() {
		FileInputStream in = null;

		try {
			in = new FileInputStream("config.txt");

			int c;
			while ((c = in.read()) != -1) {
				System.out.println(c);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}

