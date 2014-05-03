package com.prospectivestiles.dao;

import java.util.List;

import com.prospectivestiles.domain.EmergencyContact;

public interface EmergencyContactDao extends Dao<EmergencyContact> {

	List<EmergencyContact> getEmergencyContactsByUserEntityId(long userEntityId);

}
