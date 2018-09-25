package com.example.tcc.iteach;

import com.google.android.gms.maps.model.LatLng;

public class Person {

    String firstName , lastName, DOB , Gender;
    LatLng location ;
    long phoneNum;
    Account account;


    public Person(String firstName, String lastName,String DOB, String gender, LatLng location) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.DOB = DOB;
        Gender = gender;
        this.location = location;
        this.phoneNum = phoneNum;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }



    public String getDOB() {
        return DOB;
    }

    public String getGender() {
        return Gender;
    }

    public LatLng getLocation() {
        return location;
    }

    public long getPhoneNum() {
        return phoneNum;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }



    public void setDOB(String DOB) {
        this.DOB = DOB;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public void setLocation(LatLng location) {
        this.location = location;
    }

    public void setPhoneNum(long phoneNum) {
        this.phoneNum = phoneNum;
    }
}
