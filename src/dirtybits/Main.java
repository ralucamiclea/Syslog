package dirtybits;

import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        LogServer server = new LogServer();
        ArrayList<LogClientThread> clients = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);

        System.out.println("before running...");
        scanner.nextInt();

        for (int i = 0; i < 20; ++i) {
            int clientNr = i / 2 + 1;
            LogClientThread clientThread = new LogClientThread(server, "client #" + clientNr, 2, 5000);
            clientThread.start();
            clients.add(clientThread);
        }

        System.out.println("running...");
        scanner.nextInt();

        System.out.println("stopping...");
        server.stop();
        clients.forEach(LogClientThread::stop);

        System.out.println("main thread done...");
    }
}
