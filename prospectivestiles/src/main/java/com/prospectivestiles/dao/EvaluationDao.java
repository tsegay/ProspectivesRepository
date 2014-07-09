package com.prospectivestiles.dao;

import java.util.List;

import com.prospectivestiles.domain.Evaluation;



public interface EvaluationDao extends Dao<Evaluation> {
	
	Evaluation getEvaluationByUserEntityId(long userEntityId);
	
	List<Evaluation> findEvaluationsByStatus(String status);
	
	long countByStatus(String status);
	
}
