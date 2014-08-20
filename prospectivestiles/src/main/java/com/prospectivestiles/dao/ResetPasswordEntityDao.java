package com.prospectivestiles.dao;

import java.util.List;

import com.prospectivestiles.domain.Evaluation;
import com.prospectivestiles.domain.ResetPasswordEntity;



public interface ResetPasswordEntityDao extends Dao<ResetPasswordEntity> {
	
	ResetPasswordEntity getResetPasswordEntityByUserEntityId(long userEntityId);
	
}
