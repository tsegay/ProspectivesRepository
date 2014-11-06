package com.prospectivestiles.web;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Random;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.prospectivestiles.domain.UserEntity;
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
			"username", "password", "confirmPassword", "firstName", "lastName", "middleName",
			"email", "marketingOk", "acceptTerms", "international", "transferee"
		});
	}
	
	// ======================================
	// =    Student to create account            =
	// ======================================
	
	
	@RequestMapping(value = "/registrationform", method = RequestMethod.GET)
	public String getRegistrationForm(Model model) {
		System.out.println("####### registrationform displayed");
		model.addAttribute("userEntity", new UserEntity());
		return "registrationform";
	}
	
	/**
	 * UPDATE METHOD, postRegisterUserForm USED FOR APPLICANTS TO CREATE ACCOUNTS.
	 * THIS METHOD IS ONLY FOR APPLICANTS
	 * 
	 * @param form
	 * @param result
	 * @return
	 */
	@RequestMapping(value = "/registrationform", method = RequestMethod.POST)
	public String postRegistrationForm(
			@ModelAttribute("userEntity") @Valid UserEntity form,
			BindingResult result) {
		
		log.debug("####### debug creating an account");
		log.info("######## info creating an account");
		
		
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
					
//					if (authResult == null) {
//						System.out.println("######## authResult is null");
//					} else {
//						System.out.println("######## authResult is: " + authRequest.toString());
//					}
					
				}
				
			} else {
				if (!result.hasErrors()) {
					// When admission officer created a user account, redirect to profile page of the new user
//					if (userEntityService.hasRoleAdmin(currentUser.getId())) {
					if (userEntityService.hasRoleAdmissionOrAssist(currentUser.getId())) {
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
	// =    Admission Officer to create a student's account
	// acceptTerms = true => 
	// when applicant sign the application form, then they agree to the privacy policy
	// ======================================
	
	/**
	 * For an AO to create a student's account
	 * @return
	 */
	
	@RequestMapping(value = "/registerUser", method = RequestMethod.GET)
	public String getRegisterUserForm(Model model) {
		System.out.println("####### registerUser displayed");
		
		UserEntity userEntity = new UserEntity();
		userEntity.setUsername("placeholder" + randomNumber());
		userEntity.setPassword("placeholder" + randomNumber());
		userEntity.setConfirmPassword("placeholder" + randomNumber());
		/**
		 * When you load the registration form. set the acceptTerms "true".
		 * If AO registers an applicant, the applicant must have signed a form 
		 * stating the applicant has read the privacy policy and that the applicant agrees with it.
		 */
		userEntity.setAcceptTerms(true);
		model.addAttribute("userEntity", userEntity);
		return "registerUser";
	}
	
	@RequestMapping(value = "/registerUser", method = RequestMethod.POST)
	public String postRegisterUserForm(
			@ModelAttribute("userEntity") @Valid UserEntity form,
			BindingResult result,
			Model model) {
		
		log.debug("####### debug AO creating an account");
		log.info("######## info AO creating an account");
		
		
		/**
		 * When applicant first create an account it is defaulted to PENDING state
		 */
		form.setAccountState("pending");
//		form.setUsername(form.getFirstName() + randomNumber());
		form.setPassword("placeholder"+ randomNumber()); // replace with randomString
//		form.setAcceptTerms(false);
		form.setEnabled(true);
		form.setMarketingOk(true);
		
		/**
		 * When the form is submitted validate the submitted data,
		 * Check firstName, lastName and email are valid data: validate against hibernate validation by annotations
		 */
		if (result.hasErrors()) {
			System.out.println("############## result.hasErrors() after form submission: " + result.toString());

//			System.out.println("############## username: " + form.getUsername());
//			System.out.println("############## getPassword: " + form.getPassword());
//			System.out.println("############## isEnabled: " + form.isEnabled());
//			System.out.println("############## isMarketingOk: " + form.isMarketingOk());
//			System.out.println("############## getAcceptTerms before: " + form.getAcceptTerms());
			
			model.addAttribute("userEntity", form);
			return "registerUser";
		}
		
		userEntityService.createUserEntityAsAO(form, result);
		
		/**
		 * After you try to persist data to DB,
		 * If form data is not valid, due to duplicate email, the createUserEntityAsAO() will throw an error msg
		 * 
		 */
		if (result.hasErrors()) {
			System.out.println("############## result.hasErrors() after insertUserEntity(form, result): " + result.toString());
			model.addAttribute("userEntity", form);
			return "registerUser";
		}
		
		/**
		 * After an admissionOfficer created the account, 
		 * redirect user to the profile page of the newly registered student
		 * 
		 * getUserEntityFromSecurityContext returns null when user not authenticated
		 * NOT REQUIRED to check user aunthentication, the url and method is only available to RoleAdmissionOrAssist
		 */
		UserEntity createdAccount = userEntityService.getUserEntityByUsername(form.getUsername());
		return "redirect:/accounts/" + createdAccount.getId();
			
	}
	
//	// ======================================
//	// =    Admission Officer to create a student's account using JDBC and acceptTerms = false
//	// 		here the admission officer is not marking the acceptTerms as true, applicant will do it on his/her own=
//	// ======================================
//	
//	/**
//	 * For an AO to create a student's account
//	 * @return
//	 */
//	
//	@RequestMapping(value = "/registerUser", method = RequestMethod.GET)
//	public String getRegisterUserForm(Model model) {
//		System.out.println("####### registerUser displayed");
//		
//		UserEntity userEntity = new UserEntity();
//		userEntity.setUsername("placeholder" + randomNumber());
//		userEntity.setPassword("placeholder");
//		userEntity.setConfirmPassword("placeholder");
//		/**
//		 * When you load the registration form. set the acceptTerms "true".
//		 * If AO regsiters a student, the student must have signed a form 
//		 * stating the applicant has read the privacy policy and that the applicant agrees with it.
//		 */
//		userEntity.setAcceptTerms(true);
//		model.addAttribute("userEntity", userEntity);
//		return "registerUser";
//	}
//	
//	@RequestMapping(value = "/registerUser", method = RequestMethod.POST)
//	public String postRegisterUserForm(
//			@ModelAttribute("userEntity") @Valid UserEntity form,
//			BindingResult result,
//			Model model) {
//		
//		log.debug("####### debug AO creating an account");
//		log.info("######## info AO creating an account");
//		
//		
//		/**
//		 * When applicant first create an account it is defaulted to PENDING state
//		 */
//		form.setAccountState("pending");
////		form.setUsername(form.getFirstName() + randomNumber());
//		form.setPassword("placeholder"); // replace with randomString
////		form.setAcceptTerms(false);
//		form.setEnabled(true);
//		form.setMarketingOk(true);
//		
//		if (result.hasErrors()) {
////			System.out.println("############## result.hasErrors() after form submission");
//			System.out.println("############## result.hasErrors() after form submission: " + result.toString());
//
////			System.out.println("############## username: " + form.getUsername());
////			System.out.println("############## getPassword: " + form.getPassword());
////			System.out.println("############## isEnabled: " + form.isEnabled());
////			System.out.println("############## isMarketingOk: " + form.isMarketingOk());
////			System.out.println("############## getAcceptTerms before: " + form.getAcceptTerms());
//			
////			form.setUsername("placeholder" + randomNumber());
////			form.setPassword("placeholder");
////			form.setConfirmPassword("placeholder");
////			form.setAcceptTerms(true);
////			form.setEnabled(true);
////			form.setMarketingOk(true);
//			model.addAttribute("userEntity", form);
//			System.out.println("############## getAcceptTerms after: " + form.getAcceptTerms());
//			return "registerUser";
//		}
//		
//		userEntityService.insertUserEntity(form, result);
////		userEntityService.createUserEntity(form, result);
//		
//		if (result.hasErrors()) {
//			System.out.println("############## result.hasErrors() after insertUserEntity(form, result): " + result.toString());
//			model.addAttribute("userEntity", form);
//			return "registerUser";
//		}
//		
//		/**
//		 * After an admissionOfficer created the account, 
//		 * redirect user to the profile page of the newly registered student
//		 * 
//		 * getUserEntityFromSecurityContext returns null when user not authenticated
//		 * NOT REQUIRED to check user aunthentication, the url and method is only available to RoleAdmissionOrAssist
//		 */
//		UserEntity createdAccount = userEntityService.getUserEntityByUsername(form.getUsername());
//		return "redirect:/accounts/" + createdAccount.getId();
//			
//	}
	
	
	// ======================================
	// =                         =
	// ======================================
	
	public int randomNumber() {
//		SecureRandom rand = new SecureRandom();
//		return new BigInteger(130, rand).toString(32);
	    int START = 100;
	    int END = 1000;
	    Random random = new Random();
	    
	    long range = (long)END - (long)START + 1;
	    long fraction = (long)(range * random.nextDouble());
	    int randomNumber =  (int)(fraction + START); 
	    return randomNumber;
	}
	
	public String randomString() {
		SecureRandom rand = new SecureRandom();
		return new BigInteger(130, rand).toString(32);
	}
	
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
