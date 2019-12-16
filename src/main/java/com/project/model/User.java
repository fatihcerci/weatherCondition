package com.project.model;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.project.enums.UserType;

@Entity(name="user")
public class User {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdTime;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedTime;

	private String userName;
	
	private String password;
	
	private String name;
	
	private String surName;
	
	private UserType userType;
	
	@Transient
	private String createdTimeDisplay;
	
	@Transient
	private String updatedTimeDisplay;
	
	@Transient
	private String userTypeDisplay;
	
	public User() {

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public Date getUpdatedTime() {
		return updatedTime;
	}

	public void setUpdatedTime(Date updatedTime) {
		this.updatedTime = updatedTime;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurName() {
		return surName;
	}

	public void setSurName(String surName) {
		this.surName = surName;
	}

	public UserType getUserType() {
		return userType;
	}

	public void setUserType(UserType userType) {
		this.userType = userType;
	}

	public String getCreatedTimeDisplay() {
		if(this.createdTime != null) {
			SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			return df.format(this.createdTime);
		}
		return null;
	}

	public void setCreatedTimeDisplay(String createdTimeDisplay) {
		this.createdTimeDisplay = createdTimeDisplay;
	}

	public String getUpdatedTimeDisplay() {
		if(this.updatedTime != null) {
			SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			return df.format(this.updatedTime);
		}
		return null;
	}

	public void setUpdatedTimeDisplay(String updatedTimeDisplay) {
		this.updatedTimeDisplay = updatedTimeDisplay;
	}
	
}
