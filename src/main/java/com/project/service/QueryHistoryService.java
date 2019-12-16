package com.project.service;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.dao.QueryHistoryDAO;
import com.project.enums.QueryStatus;
import com.project.model.QueryHistory;
import com.project.views.UserView;

@Service
@Transactional
public class QueryHistoryService {

	@Autowired
	QueryHistoryDAO queryHistoryDAO;
	
	public List<QueryHistory> listAllQueryHistory() {
		return queryHistoryDAO.listAllQueryHistory();
	}
	
	public List<QueryHistory> listQueryHistoryWithParams(Integer userId, Date timeStart, Date timeEnd, String locationName, QueryStatus queryStatus) {
		return queryHistoryDAO.listQueryHistoryWithParams(userId, timeStart, timeEnd, locationName, queryStatus);
	}
	
	public List<UserView> listUsers() {
		return queryHistoryDAO.listUsers();
	}
	
}
