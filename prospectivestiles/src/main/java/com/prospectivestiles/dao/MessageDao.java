package com.prospectivestiles.dao;

import java.util.List;

import com.prospectivestiles.domain.Message;



public interface MessageDao extends Dao<Message> {
	
	List<Message> getMessagesByUserEntityId(long userEntityId);
	
}
