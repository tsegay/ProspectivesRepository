package com.prospectivestiles.service.impl;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prospectivestiles.dao.ChecklistDao;
import com.prospectivestiles.dao.EvaluationDao;
import com.prospectivestiles.domain.Checklist;
import com.prospectivestiles.domain.Evaluation;
import com.prospectivestiles.service.ChecklistService;
import com.prospectivestiles.service.EvaluationService;

@Service
@Transactional
public class EvaluationServiceImpl implements EvaluationService {
	
	@Inject
	private EvaluationDao evaluationDao;

	
	@Override
	public Evaluation getEvaluation(long id) {
		return evaluationDao.find(id);
	}

	@Override
	public List<Evaluation> getAllEvaluations() {
		return evaluationDao.findAll();
	}

	@Override
	public Evaluation getEvaluationByUserEntityId(long userEntityId) {
		return evaluationDao.getEvaluationByUserEntityId(userEntityId);
	}

	@Override
	public void createEvaluation(Evaluation evaluation) {
		evaluationDao.create(evaluation);
	}

	@Override
	public void updateEvaluation(Evaluation evaluation) {
		Evaluation evaluationToUpdate = evaluationDao.find(evaluation.getId());
		
		evaluationToUpdate.setBankStmt(evaluation.getBankStmt());
		evaluationToUpdate.setF1Visa(evaluation.getF1Visa());
		evaluationToUpdate.setI20(evaluation.getI20());
		evaluationToUpdate.setNotes(evaluation.getNotes());
		evaluationToUpdate.setPassport(evaluation.getPassport());
		evaluationToUpdate.setFinancialAffidavit(evaluation.getFinancialAffidavit());
		evaluationToUpdate.setApplicationFee(evaluation.getApplicationFee());
		evaluationToUpdate.setApplicationForm(evaluation.getApplicationForm());
		evaluationToUpdate.setEnrollmentAgreement(evaluation.getEnrollmentAgreement());
		evaluationToUpdate.setGrievancePolicy(evaluation.getGrievancePolicy());
		evaluationToUpdate.setRecommendationLetter(evaluation.getRecommendationLetter());
		evaluationToUpdate.setTranscript(evaluation.getTranscript());
		evaluationToUpdate.setDiplome(evaluation.getDiplome());
//		evaluationToUpdate.setAdmissionOfficer(evaluation.getAdmissionOfficer());
		evaluationToUpdate.setAdmnOfficerReport(evaluation.getAdmnOfficerReport());
//		evaluationToUpdate.setStudentQualification(evaluation.getStudentQualification());
		
		evaluationToUpdate.setStatus(evaluation.getStatus());
		evaluationToUpdate.setAdmittedBy(evaluation.getAdmittedBy());
		evaluationToUpdate.setDateAdmitted(evaluation.getDateAdmitted());
		Date now = new Date();
		evaluationToUpdate.setDateLastModified(now);
		evaluationToUpdate.setLastModifiedBy(evaluation.getLastModifiedBy());
		
		evaluationDao.update(evaluationToUpdate);
	}

	@Override
	public void deleteEvaluation(Evaluation evaluation) {
		evaluationDao.delete(evaluation);
	}

	@Override
	public List<Evaluation> findEvaluationsByStatus(String status) {
		return evaluationDao.findEvaluationsByStatus(status);
	}

	@Override
	public long countByStatus(String status) {
		return evaluationDao.countByStatus(status);
	}
	


}
