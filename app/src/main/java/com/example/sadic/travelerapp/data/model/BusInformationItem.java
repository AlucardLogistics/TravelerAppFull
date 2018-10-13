package com.example.sadic.travelerapp.data.model;

import com.google.gson.annotations.SerializedName;

public class BusInformationItem {

	@SerializedName("busid")
	private String busid;

	@SerializedName("fare")
	private String fare;

	@SerializedName("busregistrationno")
	private String busregistrationno;

	@SerializedName("journyduration")
	private String journyduration;

	@SerializedName("boardingtime")
	private String boardingtime;

	@SerializedName("bustype")
	private String bustype;

	@SerializedName("busdeparturetime")
	private String busdeparturetime;

	@SerializedName("dropingtime")
	private String dropingtime;

	public BusInformationItem() {
	}

	public BusInformationItem(String busid, String fare, String busregistrationno,
							  String journyduration, String boardingtime, String bustype,
							  String busdeparturetime, String dropingtime) {
		this.busid = busid;
		this.fare = fare;
		this.busregistrationno = busregistrationno;
		this.journyduration = journyduration;
		this.boardingtime = boardingtime;
		this.bustype = bustype;
		this.busdeparturetime = busdeparturetime;
		this.dropingtime = dropingtime;
	}

	public void setBusid(String busid){
		this.busid = busid;
	}

	public String getBusid(){
		return busid;
	}

	public void setFare(String fare){
		this.fare = fare;
	}

	public String getFare(){
		return fare;
	}

	public void setBusregistrationno(String busregistrationno){
		this.busregistrationno = busregistrationno;
	}

	public String getBusregistrationno(){
		return busregistrationno;
	}

	public void setJournyduration(String journyduration){
		this.journyduration = journyduration;
	}

	public String getJournyduration(){
		return journyduration;
	}

	public void setBoardingtime(String boardingtime){
		this.boardingtime = boardingtime;
	}

	public String getBoardingtime(){
		return boardingtime;
	}

	public void setBustype(String bustype){
		this.bustype = bustype;
	}

	public String getBustype(){
		return bustype;
	}

	public void setBusdeparturetime(String busdeparturetime){
		this.busdeparturetime = busdeparturetime;
	}

	public String getBusdeparturetime(){
		return busdeparturetime;
	}

	public void setDropingtime(String dropingtime){
		this.dropingtime = dropingtime;
	}

	public String getDropingtime(){
		return dropingtime;
	}

	@Override
 	public String toString(){
		return 
			"BusInformationItem{" +
			"busid = '" + busid + '\'' + 
			",fare = '" + fare + '\'' + 
			",busregistrationno = '" + busregistrationno + '\'' + 
			",journyduration = '" + journyduration + '\'' + 
			",boardingtime = '" + boardingtime + '\'' + 
			",bustype = '" + bustype + '\'' + 
			",busdeparturetime = '" + busdeparturetime + '\'' + 
			",dropingtime = '" + dropingtime + '\'' + 
			"}";
		}
}