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

import com.prospectivestiles.domain.StandardTest;
import com.prospectivestiles.service.StandardTestService;
import com.prospectivestiles.service.UserEntityService;

@Controller
public class AdminStandardTestController {
	
	
	@Autowired
	private UserEntityService userEntityService;
	
	@Inject
	private StandardTestService standardTestService;
	
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
	
	@RequestMapping(value = "/accounts/{userEntityId}/standardTests", method = RequestMethod.POST)
	public String postNewStandardTestForm(@PathVariable("userEntityId") Long userEntityId,
			@ModelAttribute @Valid StandardTest standardTest, BindingResult result) {
		
		if (result.hasErrors()) {
			return "newStandardTestForm";
		}

		standardTest.setUserEntity(userEntityService.getUserEntity(userEntityId));
		standardTestService.createStandardTest(standardTest);
		
		return "redirect:/accounts/{userEntityId}/standardTests";
	}
	
	@RequestMapping(value = "/accounts/{userEntityId}/standardTest/{standardTestId}", method = RequestMethod.GET)
	public String editStandardTest(@PathVariable("userEntityId") Long userEntityId,
			@PathVariable("standardTestId") Long standardTestId, Model model) {
		
		StandardTest standardTest = getStandardTestValidateUserEntityId(userEntityId, standardTestId);
		
		model.addAttribute("originalStandardTest", standardTest);
		model.addAttribute(standardTest);
		
		return "editStandardTest";
	}
	
	@RequestMapping(value = "/accounts/{userEntityId}/standardTest/{standardTestId}", method = RequestMethod.POST)
	public String editStandardTest(@PathVariable("userEntityId") Long userEntityId,
			@PathVariable("standardTestId") Long standardTestId,
			@ModelAttribute @Valid StandardTest origStandardTest, 
			BindingResult result,
			Model model) {
		
		StandardTest standardTest = getStandardTestValidateUserEntityId(userEntityId, standardTestId);

		if (result.hasErrors()) {
			model.addAttribute("originalStandardTest", origStandardTest);
			return "editStandardTest";
		}
		
		standardTest.setName(origStandardTest.getName());
		standardTest.setScore(origStandardTest.getScore());
		standardTest.setValidTill(origStandardTest.getValidTill());
		
		standardTestService.updateStandardTest(standardTest);
		
		return "redirect:/accounts/{userEntityId}/standardTests";
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
}
