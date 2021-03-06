package blockchain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Blockchain implements Serializable {

    private final List<Block> blocks;
    private final List<Message> messages;
    private int numHashZeroes;

    Blockchain() {
        this.blocks = new ArrayList<>();
        this.messages = new ArrayList<>();
        numHashZeroes = 0;
    }

    boolean validateBlockchain() {
        for (int i = 1; i < blocks.size(); i++) {
            if (!isValidNextBlock(blocks.get(i), blocks.get(i - 1))) {
                return false;
            }
        }
        return true;
    }

    private boolean isValidNextBlock(Block previousBlock, Block newBlock) {
        return newBlock.getPreviousBlockHash().equals(previousBlock.getCurrentBlockHash());
    }
    synchronized boolean validateBlock(Block minedBlock) {
        if ((blocks.size() < 1 || isValidNextBlock(blocks.get(blocks.size() - 1), minedBlock)) && containsValidZeroes(minedBlock)) {
            blocks.add(minedBlock);
            // notifies main thread to stop other threads
            notifyAll();
            return true;
        }
        return false;
    }

    private boolean containsValidZeroes(Block minedBlock) {
        for (int i = 0; i < numHashZeroes; i++) {
            if (minedBlock.getCurrentBlockHash().charAt(i) != '0') {
                return false;
            }
        }
        return true;
    }

    String updateNumHashZeroes() {
        int equilibriumTime = 10;
        if (getLastBlock().getGenerationTime() > equilibriumTime) {
            numHashZeroes--;
            return "N was decreased by 1";
        } else if (getLastBlock().getGenerationTime() < equilibriumTime) {
            numHashZeroes++;
            return "N was increased to " + numHashZeroes;
        } else {
            return "N stays the same";
        }
    }

    List<Message> getBlockMessages() {
        synchronized (messages) {
            List<Message> messagesForBlock = new ArrayList<>(messages);
            messages.clear();
            return messagesForBlock;
        }
    }

    int getBlockCount() {
        return blocks.size();
    }

    List<Block> getBlocks() {
        return blocks;
    }

    Block getLastBlock() {
        return blocks.size() > 0 ? blocks.get(blocks.size() - 1) : null;
    }

    int getNumHashZeroes() {
        return numHashZeroes;
    }

    void addMessageToBlockchain(Message message) {
        synchronized (messages) {
            messages.add(message);
        }
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Block block : blocks) {
            stringBuilder.append(block).append("\n").append("\n");
        }
        return stringBuilder.toString();
    }

}
