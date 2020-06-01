package multiplayer.util;

import multiplayer.configuration.Configuration;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * Класс-помощник, для чтения сообщений из сокета
 */
public class CommunicationHelper {

    /**
     * Прочитать сообщение из буфера, учитывая разделитель
     *
     * @param in буффер сообщений
     * @return Строка - сообщение
     * @throws IOException Ошибка при чтении сообщения из буфера
     */
    public static String readMessage(BufferedReader in) throws IOException {
        StringBuilder sb = new StringBuilder();
        int value;
        while ((value = in.read()) != -1) {
            if (value == (int) Configuration.getInstance().getMessageEscapeCharacter()) {
                in.mark(0);
                in.reset();
                return sb.toString();
            }
            sb.append((char) value);
        }
        throw new IOException("Client closed the connection");
    }
}
