package blockchain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Blockchain implements Serializable {

    final private List<Block> blocks;
    private int numHashZeroes;

    Blockchain() {
        this.blocks = new ArrayList<>();
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

    int blockCount() {
        return blocks.size();
    }

    void setNumHashZeroes(int numHashZeroes) {
        this.numHashZeroes = numHashZeroes;
    }

    List<Block> getBlocks() {
        return blocks;
    }

    int getNumHashZeroes() {
        return numHashZeroes;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Block block : blocks) {
            stringBuilder.append(block).append("\n").append("\n");
        }
        return stringBuilder.toString();
    }

    synchronized boolean addBlock(Block minedBlock) {
        if (isValidNextBlock(blocks.get(blocks.size() - 1), minedBlock)) {
            blocks.add(minedBlock);
            return true;
        }
        return false;
    }
}
