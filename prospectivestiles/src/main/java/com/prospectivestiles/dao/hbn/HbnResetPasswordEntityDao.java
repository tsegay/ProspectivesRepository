package com.prospectivestiles.dao.hbn;

import org.springframework.stereotype.Repository;

import com.prospectivestiles.dao.ResetPasswordEntityDao;
import com.prospectivestiles.domain.ResetPasswordEntity;

@Repository
public class HbnResetPasswordEntityDao extends AbstractHbnDao<ResetPasswordEntity> implements
	ResetPasswordEntityDao {

	@Override
	public ResetPasswordEntity getResetPasswordEntityByUserEntityId(
			long userEntityId) {
		return null;
	}




}
