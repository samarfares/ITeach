package com.example.tcc.iteach;

public class MessageCancel {
    private String message,date, time, instructorID,userID;
    public MessageCancel(){}

    public MessageCancel(String message,String date,String time,String instructorID,String userID)
    {
        this.message=message;this.date=date;this.time=time;this.instructorID=instructorID;
        this.userID=userID;

    }

    public String getDate() {
        return date;
    }

    public String getInstructorID() {
        return instructorID;
    }

    public String getMessage() {
        return message;
    }

    public String getTime() {
        return time;
    }
    public String getUserID() {
        return userID;
    }

}
