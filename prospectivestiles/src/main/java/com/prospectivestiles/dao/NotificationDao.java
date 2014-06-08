package com.prospectivestiles.dao;

import java.util.Date;
import java.util.List;

import com.prospectivestiles.domain.NotificationAlert;



public interface NotificationDao extends Dao<NotificationAlert> {
	
	List<NotificationAlert> getNotificationAlertsByUserEntityId(long userEntityId);
	/*overide the findAll in AbstractHbnDao as i want to sort messages by dateCreated*/
	List<NotificationAlert> findAll();
	/*
	 * Using JDBC to update to create and update NotificationAlert
	 */
	void createNotificationJDBC(String type, String notice, long studentId, Date dateCreated);
	void insertIntoNotificationJDBC(long noticeId, NotificationAlert notification); 
	
}
