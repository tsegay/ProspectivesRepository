package com.prospectivestiles.web;

import java.io.IOException;

import javax.inject.Inject;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.prospectivestiles.domain.Address;
import com.prospectivestiles.domain.EmergencyContact;
import com.prospectivestiles.domain.UserEntity;
import com.prospectivestiles.service.EmergencyContactService;
import com.prospectivestiles.service.UserEntityService;

@Controller
public class AdminEmergencyContactController {
	
	
	@Autowired
	private UserEntityService userEntityService;
	
	@Inject
	private EmergencyContactService emergencyContactService;
	
	// ======================================
	// =             emergencyContacts             =
	// ======================================
	
	@RequestMapping(value = "/accounts/{userEntityId}/emergencyContacts", method = RequestMethod.GET)
	public String getEmergencyContacts(@PathVariable("userEntityId") Long userEntityId,
			Model model) {
		
		/**
		 * load all emergencyContacts for a user
		 */
		model.addAttribute("emergencyContacts", emergencyContactService.getEmergencyContactsByUserEntityId(userEntityId));
		
		/**
		 * The modelAttribute "emergencyContact" for the form to add new emergencyContact
		 */
		EmergencyContact emergencyContact = new EmergencyContact();
		model.addAttribute("emergencyContact", emergencyContact);
		
		model.addAttribute("userEntity", userEntityService.getUserEntity(userEntityId));
		
		return "emergencyContacts";
	}
	
	/**
	 * New EmergencyContact Form
	 * @param userEntityId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/accounts/{userEntityId}/emergencyContact/new", method = RequestMethod.GET)
	public String getNewEmergencyContactForm(@PathVariable("userEntityId") Long userEntityId,
			Model model) {
		UserEntity userEntity = userEntityService.getUserEntity(userEntityId);
		
		EmergencyContact emergencyContact = new EmergencyContact();
		emergencyContact.setUserEntity(userEntity);
		
		model.addAttribute(emergencyContact);
		model.addAttribute(userEntity);
		
		return "newEmergencyContactForm";
	}
	
	/**
	 * Post New EmergencyContact Form
	 * @param userEntityId
	 * @param emergencyContact
	 * @param result
	 * @return
	 */
	@RequestMapping(value = "/accounts/{userEntityId}/emergencyContacts", method = RequestMethod.POST)
	public String postNewEmergencyContactForm(@PathVariable("userEntityId") Long userEntityId,
			@ModelAttribute @Valid EmergencyContact emergencyContact, BindingResult result, Model model) {
		
		UserEntity userEntity = userEntityService.getUserEntity(userEntityId);
		
		if (result.hasErrors()) {
			model.addAttribute(userEntity);
			return "newEmergencyContactForm";
		}

		emergencyContact.setUserEntity(userEntityService.getUserEntity(userEntityId));
		emergencyContactService.createEmergencyContact(emergencyContact);
		
		return "redirect:/accounts/{userEntityId}/emergencyContacts";
	}
	
	@RequestMapping(value = "/accounts/{userEntityId}/emergencyContact/{emergencyContactId}/edit", method = RequestMethod.GET)
	public String editEmergencyContact(@PathVariable("userEntityId") Long userEntityId,
			@PathVariable("emergencyContactId") Long emergencyContactId, Model model) {
		
		EmergencyContact emergencyContact = getEmergencyContactValidateUserEntityId(userEntityId, emergencyContactId);
		
		model.addAttribute("originalEmergencyContact", emergencyContact);
		model.addAttribute(emergencyContact);
		
		return "editEmergencyContact";
	}

	@RequestMapping(value = "/accounts/{userEntityId}/emergencyContact/{emergencyContactId}", method = RequestMethod.POST)
	public String editEmergencyContact(@PathVariable("userEntityId") Long userEntityId,
			@PathVariable("emergencyContactId") Long emergencyContactId,
			@ModelAttribute @Valid EmergencyContact origEmergencyContact, 
			BindingResult result,
			Model model) {
		
		EmergencyContact emergencyContact = getEmergencyContactValidateUserEntityId(userEntityId, emergencyContactId);

		if (result.hasErrors()) {
			model.addAttribute("originalEmergencyContact", origEmergencyContact);
			return "editEmergencyContact";
		}
		
		emergencyContact.setEmail(origEmergencyContact.getEmail());
		emergencyContact.setFirstName(origEmergencyContact.getFirstName());
		emergencyContact.setLastName(origEmergencyContact.getLastName());
		emergencyContact.setPhone(origEmergencyContact.getPhone());
		emergencyContact.setRelationship(origEmergencyContact.getRelationship());
		
		emergencyContactService.updateEmergencyContact(emergencyContact);
		
		return "redirect:/accounts/{userEntityId}/emergencyContacts";
	}
	
	@RequestMapping(value = "/accounts/{userEntityId}/emergencyContact/{emergencyContactId}/delete", method = RequestMethod.POST)
	public String deleteEmergencyContact(@PathVariable("userEntityId") Long userEntityId,
			@PathVariable("emergencyContactId") Long emergencyContactId)
			throws IOException {
		emergencyContactService.delete(getEmergencyContactValidateUserEntityId(userEntityId, emergencyContactId));
		return "redirect:/accounts/{userEntityId}/emergencyContacts";
	}
	
	// ======================================
	// =                        =
	// ======================================
	
	private EmergencyContact getEmergencyContactValidateUserEntityId(Long userEntityId, Long highschoolId) {
		EmergencyContact emergencyContact = emergencyContactService.getEmergencyContact(highschoolId);
		
		Assert.isTrue(userEntityId.equals(emergencyContact.getUserEntity().getId()), "EmergencyContact Id mismatch");
		return emergencyContact;
	}
}
