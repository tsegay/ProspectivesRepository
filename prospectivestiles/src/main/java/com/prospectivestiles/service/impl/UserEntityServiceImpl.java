package com.prospectivestiles.service.impl;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;

import com.prospectivestiles.dao.RoleDao;
import com.prospectivestiles.dao.UserEntityDao;
import com.prospectivestiles.domain.AccountState;
import com.prospectivestiles.domain.Notification;
import com.prospectivestiles.domain.Role;
import com.prospectivestiles.domain.UserEntity;
import com.prospectivestiles.service.NotificationService;
import com.prospectivestiles.service.UserEntityService;

@Service
@Transactional(readOnly = true)
//@PreAuthorize("denyAll")
public class UserEntityServiceImpl implements UserEntityService {
	private static final Logger log = LoggerFactory.getLogger(UserEntityServiceImpl.class);
	
	@Inject private UserEntityDao userEntityDao;
	@Inject private RoleDao roleDao;
	@Inject
	private NotificationService notificationService;
		
	@Transactional(readOnly = false)	
	public boolean createUserEntity(UserEntity userEntity, Errors errors) {
		validateUsername(userEntity.getUsername(), errors);
//		validateEmail(userEntity.getEmail(), errors);
		validatePassword(userEntity.getPassword(), userEntity.getConfirmPassword(), errors);
		
		System.out.println("userEntity.getPassword(): " + userEntity.getPassword());
		System.out.println("userEntity.getConfirmPassword(): " + userEntity.getConfirmPassword());
		
		boolean valid = !errors.hasErrors();
		
		if (valid) {
			Set<Role> roles = new HashSet<Role>();
			roles.add(roleDao.findByName("ROLE_USER"));
			userEntity.setRoles(roles);
			userEntityDao.createUserEntity(userEntity);
			/*
			 * After a user account is successfully created I want to create a notification
			 * I need to create an enum NotificationType: message, uploadedDoc, statusChanged, updatedProfile, ...
			*/
			Notification notification = new Notification("accountCreated", userEntity.getFullName() + " created an account", userEntity);
			notificationService.createNotification(notification);
		}
		return valid;
	}
	
	
	private void validateUsername(String username, Errors errors) {
		if (userEntityDao.findByUsername(username) != null) {
			log.info("Validation failed: duplicate username");
			errors.rejectValue("username", "error.duplicate", new String[] { username }, null);
		}
	}
	
//	private void validateEmail(String email, Errors errors) {
//		if (userEntityDao.findByEmail(email) != null) {
//			log.info("Validation failed: duplicate email");
//			errors.rejectValue("email", "error.duplicate", new String[] { email }, null);
//		}
//	}
	
	private void validatePassword(String password, String confirmPassword, Errors errors) {
		if (!password.equals(confirmPassword)) {
			log.info("Validation failed: password doesn't match confirmPassword");
			errors.rejectValue("username", "error.mismatch.userEntity.password", new String[] { password }, null);
		}
	}
	
	@Override
	public UserEntity getUserEntity(long userEntityId) {
		UserEntity userEntity = userEntityDao.find(userEntityId);
		if (userEntity != null) { 
			Hibernate.initialize(userEntity.getRoles()); 
			/**
			 * failed to lazily initialize a collection of role: com.prospectivestiles.domain.UserEntity.listOfProgramOfStudy, no session or session was closed
			 * Lazy exceptions occur when you fetch an object typically containing a collection which is lazily loaded, and try to access that collection.
			 * avoid this problem by Initalizing the collection using Hibernate.initialize(obj);
			 */
//			Hibernate.initialize(userEntity.getListOfProgramOfStudy());
		}
		return userEntity;
	}
	
	// For recipe 6.6
	public UserEntity getUserEntityByUsername(String username) {
		UserEntity userEntity = userEntityDao.findByUsername(username);
		if (userEntity != null) { Hibernate.initialize(userEntity.getRoles()); }
		return userEntity;
	}

	
	@Override
	public String getPasswordByUsername(String username) {
		
		return userEntityDao.getPasswordByUsername(username);
		
	}

	@Override
	public List<UserEntity> getAllUserEntities(){
		return userEntityDao.findAll();
	}

	/**
	 * This didn't work for me.
	 * So, I used JDBC method below.
	 */
	@Override
	public void updateUserEntity(UserEntity userEntity) {
		
		UserEntity userEntityToUpdate = userEntityDao.find(userEntity.getId());
		
		System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^ UserEntityServiceImpl called: ");
		
		
		userEntityToUpdate.setUsername(userEntity.getUsername());
		userEntityToUpdate.setEmail(userEntity.getEmail());
		userEntityToUpdate.setEnabled(userEntity.isEnabled());
		userEntityToUpdate.setFirstName(userEntity.getFirstName());
		userEntityToUpdate.setLastName(userEntity.getLastName());
		userEntityToUpdate.setGender(userEntity.getGender());
		userEntityToUpdate.setAcceptTerms(userEntity.getAcceptTerms());
		userEntityToUpdate.setPassword(userEntity.getPassword());
		userEntityToUpdate.setMarketingOk(userEntity.isMarketingOk());
		Date now = new Date();
		userEntityToUpdate.setDateLastModified(now);
		
		userEntityDao.update(userEntityToUpdate);
		
	}
	@Override
	public void delete(UserEntity userEntity) {
		userEntityDao.delete(userEntity);
	}

	@Override
	@Transactional(readOnly = false)
	public void insertTerm(long userEntityId, long termId) {
		userEntityDao.insertTerm(userEntityId, termId);
	}

	@Override
	@Transactional(readOnly = false)
	public void insertProgramOfStudy(long userEntityId, long programOfStudyId) {
		userEntityDao.insertProgramOfStudy(userEntityId, programOfStudyId);
	}

	@Override
	@Transactional(readOnly = false)
	public void insertAccountState(long userEntityId, String accountState) {
		userEntityDao.insertAccountState(userEntityId, accountState);
		
	}
	
	@Override
	public void updatePassword(long userEntityId, String password) {
		userEntityDao.updatePassword(userEntityId, password);
		
	}
	

	/**
	 * Use @Transactional(readOnly = false) or exception thrown is:
	 * org.springframework.dao.TransientDataAccessResourceException: 
	 * PreparedStatementCallback; SQL [update userEntity set accountState = ? where accountState = ?]; 
	 * Connection is read-only. Queries leading to data modification are not allowed; nested exception is java.sql.SQLException: 
	 * Connection is read-only. 
	 */
	@Override
	@Transactional(readOnly = false)
	public void insertIntoUserEntity(long userEntityId, UserEntity userEntity) {
		
		userEntityDao.insertIntoUserEntity(userEntityId, userEntity);
		
	}

	@Override
	public List<UserEntity> getAllUserEntitiesForPage(int page, int pageSize) {
		return userEntityDao.findAll(page, pageSize);
	}

	/*
	 * counts everyone in the system
	 */
	@Override
	public long count() {
		
		return userEntityDao.count();
	}

	@Override
	public List<UserEntity> getAllUserEntitiesForPage(int page, int pageSize,
			String filter, boolean asc) {
		return userEntityDao.findAll(page, pageSize, filter, asc);
	}

	@Override
	public List<UserEntity> findByRole(long roleID) {
		return userEntityDao.findByRole(roleID);
	}
	/*
	 * To count all students or admins
	 */
	@Override
	public long countByRole(long roleID) {
		return userEntityDao.countByRole(roleID);
	}

	/**
	 * To find if a user is an admin
	 */
	@Override
	public boolean hasRoleAdmin(long userEntityId) {
		return userEntityDao.hasRoleAdmin(userEntityId);
	}

	@Override
	public List<UserEntity> findUserEntitiesByAccountState(String accountState) {
		return userEntityDao.findUserEntitiesByAccountState(accountState);
	}

	@Override
	public long countByAccountState(String accountState) {
		return userEntityDao.countByAccountState(accountState);
	}

	@Override
	public List<UserEntity> findByEmail(String email) {
		return userEntityDao.findByEmail(email);
	}

	

	
	
}
