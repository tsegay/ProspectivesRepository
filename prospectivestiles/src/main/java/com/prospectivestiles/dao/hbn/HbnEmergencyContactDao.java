package com.prospectivestiles.dao.hbn;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.prospectivestiles.dao.EmergencyContactDao;
import com.prospectivestiles.domain.EmergencyContact;

@Repository
public class HbnEmergencyContactDao extends AbstractHbnDao<EmergencyContact> implements EmergencyContactDao {

	
	@SuppressWarnings("unchecked")
	@Override
	public List<EmergencyContact> getEmergencyContactsByUserEntityId(
			long userEntityId) {
		Session session = getSession();
		Query query = session.getNamedQuery("findEmergencyContactsByUserEntityId");
		query.setParameter("id", userEntityId);
		
		List<EmergencyContact> emergencyContacts = query.list();
		
		return emergencyContacts;
	}

}
