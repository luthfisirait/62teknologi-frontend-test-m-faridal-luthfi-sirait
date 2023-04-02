package com.example.mytest.dataApi;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseApi {

	@SerializedName("businesses")
	private List<BusinessesItem> businesses;

	@SerializedName("status")
	private String status;

	public List<BusinessesItem> getBusinesses(){
		return businesses;
	}

	public String getStatus(){
		return status;
	}
}