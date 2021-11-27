package com.example.medcare;

public class Message {
    private String message, sender, receiver;

    public Message(String messageID, String sender, String receiver) {
        this.message = messageID;
        this.sender = sender;
        this.receiver = receiver;

    }


    public Message() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }


}
