package com.example.tcc.iteach;

import com.google.android.gms.maps.model.LatLng;

public class Instructor extends Person {

    int YOE;
    double lessonsPrice;
    String paymentMethod, teachingMethod , lessonsPlace;
long phoneNum;



    public Instructor(String firstName, String lastName, String DOB, String gender, LatLng location, long phoneNum , int YOE , double lessonsPrice) {
        super(firstName, lastName, DOB, gender, location);
    }

    public int getYOE() {
        return YOE;
    }

    public long getPhoneNum() { return phoneNum; }

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
