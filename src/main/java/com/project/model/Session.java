package com.project.model;

public class Session {

	private User user;
	
	private static Session _instance;
	
	public Session() {

	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public static Session getInstance() {
		if(_instance == null){
			_instance = new Session();
		}
		return _instance;
	}
	
}
