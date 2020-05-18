package multiplayer.client;

import multiplayer.configuration.Configuration;

public abstract class ClientMessage {
    public void sendMessageToServer() {
        Client.getInstance().sendMessage(getMessageStringToSend() + Configuration.getInstance().getMessageEscapeCharacter());
    }

    protected abstract String getMessageStringToSend();
}
