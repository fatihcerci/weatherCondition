package com.project.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.project.enums.QueryStatus;
import com.project.model.QueryHistory;
import com.project.utility.Utils;
import com.project.views.UserView;


@Repository
public class QueryHistoryDAO {
	

	@PersistenceContext
	private EntityManager entityManager;

	public EntityManager getEntityManager() {
		return entityManager;
	}
	
	public CriteriaBuilder getCriteriaBuilder() {
		return getEntityManager().getCriteriaBuilder();
	}
	
	public Query createQuery(String qlString) {
		return getEntityManager().createQuery(qlString);
	}

	public <T> TypedQuery<T> createQuery(CriteriaQuery<T> criteriaQuery) {
		return getEntityManager().createQuery(criteriaQuery);
	}

	@SuppressWarnings("rawtypes")
	public Query createQuery(CriteriaUpdate updateQuery) {
		return getEntityManager().createQuery(updateQuery);
	}

	@SuppressWarnings("rawtypes")
	public Query createQuery(CriteriaDelete deleteQuery) {
		return getEntityManager().createQuery(deleteQuery);
	}

	public <T> TypedQuery<T> createQuery(String qlString, Class<T> resultClass) {
		return getEntityManager().createQuery(qlString, resultClass);
	}

	public Query createNativeQuery(String sqlString) {
		return getEntityManager().createNativeQuery(sqlString);
	}

	@SuppressWarnings("rawtypes")
	public Query createNativeQuery(String sqlString, Class resultClass) {
		return getEntityManager().createNativeQuery(sqlString, resultClass);
	}

	public Query createNativeQuery(String sqlString, String resultSetMapping) {
		return getEntityManager().createNativeQuery(sqlString, resultSetMapping);
	}
	
	public <T> void save(T entity) {
		getEntityManager().persist(entity);
	}

	public <T> void update(T entity) {
		EntityManager em = getEntityManager();
		if(!em.contains(entity) ) {
			entity = em.merge(entity);
		}
	}
	
	public <T> void delete(T entity) {
		getEntityManager().remove(entity);
	}
	
	public List<QueryHistory> listQueryHistoryWithParams(Integer userId, Date timeStart, Date timeEnd, String locationName, QueryStatus queryStatus) {
		CriteriaBuilder cb = getCriteriaBuilder();
		CriteriaQuery<QueryHistory> cq = cb.createQuery(QueryHistory.class);
		Root<QueryHistory> root = cq.from(QueryHistory.class);
		cq.select(root);
		
		List<Predicate> predicates = new ArrayList<Predicate>();
		
		if(userId != null) {
			predicates.add(cb.equal(root.get("userId"), userId));
		}
		if(timeStart != null) {
			predicates.add(cb.greaterThanOrEqualTo(root.get("time"), timeStart));
		}
		if(timeEnd != null) {
			predicates.add(cb.lessThanOrEqualTo(root.get("time"), timeEnd));
		}
		if(!Utils.isStringEmpty(locationName)){
			predicates.add(cb.like(root.get("locationName"), "%"+locationName+"%"));
		}
		if(queryStatus != null) {
			predicates.add(cb.equal(root.get("queryStatus"), queryStatus));
		}
		
		cq.where(predicates.toArray(new Predicate[]{}));
		
		cq.orderBy(cb.desc(root.get("time")));
		
		List<QueryHistory> queryHistoryList = null;
		try {
			queryHistoryList = createQuery(cq).getResultList();
		} catch (NoResultException e) {
		}
		return queryHistoryList;
	}
	
	public List<QueryHistory> listAllQueryHistory() {
		CriteriaBuilder cb = getCriteriaBuilder();
		CriteriaQuery<QueryHistory> cq = cb.createQuery(QueryHistory.class);
		Root<QueryHistory> root = cq.from(QueryHistory.class);
		cq.select(root);
		cq.orderBy(cb.desc(root.get("time")));
		
		List<QueryHistory> queryHistoryList = null;
		try {
			queryHistoryList = createQuery(cq).getResultList();
		} catch (NoResultException e) {
		}
		return queryHistoryList;
	}
	
	public List<UserView> listUsers() {
		CriteriaBuilder cb = getCriteriaBuilder();
		CriteriaQuery<UserView> cq = cb.createQuery(UserView.class);
		Root<QueryHistory> root = cq.from(QueryHistory.class);
		cq.multiselect(root.get("userId"), root.get("userName")).distinct(true);
		
		List<UserView> userList = null;
		try {
			userList = createQuery(cq).getResultList();
		} catch (NoResultException e) {
		}
		return userList;
	}
}
