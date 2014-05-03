package com.prospectivestiles.dao.hbn;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.prospectivestiles.dao.EmployerDao;
import com.prospectivestiles.domain.Employer;

@Repository
public class HbnEmployerDao extends AbstractHbnDao<Employer> implements EmployerDao {

	
	@SuppressWarnings("unchecked")
	@Override
	public List<Employer> getEmployersByUserEntityId(long userEntityId) {
		Session session = getSession();
		Query query = session.getNamedQuery("findEmployersByUserEntityId");
		query.setParameter("id", userEntityId);
		
		List<Employer> employers = query.list();
		
		return employers;
	}

}
