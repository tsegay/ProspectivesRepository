package com.prospectivestiles.service.impl;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prospectivestiles.dao.AssociatedUserDao;
import com.prospectivestiles.dao.ChecklistDao;
import com.prospectivestiles.dao.EvaluationDao;
import com.prospectivestiles.domain.AssociatedUser;
import com.prospectivestiles.domain.Checklist;
import com.prospectivestiles.domain.Evaluation;
import com.prospectivestiles.service.AssociatedUserService;
import com.prospectivestiles.service.ChecklistService;
import com.prospectivestiles.service.EvaluationService;

@Service
@Transactional
public class AssociatedUserServiceImpl implements AssociatedUserService {
	
	@Inject
	private AssociatedUserDao associatedUserDao;
	
	@Override
	public AssociatedUser getAssociatedUser(long id) {
		return associatedUserDao.find(id);
	}

	@Override
	public AssociatedUser getAssociatedUserByUserEntityId(long userEntityId) {
		return associatedUserDao.getAssociatedUserByUserEntityId(userEntityId);
	}

	@Override
	public void createAssociatedUser(AssociatedUser associatedUser) {
		
		/**
		 * Before creating an evaluation for an applicant,
		 * check applicant doesn't have an existing evaluation
		 * 
		 * If applicant doesn't have an evaluation already created, create the evaluation
		 * Else don't create the evaluation, instead redirect user to the existing evaluation page
		 * 
		 * TO DO: show err msg to user
		 */
		// Check if userEntity exist before calling the id
		if (validateAssociatedUser(associatedUser.getStudent().getId())) {
			associatedUserDao.create(associatedUser);
		}
	}

	@Override
	public void updateAssociatedUser(AssociatedUser associatedUser) {
		AssociatedUser associatedUserToUpdate = associatedUserDao.find(associatedUser.getId());
		
		associatedUserToUpdate.setStudent(associatedUser.getStudent());
		associatedUserToUpdate.setAdmissionOfficer(associatedUser.getAdmissionOfficer());
		associatedUserToUpdate.setAgent(associatedUser.getAgent());
		associatedUserToUpdate.setReferrer(associatedUser.getReferrer());
		Date now = new Date();
		associatedUserToUpdate.setDateLastModified(now);
		associatedUserToUpdate.setLastModifiedBy(associatedUser.getLastModifiedBy());
		
		associatedUserDao.update(associatedUserToUpdate);
	}

	@Override
	public void deleteAssociatedUser(AssociatedUser associatedUser) {
		associatedUserDao.delete(associatedUser);
		
	}

	@Override
	public List<AssociatedUser> findAllAgents() {
		return associatedUserDao.findAllAgents();
	}

	@Override
	public List<AssociatedUser> findAllReferrers() {
		return associatedUserDao.findAllReferrers();
	}
	
	private boolean validateAssociatedUser(long userEntityId) {
		System.out.println("validateEvaluation");
		
		boolean returnValue = true;
		
		if (associatedUserDao.getAssociatedUserByUserEntityId(userEntityId) != null) {
			System.out.println("inside validateAssociatedUser");
			System.out.println("evalId: " + associatedUserDao.getAssociatedUserByUserEntityId(userEntityId).getId());
			returnValue = false;
//			errors.rejectValue("email", "error.duplicateemail", new String[] { email }, null);
		}
		return returnValue;
	}


}
