package blockchain;

class Message {

    private final String fromUser;
    private final String toUser;
    private final String messageContent;

    Message(String fromUser, String toUser, String messageContent) {
        this.fromUser = fromUser;
        this.toUser = toUser;
        this.messageContent = messageContent;
    }

    String getFromUser() {
        return fromUser;
    }

    String getToUser() {
        return toUser;
    }

    String getMessageContent() {
        return messageContent;
    }
}
