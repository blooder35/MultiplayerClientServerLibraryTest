import multiplayer.server.ServerProcessingThread;

import java.util.List;
import java.util.Map;

public class ServerProcessor extends ServerProcessingThread {
    long a;
    public ServerProcessor(){
        super("PROCESSOR");
    }

    @Override
    protected void processMessages(Map<Integer, List<String>> messages, float delta) {
        for (Map.Entry<Integer, List<String>> entry : messages.entrySet()) {
            for (String message : entry.getValue()) {
                a += Integer.parseInt(message);
                if (a % 100 == 0) {
                    System.out.println("messageCount:" + a + "NumberOfClients:"+messages.entrySet().size());
                }
            }
        }
        new ServerMessageImpl().sendMessageToAll();

    }
}
