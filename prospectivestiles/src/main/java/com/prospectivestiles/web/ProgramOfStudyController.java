package com.prospectivestiles.web;

import java.io.IOException;
import java.util.Collection;

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

import com.prospectivestiles.domain.ProgramOfStudy;
import com.prospectivestiles.domain.UserEntity;
import com.prospectivestiles.service.ProgramOfStudyService;
import com.prospectivestiles.service.UserEntityService;

@Controller
public class ProgramOfStudyController {
	
	
	@Inject
	private ProgramOfStudyService programOfStudyService;
	
	@Autowired
	private UserEntityService userEntityService;
	
	
	// ======================================
	// =             myAccount programOfStudies             =
	// ======================================	

//	@RequestMapping(value = "/myAccount/programOfStudies", method = RequestMethod.GET)
	@RequestMapping(value = "/programOfStudies", method = RequestMethod.GET)
	public String getProgramOfStudies(Model model) {
		
//		UserEntity userEntity = getUserEntityFromSecurityContext();
		
//		model.addAttribute("programOfStudies", programOfStudyService.getProgramOfStudiesByUserEntityId(userEntity.getId()));
		model.addAttribute("programOfStudies", programOfStudyService.getAllProgramOfStudies());
		
		ProgramOfStudy programOfStudy = new ProgramOfStudy();
		/**
		 * ProgramOfStudy has multiple users, so add the user to the collection
		 */
		/*programOfStudy.setUserEntity(userEntity);*/
//		Collection<UserEntity> userEntitiesList = programOfStudy.getListOfUserEntity();
//		userEntitiesList.add(userEntity);
		
		model.addAttribute("programOfStudy", programOfStudy);
		
//		model.addAttribute("userEntity", userEntity);
		
		return "programOfStudies";
	}
	
//	@RequestMapping(value = "/myAccount/programOfStudies", method = RequestMethod.POST)
	@RequestMapping(value = "/programOfStudies", method = RequestMethod.POST)
	public String postNewProgramOfStudyForm(@ModelAttribute @Valid ProgramOfStudy programOfStudy, BindingResult result) {
		
		System.out.println("################### IN StudentProgramOfStudyController.postNewProgramOfStudyForm.");

//		System.out.println("######## postNewProgramOfStudyForm() Called #####");
//		System.out.println("######## getProgramOfStudy1: " + programOfStudy.getProgramOfStudy1());
//		System.out.println("######## getProgramOfStudy2: " + programOfStudy.getProgramOfStudy2());
//		System.out.println("######## getCity: " + programOfStudy.getCity());
//		
//		if (result != null) {
//			System.out.println("######## Error in: " + result.toString());
//		}
		
		/**
		 * There is no newProgramOfStudyForm page -- check this out
		 */
		if (result.hasErrors()) {
			System.out.println("######## StudentProgramOfStudyController.postNewProgramOfStudyForm result.hasErrors(): true" );
			/**
			 * Work on displaying spring err msg in Modal.
			 */
			return "redirect:/programOfStudies";
//			return "newProgramOfStudyForm";
		} else {
			System.out.println("######## StudentProgramOfStudyController.postNewProgramOfStudyForm result.hasErrors(): false" );
		}
		

//		UserEntity userEntity = getUserEntityFromSecurityContext();
		/**
		 * ProgramOfStudy has multiple users, so add the user to the collection
		 */
//		programOfStudy.setUserEntity(userEntity);
//		Collection<UserEntity> userEntitiesList = programOfStudy.getListOfUserEntity();
//		userEntitiesList.add(userEntity);
		
		programOfStudyService.createProgramOfStudy(programOfStudy);
		
		return "redirect:/programOfStudies";
	}
	
	
	
	// ======================================
	// =                         =
	// ======================================
	
	@RequestMapping(value = "/programOfStudy/{programOfStudyId}", method = RequestMethod.GET)
	public String editProgramOfStudy(@PathVariable("programOfStudyId") Long programOfStudyId, Model model) {
//		UserEntity userEntity = getUserEntityFromSecurityContext();	
//		ProgramOfStudy programOfStudy = getProgramOfStudyValidateUserEntityId(userEntity.getId(), programOfStudyId);
		
		ProgramOfStudy programOfStudy = programOfStudyService.getProgramOfStudy(programOfStudyId);
		
		model.addAttribute("originalProgramOfStudy", programOfStudy);
		model.addAttribute(programOfStudy);
		
		return "editProgramOfStudy";
	}
	
	@RequestMapping(value = "/programOfStudy/{programOfStudyId}", method = RequestMethod.POST)
	public String editProgramOfStudy(@PathVariable("programOfStudyId") Long programOfStudyId,
			@ModelAttribute @Valid ProgramOfStudy origProgramOfStudy, 
			BindingResult result,
			Model model) {
		
//		UserEntity userEntity = getUserEntityFromSecurityContext();
//		ProgramOfStudy programOfStudy = getProgramOfStudyValidateUserEntityId(userEntity.getId(), programOfStudyId);
		ProgramOfStudy programOfStudy = programOfStudyService.getProgramOfStudy(programOfStudyId);

		if (result.hasErrors()) {
			model.addAttribute("originalProgramOfStudy", origProgramOfStudy);
			return "editProgramOfStudy";
		}

		programOfStudy.setDescription(origProgramOfStudy.getDescription());
		programOfStudy.setName(origProgramOfStudy.getName());
		programOfStudy.setShortName(origProgramOfStudy.getShortName());
		
		programOfStudyService.updateProgramOfStudy(programOfStudy);
		
		return "redirect:/programOfStudies";
	}
	
	
	@RequestMapping(value = "/programOfStudy/{programOfStudyId}/delete", method = RequestMethod.POST)
	public String deleteProgramOfStudy(@PathVariable("programOfStudyId") Long programOfStudyId)
			throws IOException {
//		UserEntity userEntity = getUserEntityFromSecurityContext();
//		programOfStudyService.delete(getProgramOfStudyValidateUserEntityId(userEntity.getId(), programOfStudyId));
		ProgramOfStudy programOfStudy = programOfStudyService.getProgramOfStudy(programOfStudyId);
		programOfStudyService.delete(programOfStudy);
		
		return "redirect:/programOfStudies";
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
	
	private ProgramOfStudy getProgramOfStudyValidateUserEntityId(Long userEntityId, Long programOfStudyId) {
		
		ProgramOfStudy programOfStudy = programOfStudyService.getProgramOfStudy(programOfStudyId);
		UserEntity userEntity = userEntityService.getUserEntity(userEntityId);
		/**
		 * Need to work on retrieving user from the collection
		 */
//		Assert.isTrue(userEntity.getId().equals(programOfStudy.getUserEntity().getId()), "ProgramOfStudy Id mismatch");
		return programOfStudy;
	}

}
