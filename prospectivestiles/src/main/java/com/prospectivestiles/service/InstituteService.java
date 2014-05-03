package com.prospectivestiles.service;

import java.util.List;

import com.prospectivestiles.domain.HighSchool;
import com.prospectivestiles.domain.Institute;

public interface InstituteService {
	
	Institute getInstitute(long id);
	List<Institute> getAllInstitutes();
	void createInstitute(Institute institute);
	List<Institute> getInstitutesByUserEntityId(long userEntityId);
	void updateInstitute(Institute institute);
	void delete(Institute institute);
	
}
