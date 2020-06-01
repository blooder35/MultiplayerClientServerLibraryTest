package multiplayer.common;

import multiplayer.configuration.Configuration;
import multiplayer.util.CommunicationHelper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Общий класс, отвечающий за получение и отправку сообщений при многопользовательском взаимодействии
 */
public class CommonMultiplayerThread extends Thread {
    private Socket socket;
    private AtomicBoolean active;
    private Queue<String> messages;
    private PrintWriter out;

    protected CommonMultiplayerThread(Socket socket, String threadName) {
        super(threadName);
        this.socket = socket;
        active = new AtomicBoolean(true);
        messages = new ConcurrentLinkedQueue<>();
        try {
            out = new PrintWriter(socket.getOutputStream(), true);
            this.socket.setSoTimeout(Configuration.getInstance().getSocketReadTimeout());
        } catch (IOException e) {
            active = new AtomicBoolean(false);
        }
    }

    @Override
    public void run() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            while (active.get()) {
                try {
                    String clientMessage = CommunicationHelper.readMessage(in);
                    appendClientMessage(clientMessage);
                } catch (SocketTimeoutException e) {
//              Ничего не выполняем, обработка прерывания при чтении не требуется
                }
            }
        } catch (IOException e) {
            active.set(false);
            e.printStackTrace();
        }
    }

    /**
     * @return признак того, является ли текущий поток активным
     */
    public boolean isActive() {
        return active.get();
    }

    /**
     * Установить параметр активности, для потока
     *
     * @param active активен ли поток
     */
    public void setActive(boolean active) {
        this.active.set(active);
    }

    /**
     * Синхронный метод получения сообщений.
     * После получения списка сообщений, они удаляются из очереди
     *
     * @return список полученных сообщений.
     */
    public synchronized List<String> getAndClearMessages() {
        List<String> tmp = new LinkedList<>();
        for (int i = 0; i < messages.size(); i++) {
            tmp.add(messages.remove());
        }
        return tmp;
    }

    /**
     * Отправить сообщение
     *
     * @param message сообщение в виде строки
     */
    public void sendMessage(String message) {
        out.print(message);
        if (out.checkError()) {
            active.set(false);
        }
    }

    protected void appendClientMessage(String clientMessage) {
        if (messages.size() < Configuration.getInstance().getMaximumMessagesQueueLength()) {
            System.out.println(this.getName() + "Received message from user");
            messages.add(clientMessage);
        } else {
            System.err.println(this.getName() + "Error while adding client message to stack");
        }
    }
}
