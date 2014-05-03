package com.prospectivestiles.web;

import java.io.IOException;
import java.util.Collection;

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

import com.prospectivestiles.domain.Term;
import com.prospectivestiles.domain.UserEntity;
import com.prospectivestiles.service.TermService;
import com.prospectivestiles.service.UserEntityService;

@Controller
public class TermController {
	
	
	@Inject
	private TermService termService;
	
	@Autowired
	private UserEntityService userEntityService;
	
	
	// ======================================
	// =             myAccount terms             =
	// ======================================	

//	@RequestMapping(value = "/myAccount/terms", method = RequestMethod.GET)
	@RequestMapping(value = "/terms", method = RequestMethod.GET)
	public String getTerms(Model model) {
		
//		UserEntity userEntity = getUserEntityFromSecurityContext();
		
//		model.addAttribute("terms", termService.getTermsByUserEntityId(userEntity.getId()));
		model.addAttribute("terms", termService.getAllTerms());
		
		Term term = new Term();
		/**
		 * Term has multiple users, so add the user to the collection
		 */
		/*term.setUserEntity(userEntity);*/
//		Collection<UserEntity> userEntitiesList = term.getListOfUserEntity();
//		userEntitiesList.add(userEntity);
		
		model.addAttribute("term", term);
		
//		model.addAttribute("userEntity", userEntity);
		
		return "terms";
	}
	
//	@RequestMapping(value = "/myAccount/terms", method = RequestMethod.POST)
	@RequestMapping(value = "/terms", method = RequestMethod.POST)
	public String postNewTermForm(@ModelAttribute @Valid Term term, BindingResult result) {
		
		System.out.println("################### IN StudentTermController.postNewTermForm.");

//		System.out.println("######## postNewTermForm() Called #####");
//		System.out.println("######## getTerm1: " + term.getTerm1());
//		System.out.println("######## getTerm2: " + term.getTerm2());
//		System.out.println("######## getCity: " + term.getCity());
//		
//		if (result != null) {
//			System.out.println("######## Error in: " + result.toString());
//		}
		
		/**
		 * There is no newTermForm page -- check this out
		 */
		if (result.hasErrors()) {
			System.out.println("######## StudentTermController.postNewTermForm result.hasErrors(): true" );
			/**
			 * Work on displaying spring err msg in Modal.
			 */
			return "redirect:/myAccount/terms";
//			return "newTermForm";
		} else {
			System.out.println("######## StudentTermController.postNewTermForm result.hasErrors(): false" );
		}
		

//		UserEntity userEntity = getUserEntityFromSecurityContext();
		/**
		 * Term has multiple users, so add the user to the collection
		 */
//		term.setUserEntity(userEntity);
//		Collection<UserEntity> userEntitiesList = term.getListOfUserEntity();
//		userEntitiesList.add(userEntity);
		
		termService.createTerm(term);
		
		return "redirect:/terms";
	}
	
	
	
	// ======================================
	// =                         =
	// ======================================
	
	@RequestMapping(value = "/term/{termId}", method = RequestMethod.GET)
	public String editTerm(@PathVariable("termId") Long termId, Model model) {
//		UserEntity userEntity = getUserEntityFromSecurityContext();	
//		Term term = getTermValidateUserEntityId(userEntity.getId(), termId);
		
		Term term = termService.getTerm(termId);
		
		model.addAttribute("originalTerm", term);
		model.addAttribute(term);
		
		return "editTerm";
	}
	
	@RequestMapping(value = "/term/{termId}", method = RequestMethod.POST)
	public String editTerm(@PathVariable("termId") Long termId,
			@ModelAttribute @Valid Term origTerm, 
			BindingResult result,
			Model model) {
		
//		UserEntity userEntity = getUserEntityFromSecurityContext();
//		Term term = getTermValidateUserEntityId(userEntity.getId(), termId);
		Term term = termService.getTerm(termId);

		if (result.hasErrors()) {
			model.addAttribute("originalTerm", origTerm);
			return "editTerm";
		}

		term.setDuration(origTerm.getDuration());
		term.setEndDate(origTerm.getEndDate());
//		term.setListOfUserEntity(origTerm.getListOfUserEntity());
		term.setName(origTerm.getName());
		term.setStartDate(origTerm.getStartDate());
		
		termService.updateTerm(term);
		
		return "redirect:/terms";
	}
	
	
	@RequestMapping(value = "/term/{termId}/delete", method = RequestMethod.POST)
	public String deleteTerm(@PathVariable("termId") Long termId)
			throws IOException {
//		UserEntity userEntity = getUserEntityFromSecurityContext();
//		termService.delete(getTermValidateUserEntityId(userEntity.getId(), termId));
		Term term = termService.getTerm(termId);
		termService.delete(term);
		
		return "redirect:/terms";
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
	
	private Term getTermValidateUserEntityId(Long userEntityId, Long termId) {
		
		Term term = termService.getTerm(termId);
		UserEntity userEntity = userEntityService.getUserEntity(userEntityId);
		/**
		 * Need to work on retrieving user from the collection
		 */
//		Assert.isTrue(userEntity.getId().equals(term.getUserEntity().getId()), "Term Id mismatch");
		return term;
	}

}
