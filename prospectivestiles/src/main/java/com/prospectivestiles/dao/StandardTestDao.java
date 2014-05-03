package com.prospectivestiles.dao;

import java.util.List;

import com.prospectivestiles.domain.StandardTest;

public interface StandardTestDao extends Dao<StandardTest> {

	List<StandardTest> getStandardTestsByUserEntityId(long userEntityId);

}
