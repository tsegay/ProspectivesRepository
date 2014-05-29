package com.prospectivestiles.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.prospectivestiles.domain.Message;
import com.prospectivestiles.domain.UserEntity;
import com.prospectivestiles.service.MessageService;
import com.prospectivestiles.service.UserEntityService;

@Controller
public class AdminNotificationsController {
	
	
	@Autowired
	private UserEntityService userEntityService;
	
	@Inject
	private MessageService messageService;
	
	
	// ======================================
	// =                JSON        =
	// ======================================
	
	@RequestMapping(value = "/accounts/notification", method = RequestMethod.GET)
	public String showNotification(Model model) {

		return "notification";
	}
	
	
//	@RequestMapping(value = "/accounts/messages", method = RequestMethod.GET, produces = "application/json")
//	@ResponseBody
//	public Map<String, Object> getAllMessagesForJSON() {
//
//		List<Message> messages = messageService.getAllMessages();
//
//		Map<String, Object> data = new HashMap<String, Object>();
//		data.put("messages", messages);
//		data.put("messagesCount", messages.size());
//
//		return data;
//
//	}
	
//	@RequestMapping(value = "/accounts/{userEntityId}/getmessages", method = RequestMethod.GET, produces = "application/json")
//	@ResponseBody
//	public Map<String, Object> getMessagesForJSON(@PathVariable("userEntityId") Long userEntityId,
//			Model model) {
//
//		List<Message> messages = null;
//		if (userEntityId == null) {
//			messages = new ArrayList<Message>();
//		} else {
//			messages = messageService.getMessagesByUserEntityId(userEntityId);
//		}
//
//		Map<String, Object> data = new HashMap<String, Object>();
//		data.put("messages", messages);
//		data.put("messagesCount", messages.size());
//		return data;
//
//	}

	
	// ======================================
	// =                        =
	// ======================================
	
	private Message getMessageValidateUserEntityId(Long userEntityId, Long messageId) {
		Message message = messageService.getMessage(messageId);
		
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