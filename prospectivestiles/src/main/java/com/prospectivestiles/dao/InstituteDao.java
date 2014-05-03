package com.prospectivestiles.dao;

import java.util.List;

import com.prospectivestiles.domain.Institute;

public interface InstituteDao extends Dao<Institute> {

	List<Institute> getInstitutesByUserEntityId(long userEntityId);
	
}
