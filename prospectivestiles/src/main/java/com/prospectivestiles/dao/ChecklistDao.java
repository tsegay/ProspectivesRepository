package com.prospectivestiles.dao;

import com.prospectivestiles.domain.Checklist;



public interface ChecklistDao extends Dao<Checklist> {
	
	Checklist getChecklistByUserEntityId(long userEntityId);
	
	/*long getChecklistIdByUserEntityId(long userEntityId);*/

}
