package com.prospectivestiles.service.impl;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prospectivestiles.dao.AddressDao;
import com.prospectivestiles.dao.ProgramOfStudyDao;
import com.prospectivestiles.domain.Address;
import com.prospectivestiles.domain.ProgramOfStudy;
import com.prospectivestiles.service.AddressService;
import com.prospectivestiles.service.ProgramOfStudyService;

@Service
@Transactional
public class ProgramOfStudyServiceImpl implements ProgramOfStudyService {
	
	@Inject
	ProgramOfStudyDao programOfStudyDao;

	@Override
	public ProgramOfStudy getProgramOfStudy(long id) {
		return programOfStudyDao.find(id);
	}

	@Override
	public List<ProgramOfStudy> getAllProgramOfStudies() {
		return programOfStudyDao.findAll();
	}

	@Override
	public ProgramOfStudy findByName(String name) {
		return programOfStudyDao.findByName(name);
	}
	
	@Override
	public void createProgramOfStudy(ProgramOfStudy programOfStudy) {
		programOfStudyDao.create(programOfStudy);
	}

	@Override
	public void updateProgramOfStudy(ProgramOfStudy programOfStudy) {
		
		ProgramOfStudy programOfStudyToUpdate = programOfStudyDao.find(programOfStudy.getId());
		programOfStudyToUpdate.setName(programOfStudy.getName());
		programOfStudyToUpdate.setShortName(programOfStudy.getShortName());
		programOfStudyToUpdate.setDescription(programOfStudy.getDescription());
//		programOfStudyToUpdate.setListOfUserEntity(programOfStudy.getListOfUserEntity());
		Date now = new Date();
		programOfStudyToUpdate.setDateLastModified(now);
		programOfStudyToUpdate.setLastModifiedBy(programOfStudy.getLastModifiedBy());
		
		programOfStudyDao.update(programOfStudyToUpdate);
	}

	@Override
	public void delete(ProgramOfStudy programOfStudy) {
		programOfStudyDao.delete(programOfStudy);
	}



	

}
