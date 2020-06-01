package multiplayer.server;

import multiplayer.configuration.Configuration;

/**
 * Абстрактный класс сообщения сервера
 * Подлежит реализации пользователем
 */
public abstract class ServerMessage {
    /**
     * Отправить сообщение всем подключенным клиентам
     */
    public void sendMessageToAll() {
        Server.getInstance().sendMessageToAll(getMessageStringToSend() + Configuration.getInstance().getMessageEscapeCharacter());
    }

    /**
     * Отправить сообщение по идентификатору клиента
     * @param index идентификатор клиента
     */
    public void sendMessageTo(int index) {
        Server.getInstance().sendMessageTo(index, getMessageStringToSend() + Configuration.getInstance().getMessageEscapeCharacter());
    }

    /**
     * @return строка - которая будет отправлена клиенту
     */
    protected abstract String getMessageStringToSend();
}
