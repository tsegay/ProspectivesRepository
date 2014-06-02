package com.prospectivestiles.web;

import java.io.IOException;

import javax.inject.Inject;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.prospectivestiles.domain.EmergencyContact;
import com.prospectivestiles.domain.Employer;
import com.prospectivestiles.domain.UserEntity;
import com.prospectivestiles.service.EmployerService;
import com.prospectivestiles.service.UserEntityService;

@Controller
public class StudentEmployerController {

	@Inject
	private EmployerService employerService;

	@Autowired
	private UserEntityService userEntityService;

	// ======================================
	// = myAccount addresses =
	// ======================================

	@RequestMapping(value = "/myAccount/employers", method = RequestMethod.GET)
	public String getEmployers(Model model) {

		UserEntity userEntity = getUserEntityFromSecurityContext();

		model.addAttribute("employers", 
				employerService.getEmployersByUserEntityId(userEntity.getId()));

		/*
		 * I am loading too many entities in the model. What is the limit???
		 */
		Employer employer = new Employer();
		employer.setUserEntity(userEntity);
		
		model.addAttribute("employer", employer);
		model.addAttribute("userEntity", userEntity);

		return "employers";
	}
	
	@RequestMapping(value = "/myAccount/employer/new", method = RequestMethod.GET)
	public String getNewEmployerForm(Model model) {
		UserEntity userEntity = getUserEntityFromSecurityContext();
		
		Employer employer = new Employer();
		employer.setUserEntity(userEntity);
		model.addAttribute(employer);
		model.addAttribute(userEntity);
		
		return "newEmployerForm";
	}

	@RequestMapping(value = "/myAccount/employers", method = RequestMethod.POST)
	public String postNewEmployerForm(@ModelAttribute @Valid Employer employer,
			BindingResult result, Model model) {
		
		UserEntity userEntity = getUserEntityFromSecurityContext();

		if (result.hasErrors()) {
			// System.out.println("######## result.hasErrors(): true" );
			model.addAttribute(userEntity);
			return "employers";
		} else {
			// System.out.println("######## result.hasErrors(): false" );
		}
		//

		/*
		 * get userEntity from URL >>>>> if logged in as admin get userEntity
		 * from Session >>>>>>> if logged in as student
		 */
		employer.setUserEntity(userEntity);
		employerService.createEmployer(employer);

		return "redirect:/myAccount/employers";
	}

	// ======================================
	// = =
	// ======================================


	// @RequestMapping(value = "/myAccount/address/{addressId}/edit", method =
	// RequestMethod.GET)
	@RequestMapping(value = "/myAccount/employer/{employerId}/edit", method = RequestMethod.GET)
	public String editEmployer(@PathVariable("employerId") Long employerId,
			Model model) {
		UserEntity userEntity = getUserEntityFromSecurityContext();
		Employer employer = getEmployersValidateUserEntityId(userEntity.getId(), 
				employerId);

		model.addAttribute("originalEmployer", employer);
		model.addAttribute(employer);

		return "editEmployer";
	}

	@RequestMapping(value = "/myAccount/employer/{employerId}", method = RequestMethod.POST)
	public String editInstitute(@PathVariable("employerId") Long employerId,
			@ModelAttribute @Valid Employer origEmployer, BindingResult result,
			Model model) {

		UserEntity userEntity = getUserEntityFromSecurityContext();
		Employer employer = getEmployersValidateUserEntityId(userEntity.getId(), 
				employerId);

		if (result.hasErrors()) {
			// log.debug("Validation Error in Institute form");
			model.addAttribute("originalEmployer", origEmployer);
			model.addAttribute(userEntity);
			return "editEmployer";
		}

		// log.debug("Message validated; updating message subject and text");
		employer.setCompanyName(origEmployer.getCompanyName());
//		employer.setEmployed(origEmployer.isEmployed());
		employer.setEmployedFrom(origEmployer.getEmployedFrom());
		employer.setEmployedTo(origEmployer.getEmployedTo());
		employer.setPosition(origEmployer.getPosition());
		employer.setEmployerName(origEmployer.getEmployerName());
		employer.setCompanyName(origEmployer.getCompanyName());
		
//		employer.setUserEntity(origEmployer.getUserEntity());
		
		employerService.updateEmployer(employer);
		
		return "redirect:/myAccount/employers";
	}

	@RequestMapping(value = "/myAccount/employer/{employerId}/delete", method = RequestMethod.POST)
	public String deleteMessage(@PathVariable("employerId") Long employerId)
			throws IOException {
		UserEntity userEntity = getUserEntityFromSecurityContext();
		Employer employer = getEmployersValidateUserEntityId(userEntity.getId(), 
				employerId);
		employerService.delete(employer);
		
		return "redirect:/myAccount/employers";
	}

	// ======================================
	// = =
	// ======================================

	private UserEntity getUserEntityFromSecurityContext() {
		SecurityContext securityCtx = SecurityContextHolder.getContext();
		Authentication auth = securityCtx.getAuthentication();
		UserEntity userEntity = (UserEntity) auth.getPrincipal();
		return userEntity;
	}

	private Employer getEmployersValidateUserEntityId(Long userEntityId,
			Long employerId) {

		Employer employer = employerService.getEmployer(employerId);
//		UserEntity userEntity = userEntityService.getUserEntity(userEntityId);

		Assert.isTrue(userEntityId.equals(employer.getUserEntity().getId()),
				"Employer Id mismatch");
//		Assert.isTrue(userEntity.getId().equals(employer.getUserEntity().getId()),
//				"Employer Id mismatch");
		return employer;
	}
}
