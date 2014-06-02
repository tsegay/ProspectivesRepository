package com.prospectivestiles.web;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.prospectivestiles.domain.UserEntity;
import com.prospectivestiles.service.HighSchoolService;
import com.prospectivestiles.service.InstituteService;
import com.prospectivestiles.service.UserEntityService;

@Controller
public class AdminEducationController {
	
	@Inject
	private InstituteService instituteService;
	
	@Inject
	private HighSchoolService highSchoolService;
	
	@Autowired
	private UserEntityService userEntityService;
	
	// ======================================
	// =             myAccount/educations for highSchool and Institute             =
	// ======================================	
	
	@RequestMapping(value = "/accounts/{userEntityId}/educations", method = RequestMethod.GET)
	public String getEducations(@PathVariable("userEntityId") Long userEntityId, Model model) {
//		System.out.println("############ getEducations");
		UserEntity userEntity = userEntityService.getUserEntity(userEntityId);
		
		model.addAttribute("institutes", instituteService.getInstitutesByUserEntityId(userEntity.getId()));
		model.addAttribute("highSchools", highSchoolService.getHighSchoolsByUserEntityId(userEntity.getId()));
		model.addAttribute("userEntity", userEntity);
		
		/*
		 * I am loading too many entities in the model. What is the limit???
		 */
		/*Institute institute = new Institute();
		institute.setUserEntity(userEntity);
		model.addAttribute("institute", institute);*/
		
		/*HighSchool highSchool = new HighSchool();
		highSchool.setUserEntity(userEntity);
		model.addAttribute("highSchool", highSchool);*/
		
		return "educations";
	}
		
	
	// ======================================
	// =                        =
	// ======================================	
	
	

}
