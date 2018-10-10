package com.example.tcc.iteach;

public class Questions {
    public String uid, time, date, description, fullname;

    public Questions()
    {

    }

    public Questions(String uid, String time, String date, String description, String fullname) {
        this.uid = uid;
        this.time = time;
        this.date = date;
        this.description = description;
        this.fullname = fullname;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }



    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }



    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }
}
