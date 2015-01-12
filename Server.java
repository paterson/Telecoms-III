package cs.tcd.ie;

import java.net.DatagramSocket;
import java.net.DatagramPacket;

import tcdIO.Terminal;

import java.util.ArrayList;

public class Server extends Node {
    
    static final int DEFAULT_PORT = 50001;

    Terminal terminal;
    ArrayList<Worker> workers; // arraylist to track works
    BufferQueue bufferQueue;

    ServerStatus status;

    Server(Terminal terminal, int port) {
        try {
            this.terminal = terminal;
            socket = new DatagramSocket(port);
            listener.go();
            workers = new ArrayList<Worker>();
            bufferQueue = new BufferQueue(Constants.FILE_NAME);
        }
        catch(java.lang.Exception e) {e.printStackTrace();}
    }

    public void onReceipt(DatagramPacket packet) {

        PacketContent packetContent = PacketContent.fromDatagramPacket(packet);
   
        if (packetContent.getClass() == ConnectionPacket.class) {
            onConnect(packet);
            return;
        }
        
        if(packetContent.getClass() == Ping.class){
       		respondWorker(packetContent);
        	return;
        }
    }

    public void onConnect(DatagramPacket packet) {
        // custom packet that indicates new worker has joined.
        // this method will then be called.

        terminal.println("Worker Joined."); 

        // worker.
        Worker worker = new Worker(packet, this);
        workers.add(worker);

        // if it hasn't been found yet, send data.
        if (this.status != ServerStatus.Found) {
            byte[] names = bufferQueue.pop(3000);
            if (names.length > 0) {
                worker.send(names, Constants.NAME_TO_FIND);
            }
        }

    } 

    public synchronized void start() throws Exception {
        terminal.println("Waiting for contact");
        this.wait();
    }
    
    public void respondWorker(PacketContent packet){
    	Ping ping = (Ping)packet;
    	for(int i = 0; i < workers.size(); i++){
    		if(workers.get(i).connectionId == ping.connectionId)
    			workers.get(i).didPing(ping);
    	}
    	
    }

    public static void main(String[] args) {
        try {         
            Terminal terminal= new Terminal("Server");
            (new Server(terminal, DEFAULT_PORT)).start();
            terminal.println("Program completed");
        } catch(java.lang.Exception e) {
            e.printStackTrace();
        }
    }
}
