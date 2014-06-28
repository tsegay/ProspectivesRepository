package com.prospectivestiles.service.impl;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prospectivestiles.dao.InstituteDao;
import com.prospectivestiles.domain.Institute;
import com.prospectivestiles.service.InstituteService;

@Service
@Transactional
public class InstituteServiceImpl implements InstituteService {
	
	@Inject
	InstituteDao instituteDao;

	@Override
	public Institute getInstitute(long id) {
		return instituteDao.find(id);
	}

	@Override
	public List<Institute> getAllInstitutes() {
		return instituteDao.findAll();
	}

	@Override
	public void createInstitute(Institute institute) {
		instituteDao.create(institute);
	}

	@Override
	public List<Institute> getInstitutesByUserEntityId(long userEntityId) {
		return instituteDao.getInstitutesByUserEntityId(userEntityId);
	}

	@Override
	public void updateInstitute(Institute institute) {
		Institute instituteToUpdate = instituteDao.find(institute.getId());
		instituteToUpdate.setName(institute.getName());
		instituteToUpdate.setState(institute.getState());
		instituteToUpdate.setCountry(institute.getCountry());
		instituteToUpdate.setAttendedFrom(institute.getAttendedFrom());
		instituteToUpdate.setAttendedTo(institute.getAttendedTo());
		instituteToUpdate.setCity(institute.getCity());
		instituteToUpdate.setGraduationDate(institute.getGraduationDate());
		instituteToUpdate.setLevelOfStudy(institute.getLevelOfStudy());
		instituteToUpdate.setProgramOfStudy(institute.getProgramOfStudy());
		instituteToUpdate.setZip(institute.getZip());
		Date now = new Date();
		instituteToUpdate.setDateLastModified(now);
		instituteToUpdate.setLastModifiedBy(institute.getLastModifiedBy());
		
		instituteDao.update(instituteToUpdate);
		
	}

	@Override
	public void delete(Institute institute) {
		instituteDao.delete(institute);
		
	}
	
	

}
