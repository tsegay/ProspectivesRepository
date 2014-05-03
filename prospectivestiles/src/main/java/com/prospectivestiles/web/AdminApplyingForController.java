package com.prospectivestiles.web;

import java.io.IOException;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.prospectivestiles.domain.ProgramOfStudy;
import com.prospectivestiles.domain.Term;
import com.prospectivestiles.domain.UserEntity;
import com.prospectivestiles.service.ProgramOfStudyService;
import com.prospectivestiles.service.TermService;
import com.prospectivestiles.service.UserEntityService;

@Controller
public class AdminApplyingForController {
	
	
	@Autowired
	private UserEntityService userEntityService;
	
	@Inject
	private ProgramOfStudyService programOfStudyService;
	
	@Inject
	private TermService termService;
	
	// ======================================
	// =             emergencyContacts             =
	// ======================================
	
	@RequestMapping(value = "/accounts/{userEntityId}/applyingFor", method = RequestMethod.GET)
	public String getApplyingFor(@PathVariable("userEntityId") Long userEntityId,
			Model model) {
		
//		model.addAttribute("emergencyContacts", emergencyContactService.getEmergencyContactsByUserEntityId(userEntityId));
		model.addAttribute("terms", termService.getAllTerms());
		model.addAttribute("programOfStudies", programOfStudyService.getAllProgramOfStudies());
		/**
		 * The modelAttribute "emergencyContact" for the form to add new emergencyContact
		 */
//		EmergencyContact emergencyContact = new EmergencyContact();
//		model.addAttribute("emergencyContact", emergencyContact);
		
		model.addAttribute("userEntity", userEntityService.getUserEntity(userEntityId));
		
		return "applyingFor";
	}
//	@RequestMapping(value = "/myAccount/applyingFor", method = RequestMethod.GET)
//	public String getApplyingFor(Model model) {
//		
//		UserEntity userEntity = getUserEntityFromSecurityContext();
//		model.addAttribute("terms", termService.getAllTerms());
//		model.addAttribute("programOfStudies", programOfStudyService.getAllProgramOfStudies());
//		model.addAttribute("userEntity", userEntity);
//		
//		return "applyingFor";
//	}
	
	@RequestMapping(value = "/accounts/{userEntityId}/applyingFor", method = RequestMethod.POST)
	public String postNewApplyingForForm(@PathVariable("userEntityId") Long userEntityId,
			@ModelAttribute UserEntity origUserEntity, BindingResult result) {
		
		if (result.hasErrors()) {
			// view doesn't exist
			return "newApplyingForForm";
		}

//		UserEntity userEntity = userEntityService.getUserEntity(userEntityId);
		userEntityService.insertTerm(userEntityId, origUserEntity.getTerm().getId());
		userEntityService.insertProgramOfStudy(userEntityId, origUserEntity.getProgramOfStudy().getId());
		
		
		return "redirect:/accounts/{userEntityId}/applyingFor";
	}
//	@RequestMapping(value = "/myAccount/applyingFor", method = RequestMethod.POST)
//	public String postApplyingFor(@ModelAttribute UserEntity origUserEntity, BindingResult result) {
//		if (result.hasErrors()) {
//			System.out.println("######## StudentApplyingForController.postNewAddressForm result.hasErrors(): true" );
//			return "redirect:/myAccount/applyingFor";
//		} else {
//			System.out.println("######## StudentApplyingForController.postNewAddressForm result.hasErrors(): false" );
//		}
//		UserEntity userEntity = getUserEntityFromSecurityContext();
//		userEntityService.insertTerm(userEntity.getId(), origUserEntity.getTerm().getId());
//		userEntityService.insertProgramOfStudy(userEntity.getId(), origUserEntity.getProgramOfStudy().getId());
//		return "redirect:/myAccount/applyingFor";
//	}
	
//	@RequestMapping(value = "/accounts/{userEntityId}/emergencyContact/{emerge/ncyContactId}", method = RequestMethod.GET)
	@RequestMapping(value = "/accounts/{userEntityId}/applyingFor/edit", method = RequestMethod.GET)
	public String editApplyingFor(@PathVariable("userEntityId") Long userEntityId,
			@PathVariable("programOfStudyId") Long programOfStudyId, 
			@PathVariable("termId") Long termId,
			Model model) {
		ProgramOfStudy programOfStudy = programOfStudyService.getProgramOfStudy(programOfStudyId);
		Term term = termService.getTerm(termId);
		
		model.addAttribute("origProgramOfStudy", programOfStudy);
		model.addAttribute(programOfStudy);
		model.addAttribute("origTerm", term);
		model.addAttribute(term);
		return "editAddress";
		
//		EmergencyContact emergencyContact = getEmergencyContactValidateUserEntityId(userEntityId, emergencyContactId);
//		model.addAttribute("originalEmergencyContact", emergencyContact);
//		model.addAttribute(emergencyContact);
//		return "editEmergencyContact";
	}
	
//	@RequestMapping(value = "/myAccount/applyingFor/edit", method = RequestMethod.GET)
//	public String editApplyingFor(@PathVariable("programOfStudyId") Long programOfStudyId, 
//			@PathVariable("termId") Long termId, 
//			Model model) {
//		UserEntity userEntity = getUserEntityFromSecurityContext();	
//		
//		ProgramOfStudy programOfStudy = programOfStudyService.getProgramOfStudy(programOfStudyId);
//		Term term = termService.getTerm(termId);
//		
//		model.addAttribute("origProgramOfStudy", programOfStudy);
//		model.addAttribute(programOfStudy);
//		model.addAttribute("origTerm", term);
//		model.addAttribute(term);
//		
//		return "editAddress";
//	}
	
	/**
	 * Update this for the Term and ProgramOfStudy
	 * 1. Update the term and program of study using the getApplyingFor()
	 * By displaying alternating button labels Add/Update 
	 * 2. Use the method below
	 */
//	@RequestMapping(value = "/accounts/{userEntityId}/emergencyContact/{emergencyContactId}", method = RequestMethod.POST)
	@RequestMapping(value = "/accounts/{userEntityId}/applyingFor/edit", method = RequestMethod.POST)
	public String editApplyingFor(@PathVariable("userEntityId") Long userEntityId,
			@ModelAttribute UserEntity origUserEntity,
			BindingResult result,
			Model model) {
		
		/**
		 * Get the termId, programOfStudyId and userEntityId
		 * Update this fields in the UserEntity.
		 * Use JdbcTemplate and create method to update this fields 
		 */
		if (result.hasErrors()) {
			model.addAttribute("origUserEntity", origUserEntity);
			return "editApplyingFor";
		}
		userEntityService.insertTerm(userEntityId, origUserEntity.getTerm().getId());
		userEntityService.insertProgramOfStudy(userEntityId, origUserEntity.getProgramOfStudy().getId());
				
		return "redirect:/accounts/{userEntityId}/applyingFor";
	}
	
	
	/**
	 * DO NOT Need Delete option to remove term and program of study that a student is applying for.
	 * But to track whether students who applied actually enrolled in classes, 
	 * I have to create a field enrolled. After a students application is complete and student is admittted.
	 * then when student get enrolled in class, insert value to the enrolled field.
	 */
	@RequestMapping(value = "/accounts/{userEntityId}/applyingFor/delete", method = RequestMethod.POST)
	public String deleteApplyingFor(@PathVariable("userEntityId") Long userEntityId,
			@PathVariable("programOfStudyId") Long programOfStudyId, 
			@PathVariable("termId") Long termId)
			throws IOException {
		
		// Call a method to delete values from the term and and programOfStudy fields in the UserEntity table.
		
		return "redirect:/accounts/{userEntityId}/applyingFor";
	}
	
	
	// ======================================
	// =                        =
	// ======================================
	
	
}
