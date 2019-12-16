package com.project.views;

public class WeatherConditionDayView {
	
	private String day;
	
	private String max;
	
	private String min;
	
	public WeatherConditionDayView() {
		// TODO Auto-generated constructor stub
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public String getMax() {
		if(max == null){
			return "Unknown";
		}
		return max;
	}

	public void setMax(String max) {
		this.max = max;
	}

	public String getMin() {
		if(min == null){
			return "Unknown";
		}
		return min;
	}

	public void setMin(String min) {
		this.min = min;
	}

	@Override
	public String toString() {
		return "WeatherConditionDay [day=" + day + ", max=" + max + ", min=" + min + "]";
	}
	
}
