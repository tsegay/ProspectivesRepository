package com.prospectivestiles.dao.hbn;

import java.util.List;

import javax.inject.Inject;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.prospectivestiles.dao.TestADao;
import com.prospectivestiles.domain.TestA;

@Repository
public class HbnTestADao extends AbstractHbnDao<TestA> implements TestADao {

	@Inject private JdbcTemplate jdbcTemplate;
	
	private static final String FIND_ADDRESS1_SQL =
			"select testA1 from testA where id = ?";
	private static final String UPDATE_ZIPCODE_SQL =
			"update testA set zipcode = ? where id = ?";
	
	@SuppressWarnings("unchecked")
	@Override
	public List<TestA> getTestAsByUserEntityId(long userEntityId) {
		Session session = getSession();
		Query query = session.getNamedQuery("findTestAByUserEntityId");
		query.setParameter("id", userEntityId);
		
		List<TestA> testAes = query.list();
		
		return testAes;
	}
	

	public String findTestA1ById(String username) {
		return jdbcTemplate.queryForObject(
				FIND_ADDRESS1_SQL, new Object[] { username }, String.class);
	}
	
	/**
	 * FOR TESTING PURPOSE
	 */
	/*@Override
	public void updateZipCode(long testAId, String zipcode) {
		//jdbcTemplate.update(UPDATE_PASSWORD_SQL, encPassword, account.getUsername());
		jdbcTemplate.update(UPDATE_ZIPCODE_SQL, zipcode, testAId);
	}*/

}
