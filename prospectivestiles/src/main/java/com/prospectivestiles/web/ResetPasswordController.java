package com.prospectivestiles.web;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.prospectivestiles.domain.Address;
import com.prospectivestiles.domain.ResetPasswordEntity;
import com.prospectivestiles.domain.UserEntity;
import com.prospectivestiles.service.ResetPasswordEntityService;
import com.prospectivestiles.service.UserEntityService;

@Controller
public class ResetPasswordController {

	@Inject
	private UserEntityService userEntityService;
	@Inject
	private ResetPasswordEntityService resetPasswordEntityService;

	/**
	 * When user clicks on forgot password link, this controller is called
	 * this redirects to the resetPasswordRequest.jsp page, where user enters his/her email address
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/resetPasswordRequest", method = RequestMethod.GET)
	public String getResetPasswordForm(Model model) {
		// log.debug("################# resetPassword called");
		System.out.println("################# resetPassword called");

		ResetPasswordEntity resetPasswordEntity = new ResetPasswordEntity();
		model.addAttribute("resetPasswordEntity", resetPasswordEntity);
		return "resetPasswordRequest";
	}

	/**
	 * When user enter his/her email address on the resetPasswordRequest.jsp page and submit the form
	 * this method is called. this method passes the resetPasswordEntity that contains user email and 
	 * the request url to the resetPasswordEntityService.saveResetPasswordEntityAndSendEmail()
	 * and returns the resetPasswordRequestResult.jsp page
	 * 
	 * url eg. http://localhost:8080/prospectivestiles/getNewPasswordResetForm/7/16WSqXXMwxcRW0YXNDqAlg==
	 * req.getServerName() - localhost
	 * req.getServerPort() - 8080
	 * req.getServletContext().getContextPath() - prospectivestiles
	 * I can't use req.getServletPath() coz it returns postResetPasswordForm i.e. the current request path.
	 * I will use "getNewPasswordResetForm/" coz that is the servletPath of the method that handles the link.
	 * Reference: http://stackoverflow.com/questions/6814611/how-to-find-the-base-url-of-the-current-webapp-in-spring
	 * Check: http://forum.spring.io/forum/spring-projects/security/117238-secure-way-of-generate-new-users-reset-password
	 * 
	 * @param resetPasswordEntity
	 * @param result
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/postResetPasswordForm", method = RequestMethod.POST)
	public String postResetPasswordForm(
			@ModelAttribute("resetPasswordEntity") @Valid ResetPasswordEntity resetPasswordEntity,
			BindingResult result, HttpServletRequest req) {

		resetPasswordEntityService.saveResetPasswordEntityAndSendEmail(
				resetPasswordEntity, "http://" + req.getServerName() + ":"
						+ req.getServerPort() 
						+ req.getServletContext().getContextPath() 
						+ "/getNewPasswordResetForm");
				
//						+ req.getServletPath());

		return "resetPasswordRequestResult";
	}
	
	/**
	 * When a user clicks on the link in his/her email, this method is called
	 * This loads the resetPasswordEntity by the id passed in the url parameter, and loads it to the model
	 * and returns the newPassword.jsp that prompts user to enter new password, confirm and submit
	 * @param model
	 * @param resetPasswordEntityId
	 * @param resetKey
	 * @return
	 */
	@RequestMapping(value = "/getNewPasswordResetForm/{resetPasswordEntityId}/{resetKey}", method = RequestMethod.GET)
	public String getNewPasswordResetForm(Model model, 
			@PathVariable("resetPasswordEntityId") Long resetPasswordEntityId, 
			@PathVariable("resetKey") String resetKey) {
		
		System.out.println("################# getNewPasswordResetForm called");

		ResetPasswordEntity resetPasswordEntity = resetPasswordEntityService.getResetPasswordEntity(resetPasswordEntityId);
		
		System.out.println("resetPasswordEntity.getId: " + resetPasswordEntity.getId());
		System.out.println("resetPasswordEntity.getEmail: " + resetPasswordEntity.getEmail());
		
		model.addAttribute("resetPasswordEntity", resetPasswordEntity);
		
		return "newPassword";
	}

	/**
	 * When a user enters a password and submits the form.
	 * this method is called.
	 * it saves the new password in the ResetPasswordEntity and updates the password field of the userEntity
	 * @param resetPasswordEntity
	 * @param result
	 * @return
	 */
	@RequestMapping(value = "/postNewPasswordResetForm/{resetPasswordEntityId}", method = RequestMethod.POST)
	public String postNewPasswordResetForm(
			Model model,
			@PathVariable("resetPasswordEntityId") Long resetPasswordEntityId, 
			@ModelAttribute @Valid ResetPasswordEntity origResetPasswordEntity,
			BindingResult result) {
		
		if (result.hasErrors()) {
			origResetPasswordEntity.setId(resetPasswordEntityId);
			model.addAttribute("resetPasswordEntity", origResetPasswordEntity);
			
			return "newPassword";
		}
		
		ResetPasswordEntity resetPasswordEntity = resetPasswordEntityService.getResetPasswordEntity(resetPasswordEntityId);
		
		origResetPasswordEntity.setEmail(resetPasswordEntity.getEmail());
	
		System.out.println("Id: " + origResetPasswordEntity.getId());
		System.out.println("Email: " + origResetPasswordEntity.getEmail());
		System.out.println("Password: " + origResetPasswordEntity.getPassword());
		System.out.println("Confirm password: " + origResetPasswordEntity.getConfirmPassword());
		
		resetPasswordEntityService.updateResetPasswordEntity(origResetPasswordEntity);
		resetPasswordEntityService.updatePassword(origResetPasswordEntity, result);
		
//		userEntityService.updatePassword(origResetPasswordEntity, result);
//		resetPasswordEntityService.updatePassword(origResetPasswordEntity, result);
		
		return (result.hasErrors() ? "newPassword" : "redirect:/welcome");
		
//		return "redirect:/welcome";
	}

}
