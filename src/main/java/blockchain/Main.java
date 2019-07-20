package blockchain;

import java.io.*;
import java.util.Scanner;
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
            blockchain.setNumHashZeroes(0);
        }
        executor = Executors.newFixedThreadPool(5);
    }

    public static void main(String[] args) {
        String filePath = "blockchain.txt";
        File file = new File(filePath);

        try (Scanner scanner = new Scanner(System.in)) {
            Main main = new Main(file);
//            System.out.print("Enter how many zeros the hash must starts with: ");
//            main.blockchain.setNumHashZeroes(Integer.parseInt(scanner.nextLine()));
//            System.out.println();
            for (int blockCount = main.blockchain.blockCount(); blockCount < 5; blockCount++) {
                System.out.println("Generating block " + blockCount);
                for (int i = 0; i < 10; i++) {
                    main.executor.submit(new Miner(main.blockchain, i));
                }
            }
            main.executor.shutdown();

            main.writeToFile();
            System.out.println(main.blockchain.toString());
            file.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
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
