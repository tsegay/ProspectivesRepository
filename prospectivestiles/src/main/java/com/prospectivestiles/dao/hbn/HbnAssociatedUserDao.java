package com.prospectivestiles.dao.hbn;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.prospectivestiles.dao.AssociatedUserDao;
import com.prospectivestiles.dao.EvaluationDao;
import com.prospectivestiles.domain.AssociatedUser;
import com.prospectivestiles.domain.Evaluation;
import com.prospectivestiles.domain.UserEntity;

@Repository
public class HbnAssociatedUserDao extends AbstractHbnDao<AssociatedUser> implements
	AssociatedUserDao {

	@Override
	public AssociatedUser getAssociatedUserByUserEntityId(long userEntityId) {
		/**
		 * I should use try and catch blocks with other method in the other classes too.
		 * 
		 */
		AssociatedUser associatedUser = new AssociatedUser();
		try {
			Session session = getSession();
			Query query = session.getNamedQuery("findAssociatedUserByUserEntityId");
			query.setParameter("id", userEntityId);
			
			associatedUser = (AssociatedUser) query.uniqueResult();
			
			return associatedUser;
			
		} catch (Exception e) {
			associatedUser = null;
			e.printStackTrace();
		}
		return associatedUser;
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<AssociatedUser> findAllAgents() {
		
//		String hql = "FROM UserEntity u ORDER BY u.lastName ASC";
		
		/* I want to get all agents only, not referrers */
		String hql = "SELECT a FROM AssociatedUser a WHERE a.agent IS NOT NULL AND a.agent != '' ORDER BY a.agent ASC";

		Query query = getSession().createQuery(hql);
		
		List<AssociatedUser> results = query.list();
		
		return results;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<AssociatedUser> findAllReferrers() {
		
		/* I want to get all referrers only, not agents */
		String hql = "SELECT a FROM AssociatedUser a WHERE a.referrer IS NOT NULL AND a.referrer != '' ORDER BY a.referrer ASC";
		
		Query query = getSession().createQuery(hql);
		
		List<AssociatedUser> results = query.list();
		
		return results;
	}
	
	
	


}
