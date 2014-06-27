package com.prospectivestiles.web;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.validation.Valid;

import org.apache.velocity.tools.config.Data;
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
import org.springframework.web.bind.annotation.ResponseBody;

import com.prospectivestiles.domain.Checklist;
import com.prospectivestiles.domain.EmergencyContact;
import com.prospectivestiles.domain.Evaluation;
import com.prospectivestiles.domain.UserEntity;
import com.prospectivestiles.service.EvaluationService;
import com.prospectivestiles.service.UserEntityService;

/**
 * There is one evaluation for every student.
 * I may not need to pass evaluationId in the url, userId is enough
 * @author danielanenia
 *
 */

@Controller
public class AdminEvaluationController {
	
	
	@Autowired
	private UserEntityService userEntityService;
	
	@Inject
	private EvaluationService evaluationService;
	
	// ======================================
	// =             evaluations             =
	// ======================================
	
	/**
	 * One Student has one evaluation only.
	 * Thinking of Modifying the getEvaluations and editCheklist methods!!
	 * Check if user has evaluation
	 * If user has evaluation return it
	 * Else create new evaluation for user
	 * 
	 * in the view page, check if user has a evaluation created
	 * If no evaluation exist for user, display create evaluation button
	 * Else display Edit evaluation button
	 * 
	 * Better option: when user registers, create the checklist and evaluation by default.
	 * then admission office don't have to click on 'create ...' button as every applicant should have one.
	 * 
	 */
	
	@RequestMapping(value = "/accounts/{userEntityId}/evaluations", method = RequestMethod.GET)
	public String getEvaluations(@PathVariable("userEntityId") Long userEntityId,
			Model model) {
		
		/**
		 * load evaluation for a user, if exist
		 */
		model.addAttribute("evaluations", evaluationService.getEvaluationByUserEntityId(userEntityId));
		
		/**
		 * The modelAttribute "evaluation" for the form to add new evaluation
		 */
		Evaluation evaluation = new Evaluation();
		model.addAttribute("evaluation", evaluation);
		
		model.addAttribute("userEntity", userEntityService.getUserEntity(userEntityId));
		
		return "evaluations";
	}
	
	
	@RequestMapping(value = "/accounts/{userEntityId}/evaluation/new", method = RequestMethod.GET)
	public String getNewEvaluationForm(@PathVariable("userEntityId") Long userEntityId,
			Model model) {
		UserEntity userEntity = userEntityService.getUserEntity(userEntityId);
		
		// get the admission officer and set the evaluation.admofficer
		
		Evaluation evaluation = new Evaluation();
		evaluation.setUserEntity(userEntity);
		
		model.addAttribute(evaluation);
		model.addAttribute(userEntity);
		
		return "newEvaluationForm";
	}
	
	@RequestMapping(value = "/accounts/{userEntityId}/evaluations", method = RequestMethod.POST)
	public String postNewEvaluationForm(@PathVariable("userEntityId") Long userEntityId,
			@ModelAttribute @Valid Evaluation evaluation, BindingResult result, Model model) {
		
		UserEntity admissionOfficer = getUserEntityFromSecurityContext();
		UserEntity userEntity = userEntityService.getUserEntity(userEntityId);
		
		if (result.hasErrors()) {
			model.addAttribute(userEntity);
			return "newEvaluationForm";
		}

		evaluation.setUserEntity(userEntity);
		evaluation.setAdmissionOfficer(admissionOfficer);
		evaluation.setStatus("inprocess");
		evaluationService.createEvaluation(evaluation);
		
		return "redirect:/accounts/{userEntityId}/evaluations";
	}

	

	@RequestMapping(value = "/accounts/{userEntityId}/evaluation/{evaluationId}/edit", method = RequestMethod.GET)
	public String editEvaluation(@PathVariable("userEntityId") Long userEntityId,
			@PathVariable("evaluationId") Long evaluationId, Model model) {
		
		Evaluation evaluation = getEvaluationValidateUserEntityId(userEntityId, evaluationId);
		
		model.addAttribute("originalEvaluation", evaluation);
		model.addAttribute(evaluation);
		
		model.addAttribute("userEntity", userEntityService.getUserEntity(userEntityId));
		
		return "editEvaluation";
	}

	@RequestMapping(value = "/accounts/{userEntityId}/evaluation/{evaluationId}", method = RequestMethod.POST)
	public String editEvaluation(@PathVariable("userEntityId") Long userEntityId,
			@PathVariable("evaluationId") Long evaluationId,
			@ModelAttribute @Valid Evaluation origEvaluation, 
			BindingResult result,
			Model model) {
		
		Evaluation evaluation = getEvaluationValidateUserEntityId(userEntityId, evaluationId);
		UserEntity admissionOfficer = getUserEntityFromSecurityContext();
		UserEntity userEntity = userEntityService.getUserEntity(userEntityId);
		if (result.hasErrors()) {
			origEvaluation.setId(evaluationId);
			origEvaluation.setUserEntity(userEntityService.getUserEntity(userEntityId));
//			model.addAttribute("originalEvaluation", origEvaluation);
			model.addAttribute("evaluation", origEvaluation);
			model.addAttribute(userEntity);
			/*
			when I used this model.addAttribute("evaluation", origEvaluation);
			I got java.lang.NullPointerException: .../accounts/10/evaluation/0
			origEvaluation has no userEntity set
			And when I use model.addAttribute("evaluation", evaluation)
			page doesn't crash; but form is rediplayed and I lose all data I typed
			When I use a return "/accounts/{userEntityId}/evaluation/{evaluationId}/edit" 
				I get view can't be resolved err
			When I use a return "redirect:/accounts/" + userEntityId + "/evaluation/" + evaluationId + "/edit"
				I get redirected to the edit form page,  the page is refreshed and I lose all the data typed, no err msg displayed on page
			*/
			return "editEvaluation";
		}
		
		
		evaluation.setBankStmt(origEvaluation.getBankStmt());
		evaluation.setF1Visa(origEvaluation.getF1Visa());
		evaluation.setI20(origEvaluation.getI20());
		evaluation.setNotes(origEvaluation.getNotes());
		evaluation.setPassport(origEvaluation.getPassport());
		evaluation.setFinancialAffidavit(origEvaluation.getFinancialAffidavit());
		evaluation.setApplicationFee(origEvaluation.getApplicationFee());
		evaluation.setTranscript(origEvaluation.getTranscript());
		evaluation.setDiplome(origEvaluation.getDiplome());
		evaluation.setAdmissionOfficer(admissionOfficer);
		evaluation.setAdmnOfficerReport(origEvaluation.getAdmnOfficerReport());
		evaluation.setStudentQualification(origEvaluation.getStudentQualification());
//		evaluation.setDateLastModified(dateLastModified);
//		evaluation.setDateCreated(dateCreated);
		
		
		if (evaluation.getF1Visa().equalsIgnoreCase("valid") &&
				evaluation.getApplicationFee().equalsIgnoreCase("valid") &&  
				evaluation.getBankStmt().equalsIgnoreCase("valid") && 
				evaluation.getDiplome().equalsIgnoreCase("valid") && 
				evaluation.getF1Visa().equalsIgnoreCase("valid") && 
				evaluation.getFinancialAffidavit().equalsIgnoreCase("valid") && 
				evaluation.getI20().equalsIgnoreCase("valid") && 
				evaluation.getPassport().equalsIgnoreCase("valid") && 
				evaluation.getTranscript().equalsIgnoreCase("valid")) {
			evaluation.setStatus("complete");
		} else {
			evaluation.setStatus("inprocess");
		}
		
		evaluationService.updateEvaluation(evaluation);
		
		return "redirect:/accounts/{userEntityId}/evaluations";
	}

	@RequestMapping(value = "/accounts/{userEntityId}/evaluation/{evaluationId}/grantAdmision", method = RequestMethod.POST)
	public String grantAdmision(@PathVariable("userEntityId") Long userEntityId,
			@PathVariable("evaluationId") Long evaluationId,
			@ModelAttribute @Valid Evaluation origEvaluation, 
			BindingResult result,
			Model model){
			
		Evaluation evaluation = getEvaluationValidateUserEntityId(userEntityId, evaluationId);
		UserEntity admittedBy = getUserEntityFromSecurityContext();
		UserEntity userEntity = userEntityService.getUserEntity(userEntityId);
//		UserEntity admissionOfficer = getUserEntityFromSecurityContext();
		System.out.println("#### admittedBy: " + admittedBy);
//		System.out.println("#### admissionOfficer: " + admissionOfficer);

		if (result.hasErrors()) {
			model.addAttribute("originalEvaluation", origEvaluation);
			model.addAttribute(userEntity);
			return "editEvaluation";
		}
		
		evaluation.setStatus("admitted");
		evaluation.setAdmittedBy(admittedBy);
//		evaluation.setAdmissionOfficer(admissionOfficer);
		Date dateAdmitted = new Date();
		evaluation.setDateAdmitted(dateAdmitted);
		
		System.out.println("### evaluation.getAdmittedBy: " + evaluation.getAdmittedBy());
//		System.out.println("### evaluation.getAdmissionOfficer: " + evaluation.getAdmissionOfficer());
		
		evaluationService.updateEvaluation(evaluation);
		
		
		
		return "redirect:/accounts/{userEntityId}/evaluations";
		
	}
	
	/**
	 * NOT USING THIS AT THE TIME BEING
	 */
	@RequestMapping(value = "/accounts/{userEntityId}/evaluation/{evaluationId}/delete", method = RequestMethod.POST)
	public String deleteEvaluation(@PathVariable("userEntityId") Long userEntityId,
			@PathVariable("evaluationId") Long evaluationId)
			throws IOException {
		evaluationService.deleteEvaluation(getEvaluationValidateUserEntityId(userEntityId, evaluationId));
		return "redirect:/accounts/{userEntityId}/evaluations";
	}
	
	// ======================================
	// =                        =
	// ======================================
	
	/**
	 * accounts.jsp page has a list of all the accounts.
	 * Next to every account, display num of completed evaluations.
	 * eg 4/8. 4 items have complete or notrequired value out of the total 8 items
	 * 
	 * @param userEntityId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/accounts/{userEntityId}/evaluationState", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public Map<String, Object> getChecklistStatus(@PathVariable("userEntityId") Long userEntityId,
			Model model) {

		Evaluation evaluation = evaluationService.getEvaluationByUserEntityId(userEntityId);
		
		int evaluationCount = 0;
		String evaluationStatus = "pending";
		
		if (evaluation != null) {
			
			if (evaluation.getApplicationFee().equalsIgnoreCase("valid") || evaluation.getApplicationFee().equalsIgnoreCase("notrequired")) {
				evaluationCount = evaluationCount + 1;
			}
			if (evaluation.getBankStmt().equalsIgnoreCase("valid") || evaluation.getBankStmt().equalsIgnoreCase("notrequired")) {
				evaluationCount = evaluationCount + 1;
			}
			if (evaluation.getDiplome().equalsIgnoreCase("valid") || evaluation.getDiplome().equalsIgnoreCase("notrequired")) {
				evaluationCount = evaluationCount + 1;
			}
			if (evaluation.getFinancialAffidavit().equalsIgnoreCase("valid") || evaluation.getFinancialAffidavit().equalsIgnoreCase("notrequired")) {
				evaluationCount = evaluationCount + 1;
			}
			if (evaluation.getF1Visa().equalsIgnoreCase("valid") || evaluation.getF1Visa().equalsIgnoreCase("notrequired")) {
				evaluationCount = evaluationCount + 1;
			}
			if (evaluation.getI20().equalsIgnoreCase("valid") || evaluation.getI20().equalsIgnoreCase("notrequired")) {
				evaluationCount = evaluationCount + 1;
			}
			if (evaluation.getPassport().equalsIgnoreCase("valid") || evaluation.getPassport().equalsIgnoreCase("notrequired")) {
				evaluationCount = evaluationCount + 1;
			}
			if (evaluation.getTranscript().equalsIgnoreCase("valid") || evaluation.getTranscript().equalsIgnoreCase("notrequired")) {
				evaluationCount = evaluationCount + 1;
			}
			evaluationStatus = evaluation.getStatus();
		
		}
		
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("evaluationCount", evaluationCount);
		data.put("evaluationTotal", 8);
		data.put("evaluationStatus", evaluationStatus);
		
		System.out.println("################## evaluationCount: " + evaluationCount );
		
		return data;
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
