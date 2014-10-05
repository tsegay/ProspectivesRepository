package com.prospectivestiles.dao;

import java.util.Date;
import java.util.List;

import com.prospectivestiles.domain.Notification;
import com.prospectivestiles.domain.UserEntity;



public interface NotificationDao extends Dao<Notification> {
	
	List<Notification> getNotificationsByUserEntityId(long userEntityId);
	/*override the findAll in AbstractHbnDao as i want to sort messages by dateCreated*/
	List<Notification> findAll();
	
//	void insertIntoNotificationJDBC(long noticeId, Notification notification);
}
