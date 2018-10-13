package com.example.sadic.travelerapp.data.network.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Cupon{

	@SerializedName("coupons")
	private List<CouponsItem> coupons;

	public void setCoupons(List<CouponsItem> coupons){
		this.coupons = coupons;
	}

	public List<CouponsItem> getCoupons(){
		return coupons;
	}

	@Override
 	public String toString(){
		return 
			"Cupon{" + 
			"coupons = '" + coupons + '\'' + 
			"}";
		}
}