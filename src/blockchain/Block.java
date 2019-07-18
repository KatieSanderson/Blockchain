package blockchain;

import java.util.Date;

public class Block {

    private int id;
    private String previousBlockHash;
    private String currentBlockHash;
    private long timeStamp;

    Block(String previousBlockHash, int id) {
        this.id = id;
        this.previousBlockHash = previousBlockHash;
        currentBlockHash = StringUtil.applySha256(previousBlockHash + timeStamp);
        timeStamp = new Date().getTime();
        // 1539795682545 represents 17.10.2018, 20:01:22.545
    }

    public int getId() {
        return id;
    }

    public String getPreviousBlockHash() {
        return previousBlockHash;
    }

    public String getCurrentBlockHash() {
        return currentBlockHash;
    }

    @Override
    public String toString() {
        return "blockchain.Block: \n" +
                "Id: " + id + "\n" +
                "Timestamp: " + timeStamp + "\n" +
                "Hash of the previous block: \n" + previousBlockHash + "\n" +
                "Hash of the block: \n" + currentBlockHash + "\n";
    }
}
