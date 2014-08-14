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
		associatedUserDao.create(associatedUser);
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


}
