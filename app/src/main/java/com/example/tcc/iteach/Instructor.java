package com.example.tcc.iteach;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Instructor extends Person implements Parcelable {

    int yoe;
    double lessonsPrice ;
    int likes , dislikes, neutral;
    String paymentMethod, teachingMethod , lessonsPlace;
long phoneNum;

public Instructor(){}

    public Instructor(String firstName, String lastName, String dob, String gender, String location, long phoneNum , int yoe , double lessonsPrice, int likes, int dislikes, int neutral  , String paymentMethod , String lessonsPlace, String teachingMethod , String email , List<String> subjects , String userID ) {
        super(firstName, lastName, dob, gender, location, email  , subjects , userID  );
        this.lessonsPrice=lessonsPrice;
        this.likes=likes;
        this.dislikes=dislikes;
        this.neutral=neutral;
        this.phoneNum=phoneNum;
        this.yoe=yoe;
        this.teachingMethod=teachingMethod;
        this.paymentMethod=paymentMethod;
        this.lessonsPlace=lessonsPlace;
    }

    protected Instructor(Parcel in) {
        yoe = in.readInt();
        lessonsPrice = in.readDouble();
        likes = in.readInt();
        dislikes=in.readInt();
        neutral=in.readInt();

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

    public int getYoe() {
        return yoe;
    }

    public void setYoe(int yoe) {
        this.yoe = yoe;
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

    public int getLikes() {
        return likes;
    }

    public int getDislikes() {
        return dislikes;
    }


    public int getNeutral() {
        return neutral;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public void setDislikes(int dislikes) {
        this.dislikes = dislikes;
    }

    public void setNeutral(int neutral) {
        this.neutral = neutral;
    }

    public void setPhoneNum(long phoneNum) {
        this.phoneNum = phoneNum;
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


    public void likeInstructor(){
        this.likes+=1;
    }
    public void dislikeInstructor(){ this.dislikes+=1; }
    public void neutralizeInstructor(){
        this.neutral+=1;
    }
}
