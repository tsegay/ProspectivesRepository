package com.prospectivestiles.web;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.prospectivestiles.domain.Message;
import com.prospectivestiles.domain.NotificationAlert;
import com.prospectivestiles.domain.UserEntity;
import com.prospectivestiles.service.MessageService;
import com.prospectivestiles.service.NotificationService;
import com.prospectivestiles.service.UserEntityService;

@Controller
public class AdminNotificationsController {
	
	
	@Autowired
	private UserEntityService userEntityService;
	
	@Inject
	private MessageService messageService;
	
	@Inject
	private NotificationService notificationService;
	
	
	// ======================================
	// =                JSON        =
	// ======================================
	
	@RequestMapping(value = "/accounts/notification", method = RequestMethod.GET)
	public String showNotification(Model model) {

		return "notification";
	}
	
	@RequestMapping(value = "/accounts/notifications", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public Map<String, Object> getAllNotifications() {

		List<NotificationAlert> notifications = notificationService.getAllNotificationAlerts();

		Map<String, Object> data = new HashMap<String, Object>();
		data.put("notifications", notifications);
		data.put("notificationsCount", notifications.size());

		return data;

	}
	
	/* 
	#############################################
			markNoticeRead
	#############################################
	 */
	/*
	 * When an admission officer click on the notice link, I want to mark that notice as read and hide it from the list
	 */
	@RequestMapping(value = "/accounts/notifications/markRead", method = RequestMethod.POST, produces="application/json")
	@ResponseBody
	public Map<String, Object> markNoticeRead(@RequestBody Map<String, Object> dataOrig) {

		System.out.println("############# markNoticeRead called");
		
		long noticeId = Long.parseLong((String) dataOrig.get("noticeId"));
//		boolean read = Boolean.parseBoolean((String) dataOrig.get("read"));

		System.out.println("noticeId: " + noticeId);
		
		/**
		 * Get the admission staff creating this message from the sercurityContext
		 */
		UserEntity admissionOfficer = getUserEntityFromSecurityContext();
		NotificationAlert notification = notificationService.getNotificationAlert(noticeId);
		notification.setRead(true);
		notification.setReadOn(new Date());
		notification.setReadBy(admissionOfficer);
		notificationService.insertIntoNotificationJDBC(noticeId, notification);
		
		
/*//		notification.setNotice();
//		notification.setDateCreated();
		notification.setRead(read);
		notification.setReadOn(new Date());
		notification.setReadBy(admissionOfficer);
//		notification.setStudent();
//		notification.setType();
//		notification.setDateModified();
//		notification.setVisible();
		notificationService.updateNotificationAlert(notification);*/
		

		// a map that is going to be actual value to return, 
		// the actual json value that we return to javascript
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("success", true);
		return data;
	}
	
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