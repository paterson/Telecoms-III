package cs.tcd.ie;

import java.io.*;
import java.net.*;
import java.lang.*;

public class WorkerTracker implements Runnable {

    static final int WAIT_TIME = 3; // seconds

    Worker worker;
    Thread currentThread;
    Boolean didPing;

    WorkerTracker(Worker worker) {
        this.worker = worker;
        currentThread = new Thread(this);
        currentThread.start();
        didPing = false;
    }

    public void run() {
        while (true) {
            try {
                Thread.sleep(WAIT_TIME * 1000);
                if (!didPing) {
                    worker.didDie();
                }
            }
            catch(Exception e) {
                e.printStackTrace();
            }
            
        }
    }

}
