package com.prospectivestiles.web;

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

import com.prospectivestiles.domain.UserEntity;
import com.prospectivestiles.service.UserEntityService;

@Controller
public class StudentAccountController {
	
	@Autowired
	private UserEntityService userEntityService;
	
	// ======================================
	// =             myAccount             =
	// ======================================
	
	@RequestMapping(value="/myAccount", method = RequestMethod.GET)
	public String getMyAccount(Model model) {		
		UserEntity userEntity = getUserEntityFromSecurityContext();	
		model.addAttribute(userEntity);
		return "myAccount";
	}
	
	@RequestMapping(value="/myAccount/edit", method = RequestMethod.GET)
	public String editMyAccount(Model model) {
		
		UserEntity userEntity = getUserEntityFromSecurityContext();			
		model.addAttribute("originalUserEntity", userEntity);
		model.addAttribute(userEntity);
		
		return "editMyAccount";
	}
	
	@RequestMapping(value = "/myAccount/edit", method = RequestMethod.POST)
	public String editMyAccount(@ModelAttribute UserEntity origUserEntity, BindingResult result) {
		
		UserEntity userEntity = getUserEntityFromSecurityContext();
		
		if (result != null) {
			System.out.println("######## Error in: " + result.toString());
		}
		
		if (result.hasErrors()) {
			System.out.println("######## result.hasErrors(): true" );
//			model.addAttribute("origUserEntity", origUserEntity);
//			return "newAddressForm";
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
