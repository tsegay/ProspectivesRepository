package com.prospectivestiles.dao.hbn;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.prospectivestiles.dao.MessageDao;
import com.prospectivestiles.domain.Address;
import com.prospectivestiles.domain.Message;


@Repository
public class HbnMessageDao extends AbstractHbnDao<Message> implements
		MessageDao {

	@Override
	public List<Message> getMessagesByUserEntityId(long userEntityId) {
		Session session = getSession();
		Query query = session.getNamedQuery("findMessagesByUserEntityId");
		query.setParameter("id", userEntityId);
		
		List<Message> messages = query.list();
		
		return messages;
		
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
