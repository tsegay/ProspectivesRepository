package com.prospectivestiles.dao.hbn;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;
import javax.management.relation.RoleList;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Transient;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.Query;
import org.hibernate.validator.constraints.Email;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.authentication.dao.SaltSource;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.prospectivestiles.dao.UserEntityDao;
import com.prospectivestiles.domain.AccountState;
import com.prospectivestiles.domain.Role;
import com.prospectivestiles.domain.Term;
import com.prospectivestiles.domain.UserEntity;

@Repository("userEntityDao")
public class HbnUserEntityDao extends AbstractHbnDao<UserEntity> implements UserEntityDao {
	
	private static final Logger log = LoggerFactory.getLogger(HbnUserEntityDao.class);
	private static final String UPDATE_TERM_SQL =
			"update userEntity set term_id = ? where id = ?";
	private static final String UPDATE_PROGRAMOFSTUDY_SQL =
			"update userEntity set programOfStudy_id = ? where id = ?";
	private static final String UPDATE_ACCOUNTSTATE_SQL =
			"update userEntity set accountState = ? where id = ?";
	private static final String UPDATE_USERENTITY_SQL = 
			"update userEntity set first_name = ?, last_name = ?, middle_name = ?, email = ?, homePhone = ?, cellPhone = ?, dob = ?, gender = ?, citizenship = ?, ethnicity = ?, ssn = ?, sevisNumber = ?, transferee = ?, heardAboutAcctThru = ? where id = ?";
	private static final String UPDATE_PASSWORD_SQL =
			"update userEntity set password = ? where id = ?";
	private static final String INSERT_USERENTITY_SQL = 
			"INSERT INTO userEntity"
			+ " (username, password, first_name, last_name, middle_name, email, homePhone, cellPhone, dob, gender, citizenship, ethnicity, ssn, sevisNumber, transferee, international, heardAboutAcctThru, accept_terms, enabled, marketing_ok)"
			+ " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	
	
	@Inject private JdbcTemplate jdbcTemplate;
	
	@Inject private PasswordEncoder passwordEncoder;
	
	/*@Inject private SaltSource saltSource;
	public void create(Account account, String password) {
		log.debug("Creating account: {}", account);
		create(account);
		
		log.debug("Updating password");
		Object salt = saltSource.getSalt(new UserDetailsAdapter(account));
		String encPassword = passwordEncoder.encodePassword(password, salt);
		jdbcTemplate.update(UPDATE_PASSWORD_SQL, encPassword, account.getUsername());
	}*/
	
	public void createUserEntity(UserEntity userEntity) {
		log.info("Creating userEntity: {}", userEntity);
		System.out.println("###########Creating userEntity: {}" + userEntity);
		System.out.println("Raw Password is: {}" + userEntity.getPassword());
		
		log.info("Updating password");
//		Object salt = saltSource.getSalt(account);
//		if (salt != null) {
//			log.debug("Salting password: {}", salt.toString());
//		}
//		String encPassword = passwordEncoder.encodePassword(account.getPassword(), salt);
		String encPassword = passwordEncoder.encodePassword(userEntity.getPassword(), null);
		if (encPassword != null) {
			System.out.println("Encrypting password: {}" + encPassword);
		}
		userEntity.setPassword(encPassword);
		
		create(userEntity);
		System.out.println("Password after userEntity created. Should be encrypted: {}" + userEntity.getPassword());
//		log.debug("Password after account updated. Should be encrypted: {}", account.getPassword());
	}

	
	public UserEntity findByUsername(String username) {
		return (UserEntity) getSession()
				.getNamedQuery("findUserEntityByUsername")
				.setParameter("username", username)
				.uniqueResult();
	}

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		UserEntity userEntity = findByUsername(username);
		if (userEntity == null) {
			throw new UsernameNotFoundException("No user with username " + username);
		}
		return userEntity;
	}

	
	@Override
	public String getPasswordByUsername(String username) {
		UserEntity userEntity = (UserEntity) getSession()
				.getNamedQuery("findUserEntityByUsername")
				.setParameter("username", username)
				.uniqueResult();
		return userEntity.getPassword();
	}

	@Override
	public void updateTerm(long userEntityId, long termId) {
		jdbcTemplate.update(UPDATE_TERM_SQL, termId, userEntityId);
				
	}

	@Override
	public void updateProgramOfStudy(long userEntityId, long programOfStudyId) {
		jdbcTemplate.update(UPDATE_PROGRAMOFSTUDY_SQL, programOfStudyId, userEntityId);
	}
	
	@Override
	public void updateAccountState(long userEntityId, String accountState) {
		jdbcTemplate.update(UPDATE_ACCOUNTSTATE_SQL, accountState, userEntityId);
		
	}
	
	@Override
	public void updatePassword(long userEntityId, String password) {
		log.info("Updating password for: ", userEntityId);
		System.out.println("########### Updating password for: " + userEntityId);
		System.out.println("Raw Password is: {}" + password);
		
		log.info("Updating password");
		String encPassword = passwordEncoder.encodePassword(password, null);
		if (encPassword != null) {
			System.out.println("Encrypting password: {}" + encPassword);
		}
		
		jdbcTemplate.update(UPDATE_PASSWORD_SQL, encPassword, userEntityId);
		
//		System.out.println("Password after userEntity created. Should be encrypted: {}" + userEntity.getPassword());
	}
	
	/**
	 * Using JDBC to update userEntity
	 */
	@Override
	public void insertIntoUserEntity(long userEntityId, UserEntity userEntity) {
		jdbcTemplate.update(UPDATE_USERENTITY_SQL, new Object[] {
				userEntity.getFirstName(), userEntity.getLastName(), userEntity.getMiddleName(),
				userEntity.getEmail(), userEntity.getHomePhone(), userEntity.getCellPhone(), userEntity.getDob(), 
				userEntity.getGender(), userEntity.getCitizenship(), userEntity.getEthnicity(), userEntity.getSsn(), 
				userEntity.getSevisNumber(), userEntity.isTransferee(), userEntity.getHeardAboutAcctThru(),
				userEntityId});
	}

	/**
	 * Using JDBC to create userEntity
	 */
	@Override
	public void insertUserEntity(UserEntity userEntity) {
		jdbcTemplate.update(INSERT_USERENTITY_SQL, new Object[] {
				userEntity.getUsername(), userEntity.getPassword(),
				userEntity.getFirstName(), userEntity.getLastName(), userEntity.getMiddleName(),
				userEntity.getEmail(), userEntity.getHomePhone(), userEntity.getCellPhone(), userEntity.getDob(), 
				userEntity.getGender(), userEntity.getCitizenship(), userEntity.getEthnicity(), userEntity.getSsn(), 
				userEntity.getSevisNumber(), userEntity.isTransferee(), userEntity.isInternational(), 
				userEntity.getHeardAboutAcctThru(), userEntity.getAcceptTerms(), userEntity.isEnabled(), userEntity.isMarketingOk()
			});
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<UserEntity> findAll(int page, int pageSize) {
		
		/**
		 * I want to get all students with the status below only, not admin users
		 * 6 - ROLE_STUDENT_PENDING, 7 - ROLE_STUDENT_INPROCESS, 8 - ROLE_STUDENT_COMPLETE, 9 - ROLE_STUDENT_ADMITTED
		 */
		String hql = "SELECT u FROM UserEntity u INNER JOIN u.roles r WHERE r.id = 6 OR r.id = 7 OR r.id = 8 OR r.id = 9 ORDER BY u.lastName ASC";
		Query query = getSession().createQuery(hql);
		// setFirst should be set with the index of the first element in the page, 
		// something like page * pageSize
		query.setFirstResult((page - 1) * pageSize);
		query.setMaxResults(pageSize);
		
		List<UserEntity> results = query.list();
		
		return results;
	}
	
	/**
	 * Used for searching users on the accounts page
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<UserEntity> findAll(int page, int pageSize, String filter, boolean asc) {
		
		String hql;
		if (filter != null) {
			if(asc){
				hql = "FROM UserEntity u WHERE "
						+ "u.lastName LIKE '%" + filter + "%' OR "
						+ "u.firstName LIKE '%" + filter + "%' OR "
						+ "u.username LIKE '%" + filter + "%' OR "
						+ "u.email LIKE '%" + filter + "%'"
						+ "ORDER BY u.lastName ASC";
			} else {
				hql = "FROM UserEntity u WHERE "
						+ "u.lastName LIKE '%" + filter + "%' OR "
						+ "u.firstName LIKE '%" + filter + "%' OR "
						+ "u.username LIKE '%" + filter + "%' OR "
						+ "u.email LIKE '%" + filter + "%'"
						+ "ORDER BY u.lastName DESC";
				
			}
		} else {
			if(asc){
				hql = "FROM UserEntity u ORDER BY u.lastName ASC";
			} else {
				hql = "FROM UserEntity u ORDER BY u.lastName DESC";
				
			}
		}
		
		Query query = getSession().createQuery(hql);
		// setFirst shoulb be set with the index of the first element in the page, 
		// something like page * pageSize
		query.setFirstResult((page - 1) * pageSize);
		query.setMaxResults(pageSize);
		
		List<UserEntity> results = query.list();
		
		return results;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UserEntity> findByRole(long roleID) {
		
		return (List<UserEntity>) getSession()
				.getNamedQuery("findUserEntitiesByRole")
				.setParameter("roleID", roleID)
				.list();
	}
	
	/*I want to count students only, not admin users*/
	@Override
	public long countByRole(long roleID) {
		return (Long) getSession()
			.createQuery("SELECT count(*) FROM UserEntity u INNER JOIN u.roles r WHERE r.id = :roleID ORDER BY u.lastName ASC")
			.setParameter("roleID", roleID)
			.uniqueResult();
	}
	
	/**
	 * To get the count of users based on their roles. 
	 * Eg. all student with role ROLE_STUDENT_PENDING, ROLE_STUDENT_INPROCESS etc
	 * 
	 * CHECK IF PASSED PARAMA IS NOT NULL
	 * @param rolesList
	 * @return
	 */
	@Override
	public long countByRoles(List<Long> rolesList) {
		
		String query = "SELECT count(*) FROM UserEntity u INNER JOIN u.roles r WHERE";
		
		for (int i = 0; i < rolesList.size(); i++) {
			if (i == rolesList.size() - 1) {
				query = query + " r.id = :roleID" + i;
			} else {
				query = query + " r.id = :roleID" + i + " OR";
			}
		}
		
		query = query + " ORDER BY u.lastName ASC";
		
		Query q = getSession().createQuery(query);
		for (int i = 0; i < rolesList.size(); i++) {
			q.setParameter("roleID"+i, rolesList.get(i));
		}
		
		return (long) q.uniqueResult();
		
	}
	
	

	/**
	 * To find if a user is an admin
	 */
	@Override
	public boolean hasRoleAdmin(long userEntityId) {
		boolean isAdmin = false;
		UserEntity userEntity = find(userEntityId);
		Set<Role> roles = userEntity.getRoles();
		
		for (Role role : roles) {
			// if role is admin return true else false
			if (role.getId() == 2) {
				System.out.println("############## role: " + role.getName());
				isAdmin = true;
			}
		}
		
		return isAdmin;
	}
	
	/**
	 * To find if a user is an ROLE_ADMISSION or ROLE_ADMISSION_ASSIST
	 * AO - Admission Officer
	 */
	@Override
	public boolean hasRoleAdmissionOrAssist(long userEntityId) {
		boolean isAO = false;
		UserEntity userEntity = find(userEntityId);
		Set<Role> roles = userEntity.getRoles();
		
		for (Role role : roles) {
			// if role is ROLE_ADMISSION or ROLE_ADMISSION_ASSIST return true else false
			if (role.getId() == 11 || role.getId() == 12) {
				System.out.println("############## role: " + role.getName());
				isAO = true;
			}
		}
		
		return isAO;
	}

	/**
	 * To get evaluations by status, eg 'admitted' students
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<UserEntity> findUserEntitiesByAccountState(String accountState) {
		return (List<UserEntity>) getSession()
				.getNamedQuery("findUserEntitiesByStatus")
				.setParameter("accountState", accountState)
				.list();
	}


	/**
	 * I want to count prospective students by their status
	 * Eg. admitted students
	 */
	@Override
	public long countByAccountState(String accountState) {
		return (Long) getSession()
				.createQuery("SELECT count(*) FROM UserEntity e WHERE e.accountState = :accountState")
				.setParameter("accountState", accountState)
				.uniqueResult();
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<UserEntity> findByEmail(String email) {
		return (List<UserEntity>) getSession()
				.getNamedQuery("findUserEntitiesByEmail")
				.setParameter("email", email)
				.list();
		
	}


	
	
}



