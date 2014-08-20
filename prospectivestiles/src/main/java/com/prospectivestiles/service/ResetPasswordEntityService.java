package com.prospectivestiles.service;

import java.util.List;

import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;

import com.prospectivestiles.domain.ResetPasswordEntity;

public interface ResetPasswordEntityService {
	
	ResetPasswordEntity getResetPasswordEntity(long id);
	List<ResetPasswordEntity> getAllResetPasswordEntitys();
	ResetPasswordEntity getResetPasswordEntityByUserEntityId(long userEntityId);
	void createResetPasswordEntity(ResetPasswordEntity resetPasswordEntity);
	void updateResetPasswordEntity(ResetPasswordEntity resetPasswordEntity);
	void deleteResetPasswordEntity(ResetPasswordEntity resetPasswordEntity);
	void saveResetPasswordEntityAndSendEmail(ResetPasswordEntity resetPasswordEntity,String url);
	void updatePassword(ResetPasswordEntity origResetPasswordEntity,
			Errors errors);

}
