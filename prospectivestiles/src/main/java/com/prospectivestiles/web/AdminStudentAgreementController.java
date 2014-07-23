package com.prospectivestiles.web;

import java.util.List;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.prospectivestiles.domain.Address;
import com.prospectivestiles.domain.AgreementName;
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
public class AdminStudentAgreementController {
	
	
	@Autowired
	private UserEntityService userEntityService;
	@Inject
	private StudentAgreementService studentAgreementService;
	@Inject
	private AddressService addressService;
	@Inject
	private HighSchoolService highSchoolService;
	@Inject
	private InstituteService instituteService;
	@Inject
	private StandardTestService standardTestService;
	
	// ======================================
	// =             studentAgreements             =
	// ======================================
	
	/**
	 * Admin user CANNOT SIGN agreement for applciants.
	 * Admins has VIEW-ONLY role here, in this class.
	 * Applicants MUST sign the agreement themselves.
	 * 
	 * @param userEntityId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/accounts/{userEntityId}/studentAgreements", method = RequestMethod.GET)
	public String getStudentAgreements(@PathVariable("userEntityId") Long userEntityId,
			Model model) {
		UserEntity userEntity = userEntityService.getUserEntity(userEntityId);
		model.addAttribute("userEntity", userEntity);
		
		List<StudentAgreement> studentAgreements = studentAgreementService.getStudentAgreementsByUserEntityId(userEntityId);
		List<Address> addresses = addressService.getAddressesByUserEntityId(userEntityId);
		List<HighSchool> highSchools = highSchoolService.getHighSchoolsByUserEntityId(userEntityId);
		List<Institute> institutes = instituteService.getInstitutesByUserEntityId(userEntityId);
		List<StandardTest> standardTests = standardTestService.getStandardTestsByUserEntityId(userEntityId);
		
		
		StudentAgreement studentAgreement = studentAgreementService.getStudentAgreementByUserEntityIdAndAgreementName(userEntityId, 
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
