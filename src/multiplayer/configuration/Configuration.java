package multiplayer.configuration;

/**
 * Конфигурационный класс многопользовательской библиотеки
 */
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

    /**
     * @return экземпляр конфигурационного класса
     */
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

    /**
     * @return символ-разделитель сообщений
     */
    public char getMessageEscapeCharacter() {
        return messageEscapeCharacter;
    }

    /**
     * Установить символ-разделитель сообщений
     * @param messageEscapeCharacter символ-разделитель
     */
    public void setMessageEscapeCharacter(char messageEscapeCharacter) {
        this.messageEscapeCharacter = messageEscapeCharacter;
    }

    /**
     * @return максимально возможное количество подключений к серверу
     */
    public int getMaximumAllowedClients() {
        return maximumAllowedClients;
    }

    /**
     * Установить максимально возможное количество подключений к серверу
     * @param maximumAllowedClients число допустимых клиентов
     */
    public void setMaximumAllowedClients(int maximumAllowedClients) {
        this.maximumAllowedClients = maximumAllowedClients;
    }

    /**
     * @return максимальная длина очереди сообщений Сервера\Клиента
     */
    public int getMaximumMessagesQueueLength() {
        return maximumMessagesQueueLength;
    }

    /**
     * Установить максимальную длину очереди сообщений Сервера\Клиента
     * @param maximumMessagesQueueLength максимальное число сообщений в очереди
     */
    public void setMaximumMessagesQueueLength(int maximumMessagesQueueLength) {
        this.maximumMessagesQueueLength = maximumMessagesQueueLength;
    }

    /**
     * @return таймаут ожидания новых подключений сервера (частота вызова кода, проверки отключения сервера)
     */
    public int getSocketAcceptTimeout() {
        return socketAcceptTimeout;
    }

    /**
     * Установить таймаут ожидания новых подключений сервера (частота вызова кода, проверки отключения сервера)
     * @param socketAcceptTimeout таймаут в милисекундах
     */
    public void setSocketAcceptTimeout(int socketAcceptTimeout) {
        this.socketAcceptTimeout = socketAcceptTimeout;
    }

    /**
     * @return таймаут ожидания новых сообщений Сервером\Клиентом (частота вызова кода, проверки завершения потока Сервера\Клиента)
     */
    public int getSocketReadTimeout() {
        return socketReadTimeout;
    }

    /**
     * Установить таймаут ожидания новых сообщений Сервером\Клиентом (частота вызова кода, проверки завершения потока Сервера\Клиента)
     * @param socketReadTimeout таймаут в милисекундах
     */
    public void setSocketReadTimeout(int socketReadTimeout) {
        this.socketReadTimeout = socketReadTimeout;
    }

    /**
     * @return время цикла обработки сообщений сервером
     */
    public long getServerProcessingCycleTime() {
        return serverProcessingCycleTime;
    }

    /**
     * Установить время цикла обработки сообщений сервером
     * @param serverProcessingCycleTime время в милисекундах
     */
    public void setServerProcessingCycleTime(long serverProcessingCycleTime) {
        this.serverProcessingCycleTime = serverProcessingCycleTime;
    }
}
