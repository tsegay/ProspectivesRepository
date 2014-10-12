package com.prospectivestiles.service;

import java.util.Date;
import java.util.List;

import com.prospectivestiles.domain.EnrolledUserEntity;

public interface ExportEnrolledApplicantsService {

	
	List<EnrolledUserEntity> createEnrolledUserEntity();

	List<EnrolledUserEntity> createEnrolledUserEntity(Date dateEnrolled);

	
}
