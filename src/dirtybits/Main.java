package dirtybits;

import java.util.ArrayList;
import java.util.Date;

public class Main {

	public static void main(String[] args) {
		LogServer server = new LogServer();
		ArrayList<LogClient> clients = new ArrayList<LogClient>();
		for(int i = 0; i < 10; ++i) {
			clients.add(new LogClient("client" + i, server));
			clients.get(i).sendLog("mesajDEBUG"+i, LogLevel.DEBUG, new Date());
			clients.get(i).sendLog("mesajCRITICAL"+i, LogLevel.CRITICAL, new Date());
		}
		
	}
}
