package multiplayer.client;

import multiplayer.configuration.Configuration;

/**
 * Абстрактный класс сообщения клиента
 * Подлежит реализации пользователем
 */
public abstract class ClientMessage {
    /**
     * Отправить сообщение серверу
     */
    public void sendMessageToServer() {
        Client.getInstance().sendMessage(getMessageStringToSend() + Configuration.getInstance().getMessageEscapeCharacter());
    }

    /**
     * @return строка - которая будет отправлена на сервер
     */
    protected abstract String getMessageStringToSend();
}
