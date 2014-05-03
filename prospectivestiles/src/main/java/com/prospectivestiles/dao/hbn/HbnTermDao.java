package com.prospectivestiles.dao.hbn;

import java.util.List;

import javax.inject.Inject;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.prospectivestiles.dao.TermDao;
import com.prospectivestiles.domain.Term;

@Repository
public class HbnTermDao extends AbstractHbnDao<Term> implements TermDao {

	@Inject private JdbcTemplate jdbcTemplate;
	
	private static final String FIND_ADDRESS1_SQL =
			"select address1 from address where id = ?";
	private static final String UPDATE_ZIPCODE_SQL =
			"update address set zipcode = ? where id = ?";
	

	public String findAddress1ById(String username) {
		return jdbcTemplate.queryForObject(
				FIND_ADDRESS1_SQL, new Object[] { username }, String.class);
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<Term> getTermsByUserEntityId(long userEntityId) {
		Session session = getSession();
		Query query = session.getNamedQuery("findTermsByUserEntityId");
		query.setParameter("id", userEntityId);
		
		List<Term> terms = query.list();
		
		return terms;
	}
	
	/**
	 * FOR TESTING PURPOSE
	 */
	/*public void updateZipCode(long addressId, String zipcode) {
		//jdbcTemplate.update(UPDATE_PASSWORD_SQL, encPassword, account.getUsername());
		jdbcTemplate.update(UPDATE_ZIPCODE_SQL, zipcode, addressId);
	}
*/
}
