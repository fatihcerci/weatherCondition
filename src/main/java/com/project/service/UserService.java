package com.project.service;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.dao.UserDAO;
import com.project.model.User;
import com.project.utility.Utils;

@Service
@Transactional
public class UserService {

	@Autowired
	UserDAO userDAO;
	
	public User getUserByUserNameAndPassword(String userName, String password) {
		return userDAO.getUserByUserNameAndPassword(userName, password);
	}
	
	public List<User> listAllUsers() {
		return userDAO.listAllUsersOrderByCreatedTime();
	}
	
	public User getUserById(Integer id) {
		return userDAO.getUserById(id);
	}
	
	public void saveOrUpdateUser(User user) {
		if(user.getId() != null) {
			User kayitliUser = getUserById(user.getId());
			
			kayitliUser.setName(user.getName());
			kayitliUser.setPassword(user.getPassword());
			kayitliUser.setSurName(user.getSurName());
			kayitliUser.setUserName(user.getUserName());
			kayitliUser.setUserType(user.getUserType());
			kayitliUser.setUpdatedTime(new Date());
			
			userDAO.update(kayitliUser);
		}
		else{
			user.setCreatedTime(new Date());
			userDAO.save(user);
		}
	}
	
	public void deleteUserById(Integer id) {
		if(id != null) {
			User kayitliUser = getUserById(id);
			userDAO.delete(kayitliUser);
		}
	}
	
	public String validateUser(User user) {
		String msg = "";
		if(Utils.isStringEmpty(user.getName())) {
			msg = msg + "Adı boş olamaz<br>";
		}
		if(Utils.isStringEmpty(user.getSurName())) {
			msg = msg + "Soyadı boş olamaz<br>";
		}
		if(Utils.isStringEmpty(user.getUserName())) {
			msg = msg + "Kullanıcı Adı boş olamaz<br>";
		}
		if(Utils.isStringEmpty(user.getPassword())) {
			msg = msg + "Şifre boş olamaz<br>";
		}
		return msg;
	}
}
