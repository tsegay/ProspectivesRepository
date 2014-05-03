package com.prospectivestiles.dao;

import java.util.List;

import com.prospectivestiles.domain.Employer;

public interface EmployerDao extends Dao<Employer> {

	List<Employer> getEmployersByUserEntityId(long userEntityId);

}
