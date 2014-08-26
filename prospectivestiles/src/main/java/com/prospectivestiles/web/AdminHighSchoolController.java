package com.prospectivestiles.web;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
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

/**
 * The url to get the form and post the form are the same. 
 * eg. "/accounts/{userEntityId}/xxx/new"
 * Advantage: when a user submit a form with error the post url will be displayed in the url,
 * if the session expires, and post url used to post the form will be displayed in the url,
 * if post and get url are the same then when the user login and refresh the page, 
 * the get method will be called. or the page will crash.
 * @author danielanenia
 *
 */
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
	
	private static final Logger log = LoggerFactory.getLogger(AdminHighSchoolController.class);
	
	/*
	 * Use @InitBinder to fix the following error
	 * Failed to convert property value of type java.lang.String to required type java.util.Date 
	 * for property attendedFrom;
	 * Failed to convert from type java.lang.String to type @javax.validation.constraints.NotNull java.util.Date 
	 */
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
	
	/**
	 * New highSchool form to add highSchool
	 * @param userEntityId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/accounts/{userEntityId}/highSchool/new", method = RequestMethod.GET)
	public String getNewHighSchoolForm(@PathVariable("userEntityId") Long userEntityId,
			Model model) {
		System.out.println("########### 1 getNewHighSchoolForm");
		log.info("######## 1 getNewHighSchoolForm");
		HighSchool highSchool = new HighSchool();
		UserEntity userEntity = userEntityService.getUserEntity(userEntityId);
		
		highSchool.setUserEntity(userEntity);
		
		model.addAttribute(highSchool);
		model.addAttribute(userEntity);
		log.info("######## 1b");
		return "newHighSchoolForm";
	}
	
	@RequestMapping(value = "/accounts/{userEntityId}/highSchool/new", method = RequestMethod.POST)
	public String postNewHighSchoolForm(@PathVariable("userEntityId") Long userEntityId,
			@ModelAttribute @Valid HighSchool highSchool, BindingResult result, Model model) {

		System.out.println("########### 2 postNewHighSchoolForm");
		log.info("######## 2 postNewHighSchoolForm");
		
		UserEntity userEntity = userEntityService.getUserEntity(userEntityId);
		UserEntity currentAdmissionUser = getUserEntityFromSecurityContext();

		if (result.hasErrors()) {
			model.addAttribute(userEntity);
			return "newHighSchoolForm";
		}
		
		highSchool.setUserEntity(userEntity);
		highSchool.setCreatedBy(currentAdmissionUser);
		highSchoolService.createHighSchool(highSchool);
		log.info("######## 2b");
		return "redirect:/accounts/{userEntityId}/educations";
	}
	
	/**
	 * CLASS NOT REQUIRED, REMOVE!! I dont have highschool page.
	 * @param userEntityId
	 * @param highSchoolId
	 * @param model
	 * @return
	 */
	/*@RequestMapping(value = "/accounts/{userEntityId}/highSchool/{highSchoolId}", method = RequestMethod.GET)
	public String getHighSchool(@PathVariable("userEntityId") Long userEntityId,
			@PathVariable("highSchoolId") Long highSchoolId, Model model) {
		
		System.out.println("########### 3 getHighSchool");
		log.info("######## 3 getHighSchool");
		
		UserEntity userEntity = userEntityService.getUserEntity(userEntityId);
		model.addAttribute(getHighSchoolValidateUserEntityId(userEntityId, highSchoolId));
		model.addAttribute(userEntity);
		log.info("######## 3b");
		return "highSchool";
	}*/
	
	@RequestMapping(value = "/accounts/{userEntityId}/highSchool/{highSchoolId}/edit", method = RequestMethod.GET)
	public String editHighSchool(@PathVariable("userEntityId") Long userEntityId,
			@PathVariable("highSchoolId") Long highSchoolId, Model model) {
		
		System.out.println("########### 4 editHighSchool");
		log.info("######## 4 editHighSchool");
		
		UserEntity userEntity = userEntityService.getUserEntity(userEntityId);
		HighSchool highSchool = getHighSchoolValidateUserEntityId(userEntityId, highSchoolId);
		
		model.addAttribute("originalHighSchool", highSchool);
		model.addAttribute(highSchool);
		model.addAttribute(userEntity);
		log.info("######## 4b");
		return "editHighSchool";
	}
	
	@RequestMapping(value = "/accounts/{userEntityId}/highSchool/{highSchoolId}/edit", method = RequestMethod.POST)
	public String editHighSchool(@PathVariable("userEntityId") Long userEntityId,
			@PathVariable("highSchoolId") Long highSchoolId,
			@ModelAttribute @Valid HighSchool origHighSchool, 
			BindingResult result,
			Model model) {
		
		System.out.println("########### 5 editHighSchool");
		log.info("######## 5 editHighSchool");
		
		UserEntity userEntity = userEntityService.getUserEntity(userEntityId);
		HighSchool highSchool = getHighSchoolValidateUserEntityId(userEntityId, highSchoolId);
		UserEntity currentAdmissionUser = getUserEntityFromSecurityContext();

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
		highSchool.setLastModifiedBy(currentAdmissionUser);
		
		highSchoolService.updateHighSchool(highSchool);
		
//		return "/accounts/{userEntityId}/highSchool/{highSchoolId}/edit.html?saved=true";
//		return "accounts/editHighSchoolSaved";
//		return "highSchool";
		log.info("######## 5b");
		return "redirect:/accounts/{userEntityId}/educations";
	}
	
	/*
	 * Using a Modal to delete High School.
	 * The delete form in the Modal calls this method
	 */
	@RequestMapping(value = "/accounts/{userEntityId}/highSchool/{highSchoolId}/delete", method = RequestMethod.GET)
	public String getDeleteHighSchool(@PathVariable("userEntityId") Long userEntityId,
			@PathVariable("highSchoolId") Long highSchoolId, Model model) {
		
		System.out.println("########### 6 getDeleteHighSchool");
		log.info("######## 6 getDeleteHighSchool");
		
		HighSchool highSchool = getHighSchoolValidateUserEntityId(userEntityId, highSchoolId);
		
		model.addAttribute("originalHighSchool", highSchool);
		model.addAttribute(highSchool);
		log.info("######## 6b");
		return "deleteHighSchool";
	}
	
	@RequestMapping(value = "/accounts/{userEntityId}/highSchool/{highSchoolId}/delete", method = RequestMethod.POST)
	public String deleteHighSchool(@PathVariable("userEntityId") Long userEntityId,
			@PathVariable("highSchoolId") Long highSchoolId)
			throws IOException {
		
		System.out.println("########### 7 deleteHighSchool");
		log.info("######## 7 deleteHighSchool");
		
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

	private UserEntity getUserEntityFromSecurityContext() {
		SecurityContext securityCtx = SecurityContextHolder.getContext();
		Authentication auth = securityCtx.getAuthentication();
		UserEntity userEntity = (UserEntity) auth.getPrincipal();
		return userEntity;
	}
}
