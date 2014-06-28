package com.prospectivestiles.dao.hbn;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.prospectivestiles.dao.AssociatedUserDao;
import com.prospectivestiles.dao.EvaluationDao;
import com.prospectivestiles.domain.AssociatedUser;
import com.prospectivestiles.domain.Evaluation;

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

	


}
