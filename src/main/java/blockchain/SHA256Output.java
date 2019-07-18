package blockchain;

class SHA256Output {

    private final int magicNumber;
    private final String hash;

    SHA256Output(int magicNumber, String hash) {
        this.magicNumber = magicNumber;
        this.hash = hash;
    }

    int getMagicNumber() {
        return magicNumber;
    }

    String getHash() {
        return hash;
    }
}
