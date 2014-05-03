package com.prospectivestiles.service;

import java.util.List;

import com.prospectivestiles.domain.Checklist;

public interface ChecklistService {
	
	Checklist getChecklist(long id);
	List<Checklist> getAllChecklists();
	Checklist getChecklistByUserEntityId(long userEntityId);
	void createChecklist(Checklist checklist);
	void updateChecklist(Checklist checklist);
	void deleteChecklist(Checklist checklist);

}
