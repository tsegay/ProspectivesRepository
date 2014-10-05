package com.prospectivestiles.dao.hbn;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.prospectivestiles.dao.AddressDao;
import com.prospectivestiles.domain.Address;
import com.prospectivestiles.domain.AddressType;

@Repository
public class HbnAddressDao extends AbstractHbnDao<Address> implements AddressDao {

//	@Inject private JdbcTemplate jdbcTemplate;
//	
//	private static final String FIND_ADDRESS1_SQL =
//			"select address1 from address where id = ?";
//	private static final String UPDATE_ZIPCODE_SQL =
//			"update address set zipcode = ? where id = ?";
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Address> getAddressesByUserEntityId(long userEntityId) {
		Session session = getSession();
		Query query = session.getNamedQuery("findAddressesByUserEntityId");
		query.setParameter("id", userEntityId);
		
		List<Address> addresses = query.list();
		
		return addresses;
	}
	/**
	 * Return a single address for a user. Eg Home address or mailing address
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Address getAddressByUserEntityIdAndAddressType(long userEntityId, AddressType addressType) {
		Session session = getSession();
		Query query = session.getNamedQuery("findAddressByUserEntityIdAndAddressType");
		query.setParameter("id", userEntityId);
		query.setParameter("addressType", addressType);
		
		List<Address> addresses = query.list();
		Address address = null;
		
//		if (addresses != null) {
		if (addresses.size() != 0) {
			address = addresses.get(0);
		} 
		
		return address;
	}
	
	/**
	 * I think this is FOR TESTING PURPOSE :::: CHECK IT OUT!!
	 */
//	public String findAddress1ById(String username) {
//		return jdbcTemplate.queryForObject(
//				FIND_ADDRESS1_SQL, new Object[] { username }, String.class);
//	}
	
	/**
	 * FOR TESTING PURPOSE
	 */
//	@Override
//	public void updateZipCode(long addressId, String zipcode) {
//		//jdbcTemplate.update(UPDATE_PASSWORD_SQL, encPassword, account.getUsername());
//		jdbcTemplate.update(UPDATE_ZIPCODE_SQL, zipcode, addressId);
//	}

}
