package com.prospectivestiles.dao.hbn;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.prospectivestiles.dao.RoleDao;
import com.prospectivestiles.domain.Role;

@Repository
public class HbnRoleDao extends AbstractHbnDao<Role> implements RoleDao {

	public Role findByName(String name) {
		Query q = getSession().getNamedQuery("findRoleByName");
		q.setParameter("name", name);
		return (Role) q.uniqueResult();
	}
}
