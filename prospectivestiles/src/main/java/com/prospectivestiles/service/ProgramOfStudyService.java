package com.prospectivestiles.service;

import java.util.List;

import com.prospectivestiles.domain.ProgramOfStudy;

public interface ProgramOfStudyService {
	
	ProgramOfStudy getProgramOfStudy(long id);
	List<ProgramOfStudy> getAllProgramOfStudies();
	ProgramOfStudy findByName(String name);
//	List<ProgramOfStudy> getProgramOfStudiesByUserEntityId(long userEntityId);
	void createProgramOfStudy(ProgramOfStudy programOfStudy);
	void updateProgramOfStudy(ProgramOfStudy programOfStudy);
	void delete(ProgramOfStudy programOfStudy);
	/**
	 * FOR TESTING
	 * @param programOfStudyId
	 * @param zipcode
	 */
//	void updateProgramOfStudyZipCode(long programOfStudyId, String zipcode);
	
}
