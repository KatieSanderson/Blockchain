package blockchain;

import java.util.List;

public class Miner implements Runnable {

    private Blockchain blockchain;
    private int minerId;
    private Block previousBlock;

    Miner(Blockchain blockchain, Block previousBlock, int minerId) {
        this.blockchain = blockchain;
        this.minerId = minerId;
        this.previousBlock = previousBlock;
    }

    @Override
    public void run() {
        Block minedBlock = mineBlock();
        if (blockchain.validateBlock(minedBlock)) {
        }
    }

    private Block mineBlock() {
        List<Block> blocks = blockchain.getBlocks();
        int numHashZeroes = blockchain.getNumHashZeroes();
        if (blocks.size() > 0) {
            return new Block(previousBlock.getCurrentBlockHash(), previousBlock.getId() + 1, numHashZeroes, minerId);
        } else {
            // first block's previous hash is 0
            return new Block("0", 1, numHashZeroes, minerId);
        }
    }
}
