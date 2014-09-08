package com.prospectivestiles.dao;

import java.util.List;

import com.prospectivestiles.domain.Term;

public interface TermDao extends Dao<Term> {

	List<Term> getTermsByUserEntityId(long userEntityId);
	Term findByName(String name);
	
//	void insertTerm(Term term);

}
