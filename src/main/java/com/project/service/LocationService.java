package com.project.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.dao.LocationDAO;
import com.project.model.Location;

@Service
@Transactional
public class LocationService {

	@Autowired
	LocationDAO locationDAO;
	
	public List<Location> listAllLocations() {
		return locationDAO.listAllLocationOrderByCreatedTime();
	}
	
	public Location getLocationByPlaceId(String placeId) {
		return locationDAO.getLocationByPlaceId(placeId);
	}
	
	public void saveLocation(Location location) {
		locationDAO.save(location);
	}
	
	public void deteLocationByPlaceId(String placeId) {
		Location location = locationDAO.getLocationByPlaceId(placeId);
		locationDAO.delete(location);
	}
	
}
