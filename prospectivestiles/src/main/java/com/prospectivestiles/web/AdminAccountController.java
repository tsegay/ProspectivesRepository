package com.prospectivestiles.web;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
/*import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;*/
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.prospectivestiles.domain.Address;
import com.prospectivestiles.domain.AssociatedUser;
import com.prospectivestiles.domain.UserEntity;
import com.prospectivestiles.service.AddressService;
import com.prospectivestiles.service.AssociatedUserService;
import com.prospectivestiles.service.UserEntityService;

@Controller
public class AdminAccountController {
	
	@Autowired
	private UserEntityService userEntityService;
	
	@Inject
	private AddressService addressService;
	
	@Inject
	private AssociatedUserService associatedUserService;
	
	private static final Logger log = LoggerFactory.getLogger(AdminAccountController.class);
	
	
	// ======================================
	// =             accounts             =
	// ======================================
	
	@RequestMapping(value="/accounts", method = RequestMethod.GET)
	public String getAllAccounts(Model model) {
		Date now = new Date();
		String currentUserFullName = getUserEntityFromSecurityContext().getFullName();
		
		log.debug(currentUserFullName + " viewing /accounts on " + now);
		System.out.println("sysout " + currentUserFullName + " viewing /accounts on " + now);
		
//		List<UserEntity> users = userEntityService.getAllUserEntities();
		/*I want to get all students only, not admin users*/
		List<UserEntity> users = userEntityService.findByRole(1);
		model.addAttribute("users", users);
		return "accounts";
	}
	
	/*@RequestMapping(value="/accounts/accounts", method = RequestMethod.GET, produces="text/html")
	public String list(Pageable pageable, Model model){
		System.out.println(pageable);
		Page<UserEntity> users = this.userEntityRepository.findAll(pageable);
		
        model.addAttribute("users", users);
		return "accounts/accounts";
	}*/
	
	
	/*
	 * passing page and pageSize, MAKE optional
	 * if page is null page = 1
	 * if pageSize is null pageSize = 25
	 */
	
	@RequestMapping(value = "/accounts/accounts/{page}/{pageSize}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public Map<String, Object> getAccountsForJSON(@PathVariable("page") int page, 
			@PathVariable("pageSize") int pageSize, 
			Model model) {
		// I used @RequestParam when the c:url was not passing parameters in the url -- for testing
//	@RequestMapping(value = "/accounts/accountspage/", method = RequestMethod.GET, produces = "application/json")
//	@ResponseBody
//	public Map<String, Object> getAccountsForJSON(
//			@RequestParam(value = "page", required = false) int page, 
//			@RequestParam(value = "pageSize", required = false) int pageSize, 
//				Model model) {
		
		System.out.println("page: " + page);
		System.out.println("pageSize: " + pageSize);
//		long usersCount = userEntityService.count();
		/*I want to count all students only, not admin users*/
		long usersCount = userEntityService.countByRole(1);
		
		int totalPages = (int) Math.ceil((double)usersCount/(double)pageSize);
		// getAllUserEntitiesForPage -- TO BE CHANGED
		List<UserEntity> users = userEntityService.getAllUserEntitiesForPage(page, pageSize);
		
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
	
	@RequestMapping(value = "/accounts/accounts/{page}/{pageSize}/{asc}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public Map<String, Object> getAccountsForJSON(@PathVariable("page") int page, 
			@PathVariable("pageSize") int pageSize, 
			@PathVariable("asc") boolean asc, 
			Model model) {
		
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
	
	@RequestMapping(value = "/accounts/accounts/searchAccount", method = RequestMethod.POST, produces="application/json")
	@ResponseBody
	public Map<String, Object> sendMessageJSON( 
			@RequestBody Map<String, Object> origdata) {

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
	// =                         =
	// ======================================
	
	/*
	 * I am going to merge the personal info and addresses page together.
	 * I will load the addresses to the model
	 */
	@RequestMapping(value = "/accounts/{userEntityId}", method = RequestMethod.GET)
	public String getAccountInfo(@PathVariable("userEntityId") long userEntityId, Model model) {
		Date now = new Date();
		String currentUserFullName = getUserEntityFromSecurityContext().getFullName();
		
		log.debug(currentUserFullName + " viewing /accounts/"+userEntityId+" on " + now);
		System.out.println("sysout " + currentUserFullName + " viewing /accounts on " + now);
		
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
		
		return "account";
	}
	
	@RequestMapping(value="/accounts/{userEntityId}/edit", method = RequestMethod.GET)
	public String editAccount(@PathVariable("userEntityId") long userEntityId, Model model) {
		
		UserEntity userEntity = userEntityService.getUserEntity(userEntityId);		
		model.addAttribute("originalUserEntity", userEntity);
		model.addAttribute(userEntity);
		
		return "editAccount";
	}
	
	/**
	 * @Valid - use validation to validate userEntity
	 */
	@RequestMapping(value="/accounts/{userEntityId}/edit", method = RequestMethod.POST)
	public String editAccount(@PathVariable("userEntityId") long userEntityId, 
			@ModelAttribute UserEntity origUserEntity, BindingResult result, Model model) {
		
		UserEntity userEntity = userEntityService.getUserEntity(userEntityId);
		
		if (result.hasErrors()) {
			System.out.println("######## result.hasErrors(): true" );
//			model.addAttribute("originalUserEntity", userEntity);
			model.addAttribute("userEntity", userEntity);
//			return "accounts/account";
			return "account";
		} else {
			System.out.println("######## result.hasErrors(): false" );
		}
		
		userEntityService.insertIntoUserEntity(userEntity.getId(), origUserEntity);
		
		return "redirect:/accounts/{userEntityId}";
//		return "redirect:/account";
	}
	
	/**
	 * NO DELETE METHOD FOR STUDENTS
	 * @return
	 */
	/*@RequestMapping(value = "/myAccount/delete", method = RequestMethod.POST)
	public String deleteMyAccount(){}*/
	
	// ======================================
	// =             associatedUsers            =
	// ======================================
	
	/**
	 * 
	 * @param userEntityId
	 * @param model
	 * @return 
	 */
	@RequestMapping(value = "/accounts/{userEntityId}/associatedUser/new", method = RequestMethod.GET)
	public String getNewAssociatedUsersForm(@PathVariable("userEntityId") Long userEntityId,
			Model model) {
		AssociatedUser associatedUser = new AssociatedUser();
		UserEntity userEntity = userEntityService.getUserEntity(userEntityId);
		// get all admission counsellors and pass it to the model
		List<UserEntity> admissionCounselors = userEntityService.findByRole(2);
		
		associatedUser.setStudent(userEntity);
		
		model.addAttribute("associatedUser",associatedUser);
		model.addAttribute(userEntity);
		model.addAttribute("admissionCounselors", admissionCounselors);
		
		return "associatedUserForm";
	}
	
	/**
	 * When form is submitted, if I use path="admissionOfficer.id" in the form select field
	 * get err - save the transient instance before flushing: com.prospectivestiles.domain.UserEntity
	 * 
	 * I created a transient property long aoId in AssociatedUser class to hold the id of the AdmissionOfficer
	 * in the form I use path="aoId" in the form select field
	 * when user select admissionCounselor from the dropDown menu, 
	 * set the admissionCounselors.id in aoId
	 * in the controller get aoId from the associatedUser ModelAttribute and 
	 * pass aoId to associatedUser.setAdmissionOfficer
	 * 
	 * CreatedBy is set to the currently logged in Admission User
	 * dateCreated is set in the abstractHbn Class
	 * 
	 * @param userEntityId
	 * @param associatedUser
	 * @param result
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/accounts/{userEntityId}/associatedUsers", method = RequestMethod.POST)
	public String postNewAssociatedUsersForm(@PathVariable("userEntityId") Long userEntityId,
			@ModelAttribute @Valid AssociatedUser associatedUser, BindingResult result, Model model) {
		
		UserEntity userEntity = userEntityService.getUserEntity(userEntityId);
		UserEntity currentAdmissionUser = getUserEntityFromSecurityContext();
		
		if (result.hasErrors()) {
			model.addAttribute(userEntity);
			return "associatedUserForm";
		}

		associatedUser.setStudent(userEntity);
		associatedUser.setCreatedBy(currentAdmissionUser);
		associatedUser.setAdmissionOfficer(userEntityService.getUserEntity(associatedUser.getAoId()));
		
		associatedUserService.createAssociatedUser(associatedUser);
		
		return "redirect:/accounts/{userEntityId}";
	}

	/**
	 * When form is displayed, get the current admissionOfficer
	 * set it to the aoId form displays current admissionOfficer in the field
	 * 
	 * @param userEntityId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/accounts/{userEntityId}/associatedUser/edit", method = RequestMethod.GET)
	public String editAssociatedUser(@PathVariable("userEntityId") Long userEntityId, Model model) {
		
		UserEntity userEntity = userEntityService.getUserEntity(userEntityId);
		AssociatedUser associatedUser = associatedUserService.getAssociatedUserByUserEntityId(userEntityId);
		List<UserEntity> admissionCounselors = userEntityService.findByRole(2);
		
		/*To avoid NullPointerException when admissionOfficer is not set for the associatedUser*/
		if (associatedUser.getAdmissionOfficer() != null) {
			associatedUser.setAoId(associatedUser.getAdmissionOfficer().getId());
		}
		
//		model.addAttribute("originalAssociatedUser", associatedUser);
		model.addAttribute("associatedUser",associatedUser);
		model.addAttribute(userEntity);
		model.addAttribute("admissionCounselors", admissionCounselors);
		
		return "editAssociatedUser";
	}
	
	/**
	 * 
	 * 
	 * lastModifiedBy - currentAdmissionUser is the Admission Office who is creating...
	 * dateLastModified is set in the serviceImp class to the current time
	 * 
	 * @param userEntityId
	 * @param origAssociatedUser
	 * @param result
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/accounts/{userEntityId}/associatedUser/edit", method = RequestMethod.POST)
	public String editAssociatedUser(@PathVariable("userEntityId") Long userEntityId,
			@ModelAttribute @Valid AssociatedUser origAssociatedUser, 
			BindingResult result,
			Model model) {
		UserEntity userEntity = userEntityService.getUserEntity(userEntityId);
		UserEntity currentAdmissionUser = getUserEntityFromSecurityContext();
		AssociatedUser associatedUser = associatedUserService.getAssociatedUserByUserEntityId(userEntityId);

		if (result.hasErrors()) {
			origAssociatedUser.setId(associatedUser.getId());
			origAssociatedUser.setStudent(userEntity);
			model.addAttribute("associatedUser", origAssociatedUser);
			model.addAttribute(userEntity);
			return "editAssociatedUser";
		}

//		associatedUser.setAdmissionOfficer(origAssociatedUser.getAdmissionOfficer());
		associatedUser.setAdmissionOfficer(userEntityService.getUserEntity(origAssociatedUser.getAoId()));
		associatedUser.setAgent(origAssociatedUser.getAgent());
		associatedUser.setReferrer(origAssociatedUser.getReferrer());
		associatedUser.setLastModifiedBy(userEntity);
		associatedUser.setStudent(userEntity);
		associatedUser.setLastModifiedBy(currentAdmissionUser);
		
		associatedUserService.updateAssociatedUser(associatedUser);
		
		return "redirect:/accounts/{userEntityId}";
	}
	
	
	// ======================================
	// =                         =
	// ======================================
	
	private UserEntity getUserEntityFromSecurityContext() {
		SecurityContext securityCtx = SecurityContextHolder.getContext();
		Authentication auth = securityCtx.getAuthentication();
		UserEntity userEntity =  (UserEntity) auth.getPrincipal();
		return userEntity;
	}

}
