package cs.tcd.ie;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.util.Random;

public class ConnectionPacket extends BasePacket {

    int connectionId;

    ConnectionPacket(Client client) {
    	super(client.socket, client.dstAddress);
        type = CONNECTION_PACKET;
        Random random = new Random();
        connectionId = random.nextInt(10000);
        client.connectionId = connectionId;
    }

    public String toString() {
        return "ConnectionPacket id: " + connectionId;
    }

    public int getConnectionId() {
        return connectionId;
    }

    public void setConnectionId(int id) {
        connectionId = id;
    }

    protected ConnectionPacket(ObjectInputStream oin) {
        try {
            type = CONNECTION_PACKET;
            connectionId = oin.readInt();
        } 
        catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Writes the content into an ObjectOutputStream
     *
     */
    protected void toObjectOutputStream(ObjectOutputStream oout) {
        try {
            oout.writeInt(connectionId);
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
}