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
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.atomic.AtomicBoolean;

public class CommonMultiplayerThread extends Thread {
    private Socket socket = null;
    private AtomicBoolean active;
    private Queue<String> messages;
    private PrintWriter out;

    public CommonMultiplayerThread(Socket socket, String threadName) {
        super(threadName);
        this.socket = socket;
        active = new AtomicBoolean(true);
        messages = new ConcurrentLinkedDeque<>();
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
//                    System.out.println(this.getClass() + " readingMessage");
                }
            }
        } catch (IOException e) {
            active.set(false);
            e.printStackTrace();
        }
    }

    public boolean isActive() {
        return active.get();
    }

    public void setActive(boolean active) {
        this.active.set(active);
    }

    public synchronized List<String> getAndClearMessages() {
        //todo нужно подумать над синхронизацией вызова к хранилищу сообщений
        //todo remove trycatch
        List<String> tmp = new LinkedList<>();
        try {
            tmp.addAll(messages);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("DEBUG");
        }
        messages.clear();
        return tmp;
    }

    public void sendMessage(String message) {
        out.print(message);
        out.flush();
    }

    protected void appendClientMessage(String clientMessage) {
        if (messages.size() < Configuration.getInstance().getMaximumMessagesQueueLength()) {
//            System.out.println(this.getName() + "Received message from user");
            messages.add(clientMessage);
        } else {
            System.err.println(this.getName()+ "Error while adding client message to stack");
        }
    }
}
