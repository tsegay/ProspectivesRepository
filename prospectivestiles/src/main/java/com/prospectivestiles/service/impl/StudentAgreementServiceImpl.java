package com.prospectivestiles.service.impl;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prospectivestiles.dao.StudentAgreementDao;
import com.prospectivestiles.domain.AgreementName;
import com.prospectivestiles.domain.StudentAgreement;
import com.prospectivestiles.service.StudentAgreementService;

@Service
@Transactional
public class StudentAgreementServiceImpl implements StudentAgreementService {
	
	@Inject
	private StudentAgreementDao studentAgreementDao;

	@Override
	public StudentAgreement getStudentAgreement(long id) {
		return studentAgreementDao.find(id);
	}

	@Override
	public List<StudentAgreement> getAllStudentAgreements() {
		return studentAgreementDao.findAll();
	}

	@Override
	public List<StudentAgreement> getStudentAgreementsByUserEntityId(long userEntityId) {
		return studentAgreementDao.getStudentAgreementsByUserEntityId(userEntityId);
	}

	@Override
	public void createStudentAgreement(StudentAgreement studentAgreement) {
		studentAgreementDao.create(studentAgreement);
	}

	/**
	 * I THINK ONCE AN AGREEMENT IS MADE,
	 * NO ONE SHOULD UPDATE IT, DELETE IT.
	 * 
	 * I THINK YOU CAN'T JUST UNDO YOUR AGREEEMENT TO TERMS OF AGREEMENT!!!!!
	 */
	@Override
	public void updateStudentAgreement(StudentAgreement studentAgreement) {
		StudentAgreement  studentAgreementToUpdate = studentAgreementDao.find(studentAgreement.getId());
		
		studentAgreementToUpdate.setAgreementName(studentAgreement.getAgreementName());
		studentAgreementToUpdate.setAgree(studentAgreement.isAgree());
		studentAgreementToUpdate.setSignature(studentAgreement.getSignature());
		studentAgreementToUpdate.setUserEntity(studentAgreement.getUserEntity());
		Date now = new Date();
		studentAgreementToUpdate.setDateLastModified(now);
		studentAgreementToUpdate.setLastModifiedBy(studentAgreement.getLastModifiedBy());
		
		studentAgreementDao.update(studentAgreementToUpdate);
	}

	/**
	 * I THINK ONCE AN AGREEMENT IS MADE,
	 * NO ONE SHOULD UPDATE IT, DELETE IT.
	 * 
	 * I THINK YOU CAN'T JUST UNDO YOUR AGREEEMENT TO TERMS OF AGREEMENT!!!!!
	 */
	@Override
	public void delete(StudentAgreement studentAgreement) {
		studentAgreementDao.delete(studentAgreement);
	}

	@Override
	public StudentAgreement getStudentAgreementByUserEntityIdAndAgreementName(
			long userEntityId, AgreementName agreementName) {
		return studentAgreementDao.getStudentAgreementByUserEntityIdAndAgreementName(userEntityId, agreementName);
	}
	
	

}
