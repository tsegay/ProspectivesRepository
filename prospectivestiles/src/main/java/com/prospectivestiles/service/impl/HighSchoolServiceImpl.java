package com.prospectivestiles.service.impl;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prospectivestiles.dao.HighSchoolDao;
import com.prospectivestiles.domain.HighSchool;
import com.prospectivestiles.service.HighSchoolService;

@Service
@Transactional
public class HighSchoolServiceImpl implements HighSchoolService {
	
	@Inject
	HighSchoolDao highSchoolDao;

	@Override
	public HighSchool getHighSchool(long id) {
		return highSchoolDao.find(id);
	}

	@Override
	public List<HighSchool> getAllHighSchools() {
		return highSchoolDao.findAll();
	}

	@Override
	public void createHighSchool(HighSchool highSchool) {
		highSchoolDao.create(highSchool);
	}
	
	@Override
	public void updateHighSchool(HighSchool highSchool) {
		HighSchool highSchoolToUpdate = highSchoolDao.find(highSchool.getId());
		
		highSchoolToUpdate.setAttendedFrom(highSchool.getAttendedFrom());
		highSchoolToUpdate.setAttendedTo(highSchool.getAttendedTo());
		highSchoolToUpdate.setCity(highSchool.getCity());
		highSchoolToUpdate.setCountry(highSchool.getCountry());
		highSchoolToUpdate.setDiplome(highSchool.isDiplome());
		highSchoolToUpdate.setgED(highSchool.isgED());
		highSchoolToUpdate.setName(highSchool.getName());
		highSchoolToUpdate.setState(highSchool.getState());
		highSchoolToUpdate.setZip(highSchool.getZip());
		highSchoolToUpdate.setDiplomeAwardedDate(highSchool.getDiplomeAwardedDate());
		highSchoolToUpdate.setgEDAwardedDate(highSchool.getgEDAwardedDate());
		Date now = new Date();
		highSchoolToUpdate.setDateLastModified(now);
		highSchoolToUpdate.setLastModifiedBy(highSchool.getLastModifiedBy());
		
		highSchoolDao.update(highSchoolToUpdate);
	}
	
	@Override
	public void delete(HighSchool highSchool) {
		highSchoolDao.delete(highSchool);
		
	}

	@Override
	public List<HighSchool> getHighSchoolsByUserEntityId(long userEntityId) {
		return highSchoolDao.getHighSchoolsByUserEntityId(userEntityId);
	}


}
