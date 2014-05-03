package com.prospectivestiles.dao.hbn;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.prospectivestiles.dao.EvaluationDao;
import com.prospectivestiles.domain.Evaluation;

@Repository
public class HbnEvaluationDao extends AbstractHbnDao<Evaluation> implements
		EvaluationDao {

	@Override
	public Evaluation getEvaluationByUserEntityId(long userEntityId) {

		/**
		 * I should use try and catch blocks with other method in the other classes too.
		 * 
		 */
		Evaluation evaluation = new Evaluation();
		try {
			Session session = getSession();
			Query query = session.getNamedQuery("findEvaluationByUserEntityId");
			query.setParameter("id", userEntityId);
			
			
			
			evaluation = (Evaluation) query.uniqueResult();
			
			return evaluation;
			
		} catch (Exception e) {
			evaluation = null;
			e.printStackTrace();
		}
		return evaluation;
	}

	


}
