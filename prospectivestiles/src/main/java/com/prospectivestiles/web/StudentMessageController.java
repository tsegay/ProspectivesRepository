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
import com.prospectivestiles.domain.Notification;
import com.prospectivestiles.domain.UserEntity;
import com.prospectivestiles.service.MessageService;
import com.prospectivestiles.service.NotificationService;
import com.prospectivestiles.service.UserEntityService;

@Controller
public class StudentMessageController {
	
	
	@Inject
	private MessageService messageService;
	
	@Autowired
	private UserEntityService userEntityService;
	
	@Inject
	private MailSender mailSender;
	
	@Inject
	private NotificationService notificationService;
	
	// ======================================
	// =                JSON        =
	// ======================================
	
	/**
	 * Displays the messages page
	 * @param userEntityId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/myAccount/messages", method = RequestMethod.GET)
	public String showJsonMessages(Model model) {

		UserEntity userEntity = getUserEntityFromSecurityContext();
		
//		model.addAttribute("messagesJson", messageService.getMessagesByUserEntityId(userEntityId));
		model.addAttribute("userEntity", userEntity);
		model.addAttribute("userEntityId", userEntity.getId());

		return "messages";
		/*
		 * IDEA
		 * pass all the messages to the model 
		 * display all messages in the view
		 * use JQ to call this method on a timer
		 * the timer will update the page automatically
		 */
	}
	
	@RequestMapping(value = "/myAccount/getmessages", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public Map<String, Object> getMessagesForJSON(Model model) {
		
		UserEntity userEntity = getUserEntityFromSecurityContext();
		
		List<Message> messages = null;
		if (userEntity == null) {
			messages = new ArrayList<Message>();
		} else {
			messages = messageService.getMessagesByUserEntityId(userEntity.getId());
		}

		Map<String, Object> data = new HashMap<String, Object>();
		data.put("messages", messages);
		data.put("messagesCount", messages.size());
		return data;

		//
//		
//		model.addAttribute("messagesJson", messageService.getMessagesByUserEntityId(userEntityId));
//		model.addAttribute("userEntity", userEntityService.getUserEntity(userEntityId));
//		return "messages";
	}
	/**
	 * returning a map, test with returning a string of "success" or "error"
	 * I want to VALIDATE the subject and text. text should not be null, and subject < 100.
	 * 
	 * @requestBody - this enables you to get data in the appropriate data type
	 * going to receive the @RequestBody data from javascript or jquery post
	 * The map data is what the jquery sendMessage sent
	 */
	@RequestMapping(value = "/myAccount/sendmessage", method = RequestMethod.POST, produces="application/json")
	@ResponseBody
	public Map<String, Object> sendMessageJSON(@RequestBody Map<String, Object> data) {

		System.out.println("############# sendMessageJSON called");
		
		long studentId = Long.parseLong((String) data.get("studentId"));
		String subject = (String) data.get("subject");
		String text = (String) data.get("text");

		System.out.println(studentId + " , " + subject + " , " + text);

//		verify the studentId and userEntityId macth
		UserEntity student = userEntityService.getUserEntity(studentId);
		
		/**
		 * Get the admission staff creating this message from the sercurityContext
		 */
//		UserEntity admissionOfficer = getUserEntityFromSecurityContext();
		
//		if (result.hasErrors()) {
//			return "newMessageForm";
//			System.out.println("########## inside result.hasErrors");
//		} 
		
		Message message = new Message();
//		message.setAdmissionOfficer(admissionOfficer);
//		message.setDateCreated(dateCreated);
//		message.setDateModified(dateModified);
		message.setStudent(student);
		message.setSubject(subject);
		message.setText(text);
		message.setVisible(true);
		
		messageService.createMessage(message);
		
		/*
		 * after a message is successfuly sent I want to create a notification
		 * I need to create an enum NotificationType: message, uploadedDoc, statusChanged, updatedProfile, ...
		*/
		Notification notification = new Notification("message", student.getFullName() + " sent a message", student);
		notificationService.createNotification(notification);

		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setFrom("daniel2advance@gmail.com");
		mail.setTo(student.getEmail());
		mail.setSubject(subject);
		mail.setText(text);
		
//		String returnVal;
		
		try {
			mailSender.send(mail);
//			returnVal = "success";
		} catch (MailException e) {
			e.printStackTrace();
			System.out.println("Failed to send message");
//			returnVal = "error";
		}


		// a map that is going to be actual value to return, 
		// the actual json value that we return to javascript
		Map<String, Object> returnVal = new HashMap<String, Object>();
		returnVal.put("success", true);
		returnVal.put("stId", studentId);
		return returnVal;
	}
	
//	// ======================================
//	// =             myAccount messages             =
//	// ======================================	
//
//	@RequestMapping(value = "/myAccount/messages", method = RequestMethod.GET)
//	public String getMessages(Model model) {
//		
//		UserEntity userEntity = getUserEntityFromSecurityContext();
//		
//		model.addAttribute("messages", messageService.getMessagesByUserEntityId(userEntity.getId()));
//		
//		/*
//		 * I am loading too many entities in the model. What is the limit???
//		 */
//		Message message = new Message();
//		System.out.println("################### StudentMessageController.getMessages: " + message.toString());
////		message.setUserEntity(userEntity);
//		message.setStudent(userEntity);
//		
//		model.addAttribute("message", message);
//		
//		model.addAttribute("userEntity", userEntity);
//		
//		return "messages";
//	}
//	
//	@RequestMapping(value = "/myAccount/messages", method = RequestMethod.POST)
//	public String postNewMessageForm(@ModelAttribute @Valid Message message, BindingResult result) {
//		
//		System.out.println("################### IN StudentMessageController.postNewMessageForm.");
//
//		/**
//		 * There is no newMessageForm page -- check this out
//		 */
//		if (result.hasErrors()) {
//			System.out.println("######## StudentMessageController.postNewMessageForm result.hasErrors(): true" );
//			/**
//			 * Work on displaying spring err msg in Modal.
//			 */
//			return "redirect:/myAccount/messages";
////			return "newMessageForm";
//		} else {
//			System.out.println("######## StudentMessageController.postNewMessageForm result.hasErrors(): false" );
//		}
//
//		UserEntity userEntity = getUserEntityFromSecurityContext();
//		
////		message.setUserEntity(userEntity);
//		message.setStudent(userEntity);
//		
//		messageService.createMessage(message);
//		
//		// Would normally set Location header and HTTP status 201, but we're
//		// using the redirect-after-post pattern, which uses the Location header
//		// and status code for redirection.
//		return "redirect:/myAccount/messages";
//	}
//	
//	
//	
//	// ======================================
//	// =                         =
//	// ======================================
//	
//	@RequestMapping(value = "/myAccount/message/{messageId}", method = RequestMethod.GET)
//	public String editInstitute(@PathVariable("messageId") Long messageId, Model model) {
//		UserEntity userEntity = getUserEntityFromSecurityContext();	
//		Message message = getMessageValidateUserEntityId(userEntity.getId(), messageId);
//		
//		model.addAttribute("originalMessage", message);
//		model.addAttribute(message);
//		
//		return "editMessage";
//	}
//	
//	@RequestMapping(value = "/myAccount/message/{messageId}", method = RequestMethod.POST)
//	public String editInstitute(@PathVariable("messageId") Long messageId,
//			@ModelAttribute @Valid Message origMessage, 
//			BindingResult result,
//			Model model) {
//		
//		UserEntity userEntity = getUserEntityFromSecurityContext();
//		Message message = getMessageValidateUserEntityId(userEntity.getId(), messageId);
//
//		if (result.hasErrors()) {
////			log.debug("Validation Error in Institute form");
//			model.addAttribute("originalMessage", origMessage);
//			return "editMessage";
//		}
//
////		log.debug("Message validated; updating message subject and text");
//		
//		message.setAdmissionOfficer(origMessage.getAdmissionOfficer());
//		message.setDateModified(origMessage.getDateModified());
//		message.setStudent(origMessage.getStudent());
//		message.setSubject(origMessage.getSubject());
//		message.setText(origMessage.getText());
//		message.setVisible(origMessage.isVisible());
//		
//		messageService.updateMessage(message);
//		
//		return "redirect:/myAccount/messages";
//	}
//	
//	
//	@RequestMapping(value = "/myAccount/message/{messageId}/delete", method = RequestMethod.POST)
//	public String deleteMessage(@PathVariable("messageId") Long messageId)
//			throws IOException {
//		UserEntity userEntity = getUserEntityFromSecurityContext();
//		messageService.deleteMessage(getMessageValidateUserEntityId(userEntity.getId(), messageId));
//		return "redirect:/myAccount/messages";
//	}
	
	
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
