package multiplayer.server;

import java.util.List;

/**
 * Класс - являющийся пустым клиентом.
 */
public final class EmptyServerClientThread extends ServerClientThread {
    public EmptyServerClientThread() {
        super(null, "EmptyClientThread");
    }

    @Override
    public int getClientIdentifier() {
        return super.getClientIdentifier();
    }

    @Override
    public void setClientIdentifier(int clientIdentifier) {
        super.setClientIdentifier(clientIdentifier);
    }

    @Override
    public void run() {
    }

    @Override
    public boolean isActive() {
        return false;
    }

    @Override
    public synchronized List<String> getAndClearMessages() {
        return null;
    }

    @Override
    public void sendMessage(String message) {
    }

    @Override
    protected void appendClientMessage(String clientMessage) {
    }
}
