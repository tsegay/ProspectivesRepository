/* 
 * Copyright (c) 2013 Manning Publications Co.
 * 
 * Book: http://manning.com/wheeler/
 * Blog: http://springinpractice.com/
 * Code: https://github.com/springinpractice
 */
package com.prospectivestiles.dao;

import java.io.Serializable;
import java.util.List;

public interface Dao<T extends Object> {
	
	/**
	 * If the passed object has a <code>setDateCreated(Date)</code> method then we call it, 
	 * passing in the current timestamp.
	 * 
	 * @param t
	 */
	void create(T t);
	
	/**
	 * Finds the requested object in the repository and returns it, or null if there is no such persistent instance.
	 * 
	 * @param id ID
	 * @return requested object, or null
	 */
	T find(Serializable id);
	
	/**
	 * <p>
	 * Returns either a proxy for the requested object (one having the right class and ID), or else the actual object
	 * if it's available without hitting the repository (e.g. in cache). The basic idea behind this method is to allow
	 * apps establish references to the requested object without requiring a call to the repository.
	 * </p>
	 * <p>
	 * Use this method only if you assume the instance actually exists; i.e., non-existence is an exception.
	 * </p>
	 * 
	 * @param id
	 * @return
	 */
	T load(Serializable id);
	
	List<T> findAll();
	
	/**
	 * select all where visible is true. 
	 * to hide an item from being displayed set the visible value to false
	 * @return
	 */
	List<T> findAllVisible();
	
	void update(T t);
	
	void delete(T t);
	
	void deleteById(Serializable id);
	
	void deleteAll();
	
	long count();
	
	boolean exists(Serializable id);

}
