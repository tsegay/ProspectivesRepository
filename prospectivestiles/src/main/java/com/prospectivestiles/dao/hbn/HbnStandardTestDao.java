package com.prospectivestiles.dao.hbn;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.prospectivestiles.dao.StandardTestDao;
import com.prospectivestiles.domain.StandardTest;

@Repository
public class HbnStandardTestDao extends AbstractHbnDao<StandardTest> implements StandardTestDao {

	
	@SuppressWarnings("unchecked")
	@Override
	public List<StandardTest> getStandardTestsByUserEntityId(long userEntityId) {
		Session session = getSession();
		Query query = session.getNamedQuery("findStandardTestsByUserEntityId");
		query.setParameter("id", userEntityId);
		
		List<StandardTest> standardTests = query.list();
		
		return standardTests;
	}

}
