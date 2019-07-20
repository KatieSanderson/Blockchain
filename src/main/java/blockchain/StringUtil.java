package blockchain;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Random;

class StringUtil {
    static SHA256Output applySha256(String input, int numHashZeroes){
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            Random random = new Random();

            String uncheckedHash = "";
            int magicNumber = random.nextInt(Integer.MAX_VALUE);
            do {
                String inputTo256 = input + magicNumber;
                byte[] hash = digest.digest(inputTo256.getBytes(StandardCharsets.UTF_8));
                StringBuilder hexString = new StringBuilder();
                for (byte elem: hash) {
                    String hex = Integer.toHexString(0xff & elem);
                    if(hex.length() == 1) {
                        hexString.append('0');
                    }
                    hexString.append(hex);
                }

                uncheckedHash = hexString.toString();
                magicNumber = random.nextInt(Integer.MAX_VALUE);
            } while (!isValidZeroes(uncheckedHash, numHashZeroes));
//            System.out.println("Found magic number");
            return new SHA256Output(magicNumber, uncheckedHash);
        }
        catch(Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static boolean isValidZeroes(String hash, int numHashZeroes) {
        if (hash.length() < numHashZeroes) {
            return false;
        }
        for (int i = 0; i < numHashZeroes; i++) {
            if (hash.charAt(i) != '0') {
                return false;
            }
        }
        return true;
    }
}