package com.prospectivestiles.service;

import java.util.List;

import com.prospectivestiles.domain.EmergencyContact;

public interface EmergencyContactService {
	
	EmergencyContact getEmergencyContact(long id);
	List<EmergencyContact> getAllEmergencyContacts();
	List<EmergencyContact> getEmergencyContactsByUserEntityId(long userEntityId);
	void createEmergencyContact(EmergencyContact emergencyContact);
	void updateEmergencyContact(EmergencyContact emergencyContact);
	void delete(EmergencyContact emergencyContact);
	
}
