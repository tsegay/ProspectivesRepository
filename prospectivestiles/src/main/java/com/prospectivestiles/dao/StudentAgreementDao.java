package com.prospectivestiles.dao;

import java.util.List;

import com.prospectivestiles.domain.AgreementName;
import com.prospectivestiles.domain.StudentAgreement;

public interface StudentAgreementDao extends Dao<StudentAgreement>{

	List<StudentAgreement> getStudentAgreementsByUserEntityId(long userEntityId);

	StudentAgreement getStudentAgreementByUserEntityIdAndAgreementName(long userEntityId, AgreementName agreementName);
	
}
