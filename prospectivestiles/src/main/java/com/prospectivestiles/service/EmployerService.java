package com.prospectivestiles.service;

import java.util.List;

import com.prospectivestiles.domain.Employer;

public interface EmployerService {
	
	Employer getEmployer(long id);
	List<Employer> getAllEmployers();
	List<Employer> getEmployersByUserEntityId(long userEntityId);
	void createEmployer(Employer employer);
	void updateEmployer(Employer employer);
	void delete(Employer employer);
	
}
