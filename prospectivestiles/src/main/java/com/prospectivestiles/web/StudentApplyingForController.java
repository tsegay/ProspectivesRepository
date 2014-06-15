package com.prospectivestiles.web;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.prospectivestiles.domain.UserEntity;
import com.prospectivestiles.service.ProgramOfStudyService;
import com.prospectivestiles.service.TermService;
import com.prospectivestiles.service.UserEntityService;

@Controller
public class StudentApplyingForController {
	
	/*
	 * user can't delete term or program of study but they can change it.
	 * I am using the same method to create and edit the term and program of study to
	 * insert the term and program of study to the suerEntity uisng jdbc
	 */
	
	@Inject
	private ProgramOfStudyService programOfStudyService;
	
	@Inject
	private TermService termService;
	
	@Autowired
	private UserEntityService userEntityService;
	
	// ======================================
	// =             applyingFor             =
	// ======================================	

	@RequestMapping(value = "/myAccount/applyingFor", method = RequestMethod.GET)
	public String getApplyingFor(Model model) {
		
		UserEntity userEntity = getUserEntityFromSecurityContext();
		
		/**
		 * this return all terms. i need term for a student
		 */
		model.addAttribute("terms", termService.getAllTerms());
		model.addAttribute("programOfStudies", programOfStudyService.getAllProgramOfStudies());
		/*
		 * I am loading too many entities in the model. What is the limit???
		 */
		model.addAttribute("userEntity", userEntity);
		
		return "applyingFor";
	}
	
	@RequestMapping(value = "/myAccount/applyingFor", method = RequestMethod.POST)
	public String postApplyingFor(@ModelAttribute UserEntity origUserEntity, BindingResult result, Model model) {
		
		System.out.println("################### In StudentApplyingForController.postApplyingFor.");
		System.out.println("################### origUserEntity.getId: " + origUserEntity.getId());
		System.out.println("################### origUserEntity.getFirstName: " + origUserEntity.getFirstName());
		System.out.println("################### origUserEntity.getTerm().getId(): " + origUserEntity.getTerm().getId());
		System.out.println("################### origUserEntity.getTerm().getName(): " + origUserEntity.getTerm().getName());
		
		if (result.hasErrors()) {
			System.out.println("######## StudentApplyingForController.postNewAddressForm result.hasErrors(): true" );
			/**
			 * Work on displaying spring err msg in Modal.
			 */
			return "redirect:/myAccount/applyingFor";
		} else {
			System.out.println("######## StudentApplyingForController.postNewAddressForm result.hasErrors(): false" );
		}

		UserEntity userEntity = getUserEntityFromSecurityContext();
		model.addAttribute("userEntity", userEntity);
		
		System.out.println("######## StudentApplyingForController.userEntity.getId(): " + userEntity.getId());
		
//		Collection<UserEntity> userEntitiesListInProgram = programOfStudy.getListOfUserEntity();
//		userEntitiesListInProgram.add(userEntity);
		
//		Collection<UserEntity> userEntitiesListInTerm = term.getListOfUserEntity();
//		userEntitiesListInTerm.add(userEntity);
		
//		userEntity.setTerm(term);
		
		/**
		 * this should update the db but it doesn't
		 */
//		Term term = termService.getTerm(origUserEntity.getTerm().getId());
//		userEntity.setTerm(term);
		
//		testA.getListOfProgramOfStudy().add(testA.getProgramOfStudy());
//		userEntity.getListOfProgramOfStudy().add(origUserEntity.getProgramOfStudy());
//		userEntityService.updateUserEntity(userEntity);
		
		/**
		 * I need to use JDBC to insert data to tables
		 */
		
		userEntityService.insertTerm(userEntity.getId(), origUserEntity.getTerm().getId());
		userEntityService.insertProgramOfStudy(userEntity.getId(), origUserEntity.getProgramOfStudy().getId());
		
		
		
		return "redirect:/myAccount/applyingFor";
	}
	
	
	
	// ======================================
	// =                         =
	// ======================================
	
	/**
	 * Look at AdminController
	 */
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
	 * Look at AdminController
	 */
//	@RequestMapping(value = "/myAccount/applyingFor/edit", method = RequestMethod.POST)
//	public String editApplyingFor(@PathVariable("addressId") Long addressId,
//			@ModelAttribute @Valid Address origAddress, 
//			BindingResult result,
//			Model model) {
//		
//		UserEntity userEntity = getUserEntityFromSecurityContext();
//		Address address = getAddressValidateUserEntityId(userEntity.getId(), addressId);
//
//		if (result.hasErrors()) {
////			log.debug("Validation Error in Institute form");
//			model.addAttribute("originalAddress", origAddress);
//			return "editAddress";
//		}
//
////		log.debug("Message validated; updating message subject and text");
//		address.setAddress1(origAddress.getAddress1());
//		address.setAddress2(origAddress.getAddress2());
//		address.setCity(origAddress.getCity());
//		addressService.updateAddress(address);
//		
//		return "redirect:/myAccount/applyingFor";
//	}
	
	
//	@RequestMapping(value = "/myAccount/applyingFor/{addressId}/delete", method = RequestMethod.POST)
//	public String deleteApplyingFor(@PathVariable("addressId") Long addressId)
//			throws IOException {
//		UserEntity userEntity = getUserEntityFromSecurityContext();
//		addressService.delete(getAddressValidateUserEntityId(userEntity.getId(), addressId));
//		return "redirect:/myAccount/applyingFor";
//	}
	
	
	// ======================================
	// =                         =
	// ======================================
	
	private UserEntity getUserEntityFromSecurityContext() {
		SecurityContext securityCtx = SecurityContextHolder.getContext();
		Authentication auth = securityCtx.getAuthentication();
		UserEntity userEntity =  (UserEntity) auth.getPrincipal();
		return userEntity;
	}
	

}
