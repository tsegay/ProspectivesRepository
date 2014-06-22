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
import com.prospectivestiles.domain.HighSchool;
import com.prospectivestiles.domain.StandardTest;
import com.prospectivestiles.domain.UserEntity;
import com.prospectivestiles.service.StandardTestService;
import com.prospectivestiles.service.UserEntityService;

@Controller
public class StudentStandardTestController {

	@Inject
	private StandardTestService standardTestService;

	@Autowired
	private UserEntityService userEntityService;

	// ======================================
	// =  =
	// ======================================

	@RequestMapping(value = "/myAccount/standardTests", method = RequestMethod.GET)
	public String getStandardTests(Model model) {

		UserEntity userEntity = getUserEntityFromSecurityContext();

		model.addAttribute("standardTests", standardTestService
				.getStandardTestsByUserEntityId(userEntity.getId()));

		StandardTest standardTest = new StandardTest();
		standardTest.setUserEntity(userEntity);

		model.addAttribute("standardTest", standardTest);
		model.addAttribute("userEntity", userEntity);

		return "standardTests";
	}
	
	@RequestMapping(value = "/myAccount/standardTest/new", method = RequestMethod.GET)
	public String getNewStandardTestForm(Model model) {
		UserEntity userEntity = getUserEntityFromSecurityContext();
		
		StandardTest standardTest = new StandardTest();
		standardTest.setUserEntity(userEntity);
		model.addAttribute(standardTest);
		model.addAttribute(userEntity);
		
		return "newStandardTestForm";
	}

	@RequestMapping(value = "/myAccount/standardTests", method = RequestMethod.POST)
	public String postNewStandardTestForm(@ModelAttribute @Valid StandardTest standardTest,
			BindingResult result, Model model) {

		UserEntity userEntity = getUserEntityFromSecurityContext();
		if (result.hasErrors()) {
			model.addAttribute(userEntity);
			// System.out.println("######## result.hasErrors(): true" );
			return "newStandardTestForm";
		} else {
			// System.out.println("######## result.hasErrors(): false" );
		}
		//

		/*
		 * get userEntity from URL >>>>> if logged in as admin get userEntity
		 * from Session >>>>>>> if logged in as student
		 */
		standardTest.setUserEntity(userEntity);
		standardTestService.createStandardTest(standardTest);

		return "redirect:/myAccount/standardTests";
	}

	// ======================================
	// = =
	// ======================================

	@RequestMapping(value = "/myAccount/standardTest/{standardTestId}/edit", method = RequestMethod.GET)
	public String editStandardTest(
			@PathVariable("standardTestId") Long standardTestId, Model model) {
		UserEntity userEntity = getUserEntityFromSecurityContext();
		StandardTest standardTest = getStandardTestsValidateUserEntityId(
				userEntity.getId(), standardTestId);

		model.addAttribute("originalStandardTest", standardTest);
		model.addAttribute(standardTest);

		return "editStandardTest";
	}

	@RequestMapping(value = "/myAccount/standardTest/{standardTestId}", method = RequestMethod.POST)
	public String editStandardTest(@PathVariable("standardTestId") Long standardTestId,
			@ModelAttribute @Valid StandardTest origStandardTest,
			BindingResult result, Model model) {

		UserEntity userEntity = getUserEntityFromSecurityContext();
		StandardTest standardTest = getStandardTestsValidateUserEntityId(
				userEntity.getId(), standardTestId);

		if (result.hasErrors()) {
			// log.debug("Validation Error in Institute form");
			origStandardTest.setId(standardTestId);
			origStandardTest.setUserEntity(userEntity);
			model.addAttribute("standardTest", origStandardTest);
//			model.addAttribute("originalStandardTest", origStandardTest);
			model.addAttribute(userEntity);
			return "editStandardTest";
		}

		// log.debug("Message validated; updating message subject and text");
		standardTest.setName(origStandardTest.getName());
		standardTest.setScore(origStandardTest.getScore());
		standardTest.setValidTill(origStandardTest.getValidTill());

		standardTestService.updateStandardTest(standardTest);

		return "redirect:/myAccount/standardTests";
	}

	/*
	 * Using a Modal to delete High School.
	 * The delete form in the Modal calls this method
	 */
	@RequestMapping(value = "/myAccount/standardTest/{standardTestId}/delete", method = RequestMethod.GET)
	public String getDeleteStandardTest(@PathVariable("standardTestId") Long standardTestId, Model model) {
		
		UserEntity userEntity = getUserEntityFromSecurityContext();	
		
		StandardTest standardTest = getStandardTestsValidateUserEntityId(
				userEntity.getId(), standardTestId);

		model.addAttribute("originalStandardTest", standardTest);
		model.addAttribute(standardTest);
		
		return "deleteStandardTest";
	}
	
	@RequestMapping(value = "/myAccount/standardTest/{standardTestId}/delete", method = RequestMethod.POST)
	public String deleteStandardTest(@PathVariable("standardTestId") Long standardTestId)
			throws IOException {
		UserEntity userEntity = getUserEntityFromSecurityContext();
		StandardTest standardTest = getStandardTestsValidateUserEntityId(userEntity.getId(), standardTestId);
		standardTestService.delete(standardTest);

		return "redirect:/myAccount/standardTests";
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

	private StandardTest getStandardTestsValidateUserEntityId(
			Long userEntityId, Long standardTestId) {

		StandardTest standardTest = standardTestService
				.getStandardTest(standardTestId);

		Assert.isTrue(
				userEntityId.equals(standardTest.getUserEntity().getId()),
				"StandardTest Id mismatch");
		return standardTest;
	}
}
