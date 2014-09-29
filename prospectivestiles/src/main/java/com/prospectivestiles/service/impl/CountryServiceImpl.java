package com.prospectivestiles.service.impl;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prospectivestiles.dao.CountryDao;
import com.prospectivestiles.domain.Country;
import com.prospectivestiles.service.CountryService;

@Service
@Transactional
public class CountryServiceImpl implements CountryService {
	
	@Inject
	CountryDao countryDao;

	@Override
	public Country getCountry(long id) {
		return countryDao.find(id);
	}
	@Override
	public List<Country> getAllCountries() {
		return countryDao.findAll();
	}
	@Override
	public void createCountry(Country country) {
		countryDao.create(country);
	}
//	@Override
//	public Country findByName(String name) {
//		return countryDao.findByName(name);
//	}
//	@Override
//	public List<Country> getCountriesByUserEntityId(long userEntityId) {
//		return countryDao.getCountrysByUserEntityId(userEntityId);
//	}


//	@Override
//	public void updateCountry(Country country) {
//		
//		Country countryToUpdate = countryDao.find(country.getId());
//		countryToUpdate.setName(country.getName());
//		countryToUpdate.setStartDate(country.getStartDate());
//		countryToUpdate.setEndDate(country.getEndDate());
//		countryToUpdate.setDuration(country.getDuration());
//		Date now = new Date();
//		countryToUpdate.setDateLastModified(now);
//		countryToUpdate.setLastModifiedBy(country.getLastModifiedBy());
//		
//		countryDao.update(countryToUpdate);
//	}
//
//	@Override
//	public void delete(Country country) {
//		countryDao.delete(country);
//	}



}
