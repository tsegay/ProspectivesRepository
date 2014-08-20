package com.prospectivestiles.dao;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.prospectivestiles.domain.AccountState;
import com.prospectivestiles.domain.Term;
import com.prospectivestiles.domain.UserEntity;

public interface UserEntityDao extends Dao<UserEntity>, UserDetailsService {
	
	// ##################
//	findEvaluationsByStatus
	/**
	 * to find users by their status. eg admitted students, inprocess students etc
	 * used in pages: admittedStudents.jsp, completeStudents.jsp, inprocessStudents.jsp
	 * @param accountState
	 * @return
	 */
	List<UserEntity> findUserEntitiesByAccountState(String accountState);
//	List<Evaluation> findEvaluationsByStatus(String status);
	long countByAccountState(String accountState);
	
	// ##################
	
	void createUserEntity(UserEntity userEntity);
	
	UserEntity findByUsername(String username);
	/**
	 * This method returns list of email because email is not a unique field
	 * Make email a unique field using the method validateEmail in UserEntityServiceImpl
	 */
	List<UserEntity> findByEmail(String email);

	String getPasswordByUsername(String username);
	
	
	void insertTerm(long userEntityId, long termId);
	
	void insertProgramOfStudy(long userEntityId, long programOfStudyId);
	/**
	 * Using JDBC to update userEntity
	 * @param userEntityId
	 * @param userEntity
	 */
	void insertIntoUserEntity(long userEntityId, UserEntity userEntity);
	void insertAccountState(long userEntityId, String accountState);
//	void insertAccountState(long userEntityId, AccountState accountState);
	void updatePassword(long userEntityId, String password);
	
	List<UserEntity> findByRole(long roleID);
	
	// is the user an admin?
	boolean hasRoleAdmin(long userEntityId);
	
	// used for pagination
	// put it in dao.java
	List<UserEntity> findAll(int page, int pageSize);

	List<UserEntity> findAll(int page, int pageSize, String filter, boolean asc);

	/*
	 * To count all students or admins
	 */
	long countByRole(long roleID);

	
}
