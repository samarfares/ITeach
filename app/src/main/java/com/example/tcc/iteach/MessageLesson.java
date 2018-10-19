package com.example.tcc.iteach;

public class MessageLesson {
    private String message,date, time, instructorID;
    public MessageLesson(){}

    public MessageLesson(String message,String date,String time,String instructorID)
    {
        this.message=message;this.date=date;this.time=time;this.instructorID=instructorID;

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
}
