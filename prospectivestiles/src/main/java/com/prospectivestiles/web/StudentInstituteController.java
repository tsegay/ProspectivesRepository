package com.prospectivestiles.web;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.inject.Inject;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
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

import com.prospectivestiles.domain.HighSchool;
import com.prospectivestiles.domain.Institute;
import com.prospectivestiles.domain.UserEntity;
import com.prospectivestiles.service.CountryService;
import com.prospectivestiles.service.HighSchoolService;
import com.prospectivestiles.service.InstituteService;
import com.prospectivestiles.service.UserEntityService;

@Controller
public class StudentInstituteController {
	
	/*
	 * Instead of loading the userEntity, if there is highSchool should get userEntity in view
	 * using highSchool.userEntity.id
	 * If there is no highSchool passed to the view, then add it to model
	 */
	/*
	 * get userEntity from URL >>>>> if logged in as admin
	 * get userEntity from Session >>>>>>> if logged in as student
	 */
	
	@Inject
	private InstituteService instituteService;
	
	@Inject
	private HighSchoolService highSchoolService;
	
	@Autowired
	private UserEntityService userEntityService;
	
	@Inject
	private CountryService countryService;
	
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
	
	// ======================================
	// =             myAccount institutes             =
	// ======================================
	
	/*
	 * I think this is not needed anymore. don't have institutes page
	 */
	/*@RequestMapping(value = "/myAccount/institutes", method = RequestMethod.GET)
	public String getInstitutes(Model model) {
		
		UserEntity userEntity = getUserEntityFromSecurityContext();
		
		model.addAttribute("institutes", instituteService.getInstitutesByUserEntityId(userEntity.getId()));
		model.addAttribute("userEntity", userEntity);
		
		return "institutes";
	}
	*/
	
	
	// ======================================
	// =                         =
	// ======================================
	
	@RequestMapping(value = "/myAccount/institute/new", method = RequestMethod.GET)
	public String getNewInstituteForm(Model model) {
		UserEntity userEntity = getUserEntityFromSecurityContext();
		
		Institute institute = new Institute();
		institute.setUserEntity(userEntity);
		model.addAttribute(institute);
		model.addAttribute("countries", countryService.getAllCountries());
		
		return "newInstituteForm";
	}
	
	
	@RequestMapping(value = "/myAccount/institute/new", method = RequestMethod.POST)
	public String postNewInstituteForm(@ModelAttribute @Valid Institute institute, 
			BindingResult result, Model model) {
		UserEntity userEntity = getUserEntityFromSecurityContext();
		
		if (result.hasErrors()) {
			model.addAttribute(userEntity);
			model.addAttribute("countries", countryService.getAllCountries());
			return "newInstituteForm";
		}


		institute.setUserEntity(userEntity);
		institute.setCreatedBy(userEntity);
		instituteService.createInstitute(institute);

		return "redirect:/myAccount/educations";
	}
	
	/*@RequestMapping(value = "/myAccount/institute/{instituteId}", method = RequestMethod.GET)
	public String getInstitute(@PathVariable("instituteId") Long instituteId, Model model) {
		
		UserEntity userEntity = getUserEntityFromSecurityContext();		
		model.addAttribute(getInstituteValidateUserEntityId(userEntity.getId(), instituteId));
		return "institute";
	}*/
	
	@RequestMapping(value = "/myAccount/institute/{instituteId}/edit", method = RequestMethod.GET)
	public String editInstitute(@PathVariable("instituteId") Long instituteId, Model model) {
		UserEntity userEntity = getUserEntityFromSecurityContext();	
		Institute institute = getInstituteValidateUserEntityId(userEntity.getId(), instituteId);
		
		model.addAttribute("originalInstitute", institute);
		model.addAttribute(institute);
		model.addAttribute("countries", countryService.getAllCountries());
		
		return "editInstitute";
	}
	
	@RequestMapping(value = "/myAccount/institute/{instituteId}/edit", method = RequestMethod.POST)
	public String editInstitute(@PathVariable("instituteId") Long instituteId,
			@ModelAttribute @Valid Institute origInstitute, 
			BindingResult result,
			Model model) {
		
		UserEntity userEntity = getUserEntityFromSecurityContext();
		Institute institute = getInstituteValidateUserEntityId(userEntity.getId(), instituteId);

		if (result.hasErrors()) {
//			log.debug("Validation Error in Institute form");
			origInstitute.setId(instituteId);
			origInstitute.setUserEntity(userEntity);
			model.addAttribute(userEntity);
			model.addAttribute("institute", origInstitute);
			model.addAttribute("countries", countryService.getAllCountries());
//			model.addAttribute("originalInstitute", origInstitute);
			return "editInstitute";
//			return "accounts/editHighSchoolFail";
		}

//		log.debug("Message validated; updating message subject and text");
		institute.setName(origInstitute.getName());
		institute.setState(origInstitute.getState());
		institute.setCountry(origInstitute.getCountry());
		institute.setAttendedFrom(origInstitute.getAttendedFrom());
		institute.setAttendedTo(origInstitute.getAttendedTo());
		institute.setCity(origInstitute.getCity());
		institute.setGraduationDate(origInstitute.getGraduationDate());
		institute.setLevelOfStudy(origInstitute.getLevelOfStudy());
		institute.setProgramOfStudy(origInstitute.getProgramOfStudy());
		institute.setZip(origInstitute.getZip());
		institute.setLastModifiedBy(userEntity);
		
		instituteService.updateInstitute(institute);
		

//		return "/accounts/{userEntityId}/highSchool/{highSchoolId}/edit.html?saved=true";
//		return "accounts/editHighSchoolSaved";
//		return "institute";
		return "redirect:/myAccount/educations";
	}
	

	
	/*
	 * Using a Modal to delete High School.
	 * The delete form in the Modal calls this method
	 */
	@RequestMapping(value = "/myAccount/institute/{instituteId}/delete", method = RequestMethod.GET)
	public String getDeleteInstitute(@PathVariable("instituteId") Long instituteId, Model model) {
		
		UserEntity userEntity = getUserEntityFromSecurityContext();	
		Institute institute = getInstituteValidateUserEntityId(userEntity.getId(), instituteId);
		
		model.addAttribute("originalInstitute", institute);
		model.addAttribute(institute);
		
		return "deleteInstitute";
	}
	
	@RequestMapping(value = "/myAccount/institute/{instituteId}/delete", method = RequestMethod.POST)
	public String deleteInstitute(@PathVariable("instituteId") Long instituteId)
			throws IOException {
		UserEntity userEntity = getUserEntityFromSecurityContext();
		instituteService.delete(getInstituteValidateUserEntityId(userEntity.getId(), instituteId));
//		return "deleteInstitute";
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
	
	private Institute getInstituteValidateUserEntityId(Long userEntityId, Long instituteId) {
		Institute institute = instituteService.getInstitute(instituteId);
		UserEntity userEntity = userEntityService.getUserEntity(userEntityId);
		
		// Use userEntityId instead of userEntity.getId(),  no need to generate it when it is already passed!!
		Assert.isTrue(userEntity.getId().equals(institute.getUserEntity().getId()), "Institute Id mismatch");
		return institute;
	}

}
