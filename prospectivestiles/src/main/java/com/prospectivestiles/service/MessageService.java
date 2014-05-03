package com.prospectivestiles.service;

import java.util.List;

import com.prospectivestiles.domain.Message;

public interface MessageService {
	
	Message getMessage(long id);
	List<Message> getAllMessages();
	List<Message> getMessagesByUserEntityId(long userEntityId);
	void createMessage(Message message);
	void updateMessage(Message message);
	void deleteMessage(Message message);

}
