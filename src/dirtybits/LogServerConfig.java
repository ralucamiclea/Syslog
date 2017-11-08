package dirtybits;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class LogServerConfig {
	private boolean includeLevel;
	private boolean includeClientName;
	private int maxClients;
	private String directoryPath;

	private LogServerConfig() {
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
		try (BufferedReader br = new BufferedReader(new FileReader("config.txt"))) {
		    String line;
		    while ((line = br.readLine()) != null) {
		       String[] array = line.split("="); 
		       switch(array[0]) {
		       case "includeClientName": {
		    	   this.includeClientName = Boolean.parseBoolean(array[1]);
		    	   break;
		       }
		       case "includeLevel": {
		    	   this.includeLevel = Boolean.parseBoolean(array[1]);
		    	   break;
		       }
		       case "maxClients": {
		    	   this.maxClients = Integer.parseInt(array[1]);
		    	   break;
		       }
		       case "directoryPath": {
		    	   this.directoryPath = array[1];
		    	   break;
		       }
		       }
		    }
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public String toString() {
		return "LogServerConfig [includeLevel=" + includeLevel + ", includeClientName=" + includeClientName
				+ ", maxClients=" + maxClients + ", directoryPath=" + directoryPath + "]";
	}
	
	
}

