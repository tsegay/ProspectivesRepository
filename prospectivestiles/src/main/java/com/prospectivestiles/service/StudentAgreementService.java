package com.prospectivestiles.service;

import java.util.List;

import com.prospectivestiles.domain.AgreementName;
import com.prospectivestiles.domain.StudentAgreement;

public interface StudentAgreementService {
	
	StudentAgreement getStudentAgreement(long id);
	List<StudentAgreement> getAllStudentAgreements();
	List<StudentAgreement> getStudentAgreementsByUserEntityId(long userEntityId);
	void createStudentAgreement(StudentAgreement studentAgreement);
	void updateStudentAgreement(StudentAgreement studentAgreement);
	void delete(StudentAgreement studentAgreement);
	StudentAgreement getStudentAgreementByUserEntityIdAndAgreementName(long userEntityId, AgreementName agreementName);
	
}
