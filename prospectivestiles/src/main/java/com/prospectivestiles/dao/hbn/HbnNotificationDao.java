package com.prospectivestiles.dao.hbn;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.prospectivestiles.dao.NotificationDao;
import com.prospectivestiles.domain.Notification;
import com.prospectivestiles.domain.UserEntity;


@Repository
public class HbnNotificationDao extends AbstractHbnDao<Notification> implements
		NotificationDao {
	@Inject private JdbcTemplate jdbcTemplate;
	
	
//	private static final String INSERT_NOTIFICATION_SQL =
//			"insert into notificationAlert (type, notice, studentId, dateCreated) VALUES(?,?,?,?)";
	
	private static final String UPDATE_NOTIFICATION_SQL = 
			"update notification set visible = ?, readOn = ?, readById = ? where id = ?";
	
//	private static final String UPDATE_NOTIFICATION_SQL = 
//			"update notificationAlert set type = ? where id = ?";
	
	
	/*
	 * I am using JDBC to insert create notification, it is not working normally
	 */
	/*@Override
	public void createNotificationJDBC(String type, String notice, long studentId, Date dateCreated) {
		
		jdbcTemplate.update(INSERT_NOTIFICATION_SQL, type, notice, studentId, dateCreated);
				
	}*/
	
	
	
	/*overide the findAll in AbstractHbnDao as i want to sort messages by dateCreated*/
	@Override
	public List<Notification> findAll() {
		
//		String hql = "FROM Notification n ORDER BY n.dateCreated DESC";
		String hql = "FROM Notification n WHERE n.visible = " + true + " ORDER BY n.dateCreated DESC";
//		String hql = "FROM Notification";
		
		Session session = getSession();
		Query query = session.createQuery(hql);
		
		List<Notification> notices = query.list();
//		List<Notification> notices = new ArrayList<Notification>();
		
		return notices;
	}

	
	/*
	 * return all notifications that are visible and not read
	 * */
	@SuppressWarnings("unchecked")
	@Override
	public List<Notification> getNotificationsByUserEntityId(long userEntityId) {
		
		String hql = "FROM Notification n WHERE n.student.id = " + userEntityId + 
				" AND n.visible = " + true + " ORDER BY n.dateCreated DESC";
		
		
//		String hql = "FROM Notification";
		Session session = getSession();
		Query query = session.createQuery(hql);
		
		List<Notification> notices = query.list();
		
//		List<Notification> notices = new ArrayList<Notification>();
		return notices;
		
		
		/**
		 * I should use try and catch blocks with other method in the other classes too.
		 * 
		 */
		/*Message message = new Message();
		try {
			Session session = getSession();
			Query query = session.getNamedQuery("findMessagesByUserEntityId");
			query.setParameter("id", userEntityId);
			
			message = (Message) query.uniqueResult();
			
			return message;
			
		} catch (Exception e) {
			message = null;
			e.printStackTrace();
		}
		return message;*/
		
	}

	@Override
	public void insertIntoNotificationJDBC(long noticeId,
			Notification notification) {
		jdbcTemplate.update(UPDATE_NOTIFICATION_SQL, new Object[] {
				notification.isVisible(), notification.getReadOn(), notification.getReadBy().getId(), 
				noticeId});
	}
	


	/*@Override
	public void insertIntoNotificationJDBC(long noticeId,
			Notification notification) {
		
		jdbcTemplate.update(UPDATE_NOTIFICATION_SQL, new Object[] {
				notification.getType(), noticeId});
	}*/



}
