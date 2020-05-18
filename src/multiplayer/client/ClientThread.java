package multiplayer.client;

import multiplayer.common.CommonMultiplayerThread;

import java.net.Socket;
import java.net.SocketException;

public class ClientThread extends CommonMultiplayerThread {

    public ClientThread(Socket socket) throws SocketException {
        super(socket, "ClientThread");
    }
}
