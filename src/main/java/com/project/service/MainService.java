package com.project.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.project.dao.QueryHistoryDAO;
import com.project.enums.QueryStatus;
import com.project.model.Location;
import com.project.model.QueryHistory;
import com.project.model.User;
import com.project.views.AddressView;
import com.project.views.LocationView;
import com.project.views.WeatherConditionDayView;
import com.project.views.WeatherConditionView;

@Service
@Transactional
public class MainService {
	
	@Autowired
	private QueryHistoryDAO queryHistoryDAO;
	
	@Autowired
	private LocationService locationService;
	
	public List<AddressView> searchAddress(String address) throws Exception {
		String host = "https://api.weather.com/v3/location/";
		String apiKey = "2971f0f9accd4888b1f0f9accdd888d6";
	    String query = URLEncoder.encode (address, "UTF-8");
	    String params = "search?query=" + query + "&locationType=locid&language=tr-TR&format=json&apiKey=" + apiKey;
	    
	    URL url = new URL(host + params);
	    
	    HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
	    connection.setRequestMethod("GET");
	    connection.setRequestProperty("Ocp-Apim-Subscription-Key", "Şehir giriniz");
	    connection.setDoOutput(true);
	    
	    StringBuilder response = new StringBuilder ();
	    BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
	    
	    String line;

	    while ((line = in.readLine()) != null) {
	      response.append(line);
	    }
	    in.close();
	    
	    List<AddressView> addresses = new ArrayList<>();
	    if(response != null) {
	    	LocationView locationView = getLocationView(response.toString());
			
			for(int i=0; i<locationView.getAddress().size(); i++){
				AddressView view = new AddressView();
				
				view.setName(locationView.getAddress().get(i));
				view.setCountry(locationView.getCountry().get(i));
				view.setPlaceId(locationView.getPlaceId().get(i));
				
				addresses.add(view);
			}
	    }
	    return addresses;
	}
	
	@SuppressWarnings("deprecation")
	public LocationView getLocationView(String json_text) {
		JsonParser parser = new JsonParser();
		JsonObject json = parser.parse(json_text).getAsJsonObject();
		Gson gson = new GsonBuilder().setPrettyPrinting().create();

		LocationView locationView = gson.fromJson(json.get("location"), LocationView.class);
		
		return locationView;
	}
	
	public List<WeatherConditionDayView> queryWeatherCondition(String placeId, User user) throws Exception {
		long start = System.currentTimeMillis();
		
		String userIpAdress = ""; 
        try
        { 
            URL url_name = new URL("http://bot.whatismyipaddress.com"); 
            BufferedReader sc = 
            new BufferedReader(new InputStreamReader(url_name.openStream())); 
            userIpAdress = sc.readLine().trim(); 
        } 
        catch (Exception e) 
        { 
        	userIpAdress = "Cannot Execute Properly"; 
        } 
        
		QueryHistory queryHistory = new QueryHistory();
		
		Location location = locationService.getLocationByPlaceId(placeId);
		queryHistory.setLocationId(placeId);
		queryHistory.setLocationName(location.getName() + ", " + location.getCountry());
		queryHistory.setUserId(user.getId());
		queryHistory.setUserName(user.getUserName());
		queryHistory.setTime(new Date());
		queryHistory.setUserIpAdress(userIpAdress);
		
		List<WeatherConditionDayView> weatherConditionDayViews = new ArrayList<>();
		
		try {
			String host = "https://api.weather.com/v3/wx/forecast/daily/";
			String apiKey = "2971f0f9accd4888b1f0f9accdd888d6";
		    String query = URLEncoder.encode (placeId, "UTF-8");
		    String params = "5day?placeid=" + query + "&units=e&language=tr-TR&format=json&apiKey=" + apiKey;
		    
		    URL url = new URL(host + params);
		    
		    HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
		    connection.setRequestMethod("GET");
		    connection.setRequestProperty("Ocp-Apim-Subscription-Key", "Lokasyon giriniz");
		    connection.setDoOutput(true);
		    
		    StringBuilder response = new StringBuilder ();
		    BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		    
		    String line;

		    while ((line = in.readLine()) != null) {
		      response.append(line);
		    }
		    in.close();
		    
		    WeatherConditionView weatherConditionView = getWeatherConditionView(response.toString());

		    for(int i=0; i<weatherConditionView.getDayOfWeek().size(); i++){
		    	WeatherConditionDayView view = new WeatherConditionDayView();
		    	Double max = weatherConditionView.getTemperatureMax().get(i);
		    	Double min = weatherConditionView.getTemperatureMin().get(i);
		    	
		    	BigDecimal maxInt = null;
		    	BigDecimal minInt = null;
		    	
		    	if(max != null){
		    		max = max - new Double(32);
		    		max = max / new Double(1.8);
		    		maxInt = new BigDecimal(max.toString());
		    	}
		    	
		    	if(min != null){
		    		min = min - new Double(32);
		    		min = min / new Double(1.8);
		    		minInt = new BigDecimal(min.toString());
		    	}
		    	
		    	view.setDay(weatherConditionView.getDayOfWeek().get(i).toString());
		    	view.setMax(maxInt != null ? maxInt.setScale(0, RoundingMode.HALF_UP).toString() : null);
		    	view.setMin(minInt != null ? minInt.setScale(0, RoundingMode.HALF_UP).toString() : null);
		    	
		    	weatherConditionDayViews.add(view);
		    }
		    
		    String result = "";
		    
		    for (WeatherConditionDayView weatherConditionDayView : weatherConditionDayViews) {
				result = result.concat(weatherConditionDayView.toString() + " ");
			}
		    
		    queryHistory.setResult(result);
		    queryHistory.setQueryStatus(QueryStatus.BASARILI);
		} catch (Exception e) {
			queryHistory.setQueryStatus(QueryStatus.BASARISIZ);
		}
		finally {
			long elapsedTime = System.currentTimeMillis() - start;
			queryHistory.setElapsedTime(elapsedTime);
			queryHistoryDAO.save(queryHistory);
		}
		return weatherConditionDayViews;
	}
	
	
	@SuppressWarnings({ "deprecation", "unchecked" })
	public WeatherConditionView getWeatherConditionView(String json_text) {
		JsonParser parser = new JsonParser();
		JsonObject json = parser.parse(json_text).getAsJsonObject();
		Gson gson = new GsonBuilder().setPrettyPrinting().create();

		WeatherConditionView weatherConditionView = new WeatherConditionView(); 
				
		weatherConditionView.setDayOfWeek(gson.fromJson(json.get("dayOfWeek"), ArrayList.class));
		weatherConditionView.setTemperatureMax(gson.fromJson(json.get("temperatureMax"), ArrayList.class));
		weatherConditionView.setTemperatureMin(gson.fromJson(json.get("temperatureMin"), ArrayList.class));
		
		return weatherConditionView;
	}
}
