package com.prospectivestiles.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prospectivestiles.domain.EnrolledUserEntity;
import com.prospectivestiles.domain.UserEntity;
import com.prospectivestiles.service.ExportEnrolledApplicantsService;
import com.prospectivestiles.service.UserEntityService;

@Service
@Transactional
public class ExportEnrolledApplicantsServiceImpl implements ExportEnrolledApplicantsService {

	@Inject
	private UserEntityService userEntityService;
	
	@Override
	public List<EnrolledUserEntity> createEnrolledUserEntity() {
		
		List<UserEntity> enrolledUsers = userEntityService.findUserEntitiesByAccountState("enrolled");
		List<EnrolledUserEntity> enrolledUserEntities = new ArrayList<EnrolledUserEntity>();
		EnrolledUserEntity enrolledUserEntity;
		
		for (UserEntity enrolledUser : enrolledUsers) {
			
//			Collection<Object> listOfHighSchools = new ArrayList<Object>();
//			Collection<Object> listOfInstitutes = new ArrayList<Object>();
//			Collection<Object> listOfAddresses = new ArrayList<Object>();
//			Collection<HighSchool> highSchools = enrolledUser.getListOfHighSchools();
//			for (HighSchool highSchool : highSchools){
//				Map<String, Object> high = new HashMap<String, Object>();
//				high.put("name", highSchool.getName());
//				high.put("city", highSchool.getCity());
//				high.put("country", highSchool.getCountry());
//				
//				listOfHighSchools.add(high);
//			}
			
			
			String citizenship = (String) (enrolledUser.getCitizenship() != null ? enrolledUser.getCitizenship().getName() : enrolledUser.getCitizenship());
			String ethnicity = (String) (enrolledUser.getEthnicity() != null ? enrolledUser.getEthnicity().name() : enrolledUser.getEthnicity());
			String countryOfBirth = (String) (enrolledUser.getCountryOfBirth() != null ? enrolledUser.getCountryOfBirth().getName() : enrolledUser.getCountryOfBirth());
			String programOfStudy = (String) (enrolledUser.getProgramOfStudy() != null ? enrolledUser.getProgramOfStudy().getName() : enrolledUser.getProgramOfStudy());
			String term = (String) (enrolledUser.getTerm() != null ? enrolledUser.getTerm().getName() : enrolledUser.getTerm());
			
//			String citizenship = null;
//			String ethnicity = null;
//			String countryOfBirth = null;
//			String programOfStudy = null;
//			String term = null;
//			if (enrolledUser.getCitizenship() != null) {
//				citizenship = enrolledUser.getCitizenship().getName();
//			}
//			if (enrolledUser.getEthnicity() != null) {
//				ethnicity = enrolledUser.getEthnicity().name();
//			}
//			if (enrolledUser.getCountryOfBirth() != null) {
//				countryOfBirth = enrolledUser.getCountryOfBirth().getName();
//			}
//			if (enrolledUser.getProgramOfStudy() != null) {
//				programOfStudy = enrolledUser.getProgramOfStudy().getName();
//			}
//			if (enrolledUser.getTerm() != null) {
//				term = enrolledUser.getTerm().getName();
//			}
			
			enrolledUserEntity = new EnrolledUserEntity(
					enrolledUser.getId(), 
					enrolledUser.getApplicantId(), enrolledUser.getFirstName(), 
					enrolledUser.getLastName(), enrolledUser.getMiddleName(), 
					enrolledUser.getEmail(), enrolledUser.getHomePhone(), enrolledUser.getCellPhone(), 
					enrolledUser.getSsn(), citizenship, 
					countryOfBirth, ethnicity,
					enrolledUser.getSevisNumber(), enrolledUser.getDateCreated(), enrolledUser.getDob(), 
					enrolledUser.getGender(), 
					enrolledUser.isTransferee(), enrolledUser.isInternational(), enrolledUser.getDateEnrolled(), 
					programOfStudy, term, 
					enrolledUser.getAccountState(), 
					null, null,null
//					enrolledUser.getListOfHighSchools(), 
//					enrolledUser.getListOfInstitutes(), 
//					enrolledUser.getListOfAddresses()
					);
			
			enrolledUserEntities.add(enrolledUserEntity);
			
		}
		
		return enrolledUserEntities;
	}
	
	@Override
	public List<EnrolledUserEntity> createEnrolledUserEntity(Date dateEnrolled) {
		
		List<UserEntity> enrolledUsers = 
				userEntityService.findUserEntitiesEnrolledAfter(dateEnrolled, "enrolled");
		
//		List<UserEntity> enrolledUsers = userEntityService.findUserEntitiesByAccountState("enrolled");
		
		List<EnrolledUserEntity> enrolledUserEntities = new ArrayList<EnrolledUserEntity>();
		EnrolledUserEntity enrolledUserEntity;
		
		for (UserEntity enrolledUser : enrolledUsers) {
			
			String citizenship = (String) (enrolledUser.getCitizenship() != null ? enrolledUser.getCitizenship().getName() : enrolledUser.getCitizenship());
			String ethnicity = (String) (enrolledUser.getEthnicity() != null ? enrolledUser.getEthnicity().name() : enrolledUser.getEthnicity());
			String countryOfBirth = (String) (enrolledUser.getCountryOfBirth() != null ? enrolledUser.getCountryOfBirth().getName() : enrolledUser.getCountryOfBirth());
			String programOfStudy = (String) (enrolledUser.getProgramOfStudy() != null ? enrolledUser.getProgramOfStudy().getName() : enrolledUser.getProgramOfStudy());
			String term = (String) (enrolledUser.getTerm() != null ? enrolledUser.getTerm().getName() : enrolledUser.getTerm());
			
			enrolledUserEntity = new EnrolledUserEntity(
					enrolledUser.getId(), 
					enrolledUser.getApplicantId(), enrolledUser.getFirstName(), 
					enrolledUser.getLastName(), enrolledUser.getMiddleName(), 
					enrolledUser.getEmail(), enrolledUser.getHomePhone(), enrolledUser.getCellPhone(), 
					enrolledUser.getSsn(), citizenship, 
					countryOfBirth, ethnicity,
					enrolledUser.getSevisNumber(), enrolledUser.getDateCreated(), enrolledUser.getDob(), 
					enrolledUser.getGender(), 
					enrolledUser.isTransferee(), enrolledUser.isInternational(), enrolledUser.getDateEnrolled(), 
					programOfStudy, term, 
					enrolledUser.getAccountState(), 
					null, null,null
					);
			
			enrolledUserEntities.add(enrolledUserEntity);
			
		}
		
		return enrolledUserEntities;
	}


}
