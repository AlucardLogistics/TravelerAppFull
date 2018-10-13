package com.example.sadic.travelerapp.data.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class City {

	@SerializedName("city")
	private List<CityItem> city;

	public void setCity(List<CityItem> city){
		this.city = city;
	}

	public List<CityItem> getCity(){
		return city;
	}

	@Override
 	public String toString(){
		return 
			"City{" +
			"city = '" + city + '\'' + 
			"}";
		}
}