package com.example.sadic.travelerapp.data.network.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;


public class Seat{

	@SerializedName("seatinformation")
	private List<SeatinformationItem> seatinformation;

	public void setSeatinformation(List<SeatinformationItem> seatinformation){
		this.seatinformation = seatinformation;
	}

	public List<SeatinformationItem> getSeatinformation(){
		return seatinformation;
	}

	@Override
 	public String toString(){
		return 
			"Seat{" + 
			"seatinformation = '" + seatinformation + '\'' + 
			"}";
		}
}