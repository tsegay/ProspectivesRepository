package com.prospectivestiles.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.prospectivestiles.domain.Message;
import com.prospectivestiles.domain.UserEntity;
import com.prospectivestiles.service.MessageService;
import com.prospectivestiles.service.UserEntityService;

@Controller
public class AdminMessageController {
	
	
	@Autowired
	private UserEntityService userEntityService;
	
	@Inject
	private MessageService messageService;
	
	@Inject
	private MailSender mailSender;
	
	// ======================================
	// =             messages             =
	// ======================================
	
	@RequestMapping(value = "/accounts/{userEntityId}/messages", method = RequestMethod.GET)
	public String getMessages(@PathVariable("userEntityId") Long userEntityId,
			Model model) {
		
		/**
		 * load all messages for a user
		 */
		model.addAttribute("messages", messageService.getMessagesByUserEntityId(userEntityId));
		
		/**
		 * The modelAttribute "message" for the form to add new message
		 */
		Message message = new Message();
		model.addAttribute("message", message);
		/**
		 * 
		 */
		model.addAttribute("userEntity", userEntityService.getUserEntity(userEntityId));
		
		return "messages";
	}
	
	@RequestMapping(value = "/accounts/{userEntityId}/messages", method = RequestMethod.POST)
	public String postNewMessageForm(@PathVariable("userEntityId") Long userEntityId,
			@ModelAttribute @Valid Message message, BindingResult result) {
		
		/**
		 * Get the admission staff creating this message from the sercurityContext
		 */
		UserEntity admissionOfficer = getUserEntityFromSecurityContext();
		
		if (result.hasErrors()) {
			return "newMessageForm";
		}

		message.setStudent(userEntityService.getUserEntity(userEntityId));
		message.setAdmissionOfficer(admissionOfficer);
		messageService.createMessage(message);
		
		return "redirect:/accounts/{userEntityId}/messages";
	}
	
	@RequestMapping(value = "/accounts/{userEntityId}/message/{messageId}", method = RequestMethod.GET)
	public String editMessage(@PathVariable("userEntityId") Long userEntityId,
			@PathVariable("messageId") Long messageId, Model model) {
		
		Message message = getMessageValidateUserEntityId(userEntityId, messageId);
		
		model.addAttribute("originalMessage", message);
		model.addAttribute(message);
		
		return "editMessage";
	}
	
	@RequestMapping(value = "/accounts/{userEntityId}/message/{messageId}", method = RequestMethod.POST)
	public String editMessage(@PathVariable("userEntityId") Long userEntityId,
			@PathVariable("messageId") Long messageId,
			@ModelAttribute @Valid Message origMessage, 
			BindingResult result,
			Model model) {
		
		/**
		 * Get the admission staff updating this message from the sercurityContext
		 * db will show the last person making any changes on the message
		 */
		UserEntity admissionOfficer = getUserEntityFromSecurityContext();
		
		Message message = getMessageValidateUserEntityId(userEntityId, messageId);

		if (result.hasErrors()) {
			model.addAttribute("originalMessage", origMessage);
			return "editMessage";
		}
		
//		message.setAdmissionOfficer(origMessage.getAdmissionOfficer());
		message.setAdmissionOfficer(admissionOfficer);
		message.setDateModified(origMessage.getDateModified());
		message.setStudent(origMessage.getStudent());
		message.setSubject(origMessage.getSubject());
		message.setText(origMessage.getText());
		message.setVisible(origMessage.isVisible());
		
		messageService.updateMessage(message);
		
		return "redirect:/accounts/{userEntityId}/messages";
	}
	
	@RequestMapping(value = "/accounts/{userEntityId}/message/{messageId}/delete", method = RequestMethod.POST)
	public String deleteMessage(@PathVariable("userEntityId") Long userEntityId,
			@PathVariable("messageId") Long messageId)
			throws IOException {
		messageService.deleteMessage(getMessageValidateUserEntityId(userEntityId, messageId));
		return "redirect:/accounts/{userEntityId}/messages";
	}
	
	// ======================================
	// =                JSON        =
	// ======================================
	
	
	@RequestMapping(value = "/json/accounts/{userEntityId}/messages", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public Map<String, Object> getMessagesForJSON(@PathVariable("userEntityId") Long userEntityId,
			Model model) {
		
		List<Message> messages = null;
		if (userEntityId == null) {
			messages = new ArrayList<Message>();
		} else {
			messages = messageService.getMessagesByUserEntityId(userEntityId);
		}
		
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("messages", messages);
		data.put("number", messages.size());
		return data;
		
		//
//		
//		model.addAttribute("messagesJson", messageService.getMessagesByUserEntityId(userEntityId));
//		model.addAttribute("userEntity", userEntityService.getUserEntity(userEntityId));
//		return "messages";
	}
	/**
	 * @requestBody - this enables you to get data in the appropriate data type
	 * going to receive the @RequestBody data from javascript or jquery post
	 * The map data is what the jquery sendMessage sent
	 */
//	@RequestMapping(value = "/json/accounts/{userEntityId}/sendmessage", method = RequestMethod.POST, produces="application/json")
	@RequestMapping(value = "/json/accounts/sendmessage", method = RequestMethod.POST, produces="application/json")
	@ResponseBody
	public Map<String, Object> sendMessageJSON(@RequestBody Map<String, Object> data) {
		
		String text = (String) data.get("text");
		String name = (String) data.get("name");
		String email = (String) data.get("email");
		Integer target = (Integer)data.get("target");
		
		System.out.println(name + " , " + email + " , " + text);
		
		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setFrom("daniel2advance@gmail.com");
		mail.setTo(email);
		mail.setSubject("Re:" + name + ", your message");
		mail.setText(text);
		
		try {
			mailSender.send(mail);
		} catch (MailException e) {
			e.printStackTrace();
			System.out.println("Failed to send message");
		}
		
		
		// a map that is going to be actual value to return, 
		// the actual json value that we return to javascript
		Map<String, Object> returnVal = new HashMap<String, Object>();
		returnVal.put("success", true);
		returnVal.put("target", target);
		return returnVal;
	}
	
	
	@RequestMapping(value = "/json/accounts/messages", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public Map<String, Object> getAllMessagesForJSON() {
		
		List<Message> messages = messageService.getAllMessages();
		
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("messages", messages);
		data.put("number", messages.size());
		
		return data;
		
	}
	
	@RequestMapping(value = "/json/jsonMessages", method = RequestMethod.GET)
	public String showJsonMessages(Model model) {
		
//		model.addAttribute("messagesJson", messageService.getMessagesByUserEntityId(userEntityId));
//		model.addAttribute("userEntity", userEntityService.getUserEntity(userEntityId));
		
		return "jsonMessages";
	}
	
	
	// ======================================
	// =                        =
	// ======================================
	
	private Message getMessageValidateUserEntityId(Long userEntityId, Long highschoolId) {
		Message message = messageService.getMessage(highschoolId);
		
//		Assert.isTrue(userEntityId.equals(message.getUserEntity().getId()), "Message Id mismatch");
		Assert.isTrue(userEntityId.equals(message.getStudent().getId()), "Message Id mismatch");
		return message;
	}
	
	private UserEntity getUserEntityFromSecurityContext() {
		SecurityContext securityCtx = SecurityContextHolder.getContext();
		Authentication auth = securityCtx.getAuthentication();
		UserEntity userEntity = (UserEntity) auth.getPrincipal();
		return userEntity;
	}
}
