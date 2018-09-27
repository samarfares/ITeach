package com.example.tcc.iteach;

import com.google.android.gms.maps.model.LatLng;

import java.util.LinkedList;
import java.util.List;

public class Person {

    String firstName , lastName, DOB , Gender;
    LatLng location ;
    Account account;
    List<String> subjects;


    public Person(String firstName, String lastName,String DOB, String gender, LatLng location , List<String> subs ) {
       // subjects=new LinkedList<>();
        this.firstName = firstName;
        this.lastName = lastName;
        this.DOB = DOB;
        Gender = gender;
        this.location = location;
        subjects=subs;

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

}
