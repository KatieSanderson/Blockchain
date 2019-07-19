package blockchain;

import java.io.*;
import java.util.Scanner;

public class Main {

    private File file;
    private Blockchain blockchain;

    private Main(File file) {
        this.file = file;
    }

    public static void main(String[] args) {
        String filePath = "src\\main\\resources\\blockchain.txt";
        File file = new File(filePath);

        try (Scanner scanner = new Scanner(System.in)) {
            Main main = new Main(file);
            if (file.exists()) {
                main.loadFile();
            } else {
                System.out.print("Enter how many zeroes the hash must starts with: ");
                int numHashZeroes = Integer.parseInt(scanner.nextLine());
                main.blockchain = new Blockchain(numHashZeroes);
            }
            main.generateBlocks(5);
            main.writeToFile();
            System.out.println(main.blockchain.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void generateBlocks(int blockCount) {
        for (int i = 0; i < blockCount; i++) {
            blockchain.generateBlock();
        }
    }

    private void writeToFile() throws IOException {
        ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(file));
        outputStream.writeObject(blockchain);
    }

    private void loadFile() throws IOException, ClassNotFoundException {
        ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(file));
        blockchain = (Blockchain) inputStream.readObject();
        if (!blockchain.validate()) {
            throw new IllegalStateException("Blockchain is not valid.");
        }
    }
}
