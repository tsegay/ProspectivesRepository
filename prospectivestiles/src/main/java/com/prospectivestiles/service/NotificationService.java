package com.prospectivestiles.service;

import java.util.Date;
import java.util.List;

import com.prospectivestiles.domain.Notification;
import com.prospectivestiles.domain.UserEntity;

public interface NotificationService {
	
	Notification getNotification(long id);
	List<Notification> getAllNotifications();
	List<Notification> getNotificationsByUserEntityId(long userEntityId);
	void createNotification(Notification notification);
	void updateNotification(Notification notification);
	void deleteNotification(Notification notification);
	/*void createNotificationJDBC(String type, String notice, long studentId, Date dateCreated);
	*/
	void insertIntoNotificationJDBC(long noticeId, Notification notification);
	
}
