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
		config = new LogServerConfig();
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
			return false;
		
		String filename;
		try {
			filename = Auxiliary.getFileName(config, log);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		LogThread thread = getThreadFor(filename);
		
		if(thread != null)
			thread.enqueueMessage(log);
		else
			return false; //could not create thread
		
		return true;
	}
	
	private LogThread getThreadFor(String fileName){
		//TODO: search for the corresponding thread in the HashMap
		//if the thread doesn't exist, create it;
		return new LogThread();
	}
	
	private LogThread addThreadFor(String fileName){
		//TODO: create a new thread for the corresponding fileName
		return new LogThread();
	}
	
	/*Checks whether a client is registered or not.*/
	private boolean clientExists(LogClient client){
		if(!clients.contains(client))
			return false;
		return true;
	}
}
