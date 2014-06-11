package com.prospectivestiles.service.impl;

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
import com.prospectivestiles.domain.Role;
import com.prospectivestiles.domain.UserEntity;
import com.prospectivestiles.service.UserEntityService;

@Service
@Transactional(readOnly = true)
//@PreAuthorize("denyAll")
public class UserEntityServiceImpl implements UserEntityService {
	private static final Logger log = LoggerFactory.getLogger(UserEntityServiceImpl.class);
	
	@Inject private UserEntityDao userEntityDao;
	@Inject private RoleDao roleDao;
	
		
	@Transactional(readOnly = false)	
	public boolean createUserEntity(UserEntity userEntity, Errors errors) {
		validateUsername(userEntity.getUsername(), errors);
		boolean valid = !errors.hasErrors();
		
		if (valid) {
			Set<Role> roles = new HashSet<Role>();
//			roles.add(roleDao.findByName("user"));
			roles.add(roleDao.findByName("ROLE_USER"));
			userEntity.setRoles(roles);
			//accountDao.create(account);
			userEntityDao.createUserEntity(userEntity);
		}
		
		return valid;
	}
	
	private void validateUsername(String username, Errors errors) {
		if (userEntityDao.findByUsername(username) != null) {
			log.debug("Validation failed: duplicate username");
			errors.rejectValue("username", "error.duplicate", new String[] { username }, null);
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
		
//		userEntityToUpdate.setChecklist(userEntity.getChecklist());
//		userEntityToUpdate.setDateCreated(userEntity.getDateCreated());
//		userEntityToUpdate.setDob(userEntity.getDob());
//		userEntityToUpdate.setListOfAddresses(userEntity.getListOfAddresses());
//		userEntityToUpdate.setListOfHighSchools(userEntity.getListOfHighSchools());
//		userEntityToUpdate.setListOfInstitutes(userEntity.getListOfInstitutes());
//		userEntityToUpdate.setRoles(userEntity.getRoles());
//		userEntityToUpdate.setTransferee(userEntity.isTransferee());
		
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
	public void insertIntoUserEntity(long userEntityId, UserEntity userEntity) {
		
		userEntityDao.insertIntoUserEntity(userEntityId, userEntity);
		
	}

	@Override
	public List<UserEntity> getAllUserEntitiesForPage(int page, int pageSize) {
		// TODO Auto-generated method stub
		return userEntityDao.findAll(page, pageSize);
	}

	@Override
	public long count() {
		
		return userEntityDao.count();
	}

	@Override
	public List<UserEntity> getAllUserEntitiesForPage(int page, int pageSize,
			String filter, boolean asc) {
		return userEntityDao.findAll(page, pageSize, filter, asc);
	}
	
}
