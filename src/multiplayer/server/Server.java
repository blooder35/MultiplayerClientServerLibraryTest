package multiplayer.server;

import multiplayer.configuration.Configuration;

import java.io.IOException;
import java.net.InetSocketAddress;
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

    /**
     * @return экземпляр сервера
     */
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

    /**
     * Запуск серверной части многопользовательского приложения
     *
     * @param address ip адрес, на котором необходимо запустить сервер
     * @param port    порт, на котором необходимо запустить сервер
     * @throws Exception ошибка при запуске серверной части многопользовательского приложения
     */
    public void start(String address, int port) throws Exception {
        ServerSocket serverSocket = new ServerSocket();
        serverSocket.bind(new InetSocketAddress(address, port));
        serverFullIp = serverSocket.getInetAddress() + ":" + serverSocket.getLocalPort();
        System.out.println("SocketServer started at " + serverSocket.getInetAddress() + ":" + serverSocket.getLocalPort());
        serverSocket.setSoTimeout(Configuration.getInstance().getSocketAcceptTimeout());
        serverThread = new ServerThread(serverSocket);
        serverThread.start();
        started = true;
    }

    /**
     * Остановить серверную часть многопользовательского приложения
     */
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

    /**
     * @return признак того, запущена ли в текущий момент серверная часть многопользовательского приложения
     */
    public boolean isStarted() {
        return started;
    }

    /**
     * @return полный ip адрес сервера
     */
    public String getServerFullIp() {
        return serverFullIp;
    }

    /**
     * Получение сообщений полученных от клиентов.
     * Внимание! После каждого вызова метода выполняется очистка очереди сообщений
     *
     * @return карта сообщений, полученных клиентов, ключом карты является идентификато подключенного клиента
     */
    public Map<Integer, List<String>> getClientMessages() {
        Map<Integer, List<String>> map = new HashMap<>();
        for (ServerClientThread client : serverThread.getClients()) {
            map.put(client.getClientIdentifier(), client.getAndClearMessages());
        }
        return map;
    }

    /**
     * Отправка сообщений всем подключенным клиентам
     *
     * @param message текст сообщения
     */
    void sendMessageToAll(String message) {
        for (int i = 0; i < serverThread.getClients().size(); i++) {
            serverThread.getClients().get(i).sendMessage(message);
        }
    }

    /**
     * Отправка собщения конкретному клиенту по его идетнификатору
     *
     * @param index   идентификатор клиента
     * @param message сообщение
     */
    void sendMessageTo(int index, String message) {
        serverThread.getClients().get(index).sendMessage(message);
    }
}
