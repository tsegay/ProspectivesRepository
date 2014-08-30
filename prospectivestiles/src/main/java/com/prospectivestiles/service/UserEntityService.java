package com.prospectivestiles.service;

import java.util.List;

import org.springframework.validation.Errors;

import com.prospectivestiles.domain.AccountState;
import com.prospectivestiles.domain.UserEntity;

public interface UserEntityService {

	/**
	 * Creates a userEntity if username is available
	 * 
	 */
	boolean createUserEntity(UserEntity userEntity, Errors errors);
	
	/**
	 * Returns a userEntity by its id
	 * @param userEntityId
	 * @return
	 */
	UserEntity getUserEntity(long userEntityId);
	UserEntity getUserEntityByUsername(String username);
	String getPasswordByUsername(String username);
	List<UserEntity> getAllUserEntities();
	List<UserEntity> findByRole(long roleID);
	
	void updateUserEntity(UserEntity userEntity);
	void delete(UserEntity userEntity);
	void updateTerm(long userEntityId, long termId);
	void updateProgramOfStudy(long userEntityId, long programOfStudyId);
	void updateAccountState(long userEntityId, String accountState);
	void insertIntoUserEntity(long userEntityId, UserEntity userEntity);
	void updatePassword(long userEntityId, String password);

	List<UserEntity> getAllUserEntitiesForPage(int page, int pageSize);
	List<UserEntity> getAllUserEntitiesForPage(int page, int pageSize, String filter, boolean asc);
	
	/*
	 * counts everyone in the system
	 */
	long count();
	/*
	 * To count all students or admins
	 */
	long countByRole(long roleID);
	long countByRoles(List<Long> rolesList);
	boolean hasRoleAdmin(long userEntityId);
	
	List<UserEntity> findUserEntitiesByAccountState(String accountState);
	long countByAccountState(String accountState);
	List<UserEntity> findByEmail(String email);

}
