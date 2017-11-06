package dirtybits;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LogServer {
	
	private Map<LogThread, String> threads;
	private List<LogClient> clients;
	private LogServerConfig config;
	
	public LogServer() {
		threads = new HashMap<LogThread, String>();
		clients = new ArrayList<LogClient>();
		config = new LogServerConfig();
		createFiles();
	}
	
	private void createFiles(){
		//TODO: create log files according to the config file
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
	
	public void registerLog(LogMessage log) {
		//TODO
	}
	
	private LogThread getThreadFor(String fileName){
		//TODO
		return new LogThread();
	}
}
