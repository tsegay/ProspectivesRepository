package com.prospectivestiles.web;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

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

import com.prospectivestiles.domain.HighSchool;
import com.prospectivestiles.domain.Institute;
import com.prospectivestiles.domain.UserEntity;
import com.prospectivestiles.service.HighSchoolService;
import com.prospectivestiles.service.InstituteService;
import com.prospectivestiles.service.UserEntityService;

@Controller
public class AdminInstituteController {
	
	@Inject
	private InstituteService instituteService;
	
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
	// =             accounts institutes             =
	// ======================================
	/*
	 * I think this is not needed anymore. don't have institutes page
	 */
	/*@RequestMapping(value = "/accounts/{userEntityId}/institutes", method = RequestMethod.GET)
	public String getInstitutes(@PathVariable("userEntityId") Long userEntityId,
			Model model) {
		
		model.addAttribute("institutes", instituteService.getInstitutesByUserEntityId(userEntityId));
		model.addAttribute("userEntity", userEntityService.getUserEntity(userEntityId));
//		return "institutes";
		return "redirect:/accounts/{userEntityId}/educations";
	}
	*/
	
	
	// ======================================
	// =                        =
	// ======================================
	
	@RequestMapping(value = "/accounts/{userEntityId}/institute/new", method = RequestMethod.GET)
	public String getNewInstituteForm(@PathVariable("userEntityId") Long userEntityId,
			Model model) {
		Institute institute = new Institute();
		institute.setUserEntity(userEntityService.getUserEntity(userEntityId));
		model.addAttribute(institute);
		
		return "newInstituteForm";
	}
	
	@RequestMapping(value = "/accounts/{userEntityId}/institutes", method = RequestMethod.POST)
	public String postNewInstituteForm(@PathVariable("userEntityId") Long userEntityId,
			@ModelAttribute @Valid Institute institute, BindingResult result, Model model) {

		UserEntity userEntity = userEntityService.getUserEntity(userEntityId);
				
		if (result.hasErrors()) {
//			model.addAttribute(userEntity);
			model.addAttribute(institute);
			return "newInstituteForm";
		}

		institute.setUserEntity(userEntityService.getUserEntity(userEntityId));
		instituteService.createInstitute(institute);

		return "redirect:/accounts/{userEntityId}/educations";
	}
	
	@RequestMapping(value = "/accounts/{userEntityId}/institute/{instituteId}", method = RequestMethod.GET)
	public String getInstitute(@PathVariable("userEntityId") Long userEntityId,
			@PathVariable("instituteId") Long instituteId, Model model) {
		
		model.addAttribute(getInstituteValidateUserEntityId(userEntityId, instituteId));

		return "institute";
	}
	
	@RequestMapping(value = "/accounts/{userEntityId}/institute/{instituteId}/edit", method = RequestMethod.GET)
	public String editInstitute(@PathVariable("userEntityId") Long userEntityId,
			@PathVariable("instituteId") Long instituteId, Model model) {
		Institute institute = getInstituteValidateUserEntityId(userEntityId, instituteId);
		
		model.addAttribute("originalInstitute", institute);
		model.addAttribute(institute);
		
		return "editInstitute";
	}
	
	@RequestMapping(value = "/accounts/{userEntityId}/institute/{instituteId}", method = RequestMethod.POST)
	public String editInstitute(@PathVariable("userEntityId") Long userEntityId,
			@PathVariable("instituteId") Long instituteId,
			@ModelAttribute @Valid Institute origInstitute, 
			BindingResult result,
			Model model) {
		
		Institute institute = getInstituteValidateUserEntityId(userEntityId, instituteId);

		if (result.hasErrors()) {
//			log.debug("Validation Error in Institute form");
			model.addAttribute("originalInstitute", origInstitute);
			return "editInstitute";
//			return "accounts/editInstituteFail";
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
		
		instituteService.updateInstitute(institute);
		
//		return "/accounts/{userEntityId}/institute/{instituteId}/edit.html?saved=true";
//		return "accounts/editInstituteSaved";
//		return "institute";
		return "redirect:/accounts/{userEntityId}/educations";
	}
	
	/*
	 * Using a Modal to delete High School.
	 * The delete form in the Modal calls this method
	 */
	@RequestMapping(value = "/accounts/{userEntityId}/institute/{instituteId}/delete", method = RequestMethod.GET)
	public String getDeleteAddress(@PathVariable("userEntityId") Long userEntityId,
			@PathVariable("instituteId") Long instituteId, Model model) {
		
		Institute institute = getInstituteValidateUserEntityId(userEntityId, instituteId);
		
		model.addAttribute("originalInstitute", institute);
		model.addAttribute(institute);
		
		return "deleteInstitute";
	}
	@RequestMapping(value = "/accounts/{userEntityId}/institute/{instituteId}/delete", method = RequestMethod.POST)
	public String deleteInstitute(@PathVariable("userEntityId") Long userEntityId,
			@PathVariable("instituteId") Long instituteId)
			throws IOException {
		instituteService.delete(getInstituteValidateUserEntityId(userEntityId, instituteId));
//		return "deleteInstitute";
//		return getRedirectToForumPath(forumId) + ".html?deleted=true";
		return "redirect:/accounts/{userEntityId}/educations";
	}
	
	
	// ======================================
	// =                        =
	// ======================================	
	
	
	private Institute getInstituteValidateUserEntityId(Long userEntityId, Long instituteId) {
		Institute institute = instituteService.getInstitute(instituteId);
		UserEntity userEntity = userEntityService.getUserEntity(userEntityId);
		
		// Use userEntityId instead of userEntity.getId(),  no need to generate it when it is already passed!!
		Assert.isTrue(userEntity.getId().equals(institute.getUserEntity().getId()), "Institute Id mismatch");
		return institute;
	}

}
