package com.prospectivestiles.dao;

import com.prospectivestiles.domain.Evaluation;



public interface EvaluationDao extends Dao<Evaluation> {
	
	Evaluation getEvaluationByUserEntityId(long userEntityId);
	
}
