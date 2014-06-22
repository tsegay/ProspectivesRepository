package com.prospectivestiles.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

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
import com.prospectivestiles.domain.UserEntity;
import com.prospectivestiles.service.AddressService;
import com.prospectivestiles.service.UserEntityService;

@Controller
public class AdminAccountController {
	
	@Autowired
	private UserEntityService userEntityService;
	
	@Inject
	private AddressService addressService;
	
	// ======================================
	// =             accounts             =
	// ======================================
	
	@RequestMapping(value="/accounts/accounts", method = RequestMethod.GET)
	public String getAllAccounts(Model model) {
//		List<UserEntity> users = userEntityService.getAllUserEntities();
		/*I want to get all students only, not admin users*/
		List<UserEntity> users = userEntityService.findByRole(1);
		model.addAttribute("users", users);
		return "accounts/accounts";
	}
	
	/*@RequestMapping(value="/accounts/accounts", method = RequestMethod.GET, produces="text/html")
	public String list(Pageable pageable, Model model){
		System.out.println(pageable);
		Page<UserEntity> users = this.userEntityRepository.findAll(pageable);
		
        model.addAttribute("users", users);
		return "accounts/accounts";
	}*/
	
	/*
	 * I used this method for pagination by passing page, pageSize and the accounts to the Model.
	 * I dropped this method when I used JSON and JS instead. 
	 * that way I don't have to pass the page num and size in the url.
	 */
	
//	@RequestMapping(value="/accounts/accounts/{page}/{pageSize}", method = RequestMethod.GET)
//	public String getAllAccounts(@PathVariable("page") int page, 
//			@PathVariable("pageSize") int pageSize, 
//			Model model) {
	/*@RequestMapping(value="/accounts/accounts/{page}", method = RequestMethod.GET)
	public String getAllAccounts(@PathVariable("page") int page, Model model) {
		
		int pageSize = 4;
		long usersCount = userEntityService.count();
		int totalPages = (int) Math.ceil((double)usersCount/(double)pageSize);
		
		// getAllUserEntitiesForPage -- TO BE CHANGED
		List<UserEntity> users = userEntityService.getAllUserEntitiesForPage(page, pageSize);
		model.addAttribute("users", users);
		model.addAttribute("usersCount", usersCount);
		model.addAttribute("page", page);
		model.addAttribute("pageSize", pageSize);
		model.addAttribute("totalPages", totalPages);
		
		System.out.println("################## usersCount: " + usersCount + 
				" ### page: " + page +
				" ### pageSize: " + pageSize +
				" ### totalPages: " + totalPages);
		return "accounts/accounts";
	}*/
	
	/*
	 * passing page and pageSize, MAKE optional
	 * if page is null page = 1
	 * if pageSize is null pageSize = 10
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
		
		UserEntity userEntity = userEntityService.getUserEntity(userEntityId);
		
		/**
		 * load all addresses for a user
		 */
		model.addAttribute("addresses", addressService.getAddressesByUserEntityId(userEntityId));
		
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
		
		return "accounts/account";
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
			return "accounts/account";
		} else {
			System.out.println("######## result.hasErrors(): false" );
		}
		
		userEntityService.insertIntoUserEntity(userEntity.getId(), origUserEntity);
		
//		return "redirect:/accounts/{userEntityId}";
		return "accounts/account";
	}
	
	/**
	 * NO DELETE METHOD FOR STUDENTS
	 * @return
	 */
	/*@RequestMapping(value = "/myAccount/delete", method = RequestMethod.POST)
	public String deleteMyAccount(){}*/
	
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
