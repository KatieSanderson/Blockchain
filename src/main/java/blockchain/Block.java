package blockchain;

import java.io.Serializable;
import java.util.Date;

public class Block implements Serializable {

    private int id;
    private String previousBlockHash;
    private String currentBlockHash;
    private int magicNumber;
    private long startTimestamp;
    private long endTimestamp;

    Block(String previousBlockHash, int id, int numHashZeroes) {
        startTimestamp = new Date().getTime();
        this.id = id;
        this.previousBlockHash = previousBlockHash;
        SHA256Output output = StringUtil.applySha256(previousBlockHash + startTimestamp, numHashZeroes);
        currentBlockHash = output.getHash();
        magicNumber = output.getMagicNumber();
        // 1539795682545 represents 17.10.2018, 20:01:22.545
        endTimestamp = new Date().getTime();
    }

    int getId() {
        return id;
    }

    String getPreviousBlockHash() {
        return previousBlockHash;
    }

    String getCurrentBlockHash() {
        return currentBlockHash;
    }

    @Override
    public String toString() {
        return "Block: \n" +
                "Id: " + id + "\n" +
                "Timestamp: " + startTimestamp + "\n" +
                "Magic number: " + magicNumber + "\n" +
                "Hash of the previous block: \n" + previousBlockHash + "\n" +
                "Hash of the block: \n" + currentBlockHash + "\n" +
                "Block was generating for " + (endTimestamp - startTimestamp)/1000 + " seconds";
    }
}
