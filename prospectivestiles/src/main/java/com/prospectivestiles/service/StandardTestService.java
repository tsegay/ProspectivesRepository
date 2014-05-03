package com.prospectivestiles.service;

import java.util.List;

import com.prospectivestiles.domain.StandardTest;

public interface StandardTestService {
	
	StandardTest getStandardTest(long id);
	List<StandardTest> getAllStandardTests();
	List<StandardTest> getStandardTestsByUserEntityId(long userEntityId);
	void createStandardTest(StandardTest standardTest);
	void updateStandardTest(StandardTest standardTest);
	void delete(StandardTest standardTest);
	
}
