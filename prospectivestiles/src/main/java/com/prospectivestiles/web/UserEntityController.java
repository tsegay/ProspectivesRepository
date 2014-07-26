package com.prospectivestiles.web;

import javax.inject.Inject;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.prospectivestiles.domain.Notification;
import com.prospectivestiles.domain.UserEntity;
import com.prospectivestiles.service.NotificationService;
import com.prospectivestiles.service.UserEntityService;


@Controller
public class UserEntityController {
	
	@Inject private UserEntityService userEntityService;
	
	private static final Logger log = LoggerFactory.getLogger(UserEntityController.class);
	
	
	@Inject
	@Qualifier("authenticationManager")
	private AuthenticationManager authMgr;
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.setAllowedFields(new String[] { 
			"username", "password", "confirmPassword", "firstName", "lastName",
			"email", "marketingOk", "acceptTerms", "international", "transferee"
		});
	}
	
	@RequestMapping(value = "/registrationform", method = RequestMethod.GET)
	public String getRegistrationForm(Model model) {
		System.out.println("####### registrationform displayed");
		model.addAttribute("userEntity", new UserEntity());
		return "registrationform";
	}
	
	@RequestMapping(value = "/registrationform", method = RequestMethod.POST)
	public String postRegistrationForm(
			@ModelAttribute("userEntity") @Valid UserEntity form,
			BindingResult result) {
		
//		System.out.println("######## postRegistrationForm() Called #####");
		log.debug("####### debug creating an account");
		log.info("######## info creating an account");
		
//		System.out.println("######## getFirstName: " + form.getFirstName());
//		System.out.println("######## getLastName: " + form.getLastName());
//		System.out.println("######## getEmail: " + form.getEmail());
//		System.out.println("######## getUsername: " + form.getUsername());
//		System.out.println("######## getPassword: " + form.getPassword());
//		System.out.println("######## isMarketingOk: " + form.isMarketingOk());
//		System.out.println("######## getAcceptTerms: " + form.getAcceptTerms());
//		
//		if (result.hasErrors()) {
//			System.out.println("######## result.hasErrors(): true" );
//			System.out.println("######## Error in: " + result.toString());
//		} else {
//			System.out.println("######## result.hasErrors(): false" );
//		}
		
		String password = form.getPassword();
		/**
		 * When applicant first create an account it is defaulted to PENDING state
		 */
		form.setAccountState("pending");
		
		userEntityService.createUserEntity(form, result);
		
		/**
		 * Find who is creating the account? An applicant or an admission worker.
		 * If an applicant is creating the account, log in the user after the account is created.
		 * If an admissionOfficer is creating the account, 
		 * do not log in the admissionOfficer with the currently created account
		 * but redirect user to the profile page of the newly registered student
		 * 
		 * getUserEntityFromSecurityContext retrns null when user not authenticated
		 */
			UserEntity currentUser = getUserEntityFromSecurityContext();
			if (currentUser == null) {
//				System.out.println("######## currentUser: " + currentUser);
				log.debug("####### debug: " + form.getUsername() + " creating an account");
				log.info("####### info: " + form.getUsername() + " creating an account");
				
				if (!result.hasErrors()) {
					
					Authentication authRequest =
							new UsernamePasswordAuthenticationToken(form.getUsername(), password);
					Authentication authResult = authMgr.authenticate(authRequest);
					SecurityContextHolder.getContext().setAuthentication(authResult);
					
//					if (authRequest != null) {
//						System.out.println("######## authREquest: " + authRequest.toString());
//					}
//					if (authResult == null) {
//						System.out.println("######## authResult is null");
//					} else {
//						System.out.println("######## authResult is: " + authRequest.toString());
//					}
					
				}
				
			} else {
				if (!result.hasErrors()) {
					// When admission officer created a user account, redirect to profile page of the new user
					if (userEntityService.hasRoleAdmin(currentUser.getId())) {
//					System.out.println("######## currentUser: " + currentUser.getFullName());
						log.debug("####### debug: " + currentUser.getFullName() + " creating an account ");
						log.info("####### info: " + currentUser.getFullName() + " creating an account ");
						
						UserEntity createdAccount = userEntityService.getUserEntityByUsername(form.getUsername());
						return "redirect:/accounts/" + createdAccount.getId();
					}
					
				}
			}
		
		return (result.hasErrors() ? "registrationform" : "welcome");
	}
	
	// ======================================
	// =                         =
	// ======================================
	
	private UserEntity getUserEntityFromSecurityContext() {
		SecurityContext securityCtx = SecurityContextHolder.getContext();
		Authentication auth = securityCtx.getAuthentication();
		UserEntity userEntity = null;
		/**
		 * If user is not authenticated, principal is String
		 * Else it is an object of type UserEntity
		 * 
		 * When a user is not authenticated, 
		 * the userEntity variable returns a String value instead of an object,
		 * Then if you simply return this string, 
		 * you get exception: "java.lang.String cannot be cast to com.prospectivestiles.domain.UserEntity"
		 * 
		 * Hence, return the userEntity only when principal is not instanceof AnonymousAuthenticationToken
		 * If user is not authenticated return null
		 */
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			userEntity =  (UserEntity) auth.getPrincipal();
		}
		
		return userEntity;
	}

}
