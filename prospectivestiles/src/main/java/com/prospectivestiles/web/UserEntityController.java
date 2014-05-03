package com.prospectivestiles.web;

import javax.inject.Inject;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
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

import com.prospectivestiles.domain.UserEntity;
import com.prospectivestiles.service.UserEntityService;


/*@ManagedBean
@SessionScoped*/
@Controller
//@RequestMapping("/users")
public class UserEntityController {
//	private static final String VN_REG_FORM = "users/registrationForm";
//	private static final String VN_REG_OK = "redirect:/users/registration_ok.xhtml";
	
	@Inject private UserEntityService userEntityService;
	
	@Inject
	@Qualifier("authenticationManager")
	private AuthenticationManager authMgr;
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.setAllowedFields(new String[] { 
			"username", "password", "confirmPassword", "firstName", "lastName",
			"email", "marketingOk", "acceptTerms"
		});
	}
	
	/*
	 * Defined in HomeController.java
	 */
	/*@RequestMapping(value = "/accounts/{username}", method = RequestMethod.GET)
	public String getAccountInfo(@PathVariable("username") String username, Model model)*/ 
	
	@RequestMapping(value = "/registrationform", method = RequestMethod.GET)
	public String getRegistrationForm(Model model) {
		model.addAttribute("userEntity", new UserEntity());
		return "registrationform";
	}
	
	@RequestMapping(value = "/registrationform", method = RequestMethod.POST)
	public String postRegistrationForm(
			@ModelAttribute("userEntity") @Valid UserEntity form,
			BindingResult result) {
		
		System.out.println("######## postRegistrationForm() Called #####");
		System.out.println("######## getFirstName: " + form.getFirstName());
		System.out.println("######## getLastName: " + form.getLastName());
		System.out.println("######## getEmail: " + form.getEmail());
		System.out.println("######## getUsername: " + form.getUsername());
		System.out.println("######## getPassword: " + form.getPassword());
		System.out.println("######## isMarketingOk: " + form.isMarketingOk());
		System.out.println("######## getAcceptTerms: " + form.getAcceptTerms());
		
		if (result != null) {
			System.out.println("######## Error in: " + result.toString());
		}
		
		if (result.hasErrors()) {
			System.out.println("######## result.hasErrors(): true" );
		} else {
			System.out.println("######## result.hasErrors(): false" );
		}
		
//		convertPasswordError(result);
		String password = form.getPassword();
		userEntityService.createUserEntity(form, result);
		
		/*if(usersService.exists(user.getUsername())) {
			result.rejectValue("username", "DuplicateKey.user.username");
			return "newaccount";
		}
		try {
			usersService.create(user);
		} catch (DuplicateKeyException e) {
			result.rejectValue("username", "DuplicateKey.user.username");
			return "newaccount";
		}*/
		
		
		if (!result.hasErrors()) {
			
			Authentication authRequest =
					new UsernamePasswordAuthenticationToken(form.getUsername(), password);
			if (authRequest != null) {
				System.out.println("######## authREquest: " + authRequest.toString());
			}
			
			Authentication authResult = authMgr.authenticate(authRequest);
			
			if (authResult == null) {
				System.out.println("######## authResult is null");
			} else {
				System.out.println("######## authResult is: " + authRequest.toString());
			}
			SecurityContextHolder.getContext().setAuthentication(authResult);
		}
		
		
		return (result.hasErrors() ? "registrationform" : "registrationok");
	}

}
