package com.prospectivestiles.dao;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.prospectivestiles.domain.AccountState;
import com.prospectivestiles.domain.Term;
import com.prospectivestiles.domain.UserEntity;

public interface UserEntityDao extends Dao<UserEntity>, UserDetailsService {
	
	/**
	 * to find users by their status. eg admitted students, inprocess students etc
	 * used in pages: admittedStudents.jsp, completeStudents.jsp, inprocessStudents.jsp
	 * @param accountState
	 * @return
	 */
	List<UserEntity> findUserEntitiesByAccountState(String accountState);
	long countByAccountState(String accountState);
	
	void createUserEntity(UserEntity userEntity);
	
	UserEntity findByUsername(String username);
	UserEntity findById(long userEntityId);
	/**
	 * Email is a unique field, this should not return a list
	 */
	List<UserEntity> findByEmail(String email);

	String getPasswordByUsername(String username);
	
	/**
	 * used for pagination
	 * put it in dao.java
	 * @param page
	 * @param pageSize
	 * @return
	 */
	List<UserEntity> findAll(int page, int pageSize);

	List<UserEntity> findAll(int page, int pageSize, String filter, boolean asc);

	List<UserEntity> findByRole(long roleID);
	
	/**
	 * To count all students or admins
	 */
	long countByRole(long roleID);
	long countByRoles(List<Long> rolesList);
	
	/**
	 * is the user an admin?
	 * @param userEntityId
	 * @return
	 */
	boolean hasRoleAdmin(long userEntityId);
	boolean hasRoleAdmissionOrAssist(long userEntityId);
	List<UserEntity> getAccountsByTermStatusState(long termId, boolean status,
			String accountState);
	long countAccountsByTermStatusState(long termId, boolean status,
			String accountState);
	
//	void updateTerm(long userEntityId, long termId);
//	void updateProgramOfStudy(long userEntityId, long programOfStudyId);
//	/**
//	 * Using JDBC to update userEntity
//	 * @param userEntityId
//	 * @param userEntity
//	 */
//	void insertIntoUserEntity(long userEntityId, UserEntity userEntity);
//	/**
//	 * Using JDBC to create userEntity
//	 * @param userEntity
//	 */
//	void insertUserEntity(UserEntity userEntity);
//	void updateAccountState(long userEntityId, String accountState);
//	void insertAccountState(long userEntityId, AccountState accountState);
//	void updatePassword(long userEntityId, String password);
	


	
}
