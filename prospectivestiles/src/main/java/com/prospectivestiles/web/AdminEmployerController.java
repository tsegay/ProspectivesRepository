package com.prospectivestiles.web;

import java.io.IOException;

import javax.inject.Inject;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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
public class AdminEmployerController {
	
	
	@Autowired
	private UserEntityService userEntityService;
	
	@Inject
	private EmployerService employerService;
	
	// ======================================
	// =             employers             =
	// ======================================
	
	@RequestMapping(value = "/accounts/{userEntityId}/employers", method = RequestMethod.GET)
	public String getEmployers(@PathVariable("userEntityId") Long userEntityId,
			Model model) {
		
		/**
		 * load all employers for a user
		 */
		model.addAttribute("employers", employerService.getEmployersByUserEntityId(userEntityId));
		
		/**
		 * The modelAttribute "employer" for the form to add new employer
		 */
		Employer employer = new Employer();
		model.addAttribute("employer", employer);
		
		model.addAttribute("userEntity", userEntityService.getUserEntity(userEntityId));
		
		return "employers";
	}
	
	@RequestMapping(value = "/accounts/{userEntityId}/employer/new", method = RequestMethod.GET)
	public String getNewAddressForm(@PathVariable("userEntityId") Long userEntityId,
			Model model) {
		UserEntity userEntity = userEntityService.getUserEntity(userEntityId);
		
		Employer employer = new Employer();
		employer.setUserEntity(userEntity);
		
		model.addAttribute(employer);
		model.addAttribute(userEntity);
		
		return "newEmployerForm";
	}
	
	@RequestMapping(value = "/accounts/{userEntityId}/employers", method = RequestMethod.POST)
	public String postNewEmployerForm(@PathVariable("userEntityId") Long userEntityId,
			@ModelAttribute @Valid Employer employer, BindingResult result) {
		
		if (result.hasErrors()) {
			return "employers";
		}

		employer.setUserEntity(userEntityService.getUserEntity(userEntityId));
		employerService.createEmployer(employer);
		
		return "redirect:/accounts/{userEntityId}/employers";
	}
	
	@RequestMapping(value = "/accounts/{userEntityId}/employer/{employerId}/edit", method = RequestMethod.GET)
	public String editEmployer(@PathVariable("userEntityId") Long userEntityId,
			@PathVariable("employerId") Long employerId, Model model) {
		
		Employer employer = getEmployerValidateUserEntityId(userEntityId, employerId);
		
		model.addAttribute("originalEmployer", employer);
		model.addAttribute(employer);
		
		return "editEmployer";
	}
	
	@RequestMapping(value = "/accounts/{userEntityId}/employer/{employerId}", method = RequestMethod.POST)
	public String editEmployer(@PathVariable("userEntityId") Long userEntityId,
			@PathVariable("employerId") Long employerId,
			@ModelAttribute @Valid Employer origEmployer, 
			BindingResult result,
			Model model) {
		
		Employer employer = getEmployerValidateUserEntityId(userEntityId, employerId);

		if (result.hasErrors()) {
			model.addAttribute("originalEmployer", origEmployer);
			return "editEmployer";
		}
		
		employer.setCompanyName(origEmployer.getCompanyName());
		employer.setEmployed(origEmployer.isEmployed());
		employer.setEmployedSince(origEmployer.getEmployedSince());
		employer.setPosition(origEmployer.getPosition());
		employer.setEmployerName(origEmployer.getEmployerName());
		employer.setCompanyName(origEmployer.getCompanyName());
		
		employerService.updateEmployer(employer);
		
		return "redirect:/accounts/{userEntityId}/employers";
	}
	
	@RequestMapping(value = "/accounts/{userEntityId}/employer/{employerId}/delete", method = RequestMethod.POST)
	public String deleteEmployer(@PathVariable("userEntityId") Long userEntityId,
			@PathVariable("employerId") Long employerId)
			throws IOException {
		employerService.delete(getEmployerValidateUserEntityId(userEntityId, employerId));
		return "redirect:/accounts/{userEntityId}/employers";
	}
	
	// ======================================
	// =                        =
	// ======================================
	
	private Employer getEmployerValidateUserEntityId(Long userEntityId, Long highschoolId) {
		Employer employer = employerService.getEmployer(highschoolId);
		
		Assert.isTrue(userEntityId.equals(employer.getUserEntity().getId()), "Employer Id mismatch");
		return employer;
	}
}
