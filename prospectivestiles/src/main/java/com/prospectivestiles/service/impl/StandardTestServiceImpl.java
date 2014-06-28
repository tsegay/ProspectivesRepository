package com.prospectivestiles.service.impl;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prospectivestiles.dao.StandardTestDao;
import com.prospectivestiles.domain.StandardTest;
import com.prospectivestiles.service.StandardTestService;

@Service
@Transactional
public class StandardTestServiceImpl implements StandardTestService {
	
	@Inject
	private StandardTestDao standardTestDao;

	@Override
	public StandardTest getStandardTest(long id) {
		return standardTestDao.find(id);
	}

	@Override
	public List<StandardTest> getAllStandardTests() {
		return standardTestDao.findAll();
	}

	@Override
	public List<StandardTest> getStandardTestsByUserEntityId(long userEntityId) {
		return standardTestDao.getStandardTestsByUserEntityId(userEntityId);
	}

	@Override
	public void createStandardTest(StandardTest standardTest) {
		standardTestDao.create(standardTest);
	}

	@Override
	public void updateStandardTest(StandardTest standardTest) {
		StandardTest  standardTestToUpdate = standardTestDao.find(standardTest.getId());
		
		standardTestToUpdate.setName(standardTest.getName());
		standardTestToUpdate.setScore(standardTest.getScore());
		standardTestToUpdate.setValidTill(standardTest.getValidTill());
		Date now = new Date();
		standardTestToUpdate.setDateLastModified(now);
		standardTestToUpdate.setLastModifiedBy(standardTest.getLastModifiedBy());
		
		standardTestDao.update(standardTestToUpdate);
	}

	@Override
	public void delete(StandardTest standardTest) {
		standardTestDao.delete(standardTest);
	}
	
	

}
