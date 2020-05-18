package multiplayer.server;

import multiplayer.configuration.Configuration;

public abstract class ServerMessage {
    public void sendMessageToAll() {
        Server.getInstance().sendMessageToAll(getMessageStringToSend() + Configuration.getInstance().getMessageEscapeCharacter());
    }

    protected abstract String getMessageStringToSend();
}
