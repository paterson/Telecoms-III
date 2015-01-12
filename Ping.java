package cs.tcd.ie;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;

public class Ping extends BasePacket {

    Statistic statistic;
    int connectionId;

    Ping(Client client) {
        super(client.socket, client.dstAddress);
        type = PING_PACKET;
        connectionId = client.connectionId;
    }

    protected Ping(ObjectInputStream oin) {
        try {
            type = PING_PACKET;
            connectionId = oin.readInt();
            statistic = new Statistic();
            statistic.setBatchTotal(oin.readInt());
            statistic.setBatchProcessed(oin.readInt());
            statistic.setFoundPosition(oin.readInt());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setStatistics(Statistic statistic) {
        this.statistic = statistic;
    }

    public void setConnectionId(int connectionId) {
        this.connectionId = connectionId;
    }

	/**
	 * Writes the content into an ObjectOutputStream
	 *
	 */
	protected void toObjectOutputStream(ObjectOutputStream oout) {
		try {
			oout.writeInt(connectionId);
            oout.writeInt(statistic.batchTotal);
            oout.writeInt(statistic.batchProcessed);
            oout.writeInt(statistic.foundPosition);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
