package com.example.tcc.iteach;

public class Spot {

    String instructor_id, date, time;
    int numberOfStudent;
    boolean available, individual;

    public Spot(String instructor_id, String date, String time, int numberOfStudent, boolean available, boolean individual) {
        this.instructor_id = instructor_id;
        this.date = date;
        this.time = time;
        this.numberOfStudent = numberOfStudent;
        this.available = available;
        this.individual = individual;
    }

    public String getInstructor_id() {
        return instructor_id;
    }

    public void setInstructor_id(String instructor_id) {
        this.instructor_id = instructor_id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getNumberOfStudent() {
        return numberOfStudent;
    }

    public void setNumberOfStudent(int numberOfStudent) {
        this.numberOfStudent = numberOfStudent;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public boolean isIndividual() {
        return individual;
    }

    public void setIndividual(boolean individual) {
        this.individual = individual;
    }
}
