package com.example.tcc.iteach;

public class Instructor extends Person {

    int YOE;
    double lessonsPrice;
    String paymentMethod, teachingMethod , lessonsPlace;




    public Instructor(String firstName, String lastName, String DOB, String gender,String location, long phoneNum , int YOE ,double lessonsPrice) {
        super(firstName, lastName, DOB, gender, location, phoneNum);
    }

    public int getYOE() {
        return YOE;
    }

    public double getLessonsPrice() {
        return lessonsPrice;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public String getTeachingMethod() {
        return teachingMethod;
    }

    public String getLessonsPlace() {
        return lessonsPlace;
    }

    public void setYOE(int YOE) {
        this.YOE = YOE;
    }

    public void setLessonsPrice(double lessonsPrice) {
        this.lessonsPrice = lessonsPrice;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public void setTeachingMethod(String teachingMethod) {
        this.teachingMethod = teachingMethod;
    }

    public void setLessonsPlace(String lessonsPlace) {
        this.lessonsPlace = lessonsPlace;
    }
}
