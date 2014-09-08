package com.prospectivestiles.dao.hbn;

import org.springframework.stereotype.Repository;

import com.prospectivestiles.dao.ProgramOfStudyDao;
import com.prospectivestiles.domain.ProgramOfStudy;

@Repository
public class HbnProgramOfStudyDao extends AbstractHbnDao<ProgramOfStudy> implements ProgramOfStudyDao {

	@Override
	public ProgramOfStudy findByName(String name) {
		return (ProgramOfStudy) getSession()
				.getNamedQuery("findProgramOfStudyByName")
				.setParameter("name", name)
				.uniqueResult();
	}
	

}
