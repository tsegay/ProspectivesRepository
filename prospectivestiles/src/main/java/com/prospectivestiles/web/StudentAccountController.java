package com.prospectivestiles.web;

import java.util.Date;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.prospectivestiles.domain.UserEntity;
import com.prospectivestiles.service.AddressService;
import com.prospectivestiles.service.UserEntityService;

@Controller
public class StudentAccountController {
	
	@Autowired
	private UserEntityService userEntityService;
	
	@Inject
	private AddressService addressService;
	
	// ======================================
	// =             myAccount             =
	// ======================================
	
	/*
	 * I am going to merge the personal info and addresses page together.
	 * I will load the addresses to the model
	 */
	@RequestMapping(value="/myAccount", method = RequestMethod.GET)
	public String getMyAccount(Model model) {		
		
		/**
		 * When user makes change in account in editMyAccount page and clicks on submit,
		 * The changes are udpated in the db but the myAccount page shows the data before the update.
		 * If user logs out and login back, the new updated data is loaded from db.
		 * So, I have to override the userEntity saved in the session, 
		 * by loading the data from the db on every call of the myAccount page
		 */
		
		UserEntity userEntityInSession = getUserEntityFromSecurityContext();	
		
		UserEntity userEntity = userEntityService.getUserEntity(userEntityInSession.getId());
		
		/**
		 * load all addresses for a user
		 */
		model.addAttribute("addresses", addressService.getAddressesByUserEntityId(userEntity.getId()));
		
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
		
		return "userAccount";
	}
	
	@RequestMapping(value="/myAccount/edit", method = RequestMethod.GET)
	public String editMyAccount(Model model) {
		
		/**
		 * Read comment with in getMyAccount method
		 */
		UserEntity userEntityInSession = getUserEntityFromSecurityContext();	
		UserEntity userEntity = userEntityService.getUserEntity(userEntityInSession.getId());
		
		model.addAttribute("originalUserEntity", userEntity);
		model.addAttribute(userEntity);
		
		return "editMyAccount";
	}
	
	/*use @Valid to validate userEntity*/
	@RequestMapping(value = "/myAccount/edit", method = RequestMethod.POST)
	public String editMyAccount(@ModelAttribute UserEntity origUserEntity, BindingResult result, Model model) {
		
		UserEntity userEntity = getUserEntityFromSecurityContext();
		
		if (result != null) {
			System.out.println("######## Error in: " + result.toString());
		}
		
		if (result.hasErrors()) {
			System.out.println("######## result.hasErrors(): true" );
//			model.addAttribute("origUserEntity", origUserEntity);
			
			origUserEntity.setId(userEntity.getId());
			model.addAttribute("userEntity", origUserEntity);
			
//			model.addAttribute(userEntity);
			return "editMyAccount";
		} else {
			System.out.println("######## result.hasErrors(): false");
		}
		
		userEntityService.insertIntoUserEntity(userEntity.getId(), origUserEntity);
		
		return "redirect:/myAccount";
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
