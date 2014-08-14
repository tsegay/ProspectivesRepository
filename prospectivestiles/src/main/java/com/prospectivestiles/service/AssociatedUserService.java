package com.prospectivestiles.service;

import java.util.List;

import com.prospectivestiles.domain.AssociatedUser;

public interface AssociatedUserService {

	AssociatedUser getAssociatedUser(long id);
//	List<AssociatedUser> getAllAssociatedUsers();
	AssociatedUser getAssociatedUserByUserEntityId(long userEntityId);
	void createAssociatedUser(AssociatedUser associatedUser);
	void updateAssociatedUser(AssociatedUser associatedUser);
	void deleteAssociatedUser(AssociatedUser associatedUser);

	List<AssociatedUser> findAllAgents();
	List<AssociatedUser> findAllReferrers();
}
