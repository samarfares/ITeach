package com.example.tcc.iteach;

import com.google.android.gms.maps.model.LatLng;

import java.util.LinkedList;
import java.util.List;

public class Instructor extends Person {

    int YOE;
    double lessonsPrice ;
    int rate;
    String paymentMethod, teachingMethod , lessonsPlace;
long phoneNum;


    public Instructor(String firstName, String lastName, String DOB, String gender, LatLng location, long phoneNum , int YOE , double lessonsPrice, int rate ,List<String> subjects , String paymentMethod , String lessonsPlace, String teachingMethod ) {
        super(firstName, lastName, DOB, gender, location , subjects);
        this.lessonsPrice=lessonsPrice;
        this.rate=rate;
        this.phoneNum=phoneNum;
        this.YOE=YOE;
        this.teachingMethod=teachingMethod;
        this.paymentMethod=paymentMethod;
        this.lessonsPlace=lessonsPlace;

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

    public void setRate(int rate) {
        this.rate = rate;
    }

    public double getRate() {
        return rate;
    }
}
