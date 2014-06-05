package com.prospectivestiles.dao;

import java.util.List;

import com.prospectivestiles.domain.Message;



public interface MessageDao extends Dao<Message> {
	
	List<Message> getMessagesByUserEntityId(long userEntityId);
	/*overide the findAll in AbstractHbnDao as i want to sort messages by dateCreated*/
	List<Message> findAll();
	
}
