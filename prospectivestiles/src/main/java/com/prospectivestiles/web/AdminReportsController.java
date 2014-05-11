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

import com.prospectivestiles.domain.Checklist;
import com.prospectivestiles.domain.Evaluation;
import com.prospectivestiles.domain.UserEntity;
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
	
	// ======================================
	// =             reports             =
	// ======================================
	
	
//	@RequestMapping(value = "/accounts/{userEntityId}/reports/missingDocuments", method = RequestMethod.GET)
//	@RequestMapping(value = "/accounts/{userEntityId}/reports/evaluationReport", method = RequestMethod.GET)
//	@RequestMapping(value = "/accounts/{userEntityId}/reports/acceptanceLetter", method = RequestMethod.GET)
	
//	@RequestMapping(value = "/accounts/{userEntityId}/reports/missingDocuments", method = RequestMethod.GET)
	@RequestMapping(value = "/accounts/{userEntityId}/reports/missingDocuments", method = RequestMethod.GET)
	public String getMissingDocuments(@PathVariable("userEntityId") Long userEntityId,
			Model model) {
		
		Checklist checklist = checklistService.getChecklistByUserEntityId(userEntityId);
		ArrayList<String> missingDocuments = new ArrayList<String>();
		
		/**
		 * if user has no checklist created, you can't generate missing documents report
		 */
		if (checklist != null) {
		
			if (checklist.isF1Visa() != true) {
				missingDocuments.add("F1Visa");
			}
			if (checklist.isBankStmt() != true) {
				missingDocuments.add("BankStmt");
			}
			if (checklist.isI20() != true) {
				missingDocuments.add("I20");
			}
			if (checklist.isPassport() != true) {
				missingDocuments.add("Passport");
			}
			if (checklist.isFinancialAffidavit() != true) {
				missingDocuments.add("FinancialAffidavit");
			}
			if (checklist.isApplicationFee() != true) {
				missingDocuments.add("ApplicationFee");
			}
			if (checklist.isTranscript() != true) {
				missingDocuments.add("Transcript");
			}
			if (checklist.isDiplome() != true) {
				missingDocuments.add("Diplome");
			}
		
		}
		
		model.addAttribute("missingDocuments", missingDocuments);
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
		
		Evaluation evaluation = evaluationService.getEvaluationByUserEntityId(userEntityId);
//		ArrayList<String> evaluationReportSummary = new ArrayList<String>();
		Map<String, Object> evaluationReportSummary = new HashMap<String, Object>();
		
		/**
		 * if user has no checklist created, you can't generate missing documents report
		 */
		if (evaluation != null) {
			
			if (evaluation.getF1Visa().equalsIgnoreCase("valid") &&
			evaluation.getApplicationFee().equalsIgnoreCase("valid") &&  
			evaluation.getBankStmt().equalsIgnoreCase("valid") && 
			evaluation.getDiplome().equalsIgnoreCase("valid") && 
			evaluation.getF1Visa().equalsIgnoreCase("valid") && 
			evaluation.getFinancialAffidavit().equalsIgnoreCase("valid") && 
			evaluation.getI20().equalsIgnoreCase("valid") && 
			evaluation.getPassport().equalsIgnoreCase("valid") && 
			evaluation.getTranscript().equalsIgnoreCase("valid")) {
				evaluationReportSummary.put("admnOfficerReport",evaluation.getAdmnOfficerReport());
				evaluationReportSummary.put("studentQualification",evaluation.getStudentQualification());
				evaluationReportSummary.put("admissionOfficerName",evaluation.getAdmissionOfficer().getFullName());
				
			}
		}
		
//		evaluation.getAdmnOfficerReport()
//		evaluation.getAdmissionOfficer()
//		evaluation.getDateCreated()
//		evaluation.getDateLastModified()
		
		
		model.addAttribute("evaluationReportSummary", evaluationReportSummary);
		/**
		 * pass userEntity to the page. Else if you try to navigate to other pages from missingDocuments for eg to evaluation
		 * the url will not find the userEntity id --> accounts//evaluations instead of accounts/4/evaluations for eg.
		 */
		model.addAttribute("userEntity", userEntityService.getUserEntity(userEntityId));
		
		return "evaluationReport";
	}
	
	
//	@RequestMapping(value = "/accounts/{userEntityId}/reports", method = RequestMethod.GET)
//	public String getReports(@PathVariable("userEntityId") Long userEntityId,
//			Model model) {
//		
//		/**
//		 * load evaluation for a user, if exist
//		 */
//		model.addAttribute("evaluations", evaluationService.getEvaluationByUserEntityId(userEntityId));
//		
//		/**
//		 * The modelAttribute "evaluation" for the form to add new evaluation
//		 */
//		Evaluation evaluation = new Evaluation();
//		model.addAttribute("evaluation", evaluation);
//		
//		model.addAttribute("userEntity", userEntityService.getUserEntity(userEntityId));
//		
//		return "reports";
//	}
//	
//	@RequestMapping(value = "/accounts/{userEntityId}/reports", method = RequestMethod.POST)
//	public String postNewEvaluationForm(@PathVariable("userEntityId") Long userEntityId,
//			@ModelAttribute @Valid Evaluation evaluation, BindingResult result) {
//		
//		if (result.hasErrors()) {
//			return "newEvaluationForm";
//		}
//
//		evaluation.setUserEntity(userEntityService.getUserEntity(userEntityId));
//		evaluationService.createEvaluation(evaluation);
//		
//		return "redirect:/accounts/{userEntityId}/reports";
//	}
//	
//	@RequestMapping(value = "/accounts/{userEntityId}/report/{evaluationId}", method = RequestMethod.GET)
//	public String editEvaluation(@PathVariable("userEntityId") Long userEntityId,
//			@PathVariable("evaluationId") Long evaluationId, Model model) {
//		
//		Evaluation evaluation = getEvaluationValidateUserEntityId(userEntityId, evaluationId);
//		
//		model.addAttribute("originalEvaluation", evaluation);
//		model.addAttribute(evaluation);
//		
//		return "editEvaluation";
//	}
//	
//	@RequestMapping(value = "/accounts/{userEntityId}/report/{evaluationId}", method = RequestMethod.POST)
//	public String editEvaluation(@PathVariable("userEntityId") Long userEntityId,
//			@PathVariable("evaluationId") Long evaluationId,
//			@ModelAttribute @Valid Evaluation origEvaluation, 
//			BindingResult result,
//			Model model) {
//		
//		Evaluation evaluation = getEvaluationValidateUserEntityId(userEntityId, evaluationId);
//
//		if (result.hasErrors()) {
//			model.addAttribute("originalEvaluation", origEvaluation);
//			return "editEvaluation";
//		}
//		
//		evaluation.setBankStmt(origEvaluation.getBankStmt());
//		evaluation.setF1Visa(origEvaluation.getF1Visa());
//		evaluation.setI20(origEvaluation.getI20());
//		evaluation.setNotes(origEvaluation.getNotes());
//		
//		
//		evaluationService.updateEvaluation(evaluation);
//		
//		return "redirect:/accounts/{userEntityId}/evaluations";
//	}
//	
//	@RequestMapping(value = "/accounts/{userEntityId}/report/{evaluationId}/delete", method = RequestMethod.POST)
//	public String deleteEvaluation(@PathVariable("userEntityId") Long userEntityId,
//			@PathVariable("evaluationId") Long evaluationId)
//			throws IOException {
//		evaluationService.deleteEvaluation(getEvaluationValidateUserEntityId(userEntityId, evaluationId));
//		return "redirect:/accounts/{userEntityId}/reports";
//	}
	
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
