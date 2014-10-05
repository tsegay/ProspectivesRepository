package com.prospectivestiles.service.impl;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prospectivestiles.dao.AddressDao;
import com.prospectivestiles.domain.Address;
import com.prospectivestiles.domain.AddressType;
import com.prospectivestiles.service.AddressService;

@Service
@Transactional
public class AddressServiceImpl implements AddressService {
	
	@Inject
	AddressDao addressDao;

	@Override
	public Address getAddress(long id) {
		return addressDao.find(id);
	}

	@Override
	public List<Address> getAddressesByUserEntityId(long userEntityId) {
		return addressDao.getAddressesByUserEntityId(userEntityId);
	}

	@Override
	public void createAddress(Address address) {
		addressDao.create(address);
	}

	@Override
	public void updateAddress(Address address) {
		
		Address addressToUpdate = addressDao.find(address.getId());
		addressToUpdate.setAddressType(address.getAddressType());
		addressToUpdate.setAddress1(address.getAddress1());
		addressToUpdate.setAddress2(address.getAddress2());
		addressToUpdate.setCity(address.getCity());
		addressToUpdate.setState(address.getState());
		addressToUpdate.setZipcode(address.getZipcode());
		addressToUpdate.setCountry(address.getCountry());
		Date now = new Date();
		addressToUpdate.setDateLastModified(now);
		addressToUpdate.setLastModifiedBy(address.getLastModifiedBy());
		
		addressDao.update(addressToUpdate);
	}

	@Override
	public void delete(Address address) {
		addressDao.delete(address);
	}

	/**
	 * FOR TESTING PURPOSE
	 */
//	@Override
//	public void updateAddressZipCode(long addressId, String zipcode) {
//		addressDao.updateZipCode(addressId, zipcode);
//	}

	@Override
	public Address getAddressByUserEntityIdAndAddressType(long userEntityId,
			AddressType addressType) {
		return addressDao.getAddressByUserEntityIdAndAddressType(userEntityId, addressType);
	}
	
	

}
