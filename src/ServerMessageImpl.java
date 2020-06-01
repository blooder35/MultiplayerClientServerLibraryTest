import multiplayer.server.ServerMessage;

/**
 * Имплементация серверного сообщения, для тестирования
 */
public class ServerMessageImpl extends ServerMessage {
    @Override
    protected String getMessageStringToSend() {
        return "1";
    }
}
