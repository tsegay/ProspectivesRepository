package com.prospectivestiles.service.impl;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prospectivestiles.dao.EmergencyContactDao;
import com.prospectivestiles.domain.EmergencyContact;
import com.prospectivestiles.service.EmergencyContactService;

@Service
@Transactional
public class EmergencyContactServiceImpl implements EmergencyContactService {
	
	@Inject
	private EmergencyContactDao emergencyContactDao;

	@Override
	public EmergencyContact getEmergencyContact(long id) {
		return emergencyContactDao.find(id);
	}

	@Override
	public List<EmergencyContact> getAllEmergencyContacts() {
		return emergencyContactDao.findAll();
	}

	@Override
	public List<EmergencyContact> getEmergencyContactsByUserEntityId(
			long userEntityId) {
		return emergencyContactDao.getEmergencyContactsByUserEntityId(userEntityId);
	}

	@Override
	public void createEmergencyContact(EmergencyContact emergencyContact) {
		emergencyContactDao.create(emergencyContact);
	}

	@Override
	public void updateEmergencyContact(EmergencyContact emergencyContact) {
		
		EmergencyContact  emergencyContactToUpdate = emergencyContactDao.find(emergencyContact.getId());
		emergencyContactToUpdate.setEmail(emergencyContact.getEmail());
		emergencyContactToUpdate.setFirstName(emergencyContact.getFirstName());
		emergencyContactToUpdate.setLastName(emergencyContact.getLastName());
		emergencyContactToUpdate.setPhone(emergencyContact.getPhone());
		emergencyContactToUpdate.setRelationship(emergencyContact.getRelationship());
//		emergencyContactToUpdate.setUserEntity(emergencyContact.getUserEntity());
		Date now = new Date();
		emergencyContactToUpdate.setDateLastModified(now);
		emergencyContactToUpdate.setLastModifiedBy(emergencyContact.getLastModifiedBy());
		
		emergencyContactDao.update(emergencyContactToUpdate);
	}

	@Override
	public void delete(EmergencyContact emergencyContact) {
		emergencyContactDao.delete(emergencyContact);
	}
	
	

}
