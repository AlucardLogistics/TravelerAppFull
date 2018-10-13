package com.example.sadic.travelerapp.data.model;

public class Guest {

    String gEmail, gName, gMobile;

    public Guest(String gEmail, String gName, String gMobile) {
        this.gEmail = gEmail;
        this.gName = gName;
        this.gMobile = gMobile;
    }

    public Guest() {
    }

    public String getgEmail() {
        return gEmail;
    }

    public void setgEmail(String gEmail) {
        this.gEmail = gEmail;
    }

    public String getgName() {
        return gName;
    }

    public void setgName(String gName) {
        this.gName = gName;
    }

    public String getgMobile() {
        return gMobile;
    }

    public void setgMobile(String gMobile) {
        this.gMobile = gMobile;
    }

    @Override
    public String toString() {
        return "Guest{" +
                "gEmail='" + gEmail + '\'' +
                ", gName='" + gName + '\'' +
                ", gMobile='" + gMobile + '\'' +
                '}';
    }
}
