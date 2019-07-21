package blockchain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Block implements Serializable {

    private int id;
    private String previousBlockHash;
    private String currentBlockHash;
    private int numHashZeroes;
    private int minerId;
    private int magicNumber;
    private long startTimestamp;
    private long endTimestamp;
    private final List<Message> messages;

    Block(String previousBlockHash, int id, int numHashZeroes, int minerId, List<Message> messages) {
        startTimestamp = new Date().getTime();
        this.previousBlockHash = previousBlockHash;
        this.id = id;
        this.numHashZeroes = numHashZeroes;
        this.minerId = minerId;
        this.messages = messages;
        SHA256Output output = StringUtil.applySha256(previousBlockHash, numHashZeroes);
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

    long getGenerationTime() {
        return (endTimestamp - startTimestamp) / 1000;
    }

    private String messagesToString() {
        if (messages.size() < 1) {
            return "no messages";
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            for (Message message : messages) {
                stringBuilder.append(message.getFromUser()).append(": ").append(message.getMessageContent());
            }
            return stringBuilder.toString();
        }
    }

    @Override
    public String toString() {
        return "Block: \n" +
                "Created by miner # " + minerId + "\n" +
                "Id: " + id + "\n" +
                "Timestamp: " + startTimestamp + "\n" +
                "Magic number: " + magicNumber + "\n" +
                "Hash of the previous block: \n" + previousBlockHash + "\n" +
                "Hash of the block: \n" + currentBlockHash + "\n" +
                "Block data: " + messagesToString() + "\n" +
                "Block was generating for " + getGenerationTime() + " seconds";
    }
}
