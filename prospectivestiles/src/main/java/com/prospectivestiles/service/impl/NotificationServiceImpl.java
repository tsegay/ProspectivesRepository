package com.prospectivestiles.service.impl;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prospectivestiles.dao.NotificationDao;
import com.prospectivestiles.domain.NotificationAlert;
import com.prospectivestiles.service.NotificationService;

@Service
@Transactional(readOnly = true)
public class NotificationServiceImpl implements NotificationService {
	
	@Inject
	private NotificationDao notificationDao;
	
	@Override
	public NotificationAlert getNotificationAlert(long id) {
		return notificationDao.find(id);
	}

	@Override
	public List<NotificationAlert> getAllNotificationAlerts() {
		return notificationDao.findAll();
	}

	@Override
	public List<NotificationAlert> getNotificationAlertsByUserEntityId(long userEntityId) {
		return notificationDao.getNotificationAlertsByUserEntityId(userEntityId);
	}

	@Transactional(readOnly = false)
	@Override
	public void createNotificationAlert(NotificationAlert notification) {
		notificationDao.create(notification);
	}

	@Override
	public void updateNotificationAlert(NotificationAlert notification) {
		NotificationAlert notificationToUpdate = notificationDao.find(notification.getId());
		
		notificationToUpdate.setNotice(notification.getNotice());
		notificationToUpdate.setDateCreated(notification.getDateCreated());
		notificationToUpdate.setRead(notification.isRead());
		notificationToUpdate.setReadOn(notification.getReadOn());
		notificationToUpdate.setReadBy(notification.getReadBy());
		notificationToUpdate.setStudent(notification.getStudent());
		notificationToUpdate.setType(notification.getType());
		notificationToUpdate.setDateModified(notification.getDateModified());
		notificationToUpdate.setVisible(notification.isVisible());
		
		notificationDao.update(notificationToUpdate);
	}

	@Override
	public void deleteNotificationAlert(NotificationAlert notification) {
		notificationDao.delete(notification);
	}

	/*@Transactional(readOnly = false)
	@Override
	public void createNotificationJDBC(String type, String notice,
			Date dateCreated, boolean visible, long studentId, boolean read) {
		
		notificationDao.createNotificationJDBC(type, notice, dateCreated, visible, studentId, read);
		
	}*/

	@Transactional(readOnly = false)
	@Override
	public void createNotificationJDBC(String type, String notice,
			long studentId, Date dateCreated) {
		
		notificationDao.createNotificationJDBC(type, notice, studentId, dateCreated);
		
	}
	


}
