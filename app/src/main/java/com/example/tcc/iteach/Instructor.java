package com.example.tcc.iteach;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Instructor extends Person implements Parcelable {

    int YOE;
    double lessonsPrice ;
    int rate;
    String paymentMethod, teachingMethod , lessonsPlace;
long phoneNum;

public Instructor(){}

    public Instructor(String firstName, String lastName, String DOB, String gender, String location, long phoneNum , int YOE , double lessonsPrice, int rate  , String paymentMethod , String lessonsPlace, String teachingMethod) {
        super(firstName, lastName, DOB, gender, location );
        this.lessonsPrice=lessonsPrice;
        this.rate=rate;
        this.phoneNum=phoneNum;
        this.YOE=YOE;
        this.teachingMethod=teachingMethod;
        this.paymentMethod=paymentMethod;
        this.lessonsPlace=lessonsPlace;


    }

    protected Instructor(Parcel in) {
        YOE = in.readInt();
        lessonsPrice = in.readDouble();
        rate = in.readInt();
        paymentMethod = in.readString();
        teachingMethod = in.readString();
        lessonsPlace = in.readString();
        phoneNum = in.readLong();
    }

    public static final Creator<Instructor> CREATOR = new Creator<Instructor>() {
        @Override
        public Instructor createFromParcel(Parcel in) {
            return new Instructor( in );
        }

        @Override
        public Instructor[] newArray(int size) {
            return new Instructor[size];
        }
    };

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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(firstName);
        dest.writeString(lastName);


    }
}
