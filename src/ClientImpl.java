import multiplayer.client.ClientMessage;

/**
 * Имплементация сообщения клиента, для тестирования
 */
public class ClientImpl extends ClientMessage {

    @Override
    protected String getMessageStringToSend() {
        return "1";
    }
}
