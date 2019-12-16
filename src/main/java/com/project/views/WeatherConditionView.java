package com.project.views;

import java.util.List;

public class WeatherConditionView {
	
	private List<String> dayOfWeek;
	
	private List<Double> temperatureMin;
	
	private List<Double> temperatureMax;
	
	public WeatherConditionView() {
		// TODO Auto-generated constructor stub
	}

	public List<String> getDayOfWeek() {
		return dayOfWeek;
	}

	public void setDayOfWeek(List<String> dayOfWeek) {
		this.dayOfWeek = dayOfWeek;
	}

	public List<Double> getTemperatureMin() {
		return temperatureMin;
	}

	public void setTemperatureMin(List<Double> temperatureMin) {
		this.temperatureMin = temperatureMin;
	}

	public List<Double> getTemperatureMax() {
		return temperatureMax;
	}

	public void setTemperatureMax(List<Double> temperatureMax) {
		this.temperatureMax = temperatureMax;
	}

}
