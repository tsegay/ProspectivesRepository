/* 
 * Copyright (c) 2013 Manning Publications Co.
 * 
 * Book: http://manning.com/wheeler/
 * Blog: http://springinpractice.com/
 * Code: https://github.com/springinpractice
 */
package com.prospectivestiles.dao.hbn;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.util.ReflectionUtils;

import com.prospectivestiles.dao.Dao;



// Don't put @Transactional here. It's not that it's inherently wrong--indeed it would allow us to avoid some pass-
// though service bean methods--but using @Transactional causes Spring to create proxies, and recipe 10.3 assumes that
// it has direct access to the DAOs. I don't think we're doing direct DAO injects into controllers anywhere. [WLW]

public abstract class AbstractHbnDao<T extends Object> implements Dao<T> {
	@Inject private SessionFactory sessionFactory;
	private Class<T> domainClass;
	
	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}
	
	@SuppressWarnings("unchecked")
	private Class<T> getDomainClass() {
	    if (domainClass == null) {
	    	ParameterizedType thisType = (ParameterizedType) getClass().getGenericSuperclass();
	        this.domainClass = (Class<T>) thisType.getActualTypeArguments()[0];
	    }
	    return domainClass;
	}
	
	private String getDomainClassName() { return getDomainClass().getName(); }
	
	@Override
	public void create(T t) {
		
		// If there's a setDateCreated() method, then set the date.
		Method method = ReflectionUtils.findMethod(
				getDomainClass(), "setDateCreated", new Class[] { Date.class });
		if (method != null) {
			try {
				method.invoke(t, new Date());
			} catch (Exception e) {
				// Ignore any exception here; simply abort the setDate() attempt
			}
		}
		
		getSession().save(t);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public T find(Serializable id) {
		return (T) getSession().get(getDomainClass(), id);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public T load(Serializable id) {
		return (T) getSession().load(getDomainClass(), id);
	}
	
	@SuppressWarnings("unchecked")
	public List<T> findAll() {
		return getSession()
			.createQuery("from " + getDomainClassName())
			.list();
	}
	/**
	 * select all where visible is true. 
	 * to hide an item from being displayed set the visible value to false
	 * @return
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<T> findAllVisible() {
		return getSession()
				.createQuery("from " + getDomainClassName() + " where visible = " + true)
				.list();
	}
	@Override
	public void update(T t) { getSession().update(t); }
	
	@Override
	public void delete(T t) { getSession().delete(t); }
	
	@Override
	public void deleteById(Serializable id) { delete(load(id)); }
	
	@Override
	public void deleteAll() {
		getSession()
			.createQuery("delete " + getDomainClassName())
			.executeUpdate();
	}
	
	@Override
	public long count() {
		return (Long) getSession()
			.createQuery("select count(*) from " + getDomainClassName())
			.uniqueResult();
	}
	
	@Override
	public boolean exists(Serializable id) { return (find(id) != null); }
}
