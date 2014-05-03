package com.prospectivestiles.domain;

public enum AddressType {

	HOME_ADDRESS("Home address"), WORK_ADDRESS("Work address"), 
	MAILING_ADDRESS("Mailing address"), FOREIGN_COUNTRY_ADDRESS("Foreign country address");
	
	private String nameAsString;
	
	private AddressType(String nameAsString) {
		this.nameAsString = nameAsString;
	}
	
	@Override
	public String toString() {
		return this.nameAsString;
	}
	
}
