package com.prospectivestiles.web;

import java.io.IOException;
import java.util.List;

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

import com.prospectivestiles.domain.Address;
import com.prospectivestiles.domain.AgreementName;
import com.prospectivestiles.domain.EmergencyContact;
import com.prospectivestiles.domain.HighSchool;
import com.prospectivestiles.domain.Institute;
import com.prospectivestiles.domain.StandardTest;
import com.prospectivestiles.domain.StudentAgreement;
import com.prospectivestiles.domain.UserEntity;
import com.prospectivestiles.service.AddressService;
import com.prospectivestiles.service.HighSchoolService;
import com.prospectivestiles.service.InstituteService;
import com.prospectivestiles.service.StandardTestService;
import com.prospectivestiles.service.StudentAgreementService;
import com.prospectivestiles.service.UserEntityService;

@Controller
public class StudentAgreementController {

	@Inject
	private StudentAgreementService studentAgreementService;
	@Autowired
	private UserEntityService userEntityService;
	@Inject
	private AddressService addressService;
	@Inject
	private HighSchoolService highSchoolService;
	@Inject
	private InstituteService instituteService;
	@Inject
	private StandardTestService standardTestService;

	// ======================================
	// =  =
	// ======================================

	@RequestMapping(value = "/myAccount/studentAgreements", method = RequestMethod.GET)
	public String getStudentAgreements(Model model) {

		UserEntity userEntity = getUserEntityFromSecurityContext();
		List<StudentAgreement> studentAgreements = studentAgreementService.getStudentAgreementsByUserEntityId(userEntity.getId());
		List<Address> addresses = addressService.getAddressesByUserEntityId(userEntity.getId());
		List<HighSchool> highSchools = highSchoolService.getHighSchoolsByUserEntityId(userEntity.getId());
		List<Institute> institutes = instituteService.getInstitutesByUserEntityId(userEntity.getId());
		List<StandardTest> standardTests = standardTestService.getStandardTestsByUserEntityId(userEntity.getId());
		
		
		StudentAgreement studentAgreement = studentAgreementService.getStudentAgreementByUserEntityIdAndAgreementName(userEntity.getId(), 
				AgreementName.CERTIFY_INFO_PROVIDED_IS_TRUE_ACCURATE);
		if (studentAgreement == null) {
			studentAgreement = new StudentAgreement();
		}
		studentAgreement.setUserEntity(userEntity);

		model.addAttribute("studentAgreements", studentAgreements);
		model.addAttribute("studentAgreement", studentAgreement);
		model.addAttribute("userEntity", userEntity);
		model.addAttribute("addresses", addresses);
		model.addAttribute("highSchools", highSchools);
		model.addAttribute("institutes", institutes);
		model.addAttribute("standardTests", standardTests);

		return "studentAgreements";
	}
	
//	@RequestMapping(value = "/myAccount/studentAgreement/new", method = RequestMethod.GET)
//	public String getNewStudentAgreementForm(Model model) {
//		UserEntity userEntity = getUserEntityFromSecurityContext();
//		
//		StudentAgreement studentAgreement = new StudentAgreement();
//		studentAgreement.setUserEntity(userEntity);
//		model.addAttribute(studentAgreement);
//		model.addAttribute(userEntity);
//		
//		return "newStudentAgreementForm";
//	}

	@RequestMapping(value = "/myAccount/certifyInfoProvided", method = RequestMethod.POST)
	public String postNewStudentAgreementForm(@ModelAttribute @Valid StudentAgreement studentAgreement,
			BindingResult result, Model model) {

		UserEntity userEntity = getUserEntityFromSecurityContext();
		if (result.hasErrors()) {
			model.addAttribute(userEntity);
			// System.out.println("######## result.hasErrors(): true" );
			return "newStudentAgreementForm";
		} else {
			// System.out.println("######## result.hasErrors(): false" );
		}
		studentAgreement.setUserEntity(userEntity);
		studentAgreement.setCreatedBy(userEntity);
		studentAgreement.setAgreementName(AgreementName.CERTIFY_INFO_PROVIDED_IS_TRUE_ACCURATE);
		studentAgreement.setAgree(true);
		studentAgreementService.createStudentAgreement(studentAgreement);

		return "redirect:/myAccount/studentAgreements";
	}

	// ======================================
	// = =
	// ======================================
	/**
	 * I THINK ONCE AN AGREEMENT IS MADE,
	 * NO ONE SHOULD UPDATE IT, DELETE IT.
	 * 
	 * I THINK YOU CAN'T JUST UNDO YOUR AGREEEMENT TO TERMS OF AGREEMENT!!!!!
	 */

	// ======================================
	// = =
	// ======================================

	private UserEntity getUserEntityFromSecurityContext() {
		SecurityContext securityCtx = SecurityContextHolder.getContext();
		Authentication auth = securityCtx.getAuthentication();
		UserEntity userEntity = (UserEntity) auth.getPrincipal();
		return userEntity;
	}
}
