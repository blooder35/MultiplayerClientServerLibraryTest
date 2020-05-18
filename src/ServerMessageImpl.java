import multiplayer.server.ServerMessage;

public class ServerMessageImpl extends ServerMessage {
    @Override
    protected String getMessageStringToSend() {
        return "1";
    }
}
