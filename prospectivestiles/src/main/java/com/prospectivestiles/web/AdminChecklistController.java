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

import com.prospectivestiles.domain.Checklist;
import com.prospectivestiles.service.ChecklistService;
import com.prospectivestiles.service.UserEntityService;

@Controller
public class AdminChecklistController {
	
	
	@Autowired
	private UserEntityService userEntityService;
	
	@Inject
	private ChecklistService checklistService;
	
	// ======================================
	// =             checklists             =
	// ======================================
	
	/**
	 * One Student has one checklist only.
	 * Thinking of Modifying the getChecklists and editCheklist methods!!
	 * Check if user has checklist
	 * If user has checklist return it
	 * Else create new checklist for user
	 * 
	 * in the view page, check if user has a checklist created
	 * If no checklist exist for user, display create checklist button
	 * Else display Edit checklist button
	 * 
	 */
	
	@RequestMapping(value = "/accounts/{userEntityId}/checklists", method = RequestMethod.GET)
	public String getChecklists(@PathVariable("userEntityId") Long userEntityId,
			Model model) {
		
		/**
		 * load checklist for a user, if exist
		 */
		model.addAttribute("checklists", checklistService.getChecklistByUserEntityId(userEntityId));
		
		/**
		 * The modelAttribute "checklist" for the form to add new checklist
		 */
		Checklist checklist = new Checklist();
		model.addAttribute("checklist", checklist);
		
		model.addAttribute("userEntity", userEntityService.getUserEntity(userEntityId));
		
		return "checklists";
	}
	
	@RequestMapping(value = "/accounts/{userEntityId}/checklists", method = RequestMethod.POST)
	public String postNewChecklistForm(@PathVariable("userEntityId") Long userEntityId,
			@ModelAttribute @Valid Checklist checklist, BindingResult result) {
		
		if (result.hasErrors()) {
			return "newChecklistForm";
		}

		checklist.setUserEntity(userEntityService.getUserEntity(userEntityId));
		checklistService.createChecklist(checklist);
		
		return "redirect:/accounts/{userEntityId}/checklists";
	}
	
	@RequestMapping(value = "/accounts/{userEntityId}/checklist/{checklistId}", method = RequestMethod.GET)
	public String editChecklist(@PathVariable("userEntityId") Long userEntityId,
			@PathVariable("checklistId") Long checklistId, Model model) {
		
		Checklist checklist = getChecklistValidateUserEntityId(userEntityId, checklistId);
		
		model.addAttribute("originalChecklist", checklist);
		model.addAttribute(checklist);
		
		return "editChecklist";
	}
	
	@RequestMapping(value = "/accounts/{userEntityId}/checklist/{checklistId}", method = RequestMethod.POST)
	public String editChecklist(@PathVariable("userEntityId") Long userEntityId,
			@PathVariable("checklistId") Long checklistId,
			@ModelAttribute @Valid Checklist origChecklist, 
			BindingResult result,
			Model model) {
		
		Checklist checklist = getChecklistValidateUserEntityId(userEntityId, checklistId);

		if (result.hasErrors()) {
			model.addAttribute("originalChecklist", origChecklist);
			return "editChecklist";
		}
		
		checklist.setNotes(origChecklist.getNotes());
		checklist.setBankStmt(origChecklist.isBankStmt());
		checklist.setF1Visa(origChecklist.isF1Visa());
		checklist.setI20(origChecklist.isI20());
		checklist.setApplicationFee(origChecklist.isApplicationFee());
		checklist.setDiplome(origChecklist.isDiplome());
		checklist.setFinancialAffidavit(origChecklist.isFinancialAffidavit());
		checklist.setPassport(origChecklist.isPassport());
		checklist.setTranscript(origChecklist.isTranscript());
		
		checklistService.updateChecklist(checklist);
		
		return "redirect:/accounts/{userEntityId}/checklists";
	}
	
	@RequestMapping(value = "/accounts/{userEntityId}/checklist/{checklistId}/delete", method = RequestMethod.POST)
	public String deleteChecklist(@PathVariable("userEntityId") Long userEntityId,
			@PathVariable("checklistId") Long checklistId)
			throws IOException {
		checklistService.deleteChecklist(getChecklistValidateUserEntityId(userEntityId, checklistId));
		return "redirect:/accounts/{userEntityId}/checklists";
	}
	
	// ======================================
	// =                        =
	// ======================================
	
	private Checklist getChecklistValidateUserEntityId(Long userEntityId, Long highschoolId) {
		Checklist checklist = checklistService.getChecklist(highschoolId);
		
		Assert.isTrue(userEntityId.equals(checklist.getUserEntity().getId()), "Checklist Id mismatch");
		return checklist;
	}
}
