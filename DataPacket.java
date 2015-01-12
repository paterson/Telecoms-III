package cs.tcd.ie;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.util.Random;

public class DataPacket extends BasePacket {
	
    private byte[] content;
    private String name;
	
    DataPacket(Worker worker) {
        super(worker.server.socket, worker.dstAddress);
        type = DATA_PACKET;
    }
	
    public byte[] getContent() {
        return content;	
    }
	
    public void setContent(byte[] content) {
        this.content = content;
    }
	
    public String getName() {
        return name;
    }
	
    public void setName(String name) {
        this.name= name;
    }
	
    public String toString() {
        return "";
    }
    
    protected DataPacket(ObjectInputStream oin) {
        try {
            type= DATA_PACKET;
            this.name = oin.readUTF();
            this.content = new byte[Constants.DEFAULT_NAMES_LENGTH * Constants.MAX_AVERAGE_NAME_LENGTH]; // by definition the max size of the content (see BufferQueue)
            oin.readFully(content);
        } 
        catch(Exception e) {e.printStackTrace();}
    }

    /**
     * Writes the content into an ObjectOutputStream
     *
     */
    protected void toObjectOutputStream(ObjectOutputStream oout) {
        try {
            oout.writeUTF(name);
            oout.write(content);
        }
        catch(Exception e) {e.printStackTrace();}
 }

}
