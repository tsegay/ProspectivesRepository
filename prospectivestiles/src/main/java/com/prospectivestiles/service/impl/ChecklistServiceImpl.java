package com.prospectivestiles.service.impl;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prospectivestiles.dao.ChecklistDao;
import com.prospectivestiles.domain.Checklist;
import com.prospectivestiles.service.ChecklistService;

@Service
@Transactional
public class ChecklistServiceImpl implements ChecklistService {
	
	@Inject
	private ChecklistDao checklistDao;

	@Override
	public Checklist getChecklist(long id) {
		return checklistDao.find(id);
	}

	@Override
	public List<Checklist> getAllChecklists() {
		return checklistDao.findAll();
	}

	@Override
	public Checklist getChecklistByUserEntityId(long userEntityId) {
		return checklistDao.getChecklistByUserEntityId(userEntityId);
	}

	@Override
	public void createChecklist(Checklist checklist) {
		checklistDao.create(checklist);
	}

	@Override
	public void updateChecklist(Checklist checklist) {
		Checklist checklistToUpdate = checklistDao.find(checklist.getId());

		checklistToUpdate.setNotes(checklist.getNotes());
		checklistToUpdate.setBankStmt(checklist.getBankStmt());
		checklistToUpdate.setF1Visa(checklist.getF1Visa());
		checklistToUpdate.setI20(checklist.getI20());
		checklistToUpdate.setApplicationFee(checklist.getApplicationFee());
		checklistToUpdate.setDiplome(checklist.getDiplome());
		checklistToUpdate.setFinancialAffidavit(checklist.getFinancialAffidavit());
		checklistToUpdate.setApplicationForm(checklist.getApplicationForm());
		checklistToUpdate.setEnrollmentAgreement(checklist.getEnrollmentAgreement());
		checklistToUpdate.setGrievancePolicy(checklist.getGrievancePolicy());
		checklistToUpdate.setRecommendationLetter(checklist.getRecommendationLetter());
		checklistToUpdate.setPassport(checklist.getPassport());
		checklistToUpdate.setTranscript(checklist.getTranscript());
		Date now = new Date();
		checklistToUpdate.setDateLastModified(now);
		checklistToUpdate.setLastModifiedBy(checklist.getLastModifiedBy());
		
		checklistDao.update(checklistToUpdate);
	}

	@Override
	public void deleteChecklist(Checklist checklist) {
		checklistDao.delete(checklist);
	}
	


}
