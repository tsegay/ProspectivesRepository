package com.prospectivestiles.dao;

import java.util.List;

import com.prospectivestiles.domain.HighSchool;

public interface HighSchoolDao extends Dao<HighSchool> {

	List<HighSchool> getHighSchoolsByUserEntityId(long userEntityId);

}
