package dirtybits;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LogServer {
	
	private Map<String,LogThread> threads;
	private List<LogClient> clients;
	private LogServerConfig config;
	
	public LogServer() {
		threads = new HashMap<String,LogThread>();
		clients = new ArrayList<LogClient>();
		config = LogServerConfig.getConfig();
	}
	
	/*Adds a new client to the list as long as the max number of clients
	 * is smaller than the one set in the config file. Upon success it
	 * returns true, otherwise it returns false.*/
	public boolean registerClient(LogClient client) {
		if (config.getMaxClients() <= clients.size())
			return false;
		clients.add(client);
		return true;
	}
	
	/*Registers a new log for an existing client.
	 * Upon success return true.
	 * If the client does not exist, returns false.
	 * Upon failure, returns false.
	 * */
	public boolean registerLog(LogClient client, LogMessage log) {
		if(!clientExists(client))
			return false; //client does not exist
		
		String filename;
		try {
			filename = Auxiliary.getFileName(config, log);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return false; //could not create fileName
		} catch (IOException e) {
			e.printStackTrace();
			return false; //could not create fileName
		}
		LogThread thread = getThreadFor(filename);
		
		if(thread != null)
			thread.enqueueMessage(log);
		else
			return false; //could not create thread
		
		return true;
	}
	
	/*Looks for corresponding thread in the HashMap.
	 * Returns the thread associated with a particular fileName.
	 * If the thread doesn't exist, it creates one.*/
	private LogThread getThreadFor(String fileName){
		LogThread thread = threads.get(fileName);
		
		if(thread == null){ //key might not exists or value might be null
			thread = new LogThread(fileName);
			threads.put(fileName, thread); //put a value in that key
		}
		
		return thread;
	}
	
	/*Checks whether a client is registered or not.*/
	private boolean clientExists(LogClient client){
		if(!clients.contains(client))
			return false;
		return true;
	}
}
