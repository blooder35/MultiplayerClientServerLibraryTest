package multiplayer.client;

import java.net.Socket;
import java.util.List;

/**
 * Основной класс многопользовательского клиента
 */
public final class Client {
    private static volatile Client instance = null;
    private ClientThread clientThread = null;
    private boolean started;

    private Client() {
    }

    /**
     * @return экземпляр класса
     */
    public static Client getInstance() {
        if (instance == null) {
            synchronized (Client.class) {
                if (instance == null) {
                    instance = new Client();
                }
            }
        }
        return instance;
    }

    /**
     * Запуск клиентской части многопользовательского приложения
     *
     * @param address ip адрес сервера, к которому происходит подключение
     * @param port    порт сервера, к которому происходит подключение
     * @throws Exception ошибка при подключении к серверу
     */
    public void start(String address, int port) throws Exception {
        clientThread = new ClientThread(new Socket(address, port));
        clientThread.start();
        started = true;
    }

    /**
     * Остановка клиентской части многопользовательского приложения
     */
    public void stop() {
        started = false;
        clientThread.setActive(false);
        clientThread.interrupt();
    }

    /**
     * @return признак того, запущена ли в текущий момент клиентская часть многопользовательского приложения
     */
    public boolean isStarted() {
        return started;
    }

    /**
     * Получение сообщений полученных от сервера.
     * Внимание! После каждого вызова метода выполняется очистка очереди сообщений, пришедшей от сервера
     *
     * @return список сообщений, полученных от сервера
     */
    public List<String> getServerMessages() {
        return clientThread.getAndClearMessages();
    }

    /**
     * Отправить сообщение серверу
     *
     * @param message сообщение в виде строки
     */
    protected void sendMessage(String message) {
        clientThread.sendMessage(message);
    }
}
