package com.prospectivestiles.dao.hbn;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.prospectivestiles.dao.MessageDao;
import com.prospectivestiles.domain.Address;
import com.prospectivestiles.domain.Message;
import com.prospectivestiles.domain.UserEntity;


@Repository
public class HbnMessageDao extends AbstractHbnDao<Message> implements
		MessageDao {
	
	/**
	 * overide the findAll in AbstractHbnDao as i want to sort messages by dateCreated
	 * 
	 * USE @NamedQuery, REMMOVE sql stmt
	 */
	@Override
	public List<Message> findAll() {
		
		String hql = "FROM Message m ORDER BY m.dateCreated DESC";
		
		Session session = getSession();
		Query query = session.createQuery(hql);
		
		List<Message> messages = query.list();
		
		return messages;
	}

	@Override
	public List<Message> getMessagesByUserEntityId(long userEntityId) {
		
		String hql = "FROM Message m WHERE m.student.id = " + userEntityId + " ORDER BY m.dateCreated DESC";
		
		Session session = getSession();
//		Query query = session.getNamedQuery("findMessagesByUserEntityId");
		Query query = session.createQuery(hql);
//		query.setParameter("id", userEntityId);
		
		List<Message> messages = query.list();
		
		return messages;
		
		
//		String hql = "FROM UserEntity u ORDER BY u.lastName ASC";
//		Query query = getSession().createQuery(hql);
//		query.setFirstResult((page - 1) * pageSize);
//		query.setMaxResults(pageSize);
		
//		List<UserEntity> results = query.list();
//		return results;
		
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
