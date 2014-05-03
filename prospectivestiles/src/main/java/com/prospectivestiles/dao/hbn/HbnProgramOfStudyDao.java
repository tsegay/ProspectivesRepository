package com.prospectivestiles.dao.hbn;

import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.prospectivestiles.dao.AddressDao;
import com.prospectivestiles.dao.ProgramOfStudyDao;
import com.prospectivestiles.dao.UserEntityDao;
import com.prospectivestiles.domain.Address;
import com.prospectivestiles.domain.ProgramOfStudy;
import com.prospectivestiles.domain.UserEntity;
import com.prospectivestiles.service.UserEntityService;

@Repository
public class HbnProgramOfStudyDao extends AbstractHbnDao<ProgramOfStudy> implements ProgramOfStudyDao {

	@Inject private JdbcTemplate jdbcTemplate;
	@Inject private UserEntityDao userEntityDao;
	
	private static final String FIND_ADDRESS1_SQL =
			"select address1 from address where id = ?";
	private static final String UPDATE_ZIPCODE_SQL =
			"update address set zipcode = ? where id = ?";
	
	

	public String findAddress1ById(String username) {
		return jdbcTemplate.queryForObject(
				FIND_ADDRESS1_SQL, new Object[] { username }, String.class);
	}
	
	/**
	 * FOR TESTING PURPOSE
	 */
	/*public void updateZipCode(long addressId, String zipcode) {
		//jdbcTemplate.update(UPDATE_PASSWORD_SQL, encPassword, account.getUsername());
		jdbcTemplate.update(UPDATE_ZIPCODE_SQL, zipcode, addressId);
	}*/


//	@SuppressWarnings("unchecked")
//	@Override
//	public List<ProgramOfStudy> getProgramOfStudiesByUserEntityId(
//			long userEntityId) {
//		Session session = getSession();
//		Query query = session.getNamedQuery("findProgramOfStudiesByUserEntityId");
//		query.setParameter("id", userEntityId);
//		
//		List<ProgramOfStudy> ProgramOfStudies = query.list();
//		
//		return ProgramOfStudies;
//	}
	
	public Collection<ProgramOfStudy> getProgramOfStudiesByUserEntityId2(long userEntityId){
		
//		UserEntity userEntity = userEntityDao.find(userEntityId);
//		
//		Collection<ProgramOfStudy> listOfProgramOfStudy = userEntity.getListOfProgramOfStudy();
//		
//		return listOfProgramOfStudy;
		return null;
		
	}

}
