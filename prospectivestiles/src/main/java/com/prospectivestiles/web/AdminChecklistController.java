package com.prospectivestiles.web;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.validation.Valid;

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

import com.prospectivestiles.domain.Checklist;
import com.prospectivestiles.domain.Evaluation;
import com.prospectivestiles.domain.UserEntity;
import com.prospectivestiles.service.ChecklistService;
import com.prospectivestiles.service.UserEntityService;

/**
 * DISCONTINUED USING THIS CLASS. 
 * WILL USE IN FUTURE. 
 * Eg. When assistant creates checklist and AO reveiws files, then have to use checklist and evaluation classes.
 * 
 * 
 * The url to get the form and post the form are the same. 
 * eg. "/accounts/{userEntityId}/checklist/new"
 * Advantage: when a user submit a form with error the post url will be displayed in the url,
 * if the session expires, and post url used to post the form will be displayed in the url,
 * if post and get url are the same then when the user login and refresh the page, 
 * the get method will be called. or the page will crash.
 * @author danielanenia
 *
 */
@Controller
public class AdminChecklistController {
	
	
	@Autowired
	private UserEntityService userEntityService;
	
	@Inject
	private ChecklistService checklistService;
	
	private static final Logger log = LoggerFactory.getLogger(AdminChecklistController.class);
	
	// ======================================
	// =             checklists             =
	// ======================================
	
	/**
	 * One Student has one checklist only.
	 * Thinking of Modifying the getChecklists and editCheklist methods!!
	 * Check if user has checklist
	 * If user has checklist return it
	 * Else create new checklist for user
	 * [When a user registers, create an empty checklist at that instance]
	 * 
	 * in the view page, check if user has a checklist created
	 * If no checklist exist for user, display create checklist button
	 * Else display Edit checklist button
	 * 
	 */
	
	@RequestMapping(value = "/accounts/{userEntityId}/checklists", method = RequestMethod.GET)
	public String getChecklists(@PathVariable("userEntityId") Long userEntityId,
			Model model) {
		
		// ##### LOGGING #########
		Date now = new Date();
		String currentUserFullName = getUserEntityFromSecurityContext().getFullName();
		log.info("######## " + currentUserFullName + " viewing /accounts/userEntityId/checklists on " + now);
		log.info("######## viewing /accounts/userEntityId/checklists: ## Username: {}, ## Date: {}", currentUserFullName, now);
		System.out.println("sysout " + currentUserFullName + " viewing /accounts/userEntityId/checklists on " + now);
		// ##### END LOGGING #########
		
		UserEntity userEntity = userEntityService.getUserEntity(userEntityId);
		
		/**
		 * load checklist for a user, if exist
		 */
		model.addAttribute("checklists", checklistService.getChecklistByUserEntityId(userEntityId));
		
		/**
		 * The modelAttribute "checklist" for the form to add new checklist
		 */
		Checklist checklist = new Checklist();
		model.addAttribute("checklist", checklist);
		
		model.addAttribute("userEntity", userEntity);
		
		return "checklists";
	}
	
	@RequestMapping(value = "/accounts/{userEntityId}/checklist/new", method = RequestMethod.GET)
	public String getNewChecklistForm(@PathVariable("userEntityId") Long userEntityId,
			Model model) {
		
		// ##### LOGGING #########
		Date now = new Date();
		String currentUserFullName = getUserEntityFromSecurityContext().getFullName();
		log.info("######## " + currentUserFullName + " viewing /accounts/userEntityId/checklists/new on " + now);
		log.info("######## viewing /accounts/userEntityId/checklists/new: ## Username: {}, ## Date: {}", currentUserFullName, now);
		System.out.println("sysout " + currentUserFullName + " viewing /accounts/userEntityId/checklists/new on " + now);
		// ##### END LOGGING #########
		
		UserEntity userEntity = userEntityService.getUserEntity(userEntityId);
		
		// get the admission officer and set the evaluation.admofficer
		
		Checklist checklist = new Checklist();
		checklist.setUserEntity(userEntity);
		
		model.addAttribute("checklist", checklist);
		model.addAttribute(userEntity);
		
		return "newChecklistForm";
	}
	
	@RequestMapping(value = "/accounts/{userEntityId}/checklist/new", method = RequestMethod.POST)
	public String postNewChecklistForm(@PathVariable("userEntityId") Long userEntityId,
			@ModelAttribute @Valid Checklist checklist, BindingResult result, Model model) {
		
		// ##### LOGGING #########
		Date now = new Date();
		String currentUserFullName = getUserEntityFromSecurityContext().getFullName();
		log.info("######## " + currentUserFullName + " viewing /accounts/userEntityId/checklists on " + now);
		log.info("######## viewing /accounts/userEntityId/checklists: ## Username: {}, ## Date: {}", currentUserFullName, now);
		System.out.println("sysout " + currentUserFullName + " viewing /accounts/userEntityId/checklists on " + now);
		// ##### END LOGGING #########
		
		UserEntity userEntity = userEntityService.getUserEntity(userEntityId);
		UserEntity currentAdmissionUser = getUserEntityFromSecurityContext();
		if (result.hasErrors()) {
			model.addAttribute(userEntity);
			return "newChecklistForm";
		}

		checklist.setUserEntity(userEntity);
		checklist.setCreatedBy(currentAdmissionUser);
		/*evaluation.setStatus("pending");*/
		checklistService.createChecklist(checklist);
		
		return "redirect:/accounts/{userEntityId}/checklists";
	}
	
	@RequestMapping(value = "/accounts/{userEntityId}/checklist/{checklistId}/edit", method = RequestMethod.GET)
	public String editChecklist(@PathVariable("userEntityId") Long userEntityId,
			@PathVariable("checklistId") Long checklistId, Model model) {
		
		// ##### LOGGING #########
		Date now = new Date();
		String currentUserFullName = getUserEntityFromSecurityContext().getFullName();
		log.info("######## " + currentUserFullName + " viewing /accounts/userEntityId/checklists/checklistId on " + now);
		log.info("######## viewing /accounts/userEntityId/checklists/checklistId: ## Username: {}, ## Date: {}", currentUserFullName, now);
		System.out.println("sysout " + currentUserFullName + " viewing /accounts/userEntityId/checklists/checklistId on " + now);
		// ##### END LOGGING #########
		
		UserEntity userEntity = userEntityService.getUserEntity(userEntityId);
		Checklist checklist = getChecklistValidateUserEntityId(userEntityId, checklistId);
		
		model.addAttribute("originalChecklist", checklist);
		model.addAttribute(checklist);
		model.addAttribute(userEntity);
		
		return "editChecklist";
	}
	
	@RequestMapping(value = "/accounts/{userEntityId}/checklist/{checklistId}/edit", method = RequestMethod.POST)
	public String editChecklist(@PathVariable("userEntityId") Long userEntityId,
			@PathVariable("checklistId") Long checklistId,
			@ModelAttribute @Valid Checklist origChecklist, 
			BindingResult result,
			Model model) {
		
		// ##### LOGGING #########
		Date now = new Date();
		String currentUserFullName = getUserEntityFromSecurityContext().getFullName();
		log.info("######## " + currentUserFullName + " viewing /accounts/userEntityId/checklists/checklistId on " + now);
		log.info("######## viewing /accounts/userEntityId/checklists/checklistId: ## Username: {}, ## Date: {}", currentUserFullName, now);
		System.out.println("sysout " + currentUserFullName + " viewing /accounts/userEntityId/checklists/checklistId on " + now);
		// ##### END LOGGING #########
		
		UserEntity userEntity = userEntityService.getUserEntity(userEntityId);
		Checklist checklist = getChecklistValidateUserEntityId(userEntityId, checklistId);
		UserEntity currentAdmissionUser = getUserEntityFromSecurityContext();
		
		if (result.hasErrors()) {
			origChecklist.setId(checklistId);
			origChecklist.setUserEntity(userEntityService.getUserEntity(userEntityId));
			model.addAttribute("checklist", origChecklist);
			model.addAttribute(userEntity);
			return "editChecklist";
		}
		
		checklist.setNotes(origChecklist.getNotes());
		checklist.setBankStmt(origChecklist.getBankStmt());
		checklist.setF1Visa(origChecklist.getF1Visa());
		checklist.setI20(origChecklist.getI20());
		checklist.setApplicationFee(origChecklist.getApplicationFee());
		checklist.setApplicationForm(origChecklist.getApplicationForm());
		checklist.setEnrollmentAgreement(origChecklist.getEnrollmentAgreement());
		checklist.setGrievancePolicy(origChecklist.getGrievancePolicy());
		checklist.setRecommendationLetter(origChecklist.getRecommendationLetter());
		checklist.setDiplome(origChecklist.getDiplome());
		checklist.setFinancialAffidavit(origChecklist.getFinancialAffidavit());
		checklist.setPassport(origChecklist.getPassport());
		checklist.setTranscript(origChecklist.getTranscript());
		checklist.setLastModifiedBy(currentAdmissionUser);
		
		checklistService.updateChecklist(checklist);
		
		return "redirect:/accounts/{userEntityId}/checklists";
	}
	
	@RequestMapping(value = "/accounts/{userEntityId}/checklist/{checklistId}/delete", method = RequestMethod.POST)
	public String deleteChecklist(@PathVariable("userEntityId") Long userEntityId,
			@PathVariable("checklistId") Long checklistId)
			throws IOException {
		
		// ##### LOGGING #########
		Date now = new Date();
		String currentUserFullName = getUserEntityFromSecurityContext().getFullName();
		log.info("######## " + currentUserFullName + " viewing /accounts/userEntityId/checklists/checklistId/delete on " + now);
		log.info("######## viewing /accounts/userEntityId/checklists/checklistId/delete: ## Username: {}, ## Date: {}", currentUserFullName, now);
		System.out.println("sysout " + currentUserFullName + " viewing /accounts/userEntityId/checklists/checklistId/delete on " + now);
		// ##### END LOGGING #########
		
		checklistService.deleteChecklist(getChecklistValidateUserEntityId(userEntityId, checklistId));
		return "redirect:/accounts/{userEntityId}/checklists";
	}
	
	// ======================================
	// =                        =
	// ======================================
	
	/**
	 * Not using this method. I am using the evaluationState instead.
	 * 
	 * accounts.jsp page has a list of all the accounts.
	 * Next to every account, display num of completed checklist.
	 * eg 4/8. 4 items complete or notrequired value out of the total 8 items
	 * 
	 * @param userEntityId
	 * @param model
	 * @return 
	 */
	@RequestMapping(value = "/accounts/{userEntityId}/checklistState", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public Map<String, Object> getChecklistStatus(@PathVariable("userEntityId") Long userEntityId,
			Model model) {
		
		// ##### LOGGING #########
		Date now = new Date();
		String currentUserFullName = getUserEntityFromSecurityContext().getFullName();
		log.info("######## " + currentUserFullName + " viewing /accounts/userEntityId/checklistState on " + now);
		log.info("######## viewing /accounts/userEntityId/checklistState: ## Username: {}, ## Date: {}", currentUserFullName, now);
		System.out.println("sysout " + currentUserFullName + " viewing /accounts/userEntityId/checklistState on " + now);
		// ##### END LOGGING #########
		
		Checklist checklist = checklistService.getChecklistByUserEntityId(userEntityId);
//		ArrayList<String> evaluationReportSummary = new ArrayList<String>();
//		Map<String, Object> checklistStatus = new HashMap<String, Object>();
		
		int checklistCount = 0;
		
		if (checklist != null) {
			
			if (checklist.getF1Visa().equalsIgnoreCase("complete") || checklist.getF1Visa().equalsIgnoreCase("notrequired")) {
				checklistCount = checklistCount + 1;
			}
			if (checklist.getBankStmt().equalsIgnoreCase("complete") || checklist.getBankStmt().equalsIgnoreCase("notrequired")) {
				checklistCount = checklistCount + 1;
			}
			if (checklist.getI20().equalsIgnoreCase("complete") || checklist.getI20().equalsIgnoreCase("notrequired")) {
				checklistCount = checklistCount + 1;
			}
			if (checklist.getPassport().equalsIgnoreCase("complete") || checklist.getPassport().equalsIgnoreCase("notrequired")) {
				checklistCount = checklistCount + 1;
			}
			if (checklist.getFinancialAffidavit().equalsIgnoreCase("complete") || checklist.getFinancialAffidavit().equalsIgnoreCase("notrequired")) {
				checklistCount = checklistCount + 1;
			}
			if (checklist.getApplicationFee().equalsIgnoreCase("complete") || checklist.getApplicationFee().equalsIgnoreCase("notrequired")) {
				checklistCount = checklistCount + 1;
			}
			///#########
			if (checklist.getApplicationForm().equalsIgnoreCase("complete") || checklist.getApplicationForm().equalsIgnoreCase("notrequired")) {
				checklistCount = checklistCount + 1;
			}
			if (checklist.getEnrollmentAgreement().equalsIgnoreCase("complete") || checklist.getEnrollmentAgreement().equalsIgnoreCase("notrequired")) {
				checklistCount = checklistCount + 1;
			}
			if (checklist.getGrievancePolicy().equalsIgnoreCase("complete") || checklist.getGrievancePolicy().equalsIgnoreCase("notrequired")) {
				checklistCount = checklistCount + 1;
			}
			if (checklist.getRecommendationLetter().equalsIgnoreCase("complete") || checklist.getRecommendationLetter().equalsIgnoreCase("notrequired")) {
				checklistCount = checklistCount + 1;
			}
			///#########
			if (checklist.getTranscript().equalsIgnoreCase("complete") || checklist.getTranscript().equalsIgnoreCase("notrequired")) {
				checklistCount = checklistCount + 1;
			}
			if (checklist.getDiplome().equalsIgnoreCase("complete") || checklist.getDiplome().equalsIgnoreCase("notrequired")) {
				checklistCount = checklistCount + 1;
			}
		
		}
		
		
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("checklistCount", checklistCount);
		data.put("checklistTotal", 12);
		
		System.out.println("################## checklistCount: " + checklistCount );
		log.debug("# # # # # # # # # # # # getChecklistStatus... [log.debug]");
		log.info("# # # # # # # # # # # # getChecklistStatus... [log.info]");
		log.info("########  currentUserFullName  viewing /accounts/userEntityId/checklistState on now");
		log.info("######## " + currentUserFullName + " viewing /accounts/userEntityId/checklistState on " + now);
		
		return data;
	}

	// ======================================
	// =                        =
	// ======================================
	
	private Checklist getChecklistValidateUserEntityId(Long userEntityId, Long highschoolId) {
		Checklist checklist = checklistService.getChecklist(highschoolId);
		
		Assert.isTrue(userEntityId.equals(checklist.getUserEntity().getId()), "Checklist Id mismatch");
		return checklist;
	}
	
	private UserEntity getUserEntityFromSecurityContext() {
		SecurityContext securityCtx = SecurityContextHolder.getContext();
		Authentication auth = securityCtx.getAuthentication();
		UserEntity userEntity = (UserEntity) auth.getPrincipal();
		return userEntity;
	}
}
