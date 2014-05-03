package com.prospectivestiles.dao.hbn;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.prospectivestiles.dao.HighSchoolDao;
import com.prospectivestiles.domain.HighSchool;

@Repository
public class HbnHighSchoolDao extends AbstractHbnDao<HighSchool> implements HighSchoolDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<HighSchool> getHighSchoolsByUserEntityId(long userEntityId) {
		Session session = getSession();
		Query query = session.getNamedQuery("findHighSchoolsByUserEntityId");
		query.setParameter("id", userEntityId);
		
		List<HighSchool> highSchools = query.list();
		
		/*List<Object[]> results = query.list();

		List<HighSchool> highSchools = new ArrayList<HighSchool>();
		
		for (Object[] result : results) {
			HighSchool highSchool = (HighSchool) result[0];
			highSchools.add(highSchool);
		}*/

		return highSchools;
	}

}
