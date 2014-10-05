package com.prospectivestiles.web;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;
import javax.validation.Valid;

import org.apache.velocity.tools.config.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import com.prospectivestiles.dao.RoleDao;
import com.prospectivestiles.domain.AccountState;
import com.prospectivestiles.domain.AssociatedUser;
import com.prospectivestiles.domain.Checklist;
import com.prospectivestiles.domain.EmergencyContact;
import com.prospectivestiles.domain.Evaluation;
import com.prospectivestiles.domain.Role;
import com.prospectivestiles.domain.UserEntity;
import com.prospectivestiles.service.AssociatedUserService;
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
	@Inject
	private AssociatedUserService associatedUserService;
	
	@Inject private RoleDao roleDao;
	
	private static final Logger log = LoggerFactory.getLogger(AdminEvaluationController.class);
	
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
	 * in the view page, check if user has an evaluation created
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
		 * The modelAttribute "evaluation" for the form to add new evaluation
		 */
		Evaluation evaluation = new Evaluation();
		model.addAttribute("evaluation", evaluation);
		
		model.addAttribute("userEntity", userEntityService.getUserEntity(userEntityId));
		model.addAttribute("admissionOfficerName", admissionOfficerName);
		
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
	
	/**
	 * When admin start evaluating a student (creating a new evaluation) set status = 'inprocess'
	 * 
	 * @param userEntityId
	 * @param evaluation
	 * @param result
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/accounts/{userEntityId}/evaluation/new", method = RequestMethod.POST)
	public String postNewEvaluationForm(@PathVariable("userEntityId") Long userEntityId,
			@ModelAttribute @Valid Evaluation evaluation, BindingResult result, Model model) {
		
		UserEntity userEntity = userEntityService.getUserEntity(userEntityId);
		UserEntity currentAdmissionUser = getUserEntityFromSecurityContext();
		
		if (result.hasErrors()) {
			model.addAttribute(userEntity);
			return "newEvaluationForm";
		}

		evaluation.setUserEntity(userEntity);
		evaluation.setCreatedBy(currentAdmissionUser);
		
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
			userEntityService.updateUserEntityRole(userEntityId,"complete","ROLE_STUDENT_COMPLETE");
			
			
		} else {
			
			/**
			 * If all evaluation items are not complete or notrequired make accountState to "INPROCESS"
			 * When accountState of an applicant change the role should also change
			 */
			userEntityService.updateUserEntityRole(userEntityId, "inprocess", "ROLE_STUDENT_INPROCESS");
			
		}
		
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

	@RequestMapping(value = "/accounts/{userEntityId}/evaluation/{evaluationId}/edit", method = RequestMethod.POST)
	public String editEvaluation(@PathVariable("userEntityId") Long userEntityId,
			@PathVariable("evaluationId") Long evaluationId,
			@ModelAttribute @Valid Evaluation origEvaluation, 
			BindingResult result,
			Model model) {
		
		Evaluation evaluation = getEvaluationValidateUserEntityId(userEntityId, evaluationId);
		/**
		 * The student
		 */
		UserEntity student = userEntityService.getUserEntity(userEntityId);
		/**
		 * The admission officer that is currently logged in
		 */
		UserEntity currentAdmissionUser = getUserEntityFromSecurityContext();

		if (result.hasErrors()) {
			origEvaluation.setId(evaluationId);
			origEvaluation.setUserEntity(userEntityService.getUserEntity(userEntityId));
//			model.addAttribute("originalEvaluation", origEvaluation);
			model.addAttribute("evaluation", origEvaluation);
			model.addAttribute("userEntity", student);
			/*
			when I used this model.addAttribute("evaluation", origEvaluation);
			I got java.lang.NullPointerException: .../accounts/10/evaluation/0
			origEvaluation has no userEntity set
			And when I use model.addAttribute("evaluation", evaluation)
			page doesn't crash; but form is redisplayed and I loose all data I typed
			When I use a return "/accounts/{userEntityId}/evaluation/{evaluationId}/edit" 
				I get view can't be resolved err
			When I use a return "redirect:/accounts/" + userEntityId + "/evaluation/" + evaluationId + "/edit"
				I get redirected to the edit form page,  the page is refreshed and I loose all the data typed, no err msg displayed on page
			*/
			return "editEvaluation";
		}
		
		
		evaluation.setBankStmt(origEvaluation.getBankStmt());
		evaluation.setSourceOfMoney(origEvaluation.getSourceOfMoney());
		evaluation.setAmountOfMoney(origEvaluation.getAmountOfMoney());
		evaluation.setF1Visa(origEvaluation.getF1Visa());
		evaluation.setI20(origEvaluation.getI20());
		evaluation.setNotes(origEvaluation.getNotes());
		evaluation.setPassport(origEvaluation.getPassport());
		evaluation.setFinancialAffidavit(origEvaluation.getFinancialAffidavit());
		evaluation.setApplicationFee(origEvaluation.getApplicationFee());
		evaluation.setApplicationForm(origEvaluation.getApplicationForm());
		evaluation.setEnrollmentAgreement(origEvaluation.getEnrollmentAgreement());
		evaluation.setGrievancePolicy(origEvaluation.getGrievancePolicy());
		evaluation.setRecommendationLetter(origEvaluation.getRecommendationLetter());
		evaluation.setTranscript(origEvaluation.getTranscript());
		evaluation.setDiplome(origEvaluation.getDiplome());
		evaluation.setAdmnOfficerReport(origEvaluation.getAdmnOfficerReport());
		evaluation.setLastModifiedBy(currentAdmissionUser);
		
		/**
		 * When we udpate the evaluation page of a student, we should also update the student's status
		 * Check if the accountState of the student is "admitted". If student is admitted don't update the status.
		 * Else,
		 * If the values of all the items is either complete or notrequired make accountState = "complete"
		 * Else it is "inprocess"
		 */
		System.out.println("#### 0" + student.getFullName());
		System.out.println("#### 1" + student.getAccountState());
		System.out.println("#### 2" + student.getAccountState().toString());
		if (!student.getAccountState().equalsIgnoreCase("admitted")) {
			System.out.println("#### 3" + student.getAccountState());
			System.out.println("#### 4" + student.getAccountState().toString());
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
				userEntityService.updateUserEntityRole(userEntityId, "complete", "ROLE_STUDENT_COMPLETE");
			} else {
				/**
				 * If all evaluation items are not complete or notrequired make accountState to "INPROCESS"
				 * When accountState of an applicant change the role should also change
				 */

				userEntityService.updateUserEntityRole(userEntityId, "inprocess", "ROLE_STUDENT_INPROCESS");
			}
		
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
		UserEntity currentAdmissionUser = getUserEntityFromSecurityContext();
		
		System.out.println("#### admittedBy: " + admittedBy);

		if (result.hasErrors()) {
			model.addAttribute("originalEvaluation", origEvaluation);
			model.addAttribute(userEntity);
			return "editEvaluation";
		}
		evaluation.setAdmittedBy(admittedBy);
		Date dateAdmitted = new Date();
		evaluation.setDateAdmitted(dateAdmitted);
		evaluation.setLastModifiedBy(currentAdmissionUser);
		
		System.out.println("### evaluation.getAdmittedBy: " + evaluation.getAdmittedBy());
		
		/**
		 * When a student is admitted change accountState to "ADMITTED"
		 */
		userEntityService.updateUserEntityRole(userEntityId, "admitted", "ROLE_STUDENT_ADMITTED");
		evaluationService.updateEvaluation(evaluation);
		
		return "redirect:/accounts/{userEntityId}/evaluations";
		
	}
	
	@RequestMapping(value = "/accounts/{userEntityId}/evaluation/{evaluationId}/denyAdmision", method = RequestMethod.POST)
	public String denyAdmision(@PathVariable("userEntityId") Long userEntityId,
			@PathVariable("evaluationId") Long evaluationId,
			@ModelAttribute @Valid Evaluation origEvaluation, 
			BindingResult result,
			Model model){
			
		Evaluation evaluation = getEvaluationValidateUserEntityId(userEntityId, evaluationId);
		UserEntity deniedBy = getUserEntityFromSecurityContext();
		UserEntity userEntity = userEntityService.getUserEntity(userEntityId);
		UserEntity currentAdmissionUser = getUserEntityFromSecurityContext();
		
		System.out.println("#### deniedBy: " + deniedBy);

		if (result.hasErrors()) {
			model.addAttribute("originalEvaluation", origEvaluation);
			model.addAttribute(userEntity);
			return "editEvaluation";
		}
		/**
		 * change attribute setAdmittedBy to reflect admitted and denied
		 */
		evaluation.setAdmittedBy(deniedBy);
		Date dateAdmitted = new Date();
		evaluation.setDateAdmitted(dateAdmitted);
		evaluation.setLastModifiedBy(currentAdmissionUser);
		
		System.out.println("### evaluation.getAdmittedBy: " + evaluation.getAdmittedBy());
		
		/**
		 * When a student is admitted change accountState to "ADMITTED"
		 */
		userEntityService.updateUserEntityRole(userEntityId, "denied", "ROLE_STUDENT_DENIED");
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
		UserEntity userEntity = userEntityService.getUserEntity(userEntityId);
		
		int evaluationCount = 0;
		String evaluationStatus = "pending";
		evaluationStatus = userEntity.getAccountState();
		
		if (evaluation != null) {
			
			if (evaluation.getApplicationFee().equalsIgnoreCase("complete") || evaluation.getApplicationFee().equalsIgnoreCase("notrequired")) {
				evaluationCount = evaluationCount + 1;
			}
			if (evaluation.getBankStmt().equalsIgnoreCase("complete") || evaluation.getBankStmt().equalsIgnoreCase("notrequired")) {
				evaluationCount = evaluationCount + 1;
			}
			if (evaluation.getDiplome().equalsIgnoreCase("complete") || evaluation.getDiplome().equalsIgnoreCase("notrequired")) {
				evaluationCount = evaluationCount + 1;
			}
			if (evaluation.getFinancialAffidavit().equalsIgnoreCase("complete") || evaluation.getFinancialAffidavit().equalsIgnoreCase("notrequired")) {
				evaluationCount = evaluationCount + 1;
			}
			if (evaluation.getF1Visa().equalsIgnoreCase("complete") || evaluation.getF1Visa().equalsIgnoreCase("notrequired")) {
				evaluationCount = evaluationCount + 1;
			}
			if (evaluation.getI20().equalsIgnoreCase("complete") || evaluation.getI20().equalsIgnoreCase("notrequired")) {
				evaluationCount = evaluationCount + 1;
			}
			if (evaluation.getPassport().equalsIgnoreCase("complete") || evaluation.getPassport().equalsIgnoreCase("notrequired")) {
				evaluationCount = evaluationCount + 1;
			}
			if (evaluation.getTranscript().equalsIgnoreCase("complete") || evaluation.getTranscript().equalsIgnoreCase("notrequired")) {
				evaluationCount = evaluationCount + 1;
			}
			if (evaluation.getApplicationForm().equalsIgnoreCase("complete") || evaluation.getApplicationForm().equalsIgnoreCase("notrequired")) {
				evaluationCount = evaluationCount + 1;
			}
			if (evaluation.getEnrollmentAgreement().equalsIgnoreCase("complete") || evaluation.getEnrollmentAgreement().equalsIgnoreCase("notrequired")) {
				evaluationCount = evaluationCount + 1;
			}
			if (evaluation.getGrievancePolicy().equalsIgnoreCase("complete") || evaluation.getGrievancePolicy().equalsIgnoreCase("notrequired")) {
				evaluationCount = evaluationCount + 1;
			}
			if (evaluation.getRecommendationLetter().equalsIgnoreCase("complete") || evaluation.getRecommendationLetter().equalsIgnoreCase("notrequired")) {
				evaluationCount = evaluationCount + 1;
			}
//			evaluationStatus = userEntity.getAccountState();
		
		}
		
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("evaluationCount", evaluationCount);
		data.put("evaluationTotal", 12);
		data.put("evaluationStatus", evaluationStatus);
		
		// ##### LOGGING #########
		Date now = new Date();
		String currentUserFullName = getUserEntityFromSecurityContext().getFullName();
		log.info("######## " + currentUserFullName + " viewing /accounts/userEntityId/evaluationState on " + now);
		log.info("######## viewing /accounts/userEntityId/evaluationState: ## Username: {}, ## Date: {}", currentUserFullName, now);
		System.out.println("sysout " + currentUserFullName + " viewing /accounts/userEntityId/evaluationState on " + now);
		// ##### END LOGGING #########
		
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
