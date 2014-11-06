package com.prospectivestiles.web;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import com.prospectivestiles.domain.AssociatedUser;
import com.prospectivestiles.domain.UserEntity;
import com.prospectivestiles.service.AssociatedUserService;
import com.prospectivestiles.service.UserEntityService;

@Controller
public class AdminAssociatedUserController {
	
	@Autowired
	private UserEntityService userEntityService;
	
	@Inject
	private AssociatedUserService associatedUserService;
	
	private static final Logger log = LoggerFactory.getLogger(AdminAssociatedUserController.class);
	
	
	// ======================================
	// =             associatedUsers            =
	// 				CREATE ASSOCIATED USERS CONTROLLER				
	// ======================================
	
	/**
	 * 
	 * @param userEntityId
	 * @param model
	 * @return 
	 */
	@RequestMapping(value = "/accounts/{userEntityId}/associatedUser/new", method = RequestMethod.GET)
	public String getNewAssociatedUsersForm(@PathVariable("userEntityId") Long userEntityId,
			Model model) {
		
		// ##### LOGGING #########
		Date now = new Date();
		String currentUserFullName = getUserEntityFromSecurityContext().getFullName();
		log.info("######## " + currentUserFullName + " viewing /accounts/userEntityId/associatedUser/new on " + now);
		log.info("######## viewing /accounts/userEntityId/associatedUser/new: ## Username: {}, ## Date: {}", currentUserFullName, now);
		System.out.println("sysout " + currentUserFullName + " viewing /accounts/userEntityId/associatedUser/new on " + now);
		// ##### END LOGGING #########
		
		AssociatedUser associatedUser = new AssociatedUser();
		UserEntity userEntity = userEntityService.getUserEntity(userEntityId);
		// get all admission counsellors and pass it to the model, ROLE_ADMISSION=12
		List<UserEntity> admissionCounselors = userEntityService.findByRole(12);
		
		associatedUser.setStudent(userEntity);
		
		model.addAttribute("associatedUser",associatedUser);
		model.addAttribute(userEntity);
		model.addAttribute("admissionCounselors", admissionCounselors);
		
		return "associatedUserForm";
	}
	
	/**
	 * When form is submitted, if I use path="admissionOfficer.id" in the form select field
	 * get err - save the transient instance before flushing: com.prospectivestiles.domain.UserEntity
	 * 
	 * I created a transient property long aoId in AssociatedUser class to hold the id of the AdmissionOfficer
	 * in the form I use path="aoId" in the form select field
	 * when user select admissionCounselor from the dropDown menu, 
	 * set the admissionCounselors.id in aoId
	 * in the controller get aoId from the associatedUser ModelAttribute and 
	 * pass aoId to associatedUser.setAdmissionOfficer
	 * 
	 * CreatedBy is set to the currently logged in Admission User
	 * dateCreated is set in the abstractHbn Class
	 * 
	 * @param userEntityId
	 * @param associatedUser
	 * @param result
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/accounts/{userEntityId}/associatedUser/new", method = RequestMethod.POST)
	public String postNewAssociatedUsersForm(@PathVariable("userEntityId") Long userEntityId,
			@ModelAttribute @Valid AssociatedUser associatedUser, BindingResult result, Model model) {
		
		// ##### LOGGING #########
		Date now = new Date();
		String currentUserFullName = getUserEntityFromSecurityContext().getFullName();
		log.info("######## " + currentUserFullName + " viewing /accounts/userEntityId/associatedUsers on " + now);
		log.info("######## viewing /accounts/userEntityId/associatedUsers: ## Username: {}, ## Date: {}", currentUserFullName, now);
		System.out.println("sysout " + currentUserFullName + " viewing /accounts/userEntityId/associatedUsers on " + now);
		// ##### END LOGGING #########
		
		UserEntity userEntity = userEntityService.getUserEntity(userEntityId);
		UserEntity currentAdmissionUser = getUserEntityFromSecurityContext();
		
		if (result.hasErrors()) {
			model.addAttribute(userEntity);
			return "associatedUserForm";
		}

		associatedUser.setStudent(userEntity);
		associatedUser.setCreatedBy(currentAdmissionUser);
		associatedUser.setAdmissionOfficer(userEntityService.getUserEntity(associatedUser.getAoId()));
		
		associatedUserService.createAssociatedUser(associatedUser);
		
		return "redirect:/accounts/{userEntityId}";
	}

	/**
	 * When form is displayed, get the current admissionOfficer
	 * set it to the aoId form displays current admissionOfficer in the field
	 * 
	 * @param userEntityId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/accounts/{userEntityId}/associatedUser/edit", method = RequestMethod.GET)
	public String editAssociatedUser(@PathVariable("userEntityId") Long userEntityId, Model model) {
		
		// ##### LOGGING #########
		Date now = new Date();
		String currentUserFullName = getUserEntityFromSecurityContext().getFullName();
		log.info("######## " + currentUserFullName + " viewing /accounts/userEntityId/associatedUser/edit on " + now);
		log.info("######## viewing /accounts/userEntityId/associatedUser/edit: ## Username: {}, ## Date: {}", currentUserFullName, now);
		System.out.println("sysout " + currentUserFullName + " viewing /accounts/userEntityId/associatedUser/edit on " + now);
		// ##### END LOGGING #########
		
		UserEntity userEntity = userEntityService.getUserEntity(userEntityId);
		AssociatedUser associatedUser = associatedUserService.getAssociatedUserByUserEntityId(userEntityId);
		// get all admission counsellors and pass it to the model, ROLE_ADMISSION=12
		List<UserEntity> admissionCounselors = userEntityService.findByRole(12);
		
		/*To avoid NullPointerException when admissionOfficer is not set for the associatedUser*/
		if (associatedUser.getAdmissionOfficer() != null) {
			associatedUser.setAoId(associatedUser.getAdmissionOfficer().getId());
		}
		
//		model.addAttribute("originalAssociatedUser", associatedUser);
		model.addAttribute("associatedUser",associatedUser);
		model.addAttribute(userEntity);
		model.addAttribute("admissionCounselors", admissionCounselors);
		
		return "editAssociatedUser";
	}
	
	/**
	 * 
	 * 
	 * lastModifiedBy - currentAdmissionUser is the Admission Office who is creating...
	 * dateLastModified is set in the serviceImp class to the current time
	 * 
	 * @param userEntityId
	 * @param origAssociatedUser
	 * @param result
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/accounts/{userEntityId}/associatedUser/edit", method = RequestMethod.POST)
	public String editAssociatedUser(@PathVariable("userEntityId") Long userEntityId,
			@ModelAttribute @Valid AssociatedUser origAssociatedUser, 
			BindingResult result,
			Model model) {
		
		// ##### LOGGING #########
		Date now = new Date();
		String currentUserFullName = getUserEntityFromSecurityContext().getFullName();
		log.info("######## " + currentUserFullName + " viewing /accounts/userEntityId/associatedUser/edit on " + now);
		log.info("######## viewing /accounts/userEntityId/associatedUser/edit: ## Username: {}, ## Date: {}", currentUserFullName, now);
		System.out.println("sysout " + currentUserFullName + " viewing /accounts/userEntityId/associatedUser/edit on " + now);
		// ##### END LOGGING #########
		
		UserEntity userEntity = userEntityService.getUserEntity(userEntityId);
		UserEntity currentAdmissionUser = getUserEntityFromSecurityContext();
		AssociatedUser associatedUser = associatedUserService.getAssociatedUserByUserEntityId(userEntityId);

		if (result.hasErrors()) {
			origAssociatedUser.setId(associatedUser.getId());
			origAssociatedUser.setStudent(userEntity);
			model.addAttribute("associatedUser", origAssociatedUser);
			model.addAttribute(userEntity);
			return "editAssociatedUser";
		}

//		associatedUser.setAdmissionOfficer(origAssociatedUser.getAdmissionOfficer());
		associatedUser.setAdmissionOfficer(userEntityService.getUserEntity(origAssociatedUser.getAoId()));
		associatedUser.setAgent(origAssociatedUser.getAgent());
		associatedUser.setReferrer(origAssociatedUser.getReferrer());
		associatedUser.setLastModifiedBy(userEntity);
		associatedUser.setStudent(userEntity);
		associatedUser.setLastModifiedBy(currentAdmissionUser);
		
		associatedUserService.updateAssociatedUser(associatedUser);
		
		return "redirect:/accounts/{userEntityId}";
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
