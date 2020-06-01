package multiplayer.server;

import multiplayer.configuration.Configuration;

import java.util.List;
import java.util.Map;

/**
 * Поток, отвечающий за серверную обработку сообщений
 * Подлежит реализации пользователем
 */
public abstract class ServerProcessingThread extends Thread {

    protected ServerProcessingThread(String name) {
        super(name);
    }

    @Override
    public void run() {
        long delta = 0;
        long startTime = System.currentTimeMillis();
        long prevCycleTime = 0;
        while (Server.getInstance().isStarted()) {
            prevCycleTime = System.currentTimeMillis() - startTime;
            startTime = System.currentTimeMillis();
            Map<Integer, List<String>> temp = Server.getInstance().getClientMessages();
            processMessages(temp, prevCycleTime / 1000f);
            delta = System.currentTimeMillis() - startTime;
            if (delta < Configuration.getInstance().getServerProcessingCycleTime()) {
                try {
                    sleep(Configuration.getInstance().getServerProcessingCycleTime() - delta);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Метод обработки сообщений сервером
     *
     * @param messages карта сообщений <Идентификатор клиента, Список сообщений>
     * @param delta    время, прошедшее с момента предыдущей обработки
     */
    protected abstract void processMessages(Map<Integer, List<String>> messages, float delta);
}
