package com.prospectivestiles.service.impl;

import java.util.List;

import javax.inject.Inject;

import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prospectivestiles.dao.TestADao;
import com.prospectivestiles.domain.TestA;
import com.prospectivestiles.service.TestAService;

@Service
@Transactional
public class TestAServiceImpl implements TestAService {
	
	@Inject
	TestADao testADao;

	@Override
	public TestA getTestA(long id) {
//		return testADao.find(id);
		TestA testA = testADao.find(id);
		Hibernate.initialize(testA.getListOfProgramOfStudy());
		return testA;
	}

	@Override
	public List<TestA> getAllTestAs() {
		return testADao.findAll();
	}
	
	@Override
	public List<TestA> getTestAsByUserEntityId(long userEntityId) {
//		return testADao.getTestAsByUserEntityId(userEntityId);
		/**
		 * failed to lazily initialize a collection of role
		 * Lazy exceptions occur when you fetch an object typically containing a collection which is lazily loaded, and try to access that collection.
		 * avoid this problem by Initalizing the collection using Hibernate.initialize(obj);
		 */
		List<TestA> testAsByUser = testADao.getTestAsByUserEntityId(userEntityId);
		for (TestA testA : testAsByUser) {
			Hibernate.initialize(testA.getListOfProgramOfStudy());
		}
		return testAsByUser;
	}

	@Override
	public void createTestA(TestA testA) {
		testADao.create(testA);
	}

	@Override
	public void updateTestA(TestA testA) {
		
		TestA testAToUpdate = testADao.find(testA.getId());
		testAToUpdate.setAddress1(testA.getAddress1());
		testAToUpdate.setAddress2(testA.getAddress2());
		testAToUpdate.setCity(testA.getCity());
		testADao.update(testAToUpdate);
	}

	@Override
	public void delete(TestA testA) {
		testADao.delete(testA);
	}

	/**
	 * FOR TESTING PURPOSE
	 */
	/*@Override
	public void updateTestAZipCode(long testAId, String zipcode) {
		testADao.updateZipCode(testAId, zipcode);
	}*/

	

	
	
	

}
