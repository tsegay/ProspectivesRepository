package com.prospectivestiles.service.impl;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prospectivestiles.dao.TermDao;
import com.prospectivestiles.domain.Term;
import com.prospectivestiles.service.TermService;

@Service
@Transactional
public class TermServiceImpl implements TermService {
	
	@Inject
	TermDao termDao;

	@Override
	public Term getTerm(long id) {
		return termDao.find(id);
	}
	@Override
	public List<Term> getAllTerms() {
		return termDao.findAllVisible();
	}
	@Override
	public Term findByName(String name) {
		return termDao.findByName(name);
	}
//	@Override
//	public List<Term> getTermsByUserEntityId(long userEntityId) {
//		return termDao.getTermsByUserEntityId(userEntityId);
//	}

	@Override
	public void createTerm(Term term) {
		termDao.create(term);
	}

	@Override
	public void updateTerm(Term term) {
		
		Term termToUpdate = termDao.find(term.getId());
		termToUpdate.setName(term.getName());
		termToUpdate.setStartDate(term.getStartDate());
		termToUpdate.setEndDate(term.getEndDate());
		termToUpdate.setDuration(term.getDuration());
//		termToUpdate.setListOfUserEntity(term.getListOfUserEntity());
		Date now = new Date();
		termToUpdate.setDateLastModified(now);
		termToUpdate.setLastModifiedBy(term.getLastModifiedBy());
		
		termDao.update(termToUpdate);
	}

	@Override
	public void delete(Term term) {
		termDao.delete(term);
	}



}
