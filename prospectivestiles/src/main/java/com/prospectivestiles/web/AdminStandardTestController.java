package com.prospectivestiles.web;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.inject.Inject;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.prospectivestiles.domain.EmergencyContact;
import com.prospectivestiles.domain.HighSchool;
import com.prospectivestiles.domain.StandardTest;
import com.prospectivestiles.domain.UserEntity;
import com.prospectivestiles.service.StandardTestService;
import com.prospectivestiles.service.UserEntityService;

@Controller
public class AdminStandardTestController {
	
	
	@Autowired
	private UserEntityService userEntityService;
	
	@Inject
	private StandardTestService standardTestService;
	
	/**
	 * Use this iniit Binder to fix converstaino validTill from String to Date
	 * Failed to convert property value of type java.lang.String to required type java.util.Date 
	 * for property validTill; 
	 */
	@InitBinder
	public void initBinder(WebDataBinder binder) {
	    SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
	    dateFormat.setLenient(false);

	    // true passed to CustomDateEditor constructor means convert empty String to null
	    binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	    binder.registerCustomEditor(Double.class, new CustomNumberEditor(Double.class, true));
	}
	
	// ======================================
	// =             standardTests             =
	// ======================================
	
	@RequestMapping(value = "/accounts/{userEntityId}/standardTests", method = RequestMethod.GET)
	public String getStandardTests(@PathVariable("userEntityId") Long userEntityId,
			Model model) {
		
		/**
		 * load all standardTests for a user
		 */
		model.addAttribute("standardTests", standardTestService.getStandardTestsByUserEntityId(userEntityId));
		
		/**
		 * The modelAttribute "standardTest" for the form to add new standardTest
		 */
		StandardTest standardTest = new StandardTest();
		model.addAttribute("standardTest", standardTest);
		
		model.addAttribute("userEntity", userEntityService.getUserEntity(userEntityId));
		
		return "standardTests";
	}

	@RequestMapping(value = "/accounts/{userEntityId}/standardTest/new", method = RequestMethod.GET)
	public String getNewStandardTestForm(@PathVariable("userEntityId") Long userEntityId,
			Model model) {
		UserEntity userEntity = userEntityService.getUserEntity(userEntityId);
		
		StandardTest standardTest = new StandardTest();
		standardTest.setUserEntity(userEntity);
		
		model.addAttribute(standardTest);
		model.addAttribute(userEntity);
		
		return "newStandardTestForm";
	}
	
	@RequestMapping(value = "/accounts/{userEntityId}/standardTests", method = RequestMethod.POST)
	public String postNewStandardTestForm(
			@PathVariable("userEntityId") Long userEntityId,
			@ModelAttribute @Valid StandardTest standardTest, 
			BindingResult result, 
			Model model) {
		
		UserEntity userEntity = userEntityService.getUserEntity(userEntityId);
		UserEntity currentAdmissionUser = getUserEntityFromSecurityContext();
		
		if (result.hasErrors()) {
			model.addAttribute(userEntity);
			return "newStandardTestForm";
		}

		standardTest.setUserEntity(userEntityService.getUserEntity(userEntityId));
		standardTest.setCreatedBy(currentAdmissionUser);
		standardTestService.createStandardTest(standardTest);
		
		return "redirect:/accounts/{userEntityId}/standardTests";
	}
	
	
	@RequestMapping(value = "/accounts/{userEntityId}/standardTest/{standardTestId}/edit", method = RequestMethod.GET)
	public String editStandardTest(@PathVariable("userEntityId") Long userEntityId,
			@PathVariable("standardTestId") Long standardTestId, Model model) {
		UserEntity userEntity = userEntityService.getUserEntity(userEntityId);
		StandardTest standardTest = getStandardTestValidateUserEntityId(userEntityId, standardTestId);
		
		model.addAttribute("originalStandardTest", standardTest);
		model.addAttribute(standardTest);
		model.addAttribute(userEntity);
		
		return "editStandardTest";
	}

	
	@RequestMapping(value = "/accounts/{userEntityId}/standardTest/{standardTestId}", method = RequestMethod.POST)
	public String editStandardTest(@PathVariable("userEntityId") Long userEntityId,
			@PathVariable("standardTestId") Long standardTestId,
			@ModelAttribute @Valid StandardTest origStandardTest, 
			BindingResult result,
			Model model) {
		UserEntity userEntity = userEntityService.getUserEntity(userEntityId);
		StandardTest standardTest = getStandardTestValidateUserEntityId(userEntityId, standardTestId);
		UserEntity currentAdmissionUser = getUserEntityFromSecurityContext();

		if (result.hasErrors()) {
			origStandardTest.setId(standardTestId);
			origStandardTest.setUserEntity(userEntityService.getUserEntity(userEntityId));
			model.addAttribute("standardTest", origStandardTest);
			model.addAttribute(userEntity);
			return "editStandardTest";
		}
		
		standardTest.setName(origStandardTest.getName());
		standardTest.setScore(origStandardTest.getScore());
		standardTest.setValidTill(origStandardTest.getValidTill());
		standardTest.setLastModifiedBy(currentAdmissionUser);
		
		standardTestService.updateStandardTest(standardTest);
		
		return "redirect:/accounts/{userEntityId}/standardTests";
	}
	
	/*
	 * Using a Modal to delete High School.
	 * The delete form in the Modal calls this method
	 */
	@RequestMapping(value = "/accounts/{userEntityId}/standardTest/{standardTestId}/delete", method = RequestMethod.GET)
	public String getDeleteStandardTest(@PathVariable("userEntityId") Long userEntityId,
			@PathVariable("standardTestId") Long standardTestId, Model model) {
		
		StandardTest standardTest = getStandardTestValidateUserEntityId(userEntityId, standardTestId);
		
		model.addAttribute("originalStandardTest", standardTest);
		model.addAttribute(standardTest);
		
		return "deleteStandardTest";
	}
	
	@RequestMapping(value = "/accounts/{userEntityId}/standardTest/{standardTestId}/delete", method = RequestMethod.POST)
	public String deleteStandardTest(@PathVariable("userEntityId") Long userEntityId,
			@PathVariable("standardTestId") Long standardTestId)
			throws IOException {
		standardTestService.delete(getStandardTestValidateUserEntityId(userEntityId, standardTestId));
		return "redirect:/accounts/{userEntityId}/standardTests";
	}
	
	
	// ======================================
	// =                        =
	// ======================================
	
	private StandardTest getStandardTestValidateUserEntityId(Long userEntityId, Long highschoolId) {
		StandardTest standardTest = standardTestService.getStandardTest(highschoolId);
		
		Assert.isTrue(userEntityId.equals(standardTest.getUserEntity().getId()), "StandardTest Id mismatch");
		return standardTest;
	}
	private UserEntity getUserEntityFromSecurityContext() {
		SecurityContext securityCtx = SecurityContextHolder.getContext();
		Authentication auth = securityCtx.getAuthentication();
		UserEntity userEntity = (UserEntity) auth.getPrincipal();
		return userEntity;
	}
}
