package com.prospectivestiles.rest;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.prospectivestiles.domain.EnrolledUserEntity;
import com.prospectivestiles.domain.UserEntity;
import com.prospectivestiles.service.ExportEnrolledApplicantsService;
import com.prospectivestiles.service.UserEntityService;
import com.prospectivestiles.web.AdminAccountController;

@Controller
public class AdminEnrolledApplicantsRESTController {

	@Autowired
	private UserEntityService userEntityService;
	@Inject
	private ExportEnrolledApplicantsService exportEnrolledApplicantsService;
	
	private static final Logger log = LoggerFactory.getLogger(AdminAccountController.class);
	
	@RequestMapping(value = "/enrolledApplicants", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public Map<String, Object> getAllEnrolledApplicants() {

		List<UserEntity> enrolledUsers = userEntityService.findUserEntitiesByAccountState("enrolled");
		

		Map<String, Object> data = new HashMap<String, Object>();
		data.put("enrolledUsers", enrolledUsers);
		data.put("enrolledUsersCount", enrolledUsers.size());

		return data;

	}
	
	/**
	 * Option 1
	 * Client pass a date and expects to get all applicants pushed for enrollment since that date
	 * Clients retrieves the last date when applicant was enrolled in portal from the application system
	 * then pass that date to get all applicants pushed for enrollment after that date
	 * 
	 * Option 2
	 * Client gets a list of all enrolled users and picks the recent one's.
	 * 
	 * 
	 */
	@RequestMapping(value = "/recentEnrolledApplicants", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public Map<String, Object> getAllRecentEnrolledApplicants() {
		
		List<EnrolledUserEntity> enrolledUserEntities = 
				exportEnrolledApplicantsService.createEnrolledUserEntity();
		
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("enrolledUserEntitiesCount", enrolledUserEntities.size());
		data.put("enrolledUserEntities", enrolledUserEntities);
		
		return data;
		
	}

	/**
	 * Converters and Formatters are two different things. 
	 * Here I am trying to convert a String 'date'(any part of the url is always a string) to java.util.Date
	 * So can't use @DateTimeFormat(pattern="yyyyMMdd"). 
	 * you are NOT formatting a Date in a specific format or locale. 
	 * @param date
	 * @return
	 */
	@RequestMapping(value = "/recentEnrolledApplicants/{date}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public Map<String, Object> getEnrolledApplicantsSince(
			@PathVariable(value = "date") @DateTimeFormat(iso=ISO.DATE) Date date) {

		List<EnrolledUserEntity> enrolledUserEntities = 
				exportEnrolledApplicantsService.createEnrolledUserEntity(date);
		
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("enrolledUserEntitiesCount", enrolledUserEntities.size());
		data.put("enrolledUserEntities", enrolledUserEntities);
		
		return data;
		
	}
	
}
