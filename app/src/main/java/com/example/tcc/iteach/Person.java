package com.example.tcc.iteach;

public class Person {

    String firstName , lastName, DOB , Gender, location ;
    long phoneNum;
    Account account;


    public Person(String firstName, String lastName,String DOB, String gender, String location, long phoneNum) {
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

    public String getLocation() {
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

    public void setLocation(String location) {
        this.location = location;
    }

    public void setPhoneNum(long phoneNum) {
        this.phoneNum = phoneNum;
    }
}
