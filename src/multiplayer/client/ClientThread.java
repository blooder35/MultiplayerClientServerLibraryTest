package multiplayer.client;

import multiplayer.common.CommonMultiplayerThread;

import java.net.Socket;
import java.net.SocketException;

/**
 * Поток клиента, отвечающий за получение и отправку сообщений
 */
public final class ClientThread extends CommonMultiplayerThread {

    /**
     * ctor
     */
    public ClientThread(Socket socket) throws SocketException {
        super(socket, "ClientThread");
    }
}
