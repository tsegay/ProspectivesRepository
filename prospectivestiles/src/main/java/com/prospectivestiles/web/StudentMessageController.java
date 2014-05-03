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

import com.prospectivestiles.domain.Message;
import com.prospectivestiles.domain.UserEntity;
import com.prospectivestiles.service.MessageService;
import com.prospectivestiles.service.UserEntityService;

@Controller
public class StudentMessageController {
	
	
	@Inject
	private MessageService messageService;
	
	@Autowired
	private UserEntityService userEntityService;
	
	
	// ======================================
	// =             myAccount messages             =
	// ======================================	

	@RequestMapping(value = "/myAccount/messages", method = RequestMethod.GET)
	public String getMessages(Model model) {
		
		UserEntity userEntity = getUserEntityFromSecurityContext();
		
		model.addAttribute("messages", messageService.getMessagesByUserEntityId(userEntity.getId()));
		
		/*
		 * I am loading too many entities in the model. What is the limit???
		 */
		Message message = new Message();
		System.out.println("################### StudentMessageController.getMessages: " + message.toString());
//		message.setUserEntity(userEntity);
		message.setStudent(userEntity);
		
		model.addAttribute("message", message);
		
		model.addAttribute("userEntity", userEntity);
		
		return "messages";
	}
	
	@RequestMapping(value = "/myAccount/messages", method = RequestMethod.POST)
	public String postNewMessageForm(@ModelAttribute @Valid Message message, BindingResult result) {
		
		System.out.println("################### IN StudentMessageController.postNewMessageForm.");

		/**
		 * There is no newMessageForm page -- check this out
		 */
		if (result.hasErrors()) {
			System.out.println("######## StudentMessageController.postNewMessageForm result.hasErrors(): true" );
			/**
			 * Work on displaying spring err msg in Modal.
			 */
			return "redirect:/myAccount/messages";
//			return "newMessageForm";
		} else {
			System.out.println("######## StudentMessageController.postNewMessageForm result.hasErrors(): false" );
		}

		UserEntity userEntity = getUserEntityFromSecurityContext();
		
//		message.setUserEntity(userEntity);
		message.setStudent(userEntity);
		
		messageService.createMessage(message);
		
		// Would normally set Location header and HTTP status 201, but we're
		// using the redirect-after-post pattern, which uses the Location header
		// and status code for redirection.
		return "redirect:/myAccount/messages";
	}
	
	
	
	// ======================================
	// =                         =
	// ======================================
	
	@RequestMapping(value = "/myAccount/message/{messageId}", method = RequestMethod.GET)
	public String editInstitute(@PathVariable("messageId") Long messageId, Model model) {
		UserEntity userEntity = getUserEntityFromSecurityContext();	
		Message message = getMessageValidateUserEntityId(userEntity.getId(), messageId);
		
		model.addAttribute("originalMessage", message);
		model.addAttribute(message);
		
		return "editMessage";
	}
	
	@RequestMapping(value = "/myAccount/message/{messageId}", method = RequestMethod.POST)
	public String editInstitute(@PathVariable("messageId") Long messageId,
			@ModelAttribute @Valid Message origMessage, 
			BindingResult result,
			Model model) {
		
		UserEntity userEntity = getUserEntityFromSecurityContext();
		Message message = getMessageValidateUserEntityId(userEntity.getId(), messageId);

		if (result.hasErrors()) {
//			log.debug("Validation Error in Institute form");
			model.addAttribute("originalMessage", origMessage);
			return "editMessage";
		}

//		log.debug("Message validated; updating message subject and text");
		
		message.setAdmissionOfficer(origMessage.getAdmissionOfficer());
		message.setDateModified(origMessage.getDateModified());
		message.setStudent(origMessage.getStudent());
		message.setSubject(origMessage.getSubject());
		message.setText(origMessage.getText());
		message.setVisible(origMessage.isVisible());
		
		messageService.updateMessage(message);
		
		return "redirect:/myAccount/messages";
	}
	
	
	@RequestMapping(value = "/myAccount/message/{messageId}/delete", method = RequestMethod.POST)
	public String deleteMessage(@PathVariable("messageId") Long messageId)
			throws IOException {
		UserEntity userEntity = getUserEntityFromSecurityContext();
		messageService.deleteMessage(getMessageValidateUserEntityId(userEntity.getId(), messageId));
		return "redirect:/myAccount/messages";
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
	
	private Message getMessageValidateUserEntityId(Long userEntityId, Long messageId) {
		
		Message message = messageService.getMessage(messageId);
		
		Assert.isTrue(userEntityId.equals(message.getStudent().getId()), "Message Id mismatch");
		return message;
	}

}
