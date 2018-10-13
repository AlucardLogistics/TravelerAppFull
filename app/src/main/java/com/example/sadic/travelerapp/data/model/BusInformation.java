package com.example.sadic.travelerapp.data.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class BusInformation{

	@SerializedName("businformation")
	private List<BusInformationItem> businformation;

	public void setBusinformation(List<BusInformationItem> businformation){
		this.businformation = businformation;
	}

	public List<BusInformationItem> getBusinformation(){
		return businformation;
	}

	@Override
 	public String toString(){
		return 
			"BusInformation{" + 
			"businformation = '" + businformation + '\'' + 
			"}";
		}
}