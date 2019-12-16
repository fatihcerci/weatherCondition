package com.project.views;

public class UserView {
	
	private Integer userId;
	
	private String userName;
	
	
	public UserView() {
		// TODO Auto-generated constructor stub
	}
	
	public UserView(Integer userId, String userName) {
		this.userId = userId;
		this.userName = userName;
	}


	public Integer getUserId() {
		return userId;
	}


	public void setUserId(Integer userId) {
		this.userId = userId;
	}


	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}


	
}
