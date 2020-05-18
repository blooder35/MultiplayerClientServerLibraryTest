package multiplayer.client;

import java.net.Socket;
import java.util.List;

public class Client {
    private static volatile Client instance = null;
    private ClientThread clientThread = null;
    private boolean started;

    private Client() {
    }

    public static Client getInstance() {
        if (instance == null) {
            synchronized (Client.class) {
                if (instance == null) {
                    instance = new Client();
                }
            }
        }
        return instance;
    }

    public void start(String address, int port) throws Exception {
        clientThread = new ClientThread(new Socket(address, port));
        clientThread.start();
        started = true;
    }

    public void stop() {
        started = false;
        clientThread.setActive(false);
        clientThread.interrupt();
    }

    public boolean isStarted() {
        return started;
    }

    public List<String> getServerMessages() {
        return clientThread.getAndClearMessages();
    }

    protected void sendMessage(String message) {
        clientThread.sendMessage(message);
    }
}
