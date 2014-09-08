package com.prospectivestiles.dao.hbn;

import java.util.List;

import javax.inject.Inject;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.prospectivestiles.dao.TermDao;
import com.prospectivestiles.domain.Term;

@Repository
public class HbnTermDao extends AbstractHbnDao<Term> implements TermDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<Term> getTermsByUserEntityId(long userEntityId) {
		Session session = getSession();
		Query query = session.getNamedQuery("findTermsByUserEntityId");
		query.setParameter("id", userEntityId);
		
		List<Term> terms = query.list();
		
		return terms;
	}


	@Override
	public Term findByName(String name) {
		return (Term) getSession()
				.getNamedQuery("findTermByName")
				.setParameter("name", name)
				.uniqueResult();
	}
	
}
