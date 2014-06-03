package com.prospectivestiles.web;

import java.util.List;

import javax.inject.Inject;
import javax.validation.Valid;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.prospectivestiles.domain.Address;
import com.prospectivestiles.domain.EmergencyContact;
import com.prospectivestiles.domain.UserEntity;
import com.prospectivestiles.repository.UserEntityRepository;
import com.prospectivestiles.service.AddressService;
import com.prospectivestiles.service.UserEntityService;

@Controller
public class AdminAccountController {
	
	@Autowired
	private UserEntityService userEntityService;
	
	@Inject
	private AddressService addressService;
	
	/*@Autowired
	private UserEntityRepository userEntityRepository;*/
	
	
	// ======================================
	// =             accounts             =
	// ======================================
	
	/*@RequestMapping(value="/accounts/accounts", method = RequestMethod.GET)
	public String getAllAccounts(Model model) {
		List<UserEntity> users = userEntityService.getAllUserEntities();
		model.addAttribute("users", users);
		return "accounts/accounts";
	}*/
	
	/*@RequestMapping(value="/accounts/accounts", method = RequestMethod.GET, produces="text/html")
	public String list(Pageable pageable, Model model){
		System.out.println(pageable);
		Page<UserEntity> users = this.userEntityRepository.findAll(pageable);
		
        model.addAttribute("users", users);
		return "accounts/accounts";
	}*/
	
//	@RequestMapping(value="/accounts/accounts/{page}/{pageSize}", method = RequestMethod.GET)
//	public String getAllAccounts(@PathVariable("page") int page, 
//			@PathVariable("pageSize") int pageSize, 
//			Model model) {
	@RequestMapping(value="/accounts/accounts/{page}", method = RequestMethod.GET)
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
	}
	
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
			model.addAttribute("originalUserEntity", userEntity);
			return "accounts/account";
		} else {
			System.out.println("######## result.hasErrors(): false" );
		}
		
		userEntityService.insertIntoUserEntity(userEntity.getId(), origUserEntity);
		
		return "redirect:/accounts/{userEntityId}";
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
