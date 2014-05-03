package com.prospectivestiles.dao.hbn;

import java.math.BigInteger;
import java.util.List;

//import javax.persistence.Query;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.prospectivestiles.dao.ChecklistDao;
import com.prospectivestiles.domain.Checklist;

@Repository
public class HbnChecklistDao extends AbstractHbnDao<Checklist> implements
		ChecklistDao {

	@Override
	public Checklist getChecklistByUserEntityId(long userEntityId) {

		Checklist checklist = new Checklist();
		try {
//			checklist = (Checklist) ((Query) getSession().createQuery(
//					"SELECT c FROM Checklist c WHERE c.userEntity.id = :id")
//					.setParameter("id", userEntityId)).getSingleResult();
			/*Session session = getSession();
			Query query = (Query) session.getNamedQuery("findChecklistByUserEntityId");
			query.setParameter("id", userEntityId);
			checklist = (Checklist) query.getSingleResult();
			
			return checklist;*/
			Session session = getSession();
			Query query = session.getNamedQuery("findChecklistByUserEntityId");
			query.setParameter("id", userEntityId);
			
			/**
			 * Shouldn't be this way. I have to change this. 
			 * I need to return a SingleResult as I only expcet one record.
			 * 
			 */
			/*List<Checklist> checklists = query.list();
			for (Checklist c : checklists) {
				System.out.println("####### HbnChecklistDao. checklist: " + c.getId());
				checklist = c;
			}*/
			
			checklist = (Checklist) query.uniqueResult();
			
			return checklist;
			
		} catch (Exception e) {
			checklist = null;
			e.printStackTrace();
		}
		return checklist;
	}


}
