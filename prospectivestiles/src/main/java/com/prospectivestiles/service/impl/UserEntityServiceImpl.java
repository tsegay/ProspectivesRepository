package com.prospectivestiles.service.impl;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import javax.inject.Inject;

import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;

import com.prospectivestiles.dao.ProgramOfStudyDao;
import com.prospectivestiles.dao.RoleDao;
import com.prospectivestiles.dao.TermDao;
import com.prospectivestiles.dao.UserEntityDao;
import com.prospectivestiles.domain.AccountState;
import com.prospectivestiles.domain.Notification;
import com.prospectivestiles.domain.ProgramOfStudy;
import com.prospectivestiles.domain.Role;
import com.prospectivestiles.domain.Term;
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
	@Inject private NotificationService notificationService;
	@Inject private TermDao termDao;
	@Inject private ProgramOfStudyDao programOfStudyDao;
	@Inject private PasswordEncoder passwordEncoder;
	
	/**
	 * When a user created an account.
	 * Check username is available. no 2 users can have same username
	 * Check email is available. no 2 users can have same email
	 * Check password and confirm password match
	 * Then give the applicant the default Role: 'ROLE_STUDENT_PENDING'
	 * Save the user account
	 * Create a notification for Admins that a new account is created.
	 */
	@Transactional(readOnly = false)	
	public boolean createUserEntity(UserEntity userEntity, Errors errors) {
		validateUsername(userEntity.getUsername(), errors);
		validateEmail(userEntity.getEmail(), errors);
		validatePassword(userEntity.getPassword(), userEntity.getConfirmPassword(), errors);
		
		System.out.println("userEntity.getPassword(): " + userEntity.getPassword());
		System.out.println("userEntity.getConfirmPassword(): " + userEntity.getConfirmPassword());
		
		boolean valid = !errors.hasErrors();
		
		if (valid) {
			Role role = roleDao.findByName("ROLE_STUDENT_PENDING");
			userEntity.setRole(role);
			
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
	
	/**
	 * TO replace insertUserEntity(): If this works, remove the method insertUserEntity().
	 * 
	 * When AO creates an account for an applicant.
	 * Auto generate unique username for the applicant.
	 * Make sure the email is not already taken too.
	 * 
	 */
	@Override
	@Transactional(readOnly = false)
	public void createUserEntityAsAO(UserEntity userEntity, Errors errors) {
		userEntity.setPassword(randomString().substring(0, 8));
		userEntity.setUsername(generateUniqueUsername(userEntity.getFirstName()));
		
		/**
		 * If email is available then persist user
		 * When Admission Officer creates a user account: acceptTerms should not be 'true'. 
		 * ONLY applicant can agree to terms by him/herself
		 */
		if (validateEmail2(userEntity.getEmail(), errors)) {
			Role role = roleDao.findByName("ROLE_STUDENT_PENDING");
			userEntity.setRole(role);
			
			userEntityDao.createUserEntity(userEntity);
		}
	}
	

	/**
	 * Fetches a userEntity by its id. 
	 * Initalizing the collection using Hibernate.initialize(obj)
	 */
	@Override
	public UserEntity getUserEntity(long userEntityId) {
		/**
		 * Replacing find() inherited from AbstractHbnDao by findById in userEntityDao
		 * nested exception is org.hibernate.HibernateException: More than one row with the given identifier was found:
		 * Can't create eval or open page for user like: id=4
		 */
//		UserEntity userEntity = userEntityDao.find(userEntityId);
		UserEntity userEntity = userEntityDao.findById(userEntityId);
		if (userEntity != null) { 
//			Hibernate.initialize(userEntity.getRoles()); 
			Hibernate.initialize(userEntity.getRole()); 
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
		if (userEntity != null) { 
//			Hibernate.initialize(userEntity.getRoles()); 
			Hibernate.initialize(userEntity.getRole()); 
		}
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

	@Transactional
	@Override
	public void updateUserEntity(UserEntity userEntity) {
		System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^ UserEntityServiceImpl called: ");
		
		/**
		 * userEntityDao.find(userEntity.getId()) is crashing, let me try to get user by username. next update this method in UEdao insteado of inheriting dao
		 */
		UserEntity userEntityToUpdate = userEntityDao.find(userEntity.getId());
		
		
//		userEntityToUpdate.setAcceptTerms(userEntity.getAcceptTerms());
//		userEntityToUpdate.setAccountState(userEntity.getAccountState());
		userEntityToUpdate.setCellPhone(userEntity.getCellPhone());
		userEntityToUpdate.setCitizenship(userEntity.getCitizenship());
//		userEntity.setCountryOfBirth(userEntity.getCountryOfBirth());
		Date now = new Date();
		userEntityToUpdate.setDateLastModified(now);
		userEntityToUpdate.setDob(userEntity.getDob());
		userEntityToUpdate.setEmail(userEntity.getEmail());
//		userEntityToUpdate.setEnabled(userEntity.isEnabled());
		userEntityToUpdate.setEthnicity(userEntity.getEthnicity());
		userEntityToUpdate.setFirstName(userEntity.getFirstName());
		userEntityToUpdate.setGender(userEntity.getGender());
		userEntityToUpdate.setHeardAboutAcctThru(userEntity.getHeardAboutAcctThru());
		userEntityToUpdate.setHomePhone(userEntity.getHomePhone());
		userEntityToUpdate.setInternational(userEntity.isInternational());
		userEntityToUpdate.setLastName(userEntity.getLastName());
		userEntityToUpdate.setMarketingOk(userEntity.isMarketingOk());
		userEntityToUpdate.setMiddleName(userEntity.getMiddleName());
//		userEntityToUpdate.setPassword(userEntity.getPassword());
		userEntityToUpdate.setSevisNumber(userEntity.getSevisNumber());
		userEntityToUpdate.setSsn(userEntity.getSsn());
		userEntityToUpdate.setTransferee(userEntity.isTransferee());
		userEntityToUpdate.setUsername(userEntity.getUsername());
		
		
		
		System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^ UserEntityServiceImpl called ID: " + userEntityToUpdate.getId());
		System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^ UserEntityServiceImpl called userEntityToUpdate.Gender: " + userEntityToUpdate.getGender());
		System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^ UserEntityServiceImpl called userEntity.Gender: " + userEntity.getGender());
		
		userEntityDao.update(userEntityToUpdate);
		
		System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^ UserEntityServiceImpl called userEntityToUpdate.Gender: " + userEntityToUpdate.getGender());
		System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^ UserEntityServiceImpl called userEntity.Gender: " + userEntity.getGender());

	}
	
	/**
	 * When you create or update Evaluation, 
	 * userEntity.accountState and userEntity.role should be updated too.
	 * @param userEntityId
	 * @param accountState
	 * @param roleName
	 */
	@Transactional
	@Override
	public void updateUserEntityRole(Long userEntityId, String accountState,
			String roleName) {
		// Find User
		UserEntity userEntity = userEntityDao.findById(userEntityId);
		Role role = roleDao.findByName(roleName);
		// Update user role
		userEntity.setRole(role);
		// Update AccountState of user
		userEntity.setAccountState(accountState);
		userEntityDao.update(userEntity);
		
	}
	
	@Override
	public void delete(UserEntity userEntity) {
		userEntityDao.delete(userEntity);
	}

	/**
	 * To update term and program of study for an applicant
	 * @param userEntityId
	 * @param accountState
	 * @param roleName
	 */
	@Transactional
	@Override
	public void updateUserEntityTermAndProgram(Long userEntityId, long termId,
			long programId) {
		// Find User, term, program
		UserEntity userEntity = userEntityDao.findById(userEntityId);
		Term term = termDao.find(termId);
		ProgramOfStudy programOfStudy = programOfStudyDao.find(programId);
		// Update user term and program
		userEntity.setTerm(term);
		userEntity.setProgramOfStudy(programOfStudy);
		userEntityDao.update(userEntity);
	}
	
	
	/**
	 * Used by class ResetPasswordEntityServiceImpl for users to reset their password
	 * @param userEntityId
	 * @param password
	 */
	@Transactional
	@Override
	public void updateUserEntityPassword(Long userEntityId, String password) {
		// Find User by ID
		UserEntity userEntity = userEntityDao.findById(userEntityId);
		// Update  user password
		
		String encPassword = passwordEncoder.encodePassword(password, null);
		userEntity.setPassword(encPassword);
		
//		userEntity.setPassword(password);
		userEntityDao.update(userEntity);
		
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


	@Override
	public List<UserEntity> findUserEntitiesByAccountState(String accountState) {
		return userEntityDao.findUserEntitiesByAccountState(accountState);
	}

	@Override
	public long countByAccountState(String accountState) {
		return userEntityDao.countByAccountState(accountState);
	}

	@Override
	public UserEntity findById(long userEntityId) {
		return userEntityDao.findById(userEntityId);
	}
	
	@Override
	public List<UserEntity> findByEmail(String email) {
		return userEntityDao.findByEmail(email);
	}

	@Override
	public List<UserEntity> getAccountsByTermStatusState(long termId, boolean status, String accountState) {
		return userEntityDao.getAccountsByTermStatusState(termId, status, accountState);
	}
	
	@Override
	public long countAccountsByTermStatusState(long termId, boolean status, String accountState) {
		return userEntityDao.countAccountsByTermStatusState(termId, status, accountState);
	}

	@Override
	public long countByRoles(List<Long> rolesList) {
		return userEntityDao.countByRoles(rolesList);
	}

	/**
	 * To find if a user is an admin
	 */
	@Override
	public boolean hasRoleAdmin(long userEntityId) {
		return userEntityDao.hasRoleAdmin(userEntityId);
	}

	@Override
	public boolean hasRoleAdmissionOrAssist(long userEntityId) {
		return userEntityDao.hasRoleAdmissionOrAssist(userEntityId);
	}

	private void validateUsername(String username, Errors errors) {
//		UserEntity u = userEntityDao.findByUsername(username);
		if (userEntityDao.findByUsername(username) != null) {
			log.info("Validation failed: duplicate username");
			errors.rejectValue("username", "error.duplicate", new String[] { username }, null);
		}
	}
	
	/**
	 * When creating a new account, check the email address doesn't exist in the db
	 * Email address should be unique
	 * 
	 * MERGE validateEmail() and validateEmail2()
	 * 
	 * @param email
	 * @param errors
	 */
	private void validateEmail(String email, Errors errors) {
		System.out.println("validating email: " + email);
		System.out.println("Users: " + userEntityDao.findByEmail(email).size());
		if (!userEntityDao.findByEmail(email).isEmpty()) {
			System.out.println("inside !userEntityDao.findByEmail(email).isEmpty()");
			log.info("Validation failed: duplicate email");
			errors.rejectValue("email", "error.duplicateemail", new String[] { email }, null);
		}
	}
	
	/**
	 * when inserting userentity and the email already exists, 
	 * "email exist" err msg displayed on form page
	 * 
	 * MERGE validateEmail() and validateEmail2()
	 * 
	 * @param email
	 * @param errors
	 * @return 'true' is email is available, 'false' is email is not available
	 */
	private boolean validateEmail2(String email, Errors errors) {
		System.out.println("validating email: " + email);
		System.out.println("Users: " + userEntityDao.findByEmail(email).size());
		boolean returnValue = true;
		if (!userEntityDao.findByEmail(email).isEmpty()) {
			System.out.println("inside !userEntityDao.findByEmail(email).isEmpty()");
			log.info("Validation failed: duplicate email");
			returnValue = false;
			errors.rejectValue("email", "error.duplicateemail", new String[] { email }, null);
		}
		return returnValue;
	}
	
	/**
	 * Checks that the password and confirm password matches
	 * @param password
	 * @param confirmPassword
	 * @param errors
	 */
	private void validatePassword(String password, String confirmPassword, Errors errors) {
		if (!password.equals(confirmPassword)) {
			log.info("Validation failed: password doesn't match confirmPassword");
			errors.rejectValue("password", "error.mismatch.userEntity.password", new String[] { password }, null);
		}
	}
	

	/**
	 * When AO creates an account for an applicant.
	 * Auto generate username for the applicant.
	 * Format the username as: firstName + a 3-digit number.
	 * Make sure the username is not already taken.
	 * @param firstName
	 * @return
	 */
	private String generateUniqueUsername(String firstName) {
		String username = firstName + randomNumber();
		System.out.println("firstName + randomNumber(): " + username);
		while (userEntityDao.findByUsername(username) != null) {
			System.out.println("username taken: " + username);
			username = firstName + randomNumber();
		}
		System.out.println("returning username: " + username);
		return username;
	}
	
//	private int generateUniqueUsername(int n) {
//		int num = n + randomNumber();
//		while (num % 2 == 0) {
//			num = n + randomNumber();
//		}
//		return num;
//	}
	
	public int randomNumber() {
//		SecureRandom rand = new SecureRandom();
//		return new BigInteger(130, rand).toString(32);
	    int START = 100;
	    int END = 1000;
	    Random random = new Random();
	    
	    long range = (long)END - (long)START + 1;
	    long fraction = (long)(range * random.nextDouble());
	    int randomNumber =  (int)(fraction + START); 
	    return randomNumber;
	}
	
	public String randomString() {
		SecureRandom rand = new SecureRandom();
		return new BigInteger(130, rand).toString(32);
	}


	
//	/**
//	 * Use @Transactional(readOnly = false) or exception thrown is:
//	 * org.springframework.dao.TransientDataAccessResourceException: 
//	 * PreparedStatementCallback; SQL [update userEntity set accountState = ? where accountState = ?]; 
//	 * Connection is read-only. Queries leading to data modification are not allowed; nested exception is java.sql.SQLException: 
//	 * Connection is read-only. 
//	 */
//	@Override
//	@Transactional(readOnly = false)
//	public void insertIntoUserEntity(long userEntityId, UserEntity userEntity) {
//		userEntityDao.insertIntoUserEntity(userEntityId, userEntity);
//	}
	
//	/**
//	 * Using JDBC to update userEntity
//	 * If use hibernate persistence, the form is not validates as the agreeTerms must be 'true'
//	 * Only, student can make the agreement. AO can't fill in this field.
//	 * When AO creates an account for an applicant.
//	 * Auto generate unique username for the applicant.
//	 * Make sure the email is not already taken too.
//	 * 
//	 */
//	@Override
//	@Transactional(readOnly = false)
//	public void insertUserEntity(UserEntity userEntity, Errors errors) {
//		userEntity.setPassword(randomString().substring(0, 8));
//		userEntity.setUsername(generateUniqueUsername(userEntity.getFirstName()));
//		
//		/**
//		 * If email is available then persist user
//		 * When Admission Officer creates a user account: acceptTerms should not be 'true'. 
//		 * ONLY applicant can agree to terms by him/herself
//		 */
//		if (validateEmail2(userEntity.getEmail(), errors)) {
//			userEntity.setAcceptTerms(false);
//			userEntityDao.insertUserEntity(userEntity);
//		}
//	}
	
//	@Override
//	@Transactional(readOnly = false)
//	public void updateTerm(long userEntityId, long termId) {
//		userEntityDao.updateTerm(userEntityId, termId);
//	}
//
//	@Override
//	@Transactional(readOnly = false)
//	public void updateProgramOfStudy(long userEntityId, long programOfStudyId) {
//		userEntityDao.updateProgramOfStudy(userEntityId, programOfStudyId);
//	}

//	@Override
//	@Transactional(readOnly = false)
//	public void updateAccountState(long userEntityId, String accountState) {
//		userEntityDao.updateAccountState(userEntityId, accountState);
//		
//	}
	
//	@Override
//	public void updatePassword(long userEntityId, String password) {
//		userEntityDao.updatePassword(userEntityId, password);
//		
//	}

	
	
}
