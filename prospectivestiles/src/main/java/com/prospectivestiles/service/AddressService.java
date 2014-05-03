package com.prospectivestiles.service;

import java.util.List;

import com.prospectivestiles.domain.Address;

public interface AddressService {
	
	Address getAddress(long id);
//	List<Address> getAllAddresses();
	List<Address> getAddressesByUserEntityId(long userEntityId);
	void createAddress(Address address);
	void updateAddress(Address address);
	void delete(Address address);
	void updateAddressZipCode(long addressId, String zipcode);
	
}
