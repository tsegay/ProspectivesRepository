package com.prospectivestiles.service.impl;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prospectivestiles.dao.NotificationDao;
import com.prospectivestiles.domain.Notification;
import com.prospectivestiles.domain.UserEntity;
import com.prospectivestiles.service.NotificationService;

@Service
@Transactional(readOnly = true)
public class NotificationServiceImpl implements NotificationService {
	
	@Inject
	private NotificationDao notificationDao;
	
	@Override
	public Notification getNotification(long id) {
		return notificationDao.find(id);
	}

	@Override
	public List<Notification> getAllNotifications() {
		return notificationDao.findAll();
	}

	@Override
	public List<Notification> getNotificationsByUserEntityId(long userEntityId) {
		return notificationDao.getNotificationsByUserEntityId(userEntityId);
	}

	@Transactional(readOnly = false)
	@Override
	public void createNotification(Notification notification) {
		notificationDao.create(notification);
	}

	@Override
	public void updateNotification(Notification notification) {
		Notification notificationToUpdate = notificationDao.find(notification.getId());
		
		notificationToUpdate.setNotice(notification.getNotice());
		notificationToUpdate.setDateCreated(notification.getDateCreated());
		notificationToUpdate.setReadOn(notification.getReadOn());
		notificationToUpdate.setReadBy(notification.getReadBy());
		notificationToUpdate.setStudent(notification.getStudent());
		notificationToUpdate.setType(notification.getType());
		notificationToUpdate.setDateModified(notification.getDateModified());
		notificationToUpdate.setVisible(notification.isVisible());
		Date now = new Date();
		notificationToUpdate.setDateLastModified(now);
		notificationToUpdate.setLastModifiedBy(notification.getLastModifiedBy());
		
		notificationDao.update(notificationToUpdate);
	}

	@Override
	public void deleteNotification(Notification notification) {
		notificationDao.delete(notification);
	}

	/*@Transactional(readOnly = false)
	@Override
	public void createNotificationJDBC(String type, String notice,
			long studentId, Date dateCreated) {
		
		notificationDao.createNotificationJDBC(type, notice, studentId, dateCreated);
		
	}*/

	@Override
	@Transactional(readOnly = false)
	public void insertIntoNotificationJDBC(long noticeId, Notification notification) {
		
		notificationDao.insertIntoNotificationJDBC(noticeId, notification);
		
	}


}
