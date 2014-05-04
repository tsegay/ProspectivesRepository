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

import com.prospectivestiles.domain.HighSchool;
import com.prospectivestiles.domain.UserEntity;
import com.prospectivestiles.service.HighSchoolService;
import com.prospectivestiles.service.UserEntityService;

@Controller
public class StudentHighSchoolController {
	/*
	 * Instead of loading the userEntity, if there is highSchool should get userEntity in view
	 * using highSchool.userEntity.id
	 * If there is no highSchool passed to the view, then add it to model
	 * Or pass the userEntity.id only
	 */
	
	@Inject
	private HighSchoolService highSchoolService;
	
	@Autowired
	private UserEntityService userEntityService;
	
	
	// ======================================
	// =             myAccount hghSchools            =
	// ======================================
	
	/*@RequestMapping(value = "/myAccount/highSchools", method = RequestMethod.GET)
	public String getHighSchools(Model model) {
		
		UserEntity userEntity = getUserEntityFromSecurityContext();
		
		model.addAttribute("highSchools", highSchoolService.getHighSchoolsByUserEntityId(userEntity.getId()));
		model.addAttribute("userEntity", userEntity);
		
		return "highSchools";
	}
	*/
	
	@RequestMapping(value = "/myAccount/highSchool/new", method = RequestMethod.GET)
	public String getNewHighSchoolForm(Model model) {
		UserEntity userEntity = getUserEntityFromSecurityContext();
		
		HighSchool highSchool = new HighSchool();
		highSchool.setUserEntity(userEntity);
		model.addAttribute(highSchool);
		model.addAttribute(userEntity);
		
		return "newHighSchoolForm";
	}
	
	@RequestMapping(value = "/myAccount/highSchools", method = RequestMethod.POST)
	public String postNewHighSchoolForm(@ModelAttribute @Valid HighSchool highSchool, BindingResult result, Model model) {

		UserEntity userEntity = getUserEntityFromSecurityContext();
		
		if (result.hasErrors()) {
			model.addAttribute(userEntity);
			return "newHighSchoolForm";
		}

		highSchool.setUserEntity(userEntity);
		highSchoolService.createHighSchool(highSchool);
		return "redirect:/myAccount/educations";
	}
	
	@RequestMapping(value = "/myAccount/highSchool/{highSchoolId}", method = RequestMethod.GET)
	public String getHighSchool(@PathVariable("highSchoolId") Long highSchoolId, Model model) {
		
		UserEntity userEntity = getUserEntityFromSecurityContext();		
		model.addAttribute(getHighSchoolValidateUserEntityId(userEntity.getId(), highSchoolId));
		model.addAttribute(userEntity);
		return "highSchool";
	}
	
	@RequestMapping(value = "/myAccount/highSchool/{highSchoolId}/edit", method = RequestMethod.GET)
	public String editHighSchool(@PathVariable("highSchoolId") Long highSchoolId, Model model) {
		UserEntity userEntity = getUserEntityFromSecurityContext();	
		HighSchool highSchool = getHighSchoolValidateUserEntityId(userEntity.getId(), highSchoolId);
		
		model.addAttribute("originalHighSchool", highSchool);
		model.addAttribute(highSchool);
		
		return "editHighSchool";
	}
	
	@RequestMapping(value = "/myAccount/highSchool/{highSchoolId}", method = RequestMethod.POST)
	public String editHighSchool(@PathVariable("highSchoolId") Long highSchoolId,
			@ModelAttribute @Valid HighSchool origHighSchool, 
			BindingResult result,
			Model model) {
		
		UserEntity userEntity = getUserEntityFromSecurityContext();
		HighSchool highSchool = getHighSchoolValidateUserEntityId(userEntity.getId(), highSchoolId);

		if (result.hasErrors()) {
//			log.debug("Validation Error in HighSchool form");
			model.addAttribute("originalHighSchool", origHighSchool);
			model.addAttribute(userEntity);
			return "editHighSchool";
//			return "accounts/editHighSchoolFail";
		}

//		log.debug("Message validated; updating message subject and text");
		highSchool.setAttendedFrom(origHighSchool.getAttendedFrom());
		highSchool.setAttendedTo(origHighSchool.getAttendedTo());
		highSchool.setCity(origHighSchool.getCity());
		highSchool.setCountry(origHighSchool.getCountry());
		highSchool.setDiplome(origHighSchool.isDiplome());
		highSchool.setgED(origHighSchool.isgED());
		highSchool.setName(origHighSchool.getName());
		highSchool.setState(origHighSchool.getState());
		highSchool.setZip(origHighSchool.getZip());
		highSchool.setDiplomeAwardedDate(origHighSchool.getDiplomeAwardedDate());
		highSchool.setgEDAwardedDate(origHighSchool.getgEDAwardedDate());
		
		highSchoolService.updateHighSchool(highSchool);
		

//		return "/accounts/{userEntityId}/highSchool/{highSchoolId}/edit.html?saved=true";
//		return "accounts/editHighSchoolSaved";
//		return "highSchool";
		return "redirect:/myAccount/educations";
	}
	
	@RequestMapping(value = "/myAccount/highSchool/{highSchoolId}/delete", method = RequestMethod.POST)
	public String deleteHighSchool(@PathVariable("highSchoolId") Long highSchoolId)
			throws IOException {
		UserEntity userEntity = getUserEntityFromSecurityContext();
		highSchoolService.delete(getHighSchoolValidateUserEntityId(userEntity.getId(), highSchoolId));
//		return "deleteHighSchool";
//		return getRedirectToForumPath(forumId) + ".html?deleted=true";
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
	
	private HighSchool getHighSchoolValidateUserEntityId(Long userEntityId, Long highschoolId) {
		HighSchool highSchool = highSchoolService.getHighSchool(highschoolId);
		Assert.isTrue(userEntityId.equals(highSchool.getUserEntity().getId()), "HighSchool Id mismatch");
		return highSchool;
	}

}
