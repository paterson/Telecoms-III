package cs.tcd.ie;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketAddress;

public class BasePacket extends PacketContent {
	
    //to do: packet status
	
    Timer timer;
    DatagramSocket socket;
    SocketAddress dstAddress;
	
    BasePacket(){
        super();
    }
	
    BasePacket(DatagramSocket socket, SocketAddress address){
        this.socket = socket;	
        this.dstAddress = address;
    }
	
    public void send(){
        try {
            timer = new Timer(this);
            DatagramPacket packet = this.toDatagramPacket();
            packet.setSocketAddress(dstAddress);
            socket.send(packet);
        }
        catch(Exception e) {
        	e.printStackTrace();    		
        }
    }

    public String toString() {
        return "";
    }
    
    protected BasePacket(ObjectInputStream oin) {
        /* override */
    }

    protected void toObjectOutputStream(ObjectOutputStream oout) {
        /* override */
    }
    
    public void timerDidExpire() {
    	/* override */
    }
}
