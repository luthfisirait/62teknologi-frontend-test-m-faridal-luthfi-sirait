package com.example.mytest.dataApi;

import com.google.gson.annotations.SerializedName;

public class BusinessesItem{

	@SerializedName("address")
	private String address;

	@SerializedName("city")
	private String city;

	@SerializedName("latitude")
	private double latitude;

	@SerializedName("rating")
	private double rating;

	@SerializedName("review_count")
	private int reviewCount;

	@SerializedName("url")
	private String url;

	@SerializedName("zip_code")
	private String zipCode;

	@SerializedName("phone")
	private String phone;

	@SerializedName("name")
	private String name;

	@SerializedName("categories")
	private String categories;

	@SerializedName("id")
	private int id;

	@SerializedName("state")
	private String state;

	@SerializedName("longitude")
	private double longitude;

	public double getJarak() {
		return jarak;
	}

	public void setJarak(double jarak) {
		this.jarak = jarak;
	}

	private double jarak;

	public String getAddress(){
		return address;
	}

	public String getCity(){
		return city;
	}

	public double getLatitude(){
		return latitude;
	}

	public double getRating(){
		return rating;
	}

	public int getReviewCount(){
		return reviewCount;
	}

	public String getUrl(){
		return url;
	}

	public String getZipCode(){
		return zipCode;
	}

	public String getPhone(){
		return phone;
	}

	public String getName(){
		return name;
	}

	public String getCategories(){
		return categories;
	}

	public int getId(){
		return id;
	}

	public String getState(){
		return state;
	}

	public double getLongitude(){
		return longitude;
	}
}