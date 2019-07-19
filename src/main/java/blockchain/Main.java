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
        String filePath = "blockchain.txt";
        File file = new File(filePath);

        try (Scanner scanner = new Scanner(System.in)) {
            Main main = new Main(file);
            if (file.exists()) {
                main.loadFile();
            } else {
                main.blockchain = new Blockchain();
            }
            System.out.print("Enter how many zeros the hash must starts with: ");
            main.blockchain.setNumHashZeroes(Integer.parseInt(scanner.nextLine()));
            System.out.println();
            for (int blockCount = main.blockchain.blockCount(); blockCount < 5; blockCount++) {
                main.blockchain.generateBlock();
            }
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
        if (!blockchain.validate()) {
            throw new IllegalStateException("Blockchain is not valid.");
        }
        inputStream.close();
    }
}
