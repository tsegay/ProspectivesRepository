package com.prospectivestiles.dao;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.prospectivestiles.domain.Term;
import com.prospectivestiles.domain.UserEntity;

public interface UserEntityDao extends Dao<UserEntity>, UserDetailsService {
	
//	void create(Account account, String password);
	void createUserEntity(UserEntity userEntity);
	
	UserEntity findByUsername(String username);
	

	String getPasswordByUsername(String username);
	
	// I SHOULD USE THIS METHOD INSTEAD
	/*Account getByUsername(String username);*/
	
	void insertTerm(long userEntityId, long termId);
	void insertProgramOfStudy(long userEntityId, long programOfStudyId);
	/**
	 * Using JDBC to update userEntity
	 * @param userEntityId
	 * @param userEntity
	 */
	void insertIntoUserEntity(long userEntityId, UserEntity userEntity);
	
	List<UserEntity> findByRole(long roleID);
	
	// used for pagination
	// put it in dao.java
	List<UserEntity> findAll(int page, int pageSize);

	List<UserEntity> findAll(int page, int pageSize, String filter, boolean asc);

	/*
	 * To count all students or admins
	 */
	long countByRole(long roleID);

	
}
