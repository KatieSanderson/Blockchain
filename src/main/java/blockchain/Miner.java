package blockchain;

import java.util.List;

public class Miner implements Runnable {

    private Blockchain blockchain;
    private int minerId;
    private Block previousBlock;
    private final List<Message> messages;

    Miner(Blockchain blockchain, Block previousBlock, int minerId, List<Message> messages) {
        this.blockchain = blockchain;
        this.minerId = minerId;
        this.previousBlock = previousBlock;
        this.messages = messages;
    }

    @Override
    public void run() {
        Block minedBlock = mineBlock();
        blockchain.validateBlock(minedBlock);
    }

    private Block mineBlock() {
        List<Block> blocks = blockchain.getBlocks();
        int numHashZeroes = blockchain.getNumHashZeroes();
        if (blocks.size() > 0) {
            return new Block(previousBlock.getCurrentBlockHash(), previousBlock.getId() + 1, numHashZeroes, minerId, messages);
        } else {
            // first block's previous hash is 0
            return new Block("0", 1, numHashZeroes, minerId, messages);
        }
    }
}
