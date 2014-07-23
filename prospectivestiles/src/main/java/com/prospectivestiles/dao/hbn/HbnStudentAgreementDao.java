package com.prospectivestiles.dao.hbn;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.prospectivestiles.dao.StudentAgreementDao;
import com.prospectivestiles.domain.Address;
import com.prospectivestiles.domain.AddressType;
import com.prospectivestiles.domain.AgreementName;
import com.prospectivestiles.domain.StudentAgreement;

@Repository
public class HbnStudentAgreementDao extends AbstractHbnDao<StudentAgreement> implements StudentAgreementDao {
	
	@SuppressWarnings("unchecked")
	@Override
	public List<StudentAgreement> getStudentAgreementsByUserEntityId(long userEntityId) {
		Session session = getSession();
		Query query = session.getNamedQuery("findStudentAgreementsByUserEntityId");
		query.setParameter("id", userEntityId);
		
		List<StudentAgreement> studentAgreements = query.list();
		
		return studentAgreements;
	}
	
	/**
	 * Return a single address for a user. Eg Home address or mailing address
	 */
	@SuppressWarnings("unchecked")
	@Override
	public StudentAgreement getStudentAgreementByUserEntityIdAndAgreementName(long userEntityId, AgreementName agreementName) {
		Session session = getSession();
		Query query = session.getNamedQuery("findStudentAgreementByUserEntityIdAndAgreementName");
		query.setParameter("id", userEntityId);
		query.setParameter("agreementName", agreementName);
		
		List<StudentAgreement> studentAgreements = query.list();
		StudentAgreement studentAgreement = null;
		
//		if (addresses != null) {
		if (studentAgreements.size() != 0) {
			studentAgreement = studentAgreements.get(0);
		} 
		
		return studentAgreement;
	}

}
