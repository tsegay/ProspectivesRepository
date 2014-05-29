package com.prospectivestiles.service;

import java.util.List;

import org.springframework.validation.Errors;

import com.prospectivestiles.domain.UserEntity;

public interface UserEntityService {

	/**
	 * Registers the given account, but only if the account and password are valid. This method can perform checks
	 * against the database (e.g., duplicate username or e-mail) to assess validity.
	 * 
	 * @param account
	 *            account data
	 * @param password
	 *            user password
	 * @param errors
	 *            object for collecting and logging errors
	 * @return flag indicating whether the account was registered
	 */
//	boolean registerUserEntity(UserEntity userEntity, Errors errors);
	boolean createUserEntity(UserEntity userEntity, Errors errors);
	
	/**
	 * Returns the requested account with roles hydrated, or <code>null</code> if no such account exists.
	 * 
	 * @return the requested account, or <code>null</code> if it doesn't exist
	 */
	UserEntity getUserEntity(long userEntityId);
	
	UserEntity getUserEntityByUsername(String username);
	
	String getPasswordByUsername(String username);

	List<UserEntity> getAllUserEntities();
	
	/*void createUserEntity(UserEntity userEntity);*/
	void updateUserEntity(UserEntity userEntity);
	void delete(UserEntity userEntity);
//	void updateAddressZipCode(long addressId, String zipcode);
	void insertTerm(long userEntityId, long termId);
	void insertProgramOfStudy(long userEntityId, long programOfStudyId);
	void insertIntoUserEntity(long userEntityId, UserEntity userEntity);

	List<UserEntity> getAllUserEntitiesForPage(int page, int pageSize);

	long count();

}
