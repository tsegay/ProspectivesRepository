package com.prospectivestiles.web;

import java.util.List;

import javax.validation.Valid;

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

import com.prospectivestiles.domain.EmergencyContact;
import com.prospectivestiles.domain.UserEntity;
import com.prospectivestiles.service.UserEntityService;

@Controller
public class AdminAccountController {
	
	@Autowired
	private UserEntityService userEntityService;
	
	// ======================================
	// =             accounts             =
	// ======================================
	@RequestMapping(value="/accounts/accounts", method = RequestMethod.GET)
	public String getAllAccounts(Model model) {
		List<UserEntity> users = userEntityService.getAllUserEntities();
		model.addAttribute("users", users);
		return "accounts/accounts";
	}
	
	@RequestMapping(value = "/accounts/{userEntityId}", method = RequestMethod.GET)
	public String getAccountInfo(@PathVariable("userEntityId") long userEntityId, Model model) {
		UserEntity userEntity = userEntityService.getUserEntity(userEntityId);
		model.addAttribute(userEntity);
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
