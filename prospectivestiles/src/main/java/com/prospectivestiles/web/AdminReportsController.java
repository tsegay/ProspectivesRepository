package com.prospectivestiles.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.prospectivestiles.domain.AssociatedUser;
import com.prospectivestiles.domain.Checklist;
import com.prospectivestiles.domain.Evaluation;
import com.prospectivestiles.domain.UserEntity;
import com.prospectivestiles.service.AssociatedUserService;
import com.prospectivestiles.service.ChecklistService;
import com.prospectivestiles.service.EvaluationService;
import com.prospectivestiles.service.UserEntityService;

@Controller
public class AdminReportsController {
	
	
	@Autowired
	private UserEntityService userEntityService;
	
	@Inject
	private EvaluationService evaluationService;
	
	@Inject
	private ChecklistService checklistService;
	
	@Inject
	private AssociatedUserService associatedUserService;
	
	// ======================================
	// =             reports             =
	// ======================================
	
	/**
	 * Not using the Checklist class any longer.
	 * Get missing documents from the Evaluation class
	 * 
	 */
	@RequestMapping(value = "/accounts/{userEntityId}/reports/missingDocuments", method = RequestMethod.GET)
	public String getMissingDocuments(@PathVariable("userEntityId") Long userEntityId,
			Model model) {
		
//		Checklist checklist = checklistService.getChecklistByUserEntityId(userEntityId);
		Evaluation evaluation = evaluationService.getEvaluationByUserEntityId(userEntityId);
		ArrayList<String> missingDocuments = new ArrayList<String>();
		AssociatedUser associatedUser = associatedUserService.getAssociatedUserByUserEntityId(userEntityId);
		String admissionOfficerName = null;
		if (associatedUser != null) {
			
			if (associatedUser.getAdmissionOfficer() != null) {
				admissionOfficerName = associatedUser.getAdmissionOfficer().getFullName();
			} else {
				admissionOfficerName = "None";
			}
			
		}
		
		/**
		 * if user has no checklist created, you can't generate missing documents report
		 */
		if (evaluation != null) {
		
			if (evaluation.getF1Visa().equalsIgnoreCase("incomplete")) {
				missingDocuments.add("F1 Visa");
			}
			if (evaluation.getBankStmt().equalsIgnoreCase("incomplete")) {
				missingDocuments.add("Bank Statement");
			}
			if (evaluation.getI20().equalsIgnoreCase("incomplete")) {
				missingDocuments.add("I20");
			}
			if (evaluation.getPassport().equalsIgnoreCase("incomplete")) {
				missingDocuments.add("Passport");
			}
			if (evaluation.getFinancialAffidavit().equalsIgnoreCase("incomplete")) {
				missingDocuments.add("Financial Affidavit");
			}
			if (evaluation.getApplicationFee().equalsIgnoreCase("incomplete")) {
				missingDocuments.add("Application Fee");
			}
			if (evaluation.getApplicationForm().equalsIgnoreCase("incomplete")) {
				missingDocuments.add("Application Form");
			}
			if (evaluation.getEnrollmentAgreement().equalsIgnoreCase("incomplete")) {
				missingDocuments.add("Enrollment Agreement");
			}
			if (evaluation.getGrievancePolicy().equalsIgnoreCase("incomplete")) {
				missingDocuments.add("Grievance Policy");
			}
			if (evaluation.getRecommendationLetter().equalsIgnoreCase("incomplete")) {
				missingDocuments.add("Recommendation Letter");
			}
			if (evaluation.getTranscript().equalsIgnoreCase("incomplete")) {
				missingDocuments.add("Transcript");
			}
			if (evaluation.getDiplome().equalsIgnoreCase("incomplete")) {
				missingDocuments.add("Diplome");
			}
		
		}
		
		model.addAttribute("missingDocuments", missingDocuments);
		model.addAttribute("admissionOfficerName", admissionOfficerName);
		/**
		 * pass userEntity to the page. Else if you try to navigate to other pages from missingDocuments for eg to evaluation
		 * the url will not find the userEntity id --> accounts//evaluations instead of accounts/4/evaluations for eg.
		 */
		model.addAttribute("userEntity", userEntityService.getUserEntity(userEntityId));
		
		return "missingDocuments";
	}
	
	@RequestMapping(value = "/accounts/{userEntityId}/reports/evaluationReport", method = RequestMethod.GET)
	public String getEvaluationReport(@PathVariable("userEntityId") Long userEntityId,
			Model model) {
		
		UserEntity student = userEntityService.getUserEntity(userEntityId);
		Evaluation evaluation = evaluationService.getEvaluationByUserEntityId(userEntityId);
		AssociatedUser associatedUser = associatedUserService.getAssociatedUserByUserEntityId(userEntityId);
//		ArrayList<String> evaluationReportSummary = new ArrayList<String>();
		Map<String, Object> evaluationReportSummary = new HashMap<String, Object>();
		
		/**
		 * if user has no evaluation created, you can't generate missing documents report
		 */
		if (evaluation != null) {
			
			if (
			(evaluation.getApplicationFee().equalsIgnoreCase("complete") || evaluation.getApplicationFee().equalsIgnoreCase("notrequired")) &&  
			(evaluation.getApplicationForm().equalsIgnoreCase("complete") || evaluation.getApplicationForm().equalsIgnoreCase("notrequired")) &&  
			(evaluation.getEnrollmentAgreement().equalsIgnoreCase("complete") || evaluation.getEnrollmentAgreement().equalsIgnoreCase("notrequired")) &&  
			(evaluation.getGrievancePolicy().equalsIgnoreCase("complete") || evaluation.getGrievancePolicy().equalsIgnoreCase("notrequired")) &&  
			(evaluation.getRecommendationLetter().equalsIgnoreCase("complete") || evaluation.getRecommendationLetter().equalsIgnoreCase("notrequired")) &&  
			(evaluation.getBankStmt().equalsIgnoreCase("complete") || evaluation.getBankStmt().equalsIgnoreCase("notrequired")) && 
			(evaluation.getDiplome().equalsIgnoreCase("complete") || evaluation.getDiplome().equalsIgnoreCase("notrequired")) && 
			(evaluation.getF1Visa().equalsIgnoreCase("complete") || evaluation.getF1Visa().equalsIgnoreCase("notrequired")) && 
			(evaluation.getFinancialAffidavit().equalsIgnoreCase("complete") || evaluation.getFinancialAffidavit().equalsIgnoreCase("notrequired")) && 
			(evaluation.getI20().equalsIgnoreCase("complete") || evaluation.getI20().equalsIgnoreCase("notrequired")) && 
			(evaluation.getPassport().equalsIgnoreCase("complete") || evaluation.getPassport().equalsIgnoreCase("notrequired")) && 
			(evaluation.getTranscript().equalsIgnoreCase("complete") || evaluation.getTranscript().equalsIgnoreCase("notrequired"))) {
				
				evaluationReportSummary.put("admnOfficerReport",evaluation.getAdmnOfficerReport());
//				evaluationReportSummary.put("studentQualification",evaluation.getStudentQualification());
				evaluationReportSummary.put("dateLastModified",evaluation.getDateLastModified());
				if (associatedUser != null) {
					
					if (associatedUser.getAdmissionOfficer() != null) {
						evaluationReportSummary.put("admissionOfficerName",associatedUser.getAdmissionOfficer().getFullName());
					} else {
						evaluationReportSummary.put("admissionOfficerName","None");
					}
					
				}
				
			} else {
				if (student.getAccountState().equalsIgnoreCase("denied")) {
					
					evaluationReportSummary.put("admnOfficerReport",evaluation.getAdmnOfficerReport());
					evaluationReportSummary.put("dateLastModified",evaluation.getDateLastModified());
						
					if (associatedUser != null) {
						if (associatedUser.getAdmissionOfficer() != null) {
							evaluationReportSummary.put("admissionOfficerName",associatedUser.getAdmissionOfficer().getFullName());
						} else {
							evaluationReportSummary.put("admissionOfficerName","None");
						}
						
					}
				}
			}
		}
		
		model.addAttribute("evaluationReportSummary", evaluationReportSummary);
		/**
		 * pass userEntity to the page. Else if you try to navigate to other pages from missingDocuments for eg to evaluation
		 * the url will not find the userEntity id --> accounts//evaluations instead of accounts/4/evaluations for eg.
		 */
		model.addAttribute("userEntity", student);
		
		return "evaluationReport";
	}
	
	
	@RequestMapping(value = "/accounts/{userEntityId}/reports/checklistReport", method = RequestMethod.GET)
	public String getChecklistReport(@PathVariable("userEntityId") Long userEntityId,
			Model model) {
		
		Evaluation evaluation = evaluationService.getEvaluationByUserEntityId(userEntityId);
		
		model.addAttribute("evaluation", evaluation);
		/**
		 * pass userEntity to the page. Else if you try to navigate to other pages from missingDocuments for eg to evaluation
		 * the url will not find the userEntity id --> accounts//evaluations instead of accounts/4/evaluations for eg.
		 */
		model.addAttribute("userEntity", userEntityService.getUserEntity(userEntityId));
		
		return "checklistReport";
	}
	
	/**
	 * Fetch these entities: evaluation, associateduser, userEntity
	 * if user has an evaluation created and the account state is admitted,
	 * then pass status and ... to the jsp page to display the msg and provide optionto download the pdf letter
	 * @param userEntityId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/accounts/{userEntityId}/reports/acceptanceLetter", method = RequestMethod.GET)
	public String getAcceptanceLetter(@PathVariable("userEntityId") Long userEntityId,
			Model model) {
		
		Evaluation evaluation = evaluationService.getEvaluationByUserEntityId(userEntityId);
		AssociatedUser associatedUser = associatedUserService.getAssociatedUserByUserEntityId(userEntityId);
		Map<String, Object> acceptanceLetterReport = new HashMap<String, Object>();
		UserEntity userEntity = userEntityService.getUserEntity(userEntityId);
		
		/**
		 * if user has no checklist created, you can't generate missing documents report
		 */
		if ((evaluation != null) && (userEntity.getAccountState() != null)) {
			if(userEntity.getAccountState().equalsIgnoreCase("admitted")){
				acceptanceLetterReport.put("status", "admitted");
				acceptanceLetterReport.put("admittedBy",evaluation.getAdmittedBy().getFullName());
				acceptanceLetterReport.put("dateAdmitted",evaluation.getDateAdmitted());
				if (associatedUser != null) {
					
					if (associatedUser.getAdmissionOfficer() != null) {
						acceptanceLetterReport.put("Admissions Couselor",associatedUser.getAdmissionOfficer().getFullName());
					} else {
						acceptanceLetterReport.put("Admissions Couselor","None");
					}
					
				}
			}
		}
/*		if ((evaluation != null) && (evaluation.getStatus() != null)) {
			if(evaluation.getStatus().equalsIgnoreCase("admitted")){
				acceptanceLetterReport.put("status", "admitted");
				acceptanceLetterReport.put("admittedBy",evaluation.getAdmittedBy().getFullName());
				acceptanceLetterReport.put("dateAdmitted",evaluation.getDateAdmitted());
				if (associatedUser != null) {
					
					if (associatedUser.getAdmissionOfficer() != null) {
						acceptanceLetterReport.put("Admissions Couselor",associatedUser.getAdmissionOfficer().getFullName());
					} else {
						acceptanceLetterReport.put("Admissions Couselor","None");
					}
					
				}
			}
		}
*/		
		model.addAttribute("acceptanceLetterReport", acceptanceLetterReport);
		model.addAttribute("userEntity", userEntityService.getUserEntity(userEntityId));
		
		return "acceptanceLetter";
	}
	
	
	// ======================================
	// =                        =
	// ======================================
	
	private Evaluation getEvaluationValidateUserEntityId(Long userEntityId, Long highschoolId) {
		Evaluation evaluation = evaluationService.getEvaluation(highschoolId);
		
		Assert.isTrue(userEntityId.equals(evaluation.getUserEntity().getId()), "Evaluation Id mismatch");
		return evaluation;
	}
	
	private UserEntity getUserEntityFromSecurityContext() {
		SecurityContext securityCtx = SecurityContextHolder.getContext();
		Authentication auth = securityCtx.getAuthentication();
		UserEntity userEntity = (UserEntity) auth.getPrincipal();
		return userEntity;
	}
}
