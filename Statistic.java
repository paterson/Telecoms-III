package cs.tcd.ie;

public class Statistic {

    int batchTotal;
    int batchProcessed;
    int foundPosition;

    public boolean isFinished() {
        return batchProcessed >= batchTotal;
    }

    public boolean isFound() {
        return foundPosition != -1;
    }

    public void setBatchTotal(int batchTotal) {
        this.batchTotal = batchTotal;
    }

    public void setBatchProcessed(int batchProcessed) {
        this.batchProcessed = batchProcessed;
    }

    public void setFoundPosition(int foundPosition) {
        this.foundPosition = foundPosition;
    }
}






