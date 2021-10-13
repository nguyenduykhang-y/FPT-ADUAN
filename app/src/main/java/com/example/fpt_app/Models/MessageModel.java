package com.example.fpt_app.Models;

public class MessageModel {
    private String message;
    private Boolean fromMe;

    public MessageModel() {
    }

    public MessageModel(String message, Boolean fromMe) {
        this.message = message;
        this.fromMe = fromMe;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getFromMe() {
        return fromMe;
    }

    public void setFromMe(Boolean fromMe) {
        this.fromMe = fromMe;
    }
}
