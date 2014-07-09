package com.prospectivestiles.dao.hbn;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.prospectivestiles.dao.EvaluationDao;
import com.prospectivestiles.domain.Evaluation;
import com.prospectivestiles.domain.UserEntity;

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

	/**
	 * To get evaluations by status, eg 'admitted' students
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Evaluation> findEvaluationsByStatus(String status) {
		return (List<Evaluation>) getSession()
				.getNamedQuery("findEvaluationsByStatus")
				.setParameter("status", status)
				.list();
	}
	
	/**
	 * I want to count prospective students by their status
	 * Eg. admitted students
	 */
	@Override
	public long countByStatus(String status) {
		return (Long) getSession()
			.createQuery("SELECT count(*) FROM Evaluation e WHERE e.status = :status")
			.setParameter("status", status)
			.uniqueResult();
	}


}
