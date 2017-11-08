package dirtybits;

import java.io.IOException;
import java.io.UnsupportedEncodingException;


public class Auxiliary {
	
	//Creates log fileNames according to the config file
	public static String formatFileName(LogServerConfig config, String numeClient, String numeLevel) throws IOException, UnsupportedEncodingException{
				String fileName = null;
		
		if(config.isIncludeClientName() && config.isIncludeLevel()) {
			fileName = numeClient + numeLevel;
		}
		else 
			if(config.isIncludeClientName() && !config.isIncludeLevel()) {
				fileName = numeClient;
			}
			else
				if(!config.isIncludeClientName() && config.isIncludeLevel()){
					fileName = numeLevel;
				}
		
		return fileName + ".txt";
	}
	
	public static String getFileName(LogServerConfig config, LogMessage log) throws UnsupportedEncodingException, IOException {
		String numeClient = log.getClient();
		String numeLevel = log.getLevel().toString();
		return formatFileName(config, numeClient, numeLevel);
	}
}
