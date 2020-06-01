package multiplayer.server;

import multiplayer.configuration.Configuration;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Поток сервера отвечающий за подключение новых клиентов и создание соединения.
 */
public class ServerThread extends Thread {
    private AtomicBoolean listening;
    private List<ServerClientThread> clients;
    ServerSocket serverSocket;

    public ServerThread(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
        this.listening = new AtomicBoolean(true);
        this.clients = new ArrayList<>();
    }

    @Override
    public void run() {
        while (listening.get() && (clients.size() < Configuration.getInstance().getMaximumAllowedClients() || isSpaceAvailable(clients))) {
            try {
                ServerClientThread newPlayerThread = new ServerClientThread(serverSocket.accept());
                if (clients.size() < Configuration.getInstance().getMaximumAllowedClients()) {
                    newPlayerThread.setClientIdentifier(clients.size());
                    clients.add(newPlayerThread);
                    newPlayerThread.start();
                } else {
                    for (int i = 0; i < Configuration.getInstance().getMaximumAllowedClients(); i++) {
                        if (clients.get(i) != null || !clients.get(i).isActive()) {
                            newPlayerThread.setClientIdentifier(i);
                            clients.set(i, newPlayerThread);
                            newPlayerThread.start();
                            break;
                        }
                    }
                }

            } catch (IOException e) {
                checkInactiveClients();
            }
        }
        System.out.println("SERVER STOPPED LISTENING FOR A NEW PLAYERS");
    }

    /**
     * Установить значение того, что сервер ожидает новых подключений
     *
     * @param listening true\false
     */
    public void setListening(boolean listening) {
        this.listening.lazySet(listening);
    }

    /**
     * Получить список текущих клиентов сервера
     *
     * @return список потоков клиентов сервера
     */
    public List<ServerClientThread> getClients() {
        return clients;
    }

    private boolean isSpaceAvailable(List<ServerClientThread> clients) {
        for (int i = Configuration.getInstance().getMaximumAllowedClients() - 1; i >= 0; i--) {
            if (!clients.get(i).isActive()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Метод проверки активности клиентов, замещающий неактивных клиентов с помощью пустого клиента
     */
    private void checkInactiveClients() {
        for (int i = 0; i < clients.size(); i++) {
            if (!clients.get(i).isActive()) {
                clients.get(i).interrupt();
                clients.set(i, new EmptyServerClientThread());
            }
        }
    }
}
