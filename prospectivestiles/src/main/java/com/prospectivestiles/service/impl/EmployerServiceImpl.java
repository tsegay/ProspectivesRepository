package com.prospectivestiles.service.impl;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prospectivestiles.dao.EmployerDao;
import com.prospectivestiles.domain.Employer;
import com.prospectivestiles.service.EmployerService;

@Service
@Transactional
public class EmployerServiceImpl implements EmployerService {
	
	@Inject
	private EmployerDao employerDao;

	@Override
	public Employer getEmployer(long id) {
		return employerDao.find(id);
	}

	@Override
	public List<Employer> getAllEmployers() {
		return employerDao.findAll();
	}

	@Override
	public List<Employer> getEmployersByUserEntityId(
			long userEntityId) {
		return employerDao.getEmployersByUserEntityId(userEntityId);
	}

	@Override
	public void createEmployer(Employer employer) {
		employerDao.create(employer);
	}

	@Override
	public void updateEmployer(Employer employer) {
		
		Employer  employerToUpdate = employerDao.find(employer.getId());
		
//		employerToUpdate.setEmployed(employer.isEmployed());
		employerToUpdate.setEmployedFrom(employer.getEmployedFrom());
		employerToUpdate.setEmployedTo(employer.getEmployedTo());
		employerToUpdate.setPosition(employer.getPosition());
		employerToUpdate.setEmployerName(employer.getEmployerName());
		employerToUpdate.setCompanyName(employer.getCompanyName());
		
		employerDao.update(employerToUpdate);
	}

	@Override
	public void delete(Employer employer) {
		employerDao.delete(employer);
	}
	
	

}
