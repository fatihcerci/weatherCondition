package com.project.views;

import java.util.List;

public class LocationView {
	
	private List<String> address;
	
	private List<String> placeId;
	
	private List<String> country;
	
	public LocationView() {
		// TODO Auto-generated constructor stub
	}

	public List<String> getAddress() {
		return address;
	}

	public void setAddress(List<String> address) {
		this.address = address;
	}

	public List<String> getPlaceId() {
		return placeId;
	}

	public void setPlaceId(List<String> placeId) {
		this.placeId = placeId;
	}

	public List<String> getCountry() {
		return country;
	}

	public void setCountry(List<String> country) {
		this.country = country;
	}

}
