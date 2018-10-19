package com.example.tcc.iteach;
public class Message {
    private String title, message,email,location;

    public Message(){}

    public Message(String title, String message,String email,String location) {
        this.title = title;
        this.message = message;
        this.email=email;
        this.location=location;

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
    public String getLocation() {
        return location;
    }

}