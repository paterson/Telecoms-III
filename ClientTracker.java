package cs.tcd.ie;

import java.io.*;
import java.net.*;
import java.lang.*;

public class ClientTracker implements Runnable {

    static final int WAIT_TIME = 1; // seconds

    Client client;

    ClientTracker(Client client) {
        this.client = client;
        Thread currentThread = new Thread(this);
        currentThread.start();
    }

    public void run() {
        while (true) {
            try {
                Thread.sleep(WAIT_TIME * 1000);

                Statistic statistic = new Statistic();
                statistic.setBatchTotal(client.batchTotal);
                statistic.setBatchProcessed(client.batchProcessed);
                statistic.setFoundPosition(client.foundPosition);

                Ping ping = new Ping(client);
                ping.setStatistics(statistic);
                ping.setConnectionId(client.connectionId);

                ping.send();
            }
            catch(Exception e) {
                e.printStackTrace();
            }
            
        }
    }

}