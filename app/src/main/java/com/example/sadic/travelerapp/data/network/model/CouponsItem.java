package com.example.sadic.travelerapp.data.network.model;

import com.google.gson.annotations.SerializedName;

public class CouponsItem{

	@SerializedName("id")
	private String id;

	@SerializedName("couponno")
	private String couponno;

	@SerializedName("discount")
	private String discount;

	public void setDiscount(String discount){
		this.discount = discount;
	}

	public String getDiscount(){
		return discount;
	}

	public void setCouponno(String couponno){
		this.couponno = couponno;
	}

	public String getCouponno(){
		return couponno;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	@Override
	public String toString() {
		return "CouponsItem{" +
				"id='" + id + '\'' +
				", couponno='" + couponno + '\'' +
				", discount='" + discount + '\'' +
				'}';
	}
}