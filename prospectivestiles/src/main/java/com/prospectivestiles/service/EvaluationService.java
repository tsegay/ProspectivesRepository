package com.prospectivestiles.service;

import java.util.List;

import com.prospectivestiles.domain.Evaluation;

public interface EvaluationService {
	
	Evaluation getEvaluation(long id);
	List<Evaluation> getAllEvaluations();
	Evaluation getEvaluationByUserEntityId(long userEntityId);
	void createEvaluation(Evaluation evaluation);
	void updateEvaluation(Evaluation evaluation);
	void deleteEvaluation(Evaluation evaluation);

//	List<Evaluation> findEvaluationsByStatus(String status);
//	long countByStatus(String status);
}
