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

import com.prospectivestiles.domain.Term;
import com.prospectivestiles.domain.TestA;
import com.prospectivestiles.domain.UserEntity;
import com.prospectivestiles.service.ProgramOfStudyService;
import com.prospectivestiles.service.TermService;
import com.prospectivestiles.service.TestAService;
import com.prospectivestiles.service.UserEntityService;

@Controller
public class StudentTestAController {
	
	
	@Inject
	private TestAService testAService;
	
	@Autowired
	private UserEntityService userEntityService;
	
	@Inject 
	private TermService termService;
	
	@Inject
	private ProgramOfStudyService programOfStudyService;
	
	// ======================================
	// =             myAccount testAs             =
	// ======================================	

	@RequestMapping(value = "/myAccount/testAs", method = RequestMethod.GET)
	public String getTestAs(Model model) {
		
		UserEntity userEntity = getUserEntityFromSecurityContext();
		
		model.addAttribute("testAs", testAService.getTestAsByUserEntityId(userEntity.getId()));
		model.addAttribute("terms", termService.getAllTerms());
		model.addAttribute("programOfStudies", programOfStudyService.getAllProgramOfStudies());
				
		TestA testA = new TestA();
		System.out.println("################### StudentTestAController.getTestAs: " + testA.toString());
		testA.setUserEntity(userEntity);
		
		model.addAttribute("testA", testA);
		
		model.addAttribute("userEntity", userEntity);
		
		return "testAs";
	}
	
	@RequestMapping(value = "/myAccount/testAs", method = RequestMethod.POST)
	public String postNewTestAForm(@ModelAttribute @Valid TestA testA, BindingResult result) {
		
		System.out.println("################### IN StudentTestAController.postNewTestAForm.");
		System.out.println("################### testA.getAddress1: " + testA.getAddress1());
		System.out.println("################### testA.getAddress2: " + testA.getAddress2());
		System.out.println("################### testA.getCity: " + testA.getCity());
		System.out.println("################### testA.getTerm.getId: " + testA.getTerm().getId());
		System.out.println("################### testA.getTerm.getName: " + testA.getTerm().getName());
		System.out.println("################### testA.getUserEntity: " + testA.getUserEntity());
//		System.out.println("################### testA.getListOfProgramOfStudy: " + testA.getListOfProgramOfStudy());

		if (result.hasErrors()) {
			System.out.println("######## StudentTestAController.postNewTestAForm result.hasErrors(): true" );
			return "redirect:/myAccount/testAs";
		} else {
			System.out.println("######## StudentTestAController.postNewTestAForm result.hasErrors(): false" );
		}
		
		
		UserEntity userEntity = getUserEntityFromSecurityContext();
		
		/**
		 * on the testAs.jsp page >> I fill in address1, address2, city fields and select from terms dropdown
		 * on submit >> term is saved in the testA entity in the term_id field
		 * but on the term db, the term name becomes null
		 * 
		 */
//		Term term = testA.getTerm();
//		Term termToUpdate = termService.getTerm(term.getId());
//		System.out.println("################### termToUpdate.getId(): " + termToUpdate.getId());
//		System.out.println("################### termToUpdate.getName(): " + termToUpdate.getName());
		
//		term.setName(termToUpdate.getName());
		testA.setUserEntity(userEntity);
		
		
//		Term term = termService.getTerm(1);
//		testA.setTerm(term);
		System.out.println("######## StudentTestAController.testA.getUserEntity(): " + testA.getUserEntity().getId());
		
		testA.getListOfProgramOfStudy().add(testA.getProgramOfStudy());
		
		testAService.createTestA(testA);
		/**
		 * FOR TESTING PURPOSE
		 */
//		System.out.println("######## StudentTestAController.testA.zipcode BEFORE zipUpdate: " + testA.getZipcode());
//		testAService.updateTestAZipCode(testA.getId(), "22046");
//		System.out.println("######## StudentTestAController.testA.zipcode AFTER zipUpdate: " + testA.getZipcode());
		
		return "redirect:/myAccount/testAs";
	}
	
	
	
	// ======================================
	// =                         =
	// ======================================
	
	
	@RequestMapping(value = "/myAccount/testA/{testAId}", method = RequestMethod.GET)
	public String editInstitute(@PathVariable("testAId") Long testAId, Model model) {
		UserEntity userEntity = getUserEntityFromSecurityContext();	
		TestA testA = getTestAValidateUserEntityId(userEntity.getId(), testAId);
		
		/**
		 * COMMENT THIS OUT AND TEST - originalTestA
		 */
//		model.addAttribute("originalTestA", testA);
		model.addAttribute(testA);
		
		return "editTestA";
	}
	
	@RequestMapping(value = "/myAccount/testA/{testAId}", method = RequestMethod.POST)
	public String editInstitute(@PathVariable("testAId") Long testAId,
			@ModelAttribute @Valid TestA origTestA, 
			BindingResult result,
			Model model) {
		
		UserEntity userEntity = getUserEntityFromSecurityContext();
		TestA testA = getTestAValidateUserEntityId(userEntity.getId(), testAId);

		if (result.hasErrors()) {
//			model.addAttribute("originalTestA", origTestA);
			return "editTestA";
		}

		testA.setAddress1(origTestA.getAddress1());
		testA.setAddress2(origTestA.getAddress2());
		testA.setCity(origTestA.getCity());
		testA.setTerm(origTestA.getTerm());
		testAService.updateTestA(testA);
		
		return "redirect:/myAccount/testAs";
	}
	
	@RequestMapping(value = "/myAccount/testA/{testAId}/delete", method = RequestMethod.POST)
	public String deleteMessage(@PathVariable("testAId") Long testAId)
			throws IOException {
		UserEntity userEntity = getUserEntityFromSecurityContext();
		testAService.delete(getTestAValidateUserEntityId(userEntity.getId(), testAId));
		return "redirect:/myAccount/testAs";
	}
	
	
	// ======================================
	// =                         =
	// ======================================
	
	private UserEntity getUserEntityFromSecurityContext() {
		SecurityContext securityCtx = SecurityContextHolder.getContext();
		Authentication auth = securityCtx.getAuthentication();
		UserEntity userEntity =  (UserEntity) auth.getPrincipal();
		return userEntity;
	}
	
	private TestA getTestAValidateUserEntityId(Long userEntityId, Long testAId) {
		
		TestA testA = testAService.getTestA(testAId);
		UserEntity userEntity = userEntityService.getUserEntity(userEntityId);
		
		Assert.isTrue(userEntity.getId().equals(testA.getUserEntity().getId()), "TestA Id mismatch");
		return testA;
	}

}
