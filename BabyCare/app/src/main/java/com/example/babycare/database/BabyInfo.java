package com.example.babycare.database;



public class BabyInfo {

    public String getBabyName() {
        return babyName;
    }

    public void setBabyName(String babyName) {
        this.babyName = babyName;
    }

    public String getBabyDate() {
        return babyDate;
    }

    public void setBabyDate(String babyDate) {
        this.babyDate = babyDate;
    }

    public String getBabySex() {
        return babySex;
    }

    public void setBabySex(String babySex) {
        this.babySex = babySex;
    }

    private String babyName;
    private String babyDate;
    private String babySex;



}
