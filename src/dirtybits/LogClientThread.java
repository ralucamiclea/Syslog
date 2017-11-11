package dirtybits;

import java.util.Date;
import java.util.Random;

public class LogClientThread implements Runnable {
    private LogServer server;
    private int delay;
    private int number;
    private String clientName;
    private Thread thread = null;

    public LogClientThread(LogServer _server, String _clientName, int _delay, int _number) {
        server = _server;
        delay = _delay;
        number = _number;
        clientName = _clientName;
    }

    private boolean running = false;

    public void start() {
        if (running) {
            return;
        }
        thread = new Thread(this);
        thread.setName("Thread for client: " + clientName);
        thread.start();
    }

    public void stop() {
        if (!running || thread == null) {
            return;
        }
        thread.interrupt();
    }

    @Override
    public void run() {
        long threadId = Thread.currentThread().getId();
        System.out.println("[LogClientThread][start][" + threadId + "]");
        long threadNumber = Thread.currentThread().getId();
        LogClient client = new LogClient(clientName, server);
        running = true;
        while (number-- > 0 && running) {
            String message = "Log from thread: #" + threadNumber + "  <<";
            LogLevel level = LogLevel.values()[random.nextInt(5)];
            client.sendLog(message, level, new Date());
            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                break;
            }
        }
        running = false;
        thread = null;
        System.out.println("[LogClientThread][stop][" + threadId + "]");
    }

    private Random random = new Random();
}
