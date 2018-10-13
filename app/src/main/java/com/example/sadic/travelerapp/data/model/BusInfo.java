package com.example.sadic.travelerapp.data.model;

public class BusInfo {

    String busid, fare, busregistrationno, journyduration, boardingtime,
            bustype, busdeparturetime, dropingtime;

    public BusInfo() {
    }

    public BusInfo(String busid, String fare, String busregistrationno, String journyduration,
                   String boardingtime, String bustype, String busdeparturetime, String dropingtime) {
        this.busid = busid;
        this.fare = fare;
        this.busregistrationno = busregistrationno;
        this.journyduration = journyduration;
        this.boardingtime = boardingtime;
        this.bustype = bustype;
        this.busdeparturetime = busdeparturetime;
        this.dropingtime = dropingtime;
    }

    public String getBusid() {
        return busid;
    }

    public void setBusid(String busid) {
        this.busid = busid;
    }

    public String getFare() {
        return fare;
    }

    public void setFare(String fare) {
        this.fare = fare;
    }

    public String getBusregistrationno() {
        return busregistrationno;
    }

    public void setBusregistrationno(String busregistrationno) {
        this.busregistrationno = busregistrationno;
    }

    public String getJournyduration() {
        return journyduration;
    }

    public void setJournyduration(String journyduration) {
        this.journyduration = journyduration;
    }

    public String getBoardingtime() {
        return boardingtime;
    }

    public void setBoardingtime(String boardingtime) {
        this.boardingtime = boardingtime;
    }

    public String getBustype() {
        return bustype;
    }

    public void setBustype(String bustype) {
        this.bustype = bustype;
    }

    public String getBusdeparturetime() {
        return busdeparturetime;
    }

    public void setBusdeparturetime(String busdeparturetime) {
        this.busdeparturetime = busdeparturetime;
    }

    public String getDropingtime() {
        return dropingtime;
    }

    public void setDropingtime(String dropingtime) {
        this.dropingtime = dropingtime;
    }

    @Override
    public String toString() {
        return "BusInfo{" +
                "busid='" + busid + '\'' +
                ", fare='" + fare + '\'' +
                ", busregistrationno='" + busregistrationno + '\'' +
                ", journyduration='" + journyduration + '\'' +
                ", boardingtime='" + boardingtime + '\'' +
                ", bustype='" + bustype + '\'' +
                ", busdeparturetime='" + busdeparturetime + '\'' +
                ", dropingtime='" + dropingtime + '\'' +
                '}';
    }
}


