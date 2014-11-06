package com.prospectivestiles.service.impl;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;

import com.prospectivestiles.dao.ChecklistDao;
import com.prospectivestiles.dao.EvaluationDao;
import com.prospectivestiles.dao.UserEntityDao;
import com.prospectivestiles.domain.Checklist;
import com.prospectivestiles.domain.Evaluation;
import com.prospectivestiles.domain.Role;
import com.prospectivestiles.domain.UserEntity;
import com.prospectivestiles.service.ChecklistService;
import com.prospectivestiles.service.EvaluationService;
import com.prospectivestiles.service.UserEntityService;

@Service
@Transactional
public class EvaluationServiceImpl implements EvaluationService {
	
	@Inject
	private EvaluationDao evaluationDao;

	@Inject
	private UserEntityService userEntityService;
	
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
	@Transactional(readOnly = false)
	public void createEvaluation(Evaluation evaluation) {
		
		/**
		 * Before creating an evaluation for an applicant,
		 * check applicant doesn't have an existing evaluation
		 * 
		 * If applicant doesn't have an evaluation already created, create the evaluation
		 * Else don't create the evaluation, instead redirect user to the existing evaluation page
		 * 
		 * TO DO: show err msg to user
		 */
		// Check if userEntity exist before calling the id
		if (validateEvaluation(evaluation.getUserEntity().getId())) {
			
			if (
					(evaluation.getApplicationFee().equalsIgnoreCase("complete") || evaluation.getApplicationFee().equalsIgnoreCase("notrequired")) &&
					(evaluation.getBankStmt().equalsIgnoreCase("complete") || evaluation.getBankStmt().equalsIgnoreCase("notrequired")) &&
					(evaluation.getDiplome().equalsIgnoreCase("complete") || evaluation.getDiplome().equalsIgnoreCase("notrequired")) &&
					(evaluation.getFinancialAffidavit().equalsIgnoreCase("complete") || evaluation.getFinancialAffidavit().equalsIgnoreCase("notrequired")) && 
					(evaluation.getF1Visa().equalsIgnoreCase("complete") || evaluation.getF1Visa().equalsIgnoreCase("notrequired")) &&
					(evaluation.getI20().equalsIgnoreCase("complete") || evaluation.getI20().equalsIgnoreCase("notrequired")) &&
					(evaluation.getPassport().equalsIgnoreCase("complete") || evaluation.getPassport().equalsIgnoreCase("notrequired")) && 
					(evaluation.getTranscript().equalsIgnoreCase("complete") || evaluation.getTranscript().equalsIgnoreCase("notrequired")) &&
					(evaluation.getApplicationForm().equalsIgnoreCase("complete") || evaluation.getApplicationForm().equalsIgnoreCase("notrequired")) &&
					(evaluation.getEnrollmentAgreement().equalsIgnoreCase("complete") || evaluation.getEnrollmentAgreement().equalsIgnoreCase("notrequired")) &&
					(evaluation.getGrievancePolicy().equalsIgnoreCase("complete") || evaluation.getGrievancePolicy().equalsIgnoreCase("notrequired")) &&
					(evaluation.getRecommendationLetter().equalsIgnoreCase("complete") || evaluation.getRecommendationLetter().equalsIgnoreCase("notrequired")) 
				) {
				
				/**
				 * If all evaluation items are complete or notrequired change accountState to "COMPLETE"
				 * When accountState of an applicant change the role should also change
				 */
				userEntityService.updateUserEntityRole(evaluation.getUserEntity().getId(),"complete","ROLE_STUDENT_COMPLETE");
				
				
			} else {
				
				/**
				 * If all evaluation items are not complete or notrequired make accountState to "INPROCESS"
				 * When accountState of an applicant change the role should also change
				 */
				userEntityService.updateUserEntityRole(evaluation.getUserEntity().getId(), "inprocess", "ROLE_STUDENT_INPROCESS");
				
			}
			
			evaluationDao.create(evaluation);
		}
		
		
	}
	

	@Override
	public void updateEvaluation(Evaluation evaluation) {
		Evaluation evaluationToUpdate = evaluationDao.find(evaluation.getId());
		
		evaluationToUpdate.setBankStmt(evaluation.getBankStmt());
		evaluationToUpdate.setSourceOfMoney(evaluation.getSourceOfMoney());
		evaluationToUpdate.setAmountOfMoney(evaluation.getAmountOfMoney());
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
		evaluationToUpdate.setLanguageProficiency(evaluation.getLanguageProficiency());
		evaluationToUpdate.setTranscript(evaluation.getTranscript());
		evaluationToUpdate.setDiplome(evaluation.getDiplome());
//		evaluationToUpdate.setAdmissionOfficer(evaluation.getAdmissionOfficer());
		evaluationToUpdate.setAdmnOfficerReport(evaluation.getAdmnOfficerReport());
//		evaluationToUpdate.setStudentQualification(evaluation.getStudentQualification());
		
//		evaluationToUpdate.setStatus(evaluation.getStatus());
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
	
//	private boolean validateEvaluation(long userEntityId, Errors errors) {
	private boolean validateEvaluation(long userEntityId) {
		System.out.println("validateEvaluation");
		
		boolean returnValue = true;
		
		if (evaluationDao.getEvaluationByUserEntityId(userEntityId) != null) {
			System.out.println("inside evaluationDao.getEvaluationByUserEntityId(userEntityId)");
			System.out.println("evalId: " + evaluationDao.getEvaluationByUserEntityId(userEntityId).getId());
			returnValue = false;
//			errors.rejectValue("email", "error.duplicateemail", new String[] { email }, null);
		}
		return returnValue;
	}

	


}
