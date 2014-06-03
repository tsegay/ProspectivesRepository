package com.prospectivestiles.web;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
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
		
		UserEntity userEntity = getUserEntityFromSecurityContext();	
		
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
		
//		return "accounts/account";
		return "myAccount";
	}
	
	@RequestMapping(value="/myAccount/edit", method = RequestMethod.GET)
	public String editMyAccount(Model model) {
		
		UserEntity userEntity = getUserEntityFromSecurityContext();			
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
//			return "newAddressForm";
			model.addAttribute(userEntity);
			return "myAccount";
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
