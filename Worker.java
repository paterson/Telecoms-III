package cs.tcd.ie;

import java.net.SocketAddress;
import java.net.DatagramPacket;

import java.util.Arrays;

public class Worker {

    int connectionId;
    SocketAddress dstAddress;
    Server server;

    WorkerTracker tracker;
    Statistic statistic;
    byte[] lastSentBatch;

    Worker(DatagramPacket packet, Server server) {
        ConnectionPacket connectionPacket = (ConnectionPacket)PacketContent.fromDatagramPacket(packet);
        connectionId = connectionPacket.getConnectionId();
        server.terminal.println("Connection ID: " + connectionId);
        dstAddress = packet.getSocketAddress();
        this.server = server;

        tracker = new WorkerTracker(this);
    }
    
    public void didPing(Ping ping) {

        if (ping.statistic.isFound()) {
            server.status = ServerStatus.Found;
        }
    	if (ping.statistic.isFinished()) {
            // if found, don't send more, otherwise send more.
            if (server.status != ServerStatus.Found) {
                byte[] names = server.bufferQueue.pop(Constants.DEFAULT_NAMES_LENGTH);
                if (names.length > 0) {
                    this.send(names, Constants.NAME_TO_FIND);
                }
            }
        }

        this.statistic = ping.statistic;
        tracker.didPing = true;
        tracker.currentThread.stop();
        tracker = new WorkerTracker(this);
    }

    public void didDie() {
        // use last statistic (this.statistic) to figure out how much is processed
        // push rest back onto buffer

        try {
            System.out.println("statistic.batchProcessed: " + statistic.batchProcessed);
            System.out.println("BatchTotal: " + statistic.batchTotal);
            byte[] remainingNames = Arrays.copyOfRange(lastSentBatch, statistic.batchProcessed, statistic.batchTotal);
            System.out.println("remainingNames.length: " + remainingNames.length);
            server.bufferQueue.push(remainingNames);

            server.workers.remove(this);
            tracker.currentThread.stop();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void send(byte[] names, String name) {
    	DataPacket packet = new DataPacket(this);
    	packet.setName(name);
    	packet.setContent(names);
        packet.send();

        this.lastSentBatch = names;
    }
}
