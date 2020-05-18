import multiplayer.client.Client;
import multiplayer.server.Server;

public class Test {
    public static void main(String[] args) throws Exception {
        if (args.length != 0) {
            if (args[0].equals("server")) {
                Server.getInstance().start(44);
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
                    if (i % 100 == 0) {
                        System.out.println("Client is running, got " + i + " messages");
                    }
                }
                Thread.sleep(5);
            }

        }
    }
}

