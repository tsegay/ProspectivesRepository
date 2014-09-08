package com.prospectivestiles.web;

import java.io.IOException;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
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
	// =             applyingFor             =
	// ======================================
	
	/**
	 * user can't delete term or program of study but they can change it.
	 * I am using the same method to create and edit the term and program of study to
	 * insert the term and program of study to the userEntity using jdbc
	 * 
	 * 1. Update the term and program of study using the getApplyingFor()
	 * By displaying alternating button labels Add/Update 
	 */
	
	
	@RequestMapping(value = "/accounts/{userEntityId}/applyingFor", method = RequestMethod.GET)
	public String getApplyingFor(@PathVariable("userEntityId") Long userEntityId,
			Model model) {
		
		model.addAttribute("terms", termService.getAllTerms());
		model.addAttribute("programOfStudies", programOfStudyService.getAllProgramOfStudies());
		
		model.addAttribute("userEntity", userEntityService.getUserEntity(userEntityId));
		
		return "applyingFor";
	}
	
	@RequestMapping(value = "/accounts/{userEntityId}/applyingFor", method = RequestMethod.POST)
	public String postNewApplyingForForm(@PathVariable("userEntityId") Long userEntityId,
			@ModelAttribute UserEntity origUserEntity, BindingResult result) {
		
		if (result.hasErrors()) {
			// view doesn't exist
			return "newApplyingForForm";
		}

//		userEntityService.updateTerm(userEntityId, origUserEntity.getTerm().getId());
//		userEntityService.updateProgramOfStudy(userEntityId, origUserEntity.getProgramOfStudy().getId());
		
		userEntityService.updateUserEntityTermAndProgram(userEntityId, 
				origUserEntity.getTerm().getId(), origUserEntity.getProgramOfStudy().getId());
		
		/**
		 * I am dropping the applyingFor page. merging that page in the account page
		 */
		return "redirect:/accounts/{userEntityId}";
//		return "redirect:/accounts/{userEntityId}/applyingFor";
	}
	
	
	
	
	/**
	 * DO NOT Need Delete option to remove term and program of study that a student is applying for.
	 * But to track whether students who applied actually enrolled in classes, 
	 * I have to create a field enrolled. After a students application is complete and student is admittted.
	 * then when student get enrolled in class, insert value to the enrolled field.
	 */
	
	
	// ======================================
	// =                        =
	// ======================================
	
	private UserEntity getUserEntityFromSecurityContext() {
		SecurityContext securityCtx = SecurityContextHolder.getContext();
		Authentication auth = securityCtx.getAuthentication();
		UserEntity userEntity = (UserEntity) auth.getPrincipal();
		return userEntity;
	}
}
