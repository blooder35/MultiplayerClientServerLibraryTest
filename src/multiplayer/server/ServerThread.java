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
                        if (!clients.get(i).isActive()) {
                            newPlayerThread.setClientIdentifier(i);
                            clients.set(i, newPlayerThread);
                            newPlayerThread.start();
                            break;
                        }
                    }
                }

            } catch (IOException e) {
                //todo do nothing here just debug
//                System.out.println("Server accept hanged out of time");
            }
        }
        //todo check and delete
        System.out.println("SERVER STOPPED LISTENING FOR A NEW PLAYERS");
    }

    public void setListening(boolean listening) {
        this.listening.lazySet(listening);
    }

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
}
