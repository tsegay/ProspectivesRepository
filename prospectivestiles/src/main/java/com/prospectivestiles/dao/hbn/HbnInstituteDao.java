package com.prospectivestiles.dao.hbn;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.prospectivestiles.dao.InstituteDao;
import com.prospectivestiles.domain.HighSchool;
import com.prospectivestiles.domain.Institute;

@Repository
public class HbnInstituteDao extends AbstractHbnDao<Institute> implements InstituteDao {
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Institute> getInstitutesByUserEntityId(long userEntityId) {
		Session session = getSession();
		Query query = session.getNamedQuery("findInstitutesByUserEntityId");
		query.setParameter("id", userEntityId);
		
		List<Institute> institutes = query.list();
		
		/*List<Object[]> results = query.list();

		List<HighSchool> highSchools = new ArrayList<HighSchool>();
		
		for (Object[] result : results) {
			HighSchool highSchool = (HighSchool) result[0];
			highSchools.add(highSchool);
		}*/

		return institutes;
	}

}
