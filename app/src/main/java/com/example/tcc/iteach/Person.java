package com.example.tcc.iteach;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Person {

    String firstName , lastName, dob , Gender , email  ;
    String location ;
    Account account;
    List<String> subjects;
String userID ;

    public Person() {}



    public Person(String firstName, String lastName, String dob, String gender, String location , String email, List<String>subjects , String userID  ) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dob = dob;
        Gender = gender;
this.userID=userID;
        this.location = location;
        this.email=email;
        this.subjects=new ArrayList<String>(subjects);
//this.subjects=subjects;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getUserID() {
        return userID;
    }


    public String getGender() {
        return Gender;
    }

    public String getLocation() {
        return location;
    }


    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setSubjects(List<String> subjects) {
        this.subjects = subjects;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }


    public void setUserID(String userID) {
        this.userID = userID;
    }


    public List<String> getSubjects() {
        return subjects;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
