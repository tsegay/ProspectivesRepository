package com.prospectivestiles.web;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.prospectivestiles.domain.Address;
import com.prospectivestiles.domain.HighSchool;
import com.prospectivestiles.domain.UserEntity;
import com.prospectivestiles.service.HighSchoolService;
import com.prospectivestiles.service.UserEntityService;

@Controller
public class AdminHighSchoolController {
	
	/*
	 * Instead of loading the userEntity, if there is highSchool should get userEntity in view
	 * using highSchool.userEntity.id
	 * If there is no highSchool passed to the view, then add it to model
	 */
	/*
	 Would normally set Location header and HTTP status 201, but we're
	 using the redirect-after-post pattern, which uses the Location header
	 and status code for redirection.
	 return "redirect:/accounts/" + userEntityId + ".html";
	
	 Should I get userEntity from the URL param or session???
	 URL >>>>> if logged in as admin
	 Session >>>>>>> if logged in as student
	 
	SecurityContext securityCtx = SecurityContextHolder.getContext();
	Authentication auth = securityCtx.getAuthentication();
	institute.setUserEntity((UserEntity) auth.getPrincipal());
	*/
	
	@Inject
	private HighSchoolService highSchoolService;
	
	@Autowired
	private UserEntityService userEntityService;
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
	    SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
	    dateFormat.setLenient(false);

	    // true passed to CustomDateEditor constructor means convert empty String to null
	    binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}
	
	/*@InitBinder("message")
	public void initBinder(WebDataBinder binder) {
		binder.setAllowedFields(new String[] { "subject", "text" });
	}*/
	
	// ======================================
	// =             highSchools             =
	// ======================================
	
	/*
	 * I don't think i am using this method anymore. I am using educations...
	 * I removed the highSchools page
	 */
	/*@RequestMapping(value = "/accounts/{userEntityId}/highSchools", method = RequestMethod.GET)
	public String getHighSchools(@PathVariable("userEntityId") Long userEntityId,
			Model model) {
		
		model.addAttribute("highSchools", highSchoolService.getHighSchoolsByUserEntityId(userEntityId));
		model.addAttribute("userEntity", userEntityService.getUserEntity(userEntityId));
		return "highSchools";
//		return "redirect:/accounts/{userEntityId}/educations";
	}*/
	
	
	/**
	 * New highSchool form to add highSchool
	 * @param userEntityId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/accounts/{userEntityId}/highSchool/new", method = RequestMethod.GET)
	public String getNewHighSchoolForm(@PathVariable("userEntityId") Long userEntityId,
			Model model) {
//		System.out.println("########### getNewHighSchoolForm");
		HighSchool highSchool = new HighSchool();
		UserEntity userEntity = userEntityService.getUserEntity(userEntityId);
		
		highSchool.setUserEntity(userEntity);
		
		model.addAttribute(highSchool);
		model.addAttribute(userEntity);
		
		return "newHighSchoolForm";
	}
	
	@RequestMapping(value = "/accounts/{userEntityId}/highSchools", method = RequestMethod.POST)
	public String postNewHighSchoolForm(@PathVariable("userEntityId") Long userEntityId,
			@ModelAttribute @Valid HighSchool highSchool, BindingResult result, Model model) {

		UserEntity userEntity = userEntityService.getUserEntity(userEntityId);
		
		if (result.hasErrors()) {
			model.addAttribute(userEntity);
			return "newHighSchoolForm";
		}
		
		highSchool.setUserEntity(userEntity);
		highSchoolService.createHighSchool(highSchool);
		return "redirect:/accounts/{userEntityId}/educations";
	}
	
	@RequestMapping(value = "/accounts/{userEntityId}/highSchool/{highSchoolId}", method = RequestMethod.GET)
	public String getHighSchool(@PathVariable("userEntityId") Long userEntityId,
			@PathVariable("highSchoolId") Long highSchoolId, Model model) {
		
		UserEntity userEntity = userEntityService.getUserEntity(userEntityId);
		model.addAttribute(getHighSchoolValidateUserEntityId(userEntityId, highSchoolId));
		model.addAttribute(userEntity);

		return "highSchool";
	}
	
	@RequestMapping(value = "/accounts/{userEntityId}/highSchool/{highSchoolId}/edit", method = RequestMethod.GET)
	public String editHighSchool(@PathVariable("userEntityId") Long userEntityId,
			@PathVariable("highSchoolId") Long highSchoolId, Model model) {
		UserEntity userEntity = userEntityService.getUserEntity(userEntityId);
		HighSchool highSchool = getHighSchoolValidateUserEntityId(userEntityId, highSchoolId);
		
		model.addAttribute("originalHighSchool", highSchool);
		model.addAttribute(highSchool);
		model.addAttribute(userEntity);
		
		return "editHighSchool";
	}
	
	@RequestMapping(value = "/accounts/{userEntityId}/highSchool/{highSchoolId}", method = RequestMethod.POST)
	public String editHighSchool(@PathVariable("userEntityId") Long userEntityId,
			@PathVariable("highSchoolId") Long highSchoolId,
			@ModelAttribute @Valid HighSchool origHighSchool, 
			BindingResult result,
			Model model) {
		UserEntity userEntity = userEntityService.getUserEntity(userEntityId);
		HighSchool highSchool = getHighSchoolValidateUserEntityId(userEntityId, highSchoolId);

		if (result.hasErrors()) {
//			log.debug("Validation Error in HighSchool form");
			origHighSchool.setId(highSchoolId);
			origHighSchool.setUserEntity(userEntityService.getUserEntity(userEntityId));
			model.addAttribute("highSchool", origHighSchool);
//			model.addAttribute("originalHighSchool", origHighSchool);
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
		return "redirect:/accounts/{userEntityId}/educations";
	}
	
	/*
	 * Using a Modal to delete High School.
	 * The delete form in the Modal calls this method
	 */
	@RequestMapping(value = "/accounts/{userEntityId}/highSchool/{highSchoolId}/delete", method = RequestMethod.GET)
	public String getDeleteHighSchool(@PathVariable("userEntityId") Long userEntityId,
			@PathVariable("highSchoolId") Long highSchoolId, Model model) {
		
		HighSchool highSchool = getHighSchoolValidateUserEntityId(userEntityId, highSchoolId);
		
		model.addAttribute("originalHighSchool", highSchool);
		model.addAttribute(highSchool);
		
		return "deleteHighSchool";
	}
	
	@RequestMapping(value = "/accounts/{userEntityId}/highSchool/{highSchoolId}/delete", method = RequestMethod.POST)
	public String deleteHighSchool(@PathVariable("userEntityId") Long userEntityId,
			@PathVariable("highSchoolId") Long highSchoolId)
			throws IOException {
		highSchoolService.delete(getHighSchoolValidateUserEntityId(userEntityId, highSchoolId));
//		return "deleteHighSchool";
//		return getRedirectToForumPath(forumId) + ".html?deleted=true";
		return "redirect:/accounts/{userEntityId}/educations";
	}
	
	// ======================================
	// =                        =
	// ======================================	
		
	
	private HighSchool getHighSchoolValidateUserEntityId(Long userEntityId, Long highschoolId) {
		HighSchool highSchool = highSchoolService.getHighSchool(highschoolId);
//		UserEntity userEntity = userEntityService.getUserEntity(userEntityId);
		
		// Use userEntityId instead of userEntity.getId(),  no need to generate it when it is already passed!!
//		Assert.isTrue(userEntity.getId().equals(highSchool.getUserEntity().getId()), "HighSchool Id mismatch");
		Assert.isTrue(userEntityId.equals(highSchool.getUserEntity().getId()), "HighSchool Id mismatch");
		return highSchool;
	}

}
