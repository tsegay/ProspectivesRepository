package com.prospectivestiles.dao.hbn;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.prospectivestiles.dao.CountryDao;
import com.prospectivestiles.domain.Country;

@Repository
public class HbnCountryDao extends AbstractHbnDao<Country> implements CountryDao {

	@Override
	public Country getCountryByUserEntityId(long userEntityId) {
		Session session = getSession();
		Query query = session.getNamedQuery("findCountryByUserEntityId");
		query.setParameter("id", userEntityId);
		
		Country country = (Country) query.uniqueResult();
		
		return country;
	}

}
