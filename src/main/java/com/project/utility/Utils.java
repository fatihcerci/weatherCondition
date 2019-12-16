package com.project.utility;

public class Utils {
	
	public static Boolean isStringEmpty(String str) {
		return str == null || str == "" || str == " " || str.trim() == "";
	}
	
}
