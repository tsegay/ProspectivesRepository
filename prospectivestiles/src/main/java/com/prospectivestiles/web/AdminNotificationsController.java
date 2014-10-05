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
import com.prospectivestiles.domain.Notification;
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

		List<Notification> notifications = notificationService.getAllNotifications();

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
	 * When an admission officer click on the notice link, I want to mark that notice as read and drop it from the list of notices
	 * using 2 methods: GET and POST.
	 * Both are returning same results. success but actually not inserting data to notification table in db.
	 * I have to try using JDBC to insert to db!!
	 */
	
	/*@RequestMapping(value = "/accounts/notifications/markRead", method = RequestMethod.POST, produces="application/json")
	@ResponseBody
	public Map<String, Object> tagNoticeAsRead(@RequestBody Map<String, Object> origdata) {

		System.out.println("############# tagNoticeAsRead markRead called");
		
//		long nId = Long.parseLong((String) origdata.get("noticeId"));
		// Can't cast an Integer as a String. so use String.valueOf
		long noticeId = Long.parseLong(String.valueOf(origdata.get("noticeId")));
		long studentId = Long.parseLong(String.valueOf(origdata.get("studentId")));

		System.out.println("noticeId:" + noticeId);
		
		UserEntity admissionOfficer = getUserEntityFromSecurityContext();
		Notification notification = notificationService.getNotification(noticeId);
//		notificationService.insertIntoNotificationJDBC(noticeId, notification);
		
		notification.setVisible(false);
		notification.setReadOn(new Date());
		notification.setReadBy(admissionOfficer);
		notificationService.updateNotification(notification);
		
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("success", true);
		data.put("noticeId", noticeId);
		data.put("studentId", studentId);
		return data;
	}*/
	
	@RequestMapping(value = "/accounts/notifications/markRead/{noticeId}/{studentId}", method = RequestMethod.GET, produces="application/json")
	@ResponseBody
	public Map<String, Object> tagNoticeAsRead(@PathVariable("noticeId") Long noticeId,
			@PathVariable("studentId") Long studentId) {

		System.out.println("############# tagNoticeAsRead {noticeId}/{studentId} called");
		
		System.out.println("noticeId:" + noticeId);
		System.out.println("studentId:" + studentId);
		
		UserEntity admissionOfficer = getUserEntityFromSecurityContext();
		Notification notification = notificationService.getNotification(noticeId);
		
		/*System.out.println("############# 1 getId: " + notification.getId());
		System.out.println("############# 1 isVisible: " + notification.isVisible());
		System.out.println("############# 1 getReadBy: " + notification.getReadBy());
		System.out.println("############# 1 getReadOn: " + notification.getReadOn());*/
		
		notification.setVisible(false);
		notification.setReadOn(new Date());
		notification.setReadBy(admissionOfficer);
//		notificationService.insertIntoNotificationJDBC(noticeId, notification);
		notificationService.updateNotification(notification);
		
		/*System.out.println("############# 2 getId: " + notification.getId());
		System.out.println("############# 2 isVisible: " + notification.isVisible());
		System.out.println("############# 2 getReadBy: " + notification.getReadBy());
		System.out.println("############# 2 getReadOn: " + notification.getReadOn());*/
		
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("success", true);
		data.put("noticeId", noticeId);
		data.put("studentId", studentId);
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