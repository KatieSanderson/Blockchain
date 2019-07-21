package blockchain;

import java.io.Serializable;

class Message implements Serializable {

    private final String fromUser;
    private final String messageContent;

    Message(String fromUser, String messageContent) {
        this.fromUser = fromUser;
        this.messageContent = messageContent;
    }

    String getFromUser() {
        return fromUser;
    }

    String getMessageContent() {
        return messageContent;
    }

    @Override
    public String toString() {
        return fromUser + ": " + messageContent;
    }
}
