package com.project.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Entity(name="location")
public class Location implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	private String placeId;

	@Temporal(TemporalType.TIMESTAMP)
	private Date createdTime;
	
	@Transient
	private String createdTimeDisplay;

	private String name;
	
	private String country;
	
	@Transient
	private static Location instance;
	
	public Location() {

	}

	public String getPlaceId() {
		return placeId;
	}

	public void setPlaceId(String placeId) {
		this.placeId = placeId;
	}

	public Date getCreatedTime() {
		return createdTime;
	}
	
	public String getCreatedTimeDisplay() {
		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		return df.format(this.createdTime);
	}

	public void setCreatedTimeDisplay(String createdTimeDisplay) {
		this.createdTimeDisplay = createdTimeDisplay;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}
	
	public static Location getInstance(){
		if(instance == null){
			instance = new Location();
		}
		return instance;
	}
	
}
