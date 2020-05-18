package multiplayer.server;

import multiplayer.configuration.Configuration;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Класс отвечающий за создание и хранение подключений пользователей
 * запуск и остановку сервера.
 */
public class Server {
    private static volatile Server instance = null;
    private String serverFullIp;
    private boolean started;
    private ServerThread serverThread;

    private Server() {
    }

    public static Server getInstance() {
        if (instance == null) {
            synchronized (Server.class) {
                if (instance == null) {
                    instance = new Server();
                }
            }
        }
        return instance;
    }

    public void start(int port) throws Exception {
        ServerSocket serverSocket = new ServerSocket(port);
        started = true;
        serverFullIp = serverSocket.getInetAddress() + ":" + serverSocket.getLocalPort();
        System.out.println("SocketServer started at " + serverSocket.getInetAddress() + ":" + serverSocket.getLocalPort());
        serverSocket.setSoTimeout(Configuration.getInstance().getSocketAcceptTimeout());
        serverThread = new ServerThread(serverSocket);
        serverThread.start();
    }

    public void stop() {
        serverThread.setListening(false);
        try {
            // закрываем сокет сервер в этом случае
            serverThread.serverSocket.close();
            started = false;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isStarted() {
        return started;
    }

    public String getServerFullIp() {
        return serverFullIp;
    }

    public Map<Integer, List<String>> getClientMessages() {
        Map<Integer, List<String>> map = new HashMap<>();
        for (ServerClientThread client : serverThread.getClients()) {
            map.put(client.getClientIdentifier(), client.getAndClearMessages());
        }
        return map;
    }

    void sendMessageToAll(String message) {
        for (ServerClientThread client : serverThread.getClients()) {
            client.sendMessage(message);
        }
    }
}
