package com.prospectivestiles.dao;

import com.prospectivestiles.domain.ProgramOfStudy;

public interface ProgramOfStudyDao extends Dao<ProgramOfStudy> {

	ProgramOfStudy findByName(String name);
	
//	List<ProgramOfStudy> getProgramOfStudiesByUserEntityId(long userEntityId);
//	void updateZipCode(long addressId, String zipcode);
	

}
