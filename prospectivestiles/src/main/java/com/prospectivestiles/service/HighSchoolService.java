package com.prospectivestiles.service;

import java.util.List;

import com.prospectivestiles.domain.HighSchool;

public interface HighSchoolService {
	
	HighSchool getHighSchool(long id);
	List<HighSchool> getAllHighSchools();
	List<HighSchool> getHighSchoolsByUserEntityId(long userEntityId);
	void createHighSchool(HighSchool highSchool);
	void updateHighSchool(HighSchool highSchool);
	void delete(HighSchool highSchool);
	
}
