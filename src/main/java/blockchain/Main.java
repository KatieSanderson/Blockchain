package blockchain;

import java.io.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    private File file;
    private Blockchain blockchain;
    private ExecutorService executor;

    private Main(File file) throws IOException, ClassNotFoundException {
        this.file = file;
        if (file.exists()) {
            loadFile();
        } else {
            blockchain = new Blockchain();
        }
        executor = Executors.newFixedThreadPool(5);
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        String filePath = "blockchain.txt";
        File file = new File(filePath);
        Main main = new Main(file);

        for (int blockCount = main.blockchain.blockCount(); blockCount < 5; blockCount++) {
            Block block = main.blockchain.getLastBlock();
            for (int i = 0; i < 10; i++) {
                main.executor.submit(new Miner(main.blockchain, block, i));
            }

            synchronized (main.blockchain) {
                while (block == main.blockchain.getLastBlock()) {
                    try {
                        main.blockchain.wait();
                    } catch (InterruptedException e) {
                        // treat interrupt as exit request
                        break;
                    }
                    System.out.println(main.blockchain.getLastBlock());
                    System.out.println(main.blockchain.updateNumHashZeroes());
                    System.out.println("\n");
                }
            }
        }

        main.executor.shutdown();
        main.writeToFile();
        file.delete();
    }

    private void writeToFile() throws IOException {
        ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(file));
        outputStream.writeObject(blockchain);
        outputStream.close();
    }

    private void loadFile() throws IOException, ClassNotFoundException {
        ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(file));
        blockchain = (Blockchain) inputStream.readObject();
        if (!blockchain.validateBlockchain()) {
            throw new IllegalStateException("Blockchain is not valid.");
        }
        inputStream.close();
    }
}
