package com.example.tcc.iteach;

public class Answers {
    public String uid,answer, date, time,userName;
    public Answers(){

    }

    public Answers(String uid,String answer, String date, String time, String userName) {
        this.uid = uid;
        this.answer = answer;
        this.date = date;
        this.time = time;
        this.userName = userName;
    }
    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getComment() {
        return answer;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getUserName() {
        return userName;
    }

    public void setComment(String comment) {
        this.answer = comment;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setUserName(String username) {
        this.userName = username;
    }
}
