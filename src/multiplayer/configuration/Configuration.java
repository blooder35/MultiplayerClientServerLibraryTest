package multiplayer.configuration;

public class Configuration {
    private static Configuration instance;
    private char messageEscapeCharacter;
    private int maximumAllowedClients;
    private int maximumMessagesQueueLength;
    private int socketAcceptTimeout;
    private int socketReadTimeout;
    private long serverProcessingCycleTime;

    private Configuration() {
        messageEscapeCharacter = ServerConstants.MESSAGE_ESCAPE_CHARACTER;
        maximumAllowedClients = ServerConstants.MAXIMUM_ALLOWED_CLIENTS;
        maximumMessagesQueueLength = ServerConstants.MAXIMUM_MESSAGES_QUEUE_LENGTH;
        socketAcceptTimeout = ServerConstants.SOCKET_ACCEPT_TIMEOUT;
        socketReadTimeout = ServerConstants.SOCKET_READ_TIMEOUT;
        serverProcessingCycleTime = ServerConstants.SERVER_PROCESSING_CYCLE_TIME;
    }

    public static Configuration getInstance() {
        if (instance == null) {
            synchronized (Configuration.class) {
                if (instance == null) {
                    instance = new Configuration();
                }
            }
        }
        return instance;
    }

    public char getMessageEscapeCharacter() {
        return messageEscapeCharacter;
    }

    public void setMessageEscapeCharacter(char messageEscapeCharacter) {
        this.messageEscapeCharacter = messageEscapeCharacter;
    }

    public int getMaximumAllowedClients() {
        return maximumAllowedClients;
    }

    public void setMaximumAllowedClients(int maximumAllowedClients) {
        this.maximumAllowedClients = maximumAllowedClients;
    }

    public int getMaximumMessagesQueueLength() {
        return maximumMessagesQueueLength;
    }

    public void setMaximumMessagesQueueLength(int maximumMessagesQueueLength) {
        this.maximumMessagesQueueLength = maximumMessagesQueueLength;
    }

    public int getSocketAcceptTimeout() {
        return socketAcceptTimeout;
    }

    public void setSocketAcceptTimeout(int socketAcceptTimeout) {
        this.socketAcceptTimeout = socketAcceptTimeout;
    }

    public int getSocketReadTimeout() {
        return socketReadTimeout;
    }

    public void setSocketReadTimeout(int socketReadTimeout) {
        this.socketReadTimeout = socketReadTimeout;
    }

    public long getServerProcessingCycleTime() {
        return serverProcessingCycleTime;
    }

    public void setServerProcessingCycleTime(long serverProcessingCycleTime) {
        this.serverProcessingCycleTime = serverProcessingCycleTime;
    }
}
