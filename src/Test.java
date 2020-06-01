import multiplayer.client.Client;
import multiplayer.configuration.Configuration;
import multiplayer.server.Server;

/**
 * Основной класс, для запуска тестирования
 * Если передан параметр запуска "server", то запустится локальный сервер.
 * При запуске без параметра запустится клиент
 */
public class Test {
    public static void main(String[] args) throws Exception {
        Configuration.getInstance().setServerProcessingCycleTime(5);
        if (args.length != 0) {
            if (args[0].equals("server")) {
                Server.getInstance().start("localhost", 44);
                ServerProcessor serverProcessor = new ServerProcessor();
                serverProcessor.start();
                while (true) {
                    Thread.sleep(1000);
                }
            }
        } else {
            Client.getInstance().start("localhost", 44);
            long i = 0;
            while (true) {
                new ClientImpl().sendMessageToServer();
                for (String message : Client.getInstance().getServerMessages()) {
                    i += Integer.parseInt(message);
                    if (i % 1000 == 0) {
                        System.out.println("Client  is running, got " + i + " messages");
                    }
                }
                Thread.sleep(1);
            }
        }
    }
}

