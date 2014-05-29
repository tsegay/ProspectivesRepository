package com.prospectivestiles.dao.hbn;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;
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
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.authentication.dao.SaltSource;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.prospectivestiles.dao.UserEntityDao;
import com.prospectivestiles.domain.Role;
import com.prospectivestiles.domain.Term;
import com.prospectivestiles.domain.UserEntity;

@Repository("userEntityDao")
public class HbnUserEntityDao extends AbstractHbnDao<UserEntity> implements UserEntityDao {
	
//	private static final Logger log = LoggerFactory.getLogger(HbnUserEntityDao.class);
	private static final String INSERT_TERM_SQL =
			"update userEntity set term_id = ? where id = ?";
	private static final String INSERT_PROGRAMOFSTUDY_SQL =
			"update userEntity set programOfStudy_id = ? where id = ?";
	private static final String UPDATE_USERENTITY_SQL = 
			"update userEntity set username = ?, first_name = ?, last_name = ?, middle_name = ?, email = ?, homePhone = ?, cellPhone = ?, dob = ?, gender = ?, citizenship = ?, ethnicity = ?, ssn = ?, sevisNumber = ?, transferee = ? where id = ?";
	
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
//		log.debug("Creating userEntity: {}", userEntity);
		System.out.println("###########Creating userEntity: {}" + userEntity);
//		log.debug("Raw Password is: {}", userEntity.getPassword());
		
//		log.debug("Updating password");
//		Object salt = saltSource.getSalt(account);
//		if (salt != null) {
//			log.debug("Salting password: {}", salt.toString());
//		}
//		String encPassword = passwordEncoder.encodePassword(account.getPassword(), salt);
		String encPassword = passwordEncoder.encodePassword(userEntity.getPassword(), null);
		if (encPassword != null) {
//			log.debug("Encrypting password: {}", encPassword);
		}
		userEntity.setPassword(encPassword);
		create(userEntity);
//		log.debug("Password after userEntity created. Should be encrypted: {}", userEntity.getPassword());
//		log.debug("Password after account updated. Should be encrypted: {}", account.getPassword());
	}

	// OR U CAN USE getByUsername()
	
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
		/*return (String) getSession().createQuery("select password from Account where username = :username")
				.setParameter("username", username)
				.uniqueResult();*/
	}

	@Override
	public void insertTerm(long userEntityId, long termId) {
		jdbcTemplate.update(INSERT_TERM_SQL, termId, userEntityId);
				
	}

	@Override
	public void insertProgramOfStudy(long userEntityId, long programOfStudyId) {
		jdbcTemplate.update(INSERT_PROGRAMOFSTUDY_SQL, programOfStudyId, userEntityId);
	}
	
	
	/**
	 * Using JDBC to update userEntity
	 */
	@Override
	public void insertIntoUserEntity(long userEntityId, UserEntity userEntity) {
		jdbcTemplate.update(UPDATE_USERENTITY_SQL, new Object[] {
				userEntity.getUsername(), userEntity.getFirstName(), userEntity.getLastName(), userEntity.getMiddleName(),
				userEntity.getEmail(), userEntity.getHomePhone(), userEntity.getCellPhone(), userEntity.getDob(), 
				userEntity.getGender(), userEntity.getCitizenship(), userEntity.getEthnicity(), userEntity.getSsn(), 
				userEntity.getSevisNumber(), userEntity.isTransferee(),
				userEntityId});
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UserEntity> findAll(int page, int pageSize) {
		
		String hql = "FROM UserEntity";
		Query query = getSession().createQuery(hql);
		// setFirst shoulb be set with the index of the first element in the page, 
		// something like page * pageSize
		query.setFirstResult((page - 1) * pageSize);
		query.setMaxResults(pageSize);
		
		List<UserEntity> results = query.list();
		
//		String hql = "FROM Employee E WHERE E.id > 10 ORDER BY E.salary DESC";
//		Query query = session.createQuery(hql);
//		List results = query.list();
		
		return results;
	}
	

	
	
}



