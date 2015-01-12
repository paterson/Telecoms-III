package cs.tcd.ie;

import java.io.*;
import java.net.*;
import java.lang.*;

public class Timer implements Runnable {

    static final int TIMEOUT = 2; // seconds

    BasePacket packet;

    Timer(BasePacket packet) throws SocketException {
        this.packet = packet;
        Thread currentThread = new Thread(this);
        currentThread.start();
    }

    public void run() {
        try {
            Thread.sleep(TIMEOUT * 1000);  
            packet.timerDidExpire();
        } 
        catch(Exception e) {
            System.err.println(e);
        }
    }
} 
