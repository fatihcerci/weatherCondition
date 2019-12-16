package com.project.enums;

import java.util.HashMap;

public enum UserType {

	STANDART, ADMIN;
	
	public String getLabel() {
		if (name().equals("STANDART")) {
			return "Standart";
		} else if (name().equals("ADMIN")) {
			return "Admin";
		}

		return name();
	}
	
	public Object getValue() {
		return ordinal();
	}
	
	public static HashMap<String, String> getKeyValue() {
		HashMap<String, String> userTypes = new HashMap<>();
		for(int i=0; i<UserType.values().length; i++) {
			userTypes.put(UserType.values()[i].name(), UserType.values()[i].name());
		}
		return userTypes;
	}
}
