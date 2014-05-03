package com.prospectivestiles.service.impl;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prospectivestiles.dao.MessageDao;
import com.prospectivestiles.domain.Message;
import com.prospectivestiles.service.MessageService;

@Service
@Transactional
public class MessageServiceImpl implements MessageService {
	
	@Inject
	private MessageDao messageDao;

	
	@Override
	public Message getMessage(long id) {
		return messageDao.find(id);
	}

	@Override
	public List<Message> getAllMessages() {
		return messageDao.findAll();
	}

	@Override
	public List<Message> getMessagesByUserEntityId(long userEntityId) {
		return messageDao.getMessagesByUserEntityId(userEntityId);
	}

	@Override
	public void createMessage(Message message) {
		messageDao.create(message);
	}

	@Override
	public void updateMessage(Message message) {
		Message messageToUpdate = messageDao.find(message.getId());
		
		messageToUpdate.setAdmissionOfficer(message.getAdmissionOfficer());
//		messageToUpdate.setDateCreated(message.getDateCreated());
		messageToUpdate.setDateModified(message.getDateModified());
		messageToUpdate.setStudent(message.getStudent());
		messageToUpdate.setSubject(message.getSubject());
		messageToUpdate.setText(message.getText());
		messageToUpdate.setVisible(message.isVisible());
		
		messageDao.update(messageToUpdate);
	}

	@Override
	public void deleteMessage(Message message) {
		messageDao.delete(message);
	}
	


}
