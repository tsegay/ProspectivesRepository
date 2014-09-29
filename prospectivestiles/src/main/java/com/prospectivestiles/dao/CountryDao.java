package com.prospectivestiles.dao;


import com.prospectivestiles.domain.Country;

public interface CountryDao extends Dao<Country> {

	Country getCountryByUserEntityId(long userEntityId);

}
