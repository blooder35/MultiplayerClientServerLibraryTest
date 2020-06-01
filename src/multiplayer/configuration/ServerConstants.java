package multiplayer.configuration;

/**
 * Класс, содержащий значения настроек по умолчанию
 */
class ServerConstants {
    private ServerConstants() {

    }

    public static final char MESSAGE_ESCAPE_CHARACTER = '&';
    public static final int MAXIMUM_ALLOWED_CLIENTS = 1000;
    public static final int MAXIMUM_MESSAGES_QUEUE_LENGTH = 1000;
    public static final int SOCKET_ACCEPT_TIMEOUT = 1000;
    public static final int SOCKET_READ_TIMEOUT = 1000;
    public static final long SERVER_PROCESSING_CYCLE_TIME = 17;
}
