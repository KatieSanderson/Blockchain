package blockchain;

class User implements Runnable {

    private String user;
    private String[] messages;
    private Blockchain blockchain;

    User(String user, String[] messages, Blockchain blockchain) {
        this.user = user;
        this.messages = messages;
        this.blockchain = blockchain;
    }

    @Override
    public void run() {
        for (String message : messages) {
            blockchain.addMessageToBlockchain(new Message(user, message));
        }
    }
}
