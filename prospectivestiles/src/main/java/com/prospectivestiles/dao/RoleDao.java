package com.prospectivestiles.dao;

import com.prospectivestiles.domain.Role;

public interface RoleDao extends Dao<Role> {
	
	Role findByName(String name);
}
