package com.prospectivestiles.service;

import java.util.List;

import com.prospectivestiles.domain.TestA;

public interface TestAService {
	
	TestA getTestA(long id);
	List<TestA> getAllTestAs();
	List<TestA> getTestAsByUserEntityId(long userEntityId);
	void createTestA(TestA testA);
	void updateTestA(TestA testA);
	void delete(TestA testA);
//	void updateTestAZipCode(long testAId, String zipcode);
	
}
