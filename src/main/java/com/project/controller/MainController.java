package com.project.controller;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.project.enums.QueryStatus;
import com.project.enums.UserType;
import com.project.model.Location;
import com.project.model.QueryHistory;
import com.project.model.Session;
import com.project.model.User;
import com.project.service.LocationService;
import com.project.service.MainService;
import com.project.service.QueryHistoryService;
import com.project.service.UserService;
import com.project.utility.Utils;
import com.project.views.AddressView;
import com.project.views.WeatherConditionDayView;

@Controller
public class MainController {
	
	@Autowired
	MainService mainService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	LocationService locationService;
	
	@Autowired
	QueryHistoryService queryHistoryService;
	
	private static Session session;
	
	private List<AddressView> addressList;
	
	@GetMapping("/")
	public String home(HttpServletRequest request) {
		if(session != null && session.getUser() != null){
			request.setAttribute("mode", "MENU_HOME");
			request.setAttribute("user", session.getUser());
		}
		else{
			request.setAttribute("mode", "MENU_LOGIN");
		}
		return "index";
	}
	
	@PostMapping("/login")
	public String login(@RequestParam String username, @RequestParam String password, HttpServletRequest request) {
		User user = userService.getUserByUserNameAndPassword(username, password);
		if(user != null){
			if(session == null){
				session = Session.getInstance();
			}
			session.setUser(user);
			
			request.setAttribute("mode", "MENU_HOME");
			request.setAttribute("user", session.getUser());
			return "redirect:/";
		}
		else{
			request.setAttribute("mode", "MENU_LOGIN");
			request.removeAttribute("user");
			request.setAttribute("errorMessage", "Kullanıcı adı ya da şifre hatalı");
			return "index";
		}
		
	}
	
	
//	@GetMapping("/")
//	public String home(HttpServletRequest request) {
//		request.setAttribute("mode", "MENU_HOME");
//		return "index";
//	}
	
	@GetMapping("/menu1")
	public String menu1(HttpServletRequest request) {
		if(session != null && session.getUser() != null && session.getUser().getUserType().equals(UserType.ADMIN)){
			request.setAttribute("mode", "MENU_1");
			request.setAttribute("user", session.getUser());
			request.setAttribute("locationList", locationService.listAllLocations());
			request.setAttribute("addressList", addressList);
			return "index";
		}
		return "redirect:/";
	}
	
	@GetMapping("/menu2")
	public String menu2(HttpServletRequest request) {
		if(session != null && session.getUser() != null){
			request.setAttribute("mode", "MENU_2");
			request.setAttribute("user", session.getUser());
			request.setAttribute("locationList", locationService.listAllLocations());
			return "index";
		}
		return "redirect:/";
	}
	
	@PostMapping("/searchAddress")
	public String searchAddress(@ModelAttribute AddressView address, BindingResult bindingResult, HttpServletRequest request) {
		try {
			if(session != null && session.getUser() != null && session.getUser().getUserType().equals(UserType.ADMIN)){
				request.setAttribute("user", session.getUser());
				request.setAttribute("mode", "MENU_1");
				request.setAttribute("locations", locationService.listAllLocations());
				
				if(address.getName() != null){
					addressList = mainService.searchAddress(address.getName());
					request.setAttribute("addressList", addressList);
					request.setAttribute("locationList", locationService.listAllLocations());
				}
				else{
					return "redirect:/menu1";
				}
				return "index";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/";
	}
	
	@GetMapping("/saveLocation")
	public String saveLocation(@ModelAttribute AddressView address, BindingResult bindingResult, HttpServletRequest request) {
		if(session != null && session.getUser() != null && session.getUser().getUserType().equals(UserType.ADMIN)){
			if(address != null){
				Location location = locationService.getLocationByPlaceId(address.getPlaceId());
				if(location == null){
					location = Location.getInstance();
					location.setPlaceId(address.getPlaceId());
					location.setCountry(address.getCountry());
					location.setName(address.getName());
					location.setCreatedTime(new Date());
					locationService.saveLocation(location);
				}
				
				request.setAttribute("locationList", locationService.listAllLocations());
				request.setAttribute("addressList", addressList);
				request.setAttribute("user", session.getUser());
				request.setAttribute("mode", "MENU_1");
				return "redirect:/menu1";
			}
		}
		return "redirect:/";
	}
	
	
	@GetMapping("/deleteLocation")
	public String deleteLocation(@RequestParam String placeId, HttpServletRequest request) {
		if(session != null && session.getUser() != null && session.getUser().getUserType().equals(UserType.ADMIN)){
			if(placeId != null && placeId != ""){
				locationService.deteLocationByPlaceId(placeId);
				request.setAttribute("locationList", locationService.listAllLocations());
				request.setAttribute("addressList", addressList);
				request.setAttribute("user", session.getUser());
				request.setAttribute("mode", "MENU_1");
				return "redirect:/menu1";
			}
		}
		return "redirect:/";
	}
	
	@PostMapping("/queryWeatherCondition")
	public String queryWeatherCondition(@ModelAttribute Location location, BindingResult bindingResult, HttpServletRequest request) {
		try {
			if(session != null && session.getUser() != null){
				request.setAttribute("user", session.getUser());
				request.setAttribute("mode", "MENU_2");
				request.setAttribute("locationList", locationService.listAllLocations());
				try {
					if(location.getPlaceId() != null && !location.getPlaceId().equals("null")){
						List<WeatherConditionDayView> weatherConditionList = mainService.queryWeatherCondition(location.getPlaceId(), session.getUser());
						request.setAttribute("weatherConditionList", weatherConditionList);
						request.setAttribute("currentLocation", locationService.getLocationByPlaceId(location.getPlaceId()));
						return "index";
					}
					else{
						return "redirect:/menu2";
					}
				} catch (Exception e) {
					return "index";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/";
	}
	
	@GetMapping("/menu3")
	public String menu3(HttpServletRequest request) {
		if(session != null && session.getUser() != null && session.getUser().getUserType().equals(UserType.ADMIN)){
			request.setAttribute("mode", "MENU_3");
			request.setAttribute("user", session.getUser());
			request.setAttribute("userList", userService.listAllUsers());
			
			HashMap<Integer, String> userTypes = new HashMap<>();
			for(int i=0; i<UserType.values().length; i++) {
				userTypes.put(UserType.values()[i].ordinal(), UserType.values()[i].getLabel());
			}
			request.setAttribute("userTypes", UserType.getKeyValue());
			return "index";
		}
		return "redirect:/";
	}
	
	@GetMapping("/updateUser")
	public String updateUser(@RequestParam Integer id, HttpServletRequest request) {
		try {
			if(session != null && session.getUser() != null && session.getUser().getUserType().equals(UserType.ADMIN)){
				request.setAttribute("user", session.getUser());
				request.setAttribute("mode", "MENU_3");
				request.setAttribute("userList", userService.listAllUsers());
				request.setAttribute("userTypes", UserType.getKeyValue());
				if(id != null) {
					User kayitliUser = userService.getUserById(id);
					
					if(kayitliUser != null){
						request.setAttribute("users", kayitliUser);
						
						if(kayitliUser.getUserName().equals("root")) {
							request.setAttribute("errorMessage", "root kullanıcı güncellenemez");
							request.setAttribute("users", null);
						}
					}
					else{
						request.setAttribute("errorMessage", "Kullanıcı bulunamadı");
					}
				}
				else{
					request.setAttribute("errorMessage", "Parametre boş olamaz");
				}
				return "index";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/menu3";
	}
	
	@PostMapping("/saveUser")
	public String saveUser(@ModelAttribute User user, BindingResult bindingResult, HttpServletRequest request) {
		if(session != null && session.getUser() != null && session.getUser().getUserType().equals(UserType.ADMIN)){
			request.setAttribute("user", session.getUser());
			request.setAttribute("mode", "MENU_3");
			request.setAttribute("userTypes", UserType.getKeyValue());
			
			if(user != null){
				String errorMessage = userService.validateUser(user);
				if(Utils.isStringEmpty(errorMessage)) {
					userService.saveOrUpdateUser(user);
					request.setAttribute("userList", userService.listAllUsers());
				}
				else{
//					request.setAttribute("errorMessage", errorMessage);
					request.setAttribute("userErrorMessage", errorMessage);
					request.setAttribute("userList", userService.listAllUsers());
					return "index";
				}
			}
			return "redirect:/menu3";
		}
		return "redirect:/";
	}
	

	@GetMapping("/deleteUser")
	public String deleteUser(@RequestParam Integer id, HttpServletRequest request) {
		try {
			if(session != null && session.getUser() != null && session.getUser().getUserType().equals(UserType.ADMIN)){
				if(id != null) {
					userService.deleteUserById(id);
					
					if(id == session.getUser().getId()) {
						session.setUser(null);
						return "redirect:/";
					}
				}
				else{
					request.setAttribute("errorMessage", "Parametre boş olamaz");
				}
				request.setAttribute("user", session.getUser());
				request.setAttribute("mode", "MENU_3");
				request.setAttribute("userList", userService.listAllUsers());
				request.setAttribute("userTypes", UserType.getKeyValue());
				return "index";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/menu3";
	}
	
	@GetMapping("/menu4")
	public String menu4(HttpServletRequest request) {
		if(session != null && session.getUser() != null){
			request.setAttribute("mode", "MENU_4");
			request.setAttribute("user", session.getUser());
//			request.setAttribute("queryHistoryList", queryHistoryService.listAllQueryHistory());
			request.setAttribute("queryStatuses", QueryStatus.getKeyValue());
			request.setAttribute("users", queryHistoryService.listUsers());
			return "index";
		}
		return "redirect:/";
	}
	
	@PostMapping("/searchQueryHistory")
	public String searchQueryHistory(@ModelAttribute QueryHistory queryHistory, BindingResult bindingResult, HttpServletRequest request) {
		if(session != null && session.getUser() != null){
			Date timeStart = null;
			Date timeEnd = null;
			
			if(!Utils.isStringEmpty(queryHistory.getTimeStart())) {
				LocalDateTime localDateTime = LocalDateTime.parse(queryHistory.getTimeStart());
		    	timeStart = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
			}
			if(!Utils.isStringEmpty(queryHistory.getTimeEnd())) {
				LocalDateTime localDateTime = LocalDateTime.parse(queryHistory.getTimeEnd());
				timeEnd = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
			}
			    
			request.setAttribute("mode", "MENU_4");
			request.setAttribute("user", session.getUser());
			request.setAttribute("queryStatuses", QueryStatus.getKeyValue());
			request.setAttribute("users", queryHistoryService.listUsers());
			request.setAttribute("queryHistoryList", queryHistoryService.listQueryHistoryWithParams(queryHistory.getUserId(), timeStart, timeEnd, queryHistory.getLocationName(), queryHistory.getQueryStatus()));
			return "index";
		}
		return "redirect:/";
	}
	
	@GetMapping("/logout")
	public String logout(HttpServletRequest request) {
		session.setUser(null);
		request.setAttribute("mode", "MENU_LOGIN");
		
		return "redirect:/";
	}
	
}
