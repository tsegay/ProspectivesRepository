package com.prospectivestiles.service;

import java.util.List;

import com.prospectivestiles.domain.Term;

public interface TermService {
	
	Term getTerm(long id);
	List<Term> getAllTerms();
	Term findByName(String name);
//	List<Term> getTermsByUserEntityId(long userEntityId);
	void createTerm(Term term);
	void updateTerm(Term term);
	void delete(Term term);
	/**
	 * FOR TESTING
	 * @param termId
	 * @param zipcode
	 */
//	void updateTermZipCode(long termId, String zipcode);
	
}
