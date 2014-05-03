package com.prospectivestiles.dao;

import java.util.List;

import com.prospectivestiles.domain.TestA;

public interface TestADao extends Dao<TestA> {

	List<TestA> getTestAsByUserEntityId(long userEntityId);
//	void updateZipCode(long addressId, String zipcode);

}
