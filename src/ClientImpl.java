import multiplayer.client.ClientMessage;

public class ClientImpl extends ClientMessage {

    @Override
    protected String getMessageStringToSend() {
        return "1";
    }
}
