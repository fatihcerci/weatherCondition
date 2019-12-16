package com.project.dao;

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
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.project.model.User;

@Repository
public class UserDAO {
	

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
	
	public User getUserByUserNameAndPassword(String userName, String password) {
		CriteriaBuilder cb = getCriteriaBuilder();
		CriteriaQuery<User> cq = cb.createQuery(User.class);
		Root<User> root = cq.from(User.class);
		cq.select(root);
		cq.where(cb.and(
						cb.equal(root.get("userName"), userName), 
						cb.equal(root.get("password"), password)
						));
		
		User user = null;
		try {
			user = createQuery(cq).getSingleResult();
		} catch (NoResultException e) {
		}
		return user;
	}
	
	public User getUserById(Integer id) {
		CriteriaBuilder cb = getCriteriaBuilder();
		CriteriaQuery<User> cq = cb.createQuery(User.class);
		Root<User> root = cq.from(User.class);
		cq.select(root);
		cq.where(cb.equal(root.get("id"), id));
		
		User user = null;
		try {
			user = createQuery(cq).getSingleResult();
		} catch (NoResultException e) {
		}
		return user;
	}
	
	public List<User> listAllUsersOrderByCreatedTime() {
		CriteriaBuilder cb = getCriteriaBuilder();
		CriteriaQuery<User> cq = cb.createQuery(User.class);
		Root<User> root = cq.from(User.class);
		cq.select(root);
		cq.orderBy(cb.desc(root.get("createdTime")));
		List<User> userList = null;
		try {
			userList = createQuery(cq).getResultList();
		} catch (NoResultException e) {
		}
		return userList;
	}
	
}
