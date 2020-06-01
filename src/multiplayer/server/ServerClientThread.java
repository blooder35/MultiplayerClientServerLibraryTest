package multiplayer.server;

import multiplayer.common.CommonMultiplayerThread;

import java.net.Socket;

/**
 * Поток сервера, отвечающий за получение и отправку сообщений для конкретного пользователя
 */
public class ServerClientThread extends CommonMultiplayerThread {
    private int clientIdentifier;

    /**
     * ctor
     * @param socket сокет клиента
     */
    public ServerClientThread(Socket socket) {
        super(socket, "ServerClientThread");
    }

    /**
     * ctor
     * @param socket сокет клиента
     * @param name название потока
     */
    public ServerClientThread(Socket socket, String name) {
        super(socket, name);
    }

    /**
     * @return идентификатор клиента
     */
    public int getClientIdentifier() {
        return clientIdentifier;
    }

    /**
     * Установить идентификатор клиента
     * @param clientIdentifier идентификатор клиента
     */
    public void setClientIdentifier(int clientIdentifier) {
        this.clientIdentifier = clientIdentifier;
    }
}
