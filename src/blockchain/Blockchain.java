package blockchain;

import java.util.ArrayList;
import java.util.List;

public class Blockchain {

    private List<Block> blocks;

    Blockchain() {
        this.blocks = new ArrayList<>();
        generateBlock();
    }

    void generateBlock() {
        if (blocks.size() > 0) {
            Block previousBlock = blocks.get(blocks.size() - 1);
            blocks.add(new Block(previousBlock.getCurrentBlockHash(), previousBlock.getId() + 1));
        } else {
            // first block's previous hash is 0
            blocks.add(new Block("0", 1));
        }
    }

    public boolean validate() {
        for (int i = 1; i < blocks.size(); i++) {
            if (!blocks.get(i).getPreviousBlockHash().equals(blocks.get(i - 1).getCurrentBlockHash())) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Block block : blocks) {
            stringBuilder.append(block).append("\n");
        }
        return stringBuilder.toString();
    }
}
