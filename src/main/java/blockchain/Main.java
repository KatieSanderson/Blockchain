package blockchain;

import java.io.*;
import java.util.Scanner;

public class Main implements AutoCloseable {

    private final Scanner scanner;
    private final ObjectInputStream inputStream;
    private final ObjectOutputStream outputStream;
    private Blockchain blockchain;

    Main (ObjectInputStream inputStream, ObjectOutputStream outputStream) {
        scanner = new Scanner(System.in);
        this.inputStream = inputStream;
        this.outputStream = outputStream;
    }

    public static void main(String[] args) throws IOException {
        String filePath = "src\\main\\resources\\blockchain.txt";
        File file = new File(filePath);
        ObjectInputStream inputStream = file.createNewFile() ? null : new ObjectInputStream(new FileInputStream(file));

        try (Main main = new Main(inputStream, new ObjectOutputStream(new FileOutputStream(file)))) {
            if (inputStream != null) {
                main.loadFile();
            } else {
                System.out.print("Enter how many zeros the hash must starts with: ");
                int numHashZeroes = Integer.parseInt(main.scanner.nextLine());
                main.blockchain = new Blockchain(numHashZeroes);
            }
            for (int i = 0; i < 5; i++) {
                main.blockchain.generateBlock();
            }
            main.writeToFile();
            System.out.println(main.blockchain.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void writeToFile() throws IOException {
        System.out.println("Writing to file");
        outputStream.writeObject(blockchain);
//        outputStream.flush();
    }

    private void loadFile() throws IOException, ClassNotFoundException {
        blockchain = (Blockchain) inputStream.readObject();
        if (!blockchain.validate()) {
            throw new IllegalStateException("Blockchain is not valid.");
        }
    }

    @Override
    public void close() throws Exception {
        scanner.close();
        if (inputStream != null) {
            inputStream.close();
        }
        outputStream.close();
    }
}
