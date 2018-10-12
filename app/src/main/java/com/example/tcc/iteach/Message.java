package com.example.tcc.iteach;
public class Message {
    private String title, message,email;

    public Message(){}

    public Message(String title, String message,String email) {
        this.title = title;
        this.message = message;
        this.email=email;

    }

    public String getTitle() {
        return title;
    }

    public String getMessage() {
        return message;
    }
    public String getEmail() {
        return email;
    }

}