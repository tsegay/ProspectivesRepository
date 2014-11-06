package com.prospectivestiles.web;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
/*import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;*/
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.prospectivestiles.domain.Address;
import com.prospectivestiles.domain.AssociatedUser;
import com.prospectivestiles.domain.Evaluation;
import com.prospectivestiles.domain.Term;
import com.prospectivestiles.domain.UserEntity;
import com.prospectivestiles.domain.HighSchool;
import com.prospectivestiles.domain.UserEntity;
import com.prospectivestiles.service.AddressService;
import com.prospectivestiles.service.AssociatedUserService;
import com.prospectivestiles.service.CountryService;
import com.prospectivestiles.service.ProgramOfStudyService;
import com.prospectivestiles.service.TermService;
import com.prospectivestiles.service.UserEntityService;

/**
 * The url to get the form and post the form are the same. 
 * eg. "/accounts/{userEntityId}/xxx/new"
 * Advantage: when a user submit a form with error the post url will be displayed in the url,
 * if the session expires, and post url used to post the form will be displayed in the url,
 * if post and get url are the same then when the user login and refresh the page, 
 * the get method will be called. or the page will crash.
 * @author danielanenia
 *
 */
@Controller
public class AdminAccountController {
	
	@Autowired
	private UserEntityService userEntityService;
	
	@Inject
	private AddressService addressService;
	
	@Inject
	private AssociatedUserService associatedUserService;
	
	@Inject
	private ProgramOfStudyService programOfStudyService;
	
	@Inject
	private TermService termService;
	
	@Inject
	private CountryService countryService;
	
	private static final Logger log = LoggerFactory.getLogger(AdminAccountController.class);
	
	/*
	 * Use @InitBinder to fix the following error
	 * Failed to convert property value of type java.lang.String to required type java.util.Date 
	 * for property attendedFrom;
	 * Failed to convert from type java.lang.String to type @javax.validation.constraints.NotNull java.util.Date 
	 */
	@InitBinder
	public void initBinder(WebDataBinder binder) {
	    SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
	    dateFormat.setLenient(false);
	    // true passed to CustomDateEditor constructor means convert empty String to null
	    binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}
	
	
	// ======================================
	// =             All Accounts             =
	// ======================================
	
	/**
	 * WHY AM I ADDING ALL THE USERS TO MODEL HERE??!!
	 * I AM USING THE getAccountsForJSON METHOD TO LOAD USERS
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/accounts", method = RequestMethod.GET)
	public String getAllAccounts(Model model) {

		// ##### LOGGING #########
		Date now = new Date();
		String currentUserFullName = getUserEntityFromSecurityContext().getFullName();
		log.info("######## " + currentUserFullName + " viewing /accounts on " + now);
//		log.debug("Temperature set to {}. Old temperature was {}.", t, oldT);
		log.info("######## viewing /accounts: ## Username: {}, ## Date: {}", currentUserFullName, now);
		System.out.println("sysout " + currentUserFullName + " viewing /accounts on " + now);
		// ##### END LOGGING #########
		
		
		/*I want to get all students only, not admin users*/
		List<UserEntity> users = userEntityService.findByRole(1);
		model.addAttribute("users", users);
		return "accounts";
	}
	
	/**
	 * passing page and pageSize, MAKE optional
	 * if page is null page = 1
	 * if pageSize is null pageSize = 25
	 */
	// I used @RequestParam when the c:url was not passing parameters in the url -- for testing
//	@RequestMapping(value = "/accounts/accountspage/", method = RequestMethod.GET, produces = "application/json")
//	@ResponseBody
//	public Map<String, Object> getAccountsForJSON(
//			@RequestParam(value = "page", required = false) int page, 
//			@RequestParam(value = "pageSize", required = false) int pageSize, 
//				Model model) {
	@RequestMapping(value = "/accounts/accounts/{page}/{pageSize}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public Map<String, Object> getAccountsForJSON(@PathVariable("page") int page, 
			@PathVariable("pageSize") int pageSize, 
			Model model) {
		
		System.out.println("page: " + page);
		System.out.println("pageSize: " + pageSize);
		
		
		// ##### LOGGING #########
		Date now = new Date();
		String currentUserFullName = getUserEntityFromSecurityContext().getFullName();
		log.info("######## " + currentUserFullName + " viewing /accounts on " + now);
		log.info("######## viewing /accounts/accounts/page/pageSize: ## Username: {}, ## Date: {}", currentUserFullName, now);
		System.out.println("sysout " + currentUserFullName + " viewing /accounts/accounts/page/pageSize on " + now);
		// ##### END LOGGING #########
		
		
		/*I want to count all students only, not admin users*/
		List<Long> rolesList = new ArrayList<Long>();
		rolesList.add((long) 6);
		rolesList.add((long) 7);
		rolesList.add((long) 8);
		rolesList.add((long) 9);
		
		long usersCount = userEntityService.countByRoles(rolesList);
		
		int totalPages = (int) Math.ceil((double)usersCount/(double)pageSize);
		// getAllUserEntitiesForPage -- TO BE CHANGED
		List<UserEntity> users = userEntityService.getAllUserEntitiesForPage(page, pageSize);
		log.info("######## usersCount: " + usersCount + " page: " + page+ " pageSize: " + pageSize+ " totalPages: " + totalPages);
		
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("users", users);
		data.put("usersCount", usersCount);
		data.put("page", page);
		data.put("pageSize", pageSize);
		data.put("totalPages", totalPages);
		
		System.out.println("################## usersCount: " + usersCount + 
				" ### page: " + page +
				" ### pageSize: " + pageSize +
				" ### totalPages: " + totalPages);
		
		return data;
	}
	
	
	@RequestMapping(value = "/accounts/accounts/searchAccount", method = RequestMethod.POST, produces="application/json")
	@ResponseBody
	public Map<String, Object> getAccountsBySearchTextJSON( 
			@RequestBody Map<String, Object> origdata) {
		
		// ##### LOGGING #########
		Date now = new Date();
		String currentUserFullName = getUserEntityFromSecurityContext().getFullName();
		log.info("######## " + currentUserFullName + " viewing /accounts/accounts/searchAccount on " + now);
		log.info("######## viewing /accounts/accounts/searchAccount: ## Username: {}, ## Date: {}", currentUserFullName, now);
		System.out.println("sysout " + currentUserFullName + " viewing /accounts/accounts/searchAccount on " + now);
		// ##### END LOGGING #########

		System.out.println("############# sendMessageJSON called");
		
		String text = (String) origdata.get("text");

		System.out.println("text:" + text);
		
		int pageSize = 3; // for testing purpose
		int page = 1; // for testing purpose
		long usersCount = userEntityService.count();
		int totalPages = (int) Math.ceil((double)usersCount/(double)pageSize);

		List<UserEntity> users = userEntityService.getAllUserEntitiesForPage(1, 10, text, true);

		// a map that is going to be actual value to return, 
		// the actual json value that we return to javascript
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("users", users);
		data.put("usersCount", usersCount);
		data.put("page", page);
		data.put("pageSize", pageSize);
		data.put("totalPages", totalPages);
		data.put("success", true);
		return data;
	}
	
	@RequestMapping(value = "/accounts/accounts/getAccounts", method = RequestMethod.GET, produces="application/json")
	@ResponseBody
	public Map<String, Object> getAccountsPagination(@RequestBody Map<String, Object> origdata) {

		System.out.println("############# getAccountsPagination called");
		
		// ##### LOGGING #########
		Date now = new Date();
		String currentUserFullName = getUserEntityFromSecurityContext().getFullName();
		log.info("######## " + currentUserFullName + " viewing /accounts/accounts/getAccounts on " + now);
		log.info("######## viewing /accounts/accounts/getAccounts: ## Username: {}, ## Date: {}", currentUserFullName, now);
		System.out.println("sysout " + currentUserFullName + " viewing /accounts/accounts/getAccounts on " + now);
		// ##### END LOGGING #########
		
		int page = Integer.parseInt((String) origdata.get("pg"));
		int pageSize = Integer.parseInt((String) origdata.get("pgSize"));

		System.out.println(page + " , " + pageSize);

		
		long usersCount = userEntityService.count();
		int totalPages = (int) Math.ceil((double)usersCount/(double)pageSize);
		
		// getAllUserEntitiesForPage -- TO BE CHANGED
		List<UserEntity> users = userEntityService.getAllUserEntitiesForPage(page, pageSize);
		
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("users", users);
		data.put("usersCount", usersCount);
		data.put("page", page);
		data.put("pageSize", pageSize);
		data.put("totalPages", totalPages);
		

		// a map that is going to be actual value to return, 
		// the actual json value that we return to javascript
		data.put("success", true);
		return data;
	}

	// ======================================
	// =           Individual Account              =
	// ======================================
	
	/*
	 * I am going to merge the personal info and addresses page together.
	 * I will load the addresses to the model
	 */
	@RequestMapping(value = "/accounts/{userEntityId}", method = RequestMethod.GET)
	public String getAccountInfo(@PathVariable("userEntityId") long userEntityId, Model model) {
		
		// ##### LOGGING #########
		Date now = new Date();
		String currentUserFullName = getUserEntityFromSecurityContext().getFullName();
		log.info("######## " + currentUserFullName + " viewing /accounts/userEntityId on " + now);
		log.info("######## viewing /accounts/userEntityId: ## Username: {}, ## Date: {}", currentUserFullName, now);
		System.out.println("sysout " + currentUserFullName + " viewing /accounts/userEntityId on " + now);
		// ##### END LOGGING #########
		
		UserEntity userEntity = userEntityService.getUserEntity(userEntityId);
		
		/**
		 * load all addresses for a user
		 */
		model.addAttribute("addresses", addressService.getAddressesByUserEntityId(userEntityId));
		model.addAttribute("associatedUsers", associatedUserService.getAssociatedUserByUserEntityId(userEntityId));
		
		/**
		 * The modelAttribute "address" for the form to add new address
		 */
		Address address = new Address();
		model.addAttribute("address", address);
		
		/**
		 * Do I really need to add the userEntity? 
		 * Maybe, I just need the Full Name of the user or userId
		 */
		model.addAttribute("userEntity", userEntity);
		
		model.addAttribute("terms", termService.getAllTerms());
		model.addAttribute("programOfStudies", programOfStudyService.getAllProgramOfStudies());
		
		return "account";
	}
	
	@RequestMapping(value="/accounts/{userEntityId}/edit", method = RequestMethod.GET)
	public String editAccount(@PathVariable("userEntityId") long userEntityId, Model model) {
		
		// ##### LOGGING #########
		Date now = new Date();
		String currentUserFullName = getUserEntityFromSecurityContext().getFullName();
		log.info("######## " + currentUserFullName + " viewing /accounts/userEntityId/edit on " + now);
		log.info("######## viewing /accounts/userEntityId/edit: ## Username: {}, ## Date: {}", currentUserFullName, now);
		System.out.println("sysout " + currentUserFullName + " viewing /accounts/userEntityId/edit on " + now);
		// ##### END LOGGING #########
		
		UserEntity userEntity = userEntityService.getUserEntity(userEntityId);
		/**
		 * If the user hasn't agreed to the terms
		 * Then make is true to pass validation,
		 * then revert it to the original value of false in the post method.
		 * 
		 * NOT USING THIS: If AO is filled the web form for applicant, 
		 * the applicant has already signed the agreement on paper.
		 */
//		if (!userEntity.getAcceptTerms()) {
//			userEntity.setAcceptTerms(true);
//		}
		model.addAttribute("originalUserEntity", userEntity);
		model.addAttribute(userEntity);
		model.addAttribute("countries", countryService.getAllCountries());
		
		return "editAccount";
	}
	
	@RequestMapping(value="/accounts/{userEntityId}/edit", method = RequestMethod.POST)
	public String editAccount(
			@PathVariable("userEntityId") long userEntityId, 
			Model model,
			@ModelAttribute @Valid UserEntity origUserEntity, 
			BindingResult result 
			) {
		
		// ##### LOGGING #########
		Date now = new Date();
		String currentUserFullName = getUserEntityFromSecurityContext().getFullName();
		log.info("######## " + currentUserFullName + " viewing /accounts/userEntityId/edit on " + now);
		log.info("######## viewing /accounts/userEntityId/edit: ## Username: {}, ## Date: {}", currentUserFullName, now);
		System.out.println("sysout " + currentUserFullName + " viewing /accounts/userEntityId/edit on " + now);
		// ##### END LOGGING #########
		
		if (result.hasErrors()) {
//			log.debug("Validation Error in HighSchool form");
			System.out.println("######## getErrorCount: " + result.getErrorCount());
			System.out.println("######## getAllErrors: " + result.getAllErrors());
			System.out.println("######## toString: " + result.toString());
			origUserEntity.setId(userEntityId);
			model.addAttribute("userEntity", origUserEntity);
			model.addAttribute("countries", countryService.getAllCountries());
			return "editAccount";
			
		}
		
		/**
		 * Check if user has already agreed to the terms.
		 * get acceptTerms value form the DB.
		 * If user has accepted terms, leave it as it is.
		 * 
		 * Else,
		 * If account was created by AO, and not yet updated by the student. 
		 * then the acceptTerms is stil in false.
		 * To pass validation acceptTerms is set to true in the GET method.
		 * Now, revert it back to False.
		 * 
		 * 
		 * NOT USING THIS: If AO is filled the web form for applicant, 
		 * the applicant has already signed the agreement on paper.
		 * 
		 */
//		if (!userEntityService.getUserEntity(userEntityId).getAcceptTerms()) {
//			userEntity.setAcceptTerms(false);
//		}
		
		/**
		 * Why origUserEntity has no id set
		 * When persisting without setting the id: err msg = The given object has a null identifier
		 * If i do origUserEntity.setId(userEntityId); and persist this origUserEntity
		 * the fields in DB which are not displayed in the form are set to null
		 * Eg. dateCreated, createdBy in DB will be set to null, losing creator's data
		 * 
		 * Solution: get entity from DB and pass the values that are updated in the form and persist it.
		 * Another option is to set all the other fields are hidden fields in the form
		 */

		origUserEntity.setId(userEntityId);
		userEntityService.updateUserEntity(origUserEntity);
		
		return "redirect:/accounts/{userEntityId}";
	}
	
	/*
	 * Using a Modal to updateAccountState to 'pending' to enable an applicant can make changes
	 * The updateAccountState form in the Modal calls this method
	 */
	@RequestMapping(value = "/accounts/{userEntityId}/updateAccountState", method = RequestMethod.GET)
	public String getUpdateAccountState(@PathVariable("userEntityId") Long userEntityId,
			Model model) {
		System.out.println("################# updateAccountState");
		
		UserEntity userEntity = userEntityService.getUserEntity(userEntityId);
		
		model.addAttribute("userEntity", userEntity);
		
		return "updateAccountState";
	}
	
	@RequestMapping(value = "/accounts/{userEntityId}/updateAccountState", method = RequestMethod.POST)
	public String postUpdateAccountState(@PathVariable("userEntityId") Long userEntityId,
			Model model)
			throws IOException {
		System.out.println("################# updateAccountState");
		
		userEntityService.updateUserEntityRole(userEntityId, "pending", "ROLE_STUDENT_PENDING");
		return "redirect:/accounts/{userEntityId}";
	}
	
	/*
	 * Using a Modal to updateAccountState to 'enrolled' to push applicant from admission to registration office.
	 * The updateAccountState form in the Modal calls this method
	 * set enrolledDate current date
	 */
	@RequestMapping(value = "/accounts/{userEntityId}/updateAccountState2Enrolled", method = RequestMethod.GET)
	public String getUpdateAccountState2(@PathVariable("userEntityId") Long userEntityId,
			Model model) {
		System.out.println("################# updateAccountState");
		
		UserEntity userEntity = userEntityService.getUserEntity(userEntityId);
		
		model.addAttribute("userEntity", userEntity);
		
		return "updateAccountState2Enrolled";
	}
	
	@RequestMapping(value = "/accounts/{userEntityId}/updateAccountState2Enrolled", method = RequestMethod.POST)
	public String postUpdateAccountState2(@PathVariable("userEntityId") Long userEntityId,
			Model model)
					throws IOException {
		System.out.println("################# updateAccountState");
		
		userEntityService.updateUserEntityRole(userEntityId, "enrolled", "ROLE_STUDENT_ENROLLED");
		return "redirect:/accounts/{userEntityId}";
	}
	
	/**
	 * NO DELETE METHOD
	 */
	
	
	// ======================================
	// =                         =
	// ======================================

	@RequestMapping(value = "/accounts/getAccountsByTermStatusState", method = RequestMethod.GET)
	public String getAccountsByTermStatusState(Model model) {
		List<Term> terms = termService.getAllTerms();
		System.out.println("####### getAccountsByTermStatusState displayed");
		model.addAttribute("userEntity", new UserEntity());
		model.addAttribute("terms", terms);
		return "searchAccountsByTermStatusState";
	}
	

	/**
	 * 
	 * POST method is in AdminPDFReportGenerator.postAccountsByTermStatusState
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/accounts/admittedStudents", method = RequestMethod.GET)
	public String getAdmittedStudents(Model model) {
		
		List<UserEntity> admittedUsers = userEntityService.findUserEntitiesByAccountState("admitted");
		long admittedCount = userEntityService.countByAccountState("admitted");
		model.addAttribute("admittedUsers", admittedUsers);
		model.addAttribute("admittedCount", admittedCount);
		
		return "admittedStudents";
	}
	
	@RequestMapping(value = "/accounts/enrolledStudents", method = RequestMethod.GET)
	public String getenrolledStudents(Model model) {
		
		List<UserEntity> enrolledUsers = userEntityService.findUserEntitiesByAccountState("enrolled");
		long enrolledCount = userEntityService.countByAccountState("enrolled");
		model.addAttribute("enrolledUsers", enrolledUsers);
		model.addAttribute("enrolledCount", enrolledCount);
		
		return "enrolledStudents";
	}
	
	@RequestMapping(value = "/accounts/completeStudents", method = RequestMethod.GET)
	public String getCompleteStudents(Model model) {
		
		List<UserEntity> completeUserEntities = userEntityService.findUserEntitiesByAccountState("complete");
		long completeCount = userEntityService.countByAccountState("complete");
		model.addAttribute("completeUserEntities", completeUserEntities);
		model.addAttribute("completeCount", completeCount);

		return "completeStudents";
	}
	
	@RequestMapping(value = "/accounts/inprocessStudents", method = RequestMethod.GET)
	public String getInprocessStudents(Model model) {
		
		List<UserEntity> inprocessUserEntities = userEntityService.findUserEntitiesByAccountState("inprocess");
		long inprocessCount = userEntityService.countByAccountState("inprocess");
		model.addAttribute("inprocessUserEntities", inprocessUserEntities);
		model.addAttribute("inprocessCount", inprocessCount);
		
		return "inprocessStudents";
	}
	
	@RequestMapping(value = "/accounts/pendingStudents", method = RequestMethod.GET)
	public String getPendingStudents(Model model) {
		
		List<UserEntity> pendingUserEntities = userEntityService.findUserEntitiesByAccountState("pending");
		long pendingCount = userEntityService.countByAccountState("pending");
		model.addAttribute("pendingUserEntities", pendingUserEntities);
		model.addAttribute("pendingCount", pendingCount);
		
		return "pendingStudents";
	}
	
	// ======================================
	// =             Agents and Referrers            =
	// ======================================
	@RequestMapping(value = "/accounts/agents", method = RequestMethod.GET)
	public String getAgents(Model model) {
		
		List<AssociatedUser> associatedUsersAgents = associatedUserService.findAllAgents();
		
		model.addAttribute("associatedUsersAgents", associatedUsersAgents);
		
		return "agents";
	}
	
	@RequestMapping(value = "/accounts/referrers", method = RequestMethod.GET)
	public String getReferrers(Model model) {
		
		List<AssociatedUser> associatedUsersReferrers = associatedUserService.findAllReferrers();
		
		model.addAttribute("associatedUsersReferrers", associatedUsersReferrers);
		
		return "referrers";
	}
	
	
	// ======================================
	// =             associatedUsers            =
	// 				CREATE ASSOCIATED USERS CONTROLLER				
	// ======================================
//	
//	/**
//	 * 
//	 * @param userEntityId
//	 * @param model
//	 * @return 
//	 */
//	@RequestMapping(value = "/accounts/{userEntityId}/associatedUser/new", method = RequestMethod.GET)
//	public String getNewAssociatedUsersForm(@PathVariable("userEntityId") Long userEntityId,
//			Model model) {
//		
//		// ##### LOGGING #########
//		Date now = new Date();
//		String currentUserFullName = getUserEntityFromSecurityContext().getFullName();
//		log.info("######## " + currentUserFullName + " viewing /accounts/userEntityId/associatedUser/new on " + now);
//		log.info("######## viewing /accounts/userEntityId/associatedUser/new: ## Username: {}, ## Date: {}", currentUserFullName, now);
//		System.out.println("sysout " + currentUserFullName + " viewing /accounts/userEntityId/associatedUser/new on " + now);
//		// ##### END LOGGING #########
//		
//		AssociatedUser associatedUser = new AssociatedUser();
//		UserEntity userEntity = userEntityService.getUserEntity(userEntityId);
//		// get all admission counsellors and pass it to the model, ROLE_ADMISSION=12
//		List<UserEntity> admissionCounselors = userEntityService.findByRole(12);
//		
//		associatedUser.setStudent(userEntity);
//		
//		model.addAttribute("associatedUser",associatedUser);
//		model.addAttribute(userEntity);
//		model.addAttribute("admissionCounselors", admissionCounselors);
//		
//		return "associatedUserForm";
//	}
//	
//	/**
//	 * When form is submitted, if I use path="admissionOfficer.id" in the form select field
//	 * get err - save the transient instance before flushing: com.prospectivestiles.domain.UserEntity
//	 * 
//	 * I created a transient property long aoId in AssociatedUser class to hold the id of the AdmissionOfficer
//	 * in the form I use path="aoId" in the form select field
//	 * when user select admissionCounselor from the dropDown menu, 
//	 * set the admissionCounselors.id in aoId
//	 * in the controller get aoId from the associatedUser ModelAttribute and 
//	 * pass aoId to associatedUser.setAdmissionOfficer
//	 * 
//	 * CreatedBy is set to the currently logged in Admission User
//	 * dateCreated is set in the abstractHbn Class
//	 * 
//	 * @param userEntityId
//	 * @param associatedUser
//	 * @param result
//	 * @param model
//	 * @return
//	 */
//	@RequestMapping(value = "/accounts/{userEntityId}/associatedUser/new", method = RequestMethod.POST)
//	public String postNewAssociatedUsersForm(@PathVariable("userEntityId") Long userEntityId,
//			@ModelAttribute @Valid AssociatedUser associatedUser, BindingResult result, Model model) {
//		
//		// ##### LOGGING #########
//		Date now = new Date();
//		String currentUserFullName = getUserEntityFromSecurityContext().getFullName();
//		log.info("######## " + currentUserFullName + " viewing /accounts/userEntityId/associatedUsers on " + now);
//		log.info("######## viewing /accounts/userEntityId/associatedUsers: ## Username: {}, ## Date: {}", currentUserFullName, now);
//		System.out.println("sysout " + currentUserFullName + " viewing /accounts/userEntityId/associatedUsers on " + now);
//		// ##### END LOGGING #########
//		
//		UserEntity userEntity = userEntityService.getUserEntity(userEntityId);
//		UserEntity currentAdmissionUser = getUserEntityFromSecurityContext();
//		
//		if (result.hasErrors()) {
//			model.addAttribute(userEntity);
//			return "associatedUserForm";
//		}
//
//		associatedUser.setStudent(userEntity);
//		associatedUser.setCreatedBy(currentAdmissionUser);
//		associatedUser.setAdmissionOfficer(userEntityService.getUserEntity(associatedUser.getAoId()));
//		
//		associatedUserService.createAssociatedUser(associatedUser);
//		
//		return "redirect:/accounts/{userEntityId}";
//	}
//
//	/**
//	 * When form is displayed, get the current admissionOfficer
//	 * set it to the aoId form displays current admissionOfficer in the field
//	 * 
//	 * @param userEntityId
//	 * @param model
//	 * @return
//	 */
//	@RequestMapping(value = "/accounts/{userEntityId}/associatedUser/edit", method = RequestMethod.GET)
//	public String editAssociatedUser(@PathVariable("userEntityId") Long userEntityId, Model model) {
//		
//		// ##### LOGGING #########
//		Date now = new Date();
//		String currentUserFullName = getUserEntityFromSecurityContext().getFullName();
//		log.info("######## " + currentUserFullName + " viewing /accounts/userEntityId/associatedUser/edit on " + now);
//		log.info("######## viewing /accounts/userEntityId/associatedUser/edit: ## Username: {}, ## Date: {}", currentUserFullName, now);
//		System.out.println("sysout " + currentUserFullName + " viewing /accounts/userEntityId/associatedUser/edit on " + now);
//		// ##### END LOGGING #########
//		
//		UserEntity userEntity = userEntityService.getUserEntity(userEntityId);
//		AssociatedUser associatedUser = associatedUserService.getAssociatedUserByUserEntityId(userEntityId);
//		// get all admission counsellors and pass it to the model, ROLE_ADMISSION=12
//		List<UserEntity> admissionCounselors = userEntityService.findByRole(12);
//		
//		/*To avoid NullPointerException when admissionOfficer is not set for the associatedUser*/
//		if (associatedUser.getAdmissionOfficer() != null) {
//			associatedUser.setAoId(associatedUser.getAdmissionOfficer().getId());
//		}
//		
////		model.addAttribute("originalAssociatedUser", associatedUser);
//		model.addAttribute("associatedUser",associatedUser);
//		model.addAttribute(userEntity);
//		model.addAttribute("admissionCounselors", admissionCounselors);
//		
//		return "editAssociatedUser";
//	}
//	
//	/**
//	 * 
//	 * 
//	 * lastModifiedBy - currentAdmissionUser is the Admission Office who is creating...
//	 * dateLastModified is set in the serviceImp class to the current time
//	 * 
//	 * @param userEntityId
//	 * @param origAssociatedUser
//	 * @param result
//	 * @param model
//	 * @return
//	 */
//	@RequestMapping(value = "/accounts/{userEntityId}/associatedUser/edit", method = RequestMethod.POST)
//	public String editAssociatedUser(@PathVariable("userEntityId") Long userEntityId,
//			@ModelAttribute @Valid AssociatedUser origAssociatedUser, 
//			BindingResult result,
//			Model model) {
//		
//		// ##### LOGGING #########
//		Date now = new Date();
//		String currentUserFullName = getUserEntityFromSecurityContext().getFullName();
//		log.info("######## " + currentUserFullName + " viewing /accounts/userEntityId/associatedUser/edit on " + now);
//		log.info("######## viewing /accounts/userEntityId/associatedUser/edit: ## Username: {}, ## Date: {}", currentUserFullName, now);
//		System.out.println("sysout " + currentUserFullName + " viewing /accounts/userEntityId/associatedUser/edit on " + now);
//		// ##### END LOGGING #########
//		
//		UserEntity userEntity = userEntityService.getUserEntity(userEntityId);
//		UserEntity currentAdmissionUser = getUserEntityFromSecurityContext();
//		AssociatedUser associatedUser = associatedUserService.getAssociatedUserByUserEntityId(userEntityId);
//
//		if (result.hasErrors()) {
//			origAssociatedUser.setId(associatedUser.getId());
//			origAssociatedUser.setStudent(userEntity);
//			model.addAttribute("associatedUser", origAssociatedUser);
//			model.addAttribute(userEntity);
//			return "editAssociatedUser";
//		}
//
////		associatedUser.setAdmissionOfficer(origAssociatedUser.getAdmissionOfficer());
//		associatedUser.setAdmissionOfficer(userEntityService.getUserEntity(origAssociatedUser.getAoId()));
//		associatedUser.setAgent(origAssociatedUser.getAgent());
//		associatedUser.setReferrer(origAssociatedUser.getReferrer());
//		associatedUser.setLastModifiedBy(userEntity);
//		associatedUser.setStudent(userEntity);
//		associatedUser.setLastModifiedBy(currentAdmissionUser);
//		
//		associatedUserService.updateAssociatedUser(associatedUser);
//		
//		return "redirect:/accounts/{userEntityId}";
//	}
	
	
	// ======================================
	// =                         =
	// ======================================
	
	private UserEntity getUserEntityFromSecurityContext() {
		SecurityContext securityCtx = SecurityContextHolder.getContext();
		Authentication auth = securityCtx.getAuthentication();
		UserEntity userEntity =  (UserEntity) auth.getPrincipal();
		return userEntity;
	}
	
	
	
	// ======================================
	// =                         =
	// ======================================
	
	/*@RequestMapping(value="/accounts/accounts", method = RequestMethod.GET, produces="text/html")
	public String list(Pageable pageable, Model model){
		System.out.println(pageable);
		Page<UserEntity> users = this.userEntityRepository.findAll(pageable);
		
        model.addAttribute("users", users);
		return "accounts/accounts";
	}*/
	
	@RequestMapping(value = "/accounts/accounts/{page}/{pageSize}/{asc}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public Map<String, Object> getAccountsForJSON(@PathVariable("page") int page, 
			@PathVariable("pageSize") int pageSize, 
			@PathVariable("asc") boolean asc, 
			Model model) {
		
		// ##### LOGGING #########
		Date now = new Date();
		String currentUserFullName = getUserEntityFromSecurityContext().getFullName();
		log.info("######## " + currentUserFullName + " viewing /accounts on " + now);
		log.info("######## viewing /accounts/accounts/page/pageSize/asc: ## Username: {}, ## Date: {}", currentUserFullName, now);
		System.out.println("sysout " + currentUserFullName + " viewing /accounts/accounts/page/pageSize/ASC on " + now);
		// ##### END LOGGING #########
				
		System.out.println("page: " + page);
		System.out.println("pageSize: " + pageSize);
		System.out.println("asc: " + asc);
		long usersCount = userEntityService.count();
		int totalPages = (int) Math.ceil((double)usersCount/(double)pageSize);
		// getAllUserEntitiesForPage -- TO BE CHANGED
		List<UserEntity> users = userEntityService.getAllUserEntitiesForPage(page, pageSize, null, asc);
		
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("users", users);
		data.put("usersCount", usersCount);
		data.put("page", page);
		data.put("pageSize", pageSize);
		data.put("totalPages", totalPages);
		
		System.out.println("################## usersCount: " + usersCount + 
				" ### page: " + page +
				" ### pageSize: " + pageSize +
				" ### totalPages: " + totalPages);
		
		return data;
	}
	@RequestMapping(value = "/accounts/accounts/{page}/{pageSize}/{filter}/{asc}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public Map<String, Object> getAccountsForJSON(@PathVariable("page") int page, 
			@PathVariable("pageSize") int pageSize, 
			@PathVariable("filter") String filter, 
			@PathVariable("asc") boolean asc, 
			Model model) {
		
		System.out.println("page: " + page);
		System.out.println("pageSize: " + pageSize);
		System.out.println("filter: " + filter);
		System.out.println("asc: " + asc);
		long usersCount = userEntityService.count();
		int totalPages = (int) Math.ceil((double)usersCount/(double)pageSize);
		// getAllUserEntitiesForPage -- TO BE CHANGED
		List<UserEntity> users = userEntityService.getAllUserEntitiesForPage(page, pageSize, filter, asc);
		
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("users", users);
		data.put("usersCount", usersCount);
		data.put("page", page);
		data.put("pageSize", pageSize);
		data.put("totalPages", totalPages);
		
		System.out.println("################## usersCount: " + usersCount + 
				" ### page: " + page +
				" ### pageSize: " + pageSize +
				" ### totalPages: " + totalPages);
		
		return data;
	}

}
