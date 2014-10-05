package com.prospectivestiles.dao;

import java.util.List;

import com.prospectivestiles.domain.Address;
import com.prospectivestiles.domain.AddressType;

public interface AddressDao extends Dao<Address> {

	List<Address> getAddressesByUserEntityId(long userEntityId);
//	void updateZipCode(long addressId, String zipcode);
	Address getAddressByUserEntityIdAndAddressType(long userEntityId, AddressType addressType);

}
