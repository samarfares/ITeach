package com.example.tcc.iteach;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Person {

    String firstName , lastName, DOB , Gender;
    String location ;
    Account account;
    LinkedList<String> subjects=new LinkedList<String>();


    public Person() {}


    public Person(String firstName, String lastName,String DOB, String gender, String location  ) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.DOB = DOB;
        Gender = gender;
        this.location = location;

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

    public String getLocation() {
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

    public void setLocation(String location) {
        this.location = location;
    }

}
