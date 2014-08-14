package com.prospectivestiles.dao;

import java.util.List;

import com.prospectivestiles.domain.AssociatedUser;



public interface AssociatedUserDao extends Dao<AssociatedUser> {
	
	AssociatedUser getAssociatedUserByUserEntityId(long userEntityId);

	List<AssociatedUser> findAllAgents();

	List<AssociatedUser> findAllReferrers();
	
}
