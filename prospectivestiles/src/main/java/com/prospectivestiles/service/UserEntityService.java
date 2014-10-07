package com.prospectivestiles.service;

import java.util.List;
import java.util.Map;

import org.springframework.validation.Errors;

import com.prospectivestiles.domain.AccountState;
import com.prospectivestiles.domain.Term;
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

	List<UserEntity> getAllUserEntitiesForPage(int page, int pageSize);
	List<UserEntity> getAllUserEntitiesForPage(int page, int pageSize, String filter, boolean asc);
	
	/**
	 * counts everyone in the system
	 */
	long count();
	/**
	 * To count all students or admins
	 */
	long countByRole(long roleID);
	long countByRoles(List<Long> rolesList);
	boolean hasRoleAdmin(long userEntityId);
	boolean hasRoleAdmissionOrAssist(long userEntityId);
	
	UserEntity findById(long userEntityId);
	List<UserEntity> findUserEntitiesByAccountState(String accountState);
	long countByAccountState(String accountState);
	List<UserEntity> findByEmail(String email);

	void createUserEntityAsAO(UserEntity userEntity, Errors errors);

	/**
	 * When you create or update Evaluation, 
	 * userEntity.accountState and userEntity.role should be updated too.
	 * @param userEntityId
	 * @param accountState
	 * @param roleName
	 */
	void updateUserEntityRole(Long userEntityId, String accountState, String roleName);

	void updateUserEntityTermAndProgram(Long userEntityId, long termName,
			long programName);

	void updateUserEntityPassword(Long userEntityId, String password);

	List<UserEntity> getAccountsByTermStatusState(long termId, boolean status,
			String accountState);

	long countAccountsByTermStatusState(long termId, boolean status,
			String accountState);

	Map<String, Object> countAccountsByTerm(long termId);
	
//	void updateUE(UserEntity userEntity);
//	void updateTerm(long userEntityId, long termId);
//	void updateProgramOfStudy(long userEntityId, long programOfStudyId);
//	void updateAccountState(long userEntityId, String accountState);
//	/**
//	 * To update a userEntity
//	 * @param userEntityId
//	 * @param userEntity
//	 */
//	void insertIntoUserEntity(long userEntityId, UserEntity userEntity);
//	/**
//	 * To create new userEntity
//	 * @param userEntity
//	 */
//	void insertUserEntity(UserEntity userEntity, Errors errors);
//	void updatePassword(long userEntityId, String password);
//	void updateUserRole(Long userEntityId, String string);

//	void updateUEWithRole(UserEntity userEntity, String role_name);
}
