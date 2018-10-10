package com.example.tcc.iteach;

public class Lesson {
    private String date, time, instructorID, studentID, feedback, subject, price, paymentMethod, lessonPlace, teachingMethod;

    public Lesson() {
    }

    public Lesson(String date, String time, String instructorID, String studentID, String feedback, String subject, String price, String paymentMethod, String lessonPlace, String method) {
        this.date = date;
        this.time = time;
        this.instructorID = instructorID;
        this.studentID = studentID;
        this.feedback = feedback;
        this.subject = subject;
        this.price = price;
        this.paymentMethod = paymentMethod;
        this.lessonPlace = lessonPlace;
        this.teachingMethod = method;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getInstructorID() {
        return instructorID;
    }

    public void setInstructorID(String instructorID) {
        this.instructorID = instructorID;
    }

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getLessonPlace() {
        return lessonPlace;
    }

    public void setLessonPlace(String lessonPlace) {
        this.lessonPlace = lessonPlace;
    }

    public String getTeachingMethod() {
        return teachingMethod;
    }

    public void setTeachingMethod(String method) {
        this.teachingMethod = method;
    }
}
