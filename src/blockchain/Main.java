package blockchain;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        Blockchain blockchain = new Blockchain();
        for (int i = 0; i < 5; i++) {
            blockchain.generateBlock();
        }
        System.out.println(blockchain.toString());
//        System.out.println("blockchain.Blockchain validation check: " + blockchain.validate());
    }

    static class Blockchain {

        private List<Block> blocks;

        Blockchain() {
            this.blocks = new ArrayList<>();
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
                stringBuilder.append(block).append("\n").append("\n");
            }
            return stringBuilder.toString();
        }
    }

    static class Block {

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
            return "Block:\n" +
                    "Id: " + id + "\n" +
                    "Timestamp: " + timeStamp + "\n" +
                    "Hash of the previous block: \n" + previousBlockHash + "\n" +
                    "Hash of the block: \n" + currentBlockHash;
        }
    }

    static class StringUtil {
        /* Applies Sha256 to a string and returns a hash. */
        public static String applySha256(String input){
            try {
                MessageDigest digest = MessageDigest.getInstance("SHA-256");
                /* Applies sha256 to our input */
                byte[] hash = digest.digest(input.getBytes(StandardCharsets.UTF_8));
                StringBuilder hexString = new StringBuilder();
                for (byte elem: hash) {
                    String hex = Integer.toHexString(0xff & elem);
                    if(hex.length() == 1) {
                        hexString.append('0');
                    }
                    hexString.append(hex);
                }
                return hexString.toString();
            }
            catch(Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

}
