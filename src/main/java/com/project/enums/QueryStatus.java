package com.project.enums;

import java.util.HashMap;

public enum QueryStatus {

	BASARISIZ, BASARILI;
	
	public String getLabel() {
		if (name().equals("BASARISIZ")) {
			return "Başarısız";
		} else if (name().equals("BASARILI")) {
			return "Başarılı";
		}

		return name();
	}
	
	public Object getValue() {
		return ordinal();
	}

	public static HashMap<String, String> getKeyValue() {
		HashMap<String, String> queryStatus = new HashMap<>();
		for(int i=0; i<QueryStatus.values().length; i++) {
			queryStatus.put(QueryStatus.values()[i].name(), QueryStatus.values()[i].name());
		}
		return queryStatus;
	}
}
