package com.prospectivestiles.web;

import javax.inject.Inject;
import javax.validation.Valid;

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

import com.prospectivestiles.domain.HighSchool;
import com.prospectivestiles.domain.Institute;
import com.prospectivestiles.domain.UserEntity;
import com.prospectivestiles.service.HighSchoolService;
import com.prospectivestiles.service.InstituteService;
import com.prospectivestiles.service.StandardTestService;
import com.prospectivestiles.service.UserEntityService;

@Controller
public class StudentEducationController {
	
	@Inject
	private InstituteService instituteService;
	
	@Inject
	private HighSchoolService highSchoolService;
	
	@Autowired
	private UserEntityService userEntityService;
	
	@Inject
	private StandardTestService standardTestService;
	
	
	// ======================================
	// =             myAccount/educations for highSchool and Institute             =
	// ======================================	
	@RequestMapping(value = "/myAccount/educations", method = RequestMethod.GET)
	public String getEducations(Model model) {
		
		UserEntity userEntity = getUserEntityFromSecurityContext();
		
		model.addAttribute("institutes", instituteService.getInstitutesByUserEntityId(userEntity.getId()));
		model.addAttribute("highSchools", highSchoolService.getHighSchoolsByUserEntityId(userEntity.getId()));
	
		/**
		 * load all standardTests for a user
		 * I am dropping the standardTests page. merging that page in the educations page
		 */
		model.addAttribute("standardTests", standardTestService.getStandardTestsByUserEntityId(userEntity.getId()));
		
		/*
		 * I am loading too many entities in the model. What is the limit???
		 */
//		Institute institute = new Institute();
//		institute.setUserEntity(userEntity);
//		model.addAttribute("institute", institute);
//		
//		HighSchool highSchool = new HighSchool();
//		highSchool.setUserEntity(userEntity);
//		model.addAttribute("highSchool", highSchool);
		
		model.addAttribute("userEntity", userEntity);
		
		return "educations";
	}
	
	/*
	 * Not in use now
	 * for testing two modals in a page
	 */
	@RequestMapping(value = "/myAccount/educations", method = RequestMethod.POST)
	public String postNewEducationsForm(@ModelAttribute @Valid Institute institute, BindingResult result) {

		if (result.hasErrors()) {
			return "newInstituteForm";
		}

		UserEntity userEntity = getUserEntityFromSecurityContext();
		
		institute.setUserEntity(userEntity);
		institute.setCreatedBy(userEntity);
		instituteService.createInstitute(institute);

		// Would normally set Location header and HTTP status 201, but we're
		// using the redirect-after-post pattern, which uses the Location header
		// and status code for redirection.
//			return "redirect:/accounts/" + userEntityId + ".html";
//			return "institutes";
		return "redirect:/myAccount/educations";
	}
	
	/*
	 * Not in use now
	 * for testing two modals in a page
	 */
	@RequestMapping(value = "/myAccount/educations/highSchools", method = RequestMethod.POST)
	public String postNewEducationHighSchoolForm(@ModelAttribute @Valid HighSchool highSchool, BindingResult result) {

		if (result.hasErrors()) {
			return "newHighSchoolForm";
		}

		UserEntity userEntity = getUserEntityFromSecurityContext();
		highSchool.setUserEntity(userEntity);
		highSchool.setCreatedBy(userEntity);
		highSchoolService.createHighSchool(highSchool);

		return "redirect:/myAccount/educations";
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
}
