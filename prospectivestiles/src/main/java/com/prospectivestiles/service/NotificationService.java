package com.prospectivestiles.service;

import java.util.Date;
import java.util.List;

import com.prospectivestiles.domain.NotificationAlert;

public interface NotificationService {
	
	NotificationAlert getNotificationAlert(long id);
	List<NotificationAlert> getAllNotificationAlerts();
	List<NotificationAlert> getNotificationAlertsByUserEntityId(long userEntityId);
	void createNotificationAlert(NotificationAlert notification);
	void updateNotificationAlert(NotificationAlert notification);
	void deleteNotificationAlert(NotificationAlert notification);
//	void createNotificationJDBC(String type, String notice, Date dateCreated, boolean visible, long studentId, boolean read);
	void createNotificationJDBC(String type, String notice, long studentId, Date dateCreated);
}
