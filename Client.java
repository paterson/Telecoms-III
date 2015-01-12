package cs.tcd.ie;

import java.net.DatagramSocket;
import java.net.DatagramPacket;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.io.File;
import java.io.FileInputStream;

import tcdIO.*;

public class Client extends Node {
	
    // these constants are temporary
    static final int DEFAULT_SRC_PORT = 50000;
    static final int DEFAULT_DST_PORT = 50001;
    static final String DEFAULT_DST_NODE = "localhost";

    Terminal terminal;
    SocketAddress dstAddress;

    ClientTracker tracker;
    int batchTotal;
    int batchProcessed;
    int foundPosition;

    int connectionId;

    Client(Terminal terminal, String dstHost, int dstPort, int srcPort) {
        try {
            this.terminal = terminal;
            dstAddress= (SocketAddress)new InetSocketAddress(dstHost, dstPort);
            socket= new DatagramSocket(srcPort);
            listener.go();
        }
        catch(java.lang.Exception e) {e.printStackTrace();}
    }

    public void onReceipt(DatagramPacket packet) {
    	BasePacket basePacket = (BasePacket)BasePacket.fromDatagramPacket(packet);
    	if (basePacket.getClass() == DataPacket.class) {
            terminal.println("Received work.");

            DataPacket dataPacket = (DataPacket)basePacket;
            //batchTotal = dataPacket.getContent().length;
            batchProcessed = 0;
            foundPosition = -1;
            int pos = find(dataPacket.getContent(), dataPacket.getName());
            foundPosition = pos;
    	}
    }

    public synchronized void start() throws Exception {
        // Wait one second to ensure Server is running.
        Thread.sleep(1000);
        terminal.println("Client ready.");
        ConnectionPacket connectionPacket = new ConnectionPacket(this);
        connectionPacket.send();

        tracker = new ClientTracker(this);
    }

    public int find(byte[] names, String name) {
        
        try{
            String str = new String(names, "UTF-8");
            String[] parts = str.split("\n");
            batchTotal = (parts.length - 1) * Constants.MAX_AVERAGE_NAME_LENGTH;
            for (int i = 0; i < parts.length - 1; i++) {
                String s = parts[i];
                //terminal.println("name: " + s);
                if (s.equals(name)) {
                    terminal.println("Found!");
                    batchProcessed += Constants.MAX_AVERAGE_NAME_LENGTH;
                    return i;
                }
                batchProcessed += Constants.MAX_AVERAGE_NAME_LENGTH;
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return -1;
    }

    public static void main(String[] args) {
        try {        
            Terminal terminal= new Terminal("Client");   

            int port = DEFAULT_SRC_PORT;
            if (args.length > 0) {
                port = Integer.parseInt(args[0]);
            }
            (new Client(terminal, DEFAULT_DST_NODE, DEFAULT_DST_PORT, port)).start();
        } catch(java.lang.Exception e) {
            e.printStackTrace();
        }
    }
}
