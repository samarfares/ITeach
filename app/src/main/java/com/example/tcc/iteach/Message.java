package com.example.tcc.iteach;
public class Message {
    private String title, message,email,location,name,insID;

    public Message(){}

    public Message(String title, String message,String email,String location,String name,String insID) {
        this.title = title;
        this.message = message;
        this.email=email;
        this.location=location;
        this.name=name;
        this.insID=insID;

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
    public String getName() {
        return name;
    }
    public String getInsID() {
        return insID;
    }

}