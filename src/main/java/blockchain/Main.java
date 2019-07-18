package blockchain;

import java.io.*;
import java.util.Scanner;

public class Main implements AutoCloseable {

    Scanner scanner;
    BufferedReader bufferedReader;
    FileWriter fileWriter;

    Main (BufferedReader bufferedReader, FileWriter fileWriter) {
        scanner = new Scanner(System.in);
        this.bufferedReader = bufferedReader;
        this.fileWriter = fileWriter;
    }

    public static void main(String[] args) throws IOException {
        String filePath = "src\\main\\resources\\blockchain.txt";
        File file = new File(filePath);
        if (!file.exists()) {
            file.createNewFile();
        }

        try (Main main = new Main(new BufferedReader(new FileReader(file)), new FileWriter(file))) {
            main.loadFile();
            System.out.print("Enter how many zeros the hash must starts with: ");
            int numHashZeroes = Integer.parseInt(main.scanner.nextLine());
            Blockchain blockchain = new Blockchain(numHashZeroes);
            for (int i = 0; i < 5; i++) {
                blockchain.generateBlock();
            }
            System.out.println(blockchain.toString());
//        System.out.println("blockchain.Blockchain validation check: " + blockchain.validate());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadFile() {

    }

    @Override
    public void close() throws Exception {
        scanner.close();
        bufferedReader.close();
        fileWriter.close();
    }
}
