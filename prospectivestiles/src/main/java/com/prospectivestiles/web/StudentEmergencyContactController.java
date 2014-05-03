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

import com.prospectivestiles.domain.EmergencyContact;
import com.prospectivestiles.domain.UserEntity;
import com.prospectivestiles.service.EmergencyContactService;
import com.prospectivestiles.service.UserEntityService;

@Controller
public class StudentEmergencyContactController {

	@Inject
	private EmergencyContactService emergencyContactService;

	@Autowired
	private UserEntityService userEntityService;

	// ======================================
	// = myAccount addresses =
	// ======================================

	@RequestMapping(value = "/myAccount/emergencyContacts", method = RequestMethod.GET)
	public String getEmergencyContacts(Model model) {

		UserEntity userEntity = getUserEntityFromSecurityContext();

		model.addAttribute("emergencyContacts", 
				emergencyContactService.getEmergencyContactsByUserEntityId(userEntity.getId()));

		/*
		 * I am loading too many entities in the model. What is the limit???
		 */
		EmergencyContact emergencyContact = new EmergencyContact();
		emergencyContact.setUserEntity(userEntity);
		
		model.addAttribute("emergencyContact", emergencyContact);
		model.addAttribute("userEntity", userEntity);

		return "emergencyContacts";
	}

	@RequestMapping(value = "/myAccount/emergencyContacts", method = RequestMethod.POST)
	public String postNewEmergencyContactForm(@ModelAttribute @Valid EmergencyContact emergencyContact,
			BindingResult result) {

		/**
		 * There is no newAddressForm page -- check this out
		 */
		if (result.hasErrors()) {
			// System.out.println("######## result.hasErrors(): true" );
			return "newAddressForm";
		} else {
			// System.out.println("######## result.hasErrors(): false" );
		}
		//

		/*
		 * get userEntity from URL >>>>> if logged in as admin get userEntity
		 * from Session >>>>>>> if logged in as student
		 */
		UserEntity userEntity = getUserEntityFromSecurityContext();
		System.out
				.println("######## userEntity.getId(): " + userEntity.getId());
		emergencyContact.setUserEntity(userEntity);
		System.out.println("######## address.getUserEntity(): "
				+ emergencyContact.getUserEntity());
		emergencyContactService.createEmergencyContact(emergencyContact);

		return "redirect:/myAccount/emergencyContacts";
	}

	// ======================================
	// = =
	// ======================================


	// @RequestMapping(value = "/myAccount/address/{addressId}/edit", method =
	// RequestMethod.GET)
	@RequestMapping(value = "/myAccount/emergencyContact/{emergencyContactId}", method = RequestMethod.GET)
	public String editEmergencyContact(@PathVariable("emergencyContactId") Long emergencyContactId,
			Model model) {
		UserEntity userEntity = getUserEntityFromSecurityContext();
		EmergencyContact emergencyContact = getEmergencyContactsValidateUserEntityId(userEntity.getId(), 
				emergencyContactId);

		model.addAttribute("originalEmergencyContact", emergencyContact);
		model.addAttribute(emergencyContact);

		return "editEmergencyContact";
	}

	@RequestMapping(value = "/myAccount/emergencyContact/{emergencyContactId}", method = RequestMethod.POST)
	public String editEmergencyContact(@PathVariable("emergencyContactId") Long emergencyContactId,
			@ModelAttribute @Valid EmergencyContact origEmergencyContact, BindingResult result,
			Model model) {

		UserEntity userEntity = getUserEntityFromSecurityContext();
		EmergencyContact emergencyContact = getEmergencyContactsValidateUserEntityId(userEntity.getId(), 
				emergencyContactId);

		if (result.hasErrors()) {
			// log.debug("Validation Error in Institute form");
			model.addAttribute("originalEmergencyContact", origEmergencyContact);
			return "editEmergencyContact";
		}

		// log.debug("Message validated; updating message subject and text");
		emergencyContact.setEmail(origEmergencyContact.getEmail());
		emergencyContact.setFirstName(origEmergencyContact.getFirstName());
		emergencyContact.setLastName(origEmergencyContact.getLastName());
		emergencyContact.setPhone(origEmergencyContact.getPhone());
		emergencyContact.setRelationship(origEmergencyContact.getRelationship());
//		emergencyContact.setUserEntity(origEmergencyContact.getUserEntity());
		
		emergencyContactService.updateEmergencyContact(emergencyContact);
		
		return "redirect:/myAccount/emergencyContacts";
	}

	@RequestMapping(value = "/myAccount/emergencyContact/{emergencyContactId}/delete", method = RequestMethod.POST)
	public String deleteEmergencyContact(@PathVariable("emergencyContactId") Long emergencyContactId)
			throws IOException {
		UserEntity userEntity = getUserEntityFromSecurityContext();
		EmergencyContact emergencyContact = getEmergencyContactsValidateUserEntityId(userEntity.getId(), 
				emergencyContactId);
		emergencyContactService.delete(emergencyContact);
		
		return "redirect:/myAccount/emergencyContacts";
	}

	// ======================================
	// = =
	// ======================================

	private UserEntity getUserEntityFromSecurityContext() {
		SecurityContext securityCtx = SecurityContextHolder.getContext();
		Authentication auth = securityCtx.getAuthentication();
		UserEntity userEntity = (UserEntity) auth.getPrincipal();
		return userEntity;
	}

	private EmergencyContact getEmergencyContactsValidateUserEntityId(Long userEntityId,
			Long emergencyContactId) {

		EmergencyContact emergencyContact = emergencyContactService.getEmergencyContact(emergencyContactId);
		UserEntity userEntity = userEntityService.getUserEntity(userEntityId);

		// Use userEntityId instead of userEntity.getId(), no need to generate
		// it when it is already passed!!
		// Assert.isTrue(userEntity.getId().equals(address.getUserEntity().getId()),
		// "Address Id mismatch");
		Assert.isTrue(userEntity.getId().equals(emergencyContact.getUserEntity().getId()),
				"EmergencyContact Id mismatch");
		return emergencyContact;
	}
}
