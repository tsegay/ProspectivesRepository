package com.prospectivestiles.service;

import java.util.List;

import com.prospectivestiles.domain.Country;

public interface CountryService {
	
	Country getCountry(long id);
	List<Country> getAllCountries();
	void createCountry(Country country);
//	Country findByName(String name);
//	List<Country> getCountriesByUserEntityId(long userEntityId);
//	void updateCountry(Country country);
//	void delete(Country country);
	
}
