package blockchain;

import java.util.Scanner;

public class Main {

    Scanner scanner;

    Main () {
        scanner = new Scanner(System.in);
    }

    public static void main(String[] args) {
        Main main = new Main();
//        BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(file)));
        System.out.print("Enter how many zeros the hash must starts with: ");
        int numHashZeroes = Integer.parseInt(main.scanner.nextLine());
        Blockchain blockchain = new Blockchain(numHashZeroes);
        for (int i = 0; i < 5; i++) {
            blockchain.generateBlock();
        }
        System.out.println(blockchain.toString());
//        System.out.println("blockchain.Blockchain validation check: " + blockchain.validate());
    }
}
