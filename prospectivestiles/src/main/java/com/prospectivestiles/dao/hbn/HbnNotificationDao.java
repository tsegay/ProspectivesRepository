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
import com.prospectivestiles.domain.NotificationAlert;
import com.prospectivestiles.domain.UserEntity;


@Repository
public class HbnNotificationDao extends AbstractHbnDao<NotificationAlert> implements
		NotificationDao {
	@Inject private JdbcTemplate jdbcTemplate;
	private static final String INSERT_NOTIFICATION_SQL =
			"insert into notificationAlert (type, notice, studentId, dateCreated) VALUES(?,?,?,?)";
	@Override
	/*public void createNotification(NotificationAlert notification) {*/
	public void createNotificationJDBC(String type, String notice, long studentId, Date dateCreated) {
//		jdbcTemplate.update(INSERT_TERM_SQL, termId, userEntityId);
//		jdbcTemplate.update("INSERT INTO PERSON (FIRSTNAME, LASTNAME) VALUES(?,?)",
//		        new Object[] { firstName, lastName });
		
		jdbcTemplate.update(INSERT_NOTIFICATION_SQL, type, notice, studentId, dateCreated);
				
	}
//	private static final String INSERT_NOTIFICATION_SQL =
//			"insert into notificationAlert (type, notice, dateCreated, visible, studentId, read) VALUES(?,?,?,?,?,?)";
	/*
	 * I am using JDBC to insert create notification, it is not working normally
	 */
//	@Override
//	/*public void createNotification(NotificationAlert notification) {*/
//	public void createNotificationJDBC(String type, String notice,
//			Date dateCreated, boolean visible, long studentId, boolean read) {
//		
////		jdbcTemplate.update(INSERT_TERM_SQL, termId, userEntityId);
////		jdbcTemplate.update("INSERT INTO PERSON (FIRSTNAME, LASTNAME) VALUES(?,?)",
////		        new Object[] { firstName, lastName });
//		
//		jdbcTemplate.update(INSERT_NOTIFICATION_SQL, type, notice, dateCreated, visible, studentId, read);
//		
//	}
	
	
	/*overide the findAll in AbstractHbnDao as i want to sort messages by dateCreated*/
	@Override
	public List<NotificationAlert> findAll() {
		
//		String hql = "FROM NotificationAlert n ORDER BY n.dateCreated DESC";
		String hql = "FROM NotificationAlert n WHERE n.visible = " + true + " AND read = " + false + " ORDER BY n.dateCreated DESC";
//		String hql = "FROM NotificationAlert";
		
		Session session = getSession();
		Query query = session.createQuery(hql);
		
		List<NotificationAlert> notices = query.list();
//		List<NotificationAlert> notices = new ArrayList<NotificationAlert>();
		
		return notices;
	}

	
	/*
	 * return all notifications that are visible and not read
	 * */
	@SuppressWarnings("unchecked")
	@Override
	public List<NotificationAlert> getNotificationAlertsByUserEntityId(long userEntityId) {
		
		String hql = "FROM NotificationAlert n WHERE n.student.id = " + userEntityId + 
				" AND n.visible = " + true + " AND read = " + false + " ORDER BY n.dateCreated DESC";
//		String hql = "FROM NotificationAlert";
		Session session = getSession();
		Query query = session.createQuery(hql);
		
		List<NotificationAlert> notices = query.list();
		
//		List<NotificationAlert> notices = new ArrayList<NotificationAlert>();
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



	


	


}
