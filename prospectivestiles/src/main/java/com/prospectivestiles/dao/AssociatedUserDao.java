package com.prospectivestiles.dao;

import com.prospectivestiles.domain.AssociatedUser;



public interface AssociatedUserDao extends Dao<AssociatedUser> {
	
	AssociatedUser getAssociatedUserByUserEntityId(long userEntityId);
	
}
