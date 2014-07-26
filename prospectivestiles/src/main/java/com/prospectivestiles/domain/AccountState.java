package com.prospectivestiles.domain;

public enum AccountState {
	
	/**
	 * This are different states of an account.
	 * When applicant first create an account it is defaulted to PENDING state
	 * When Admission Office start processing the application, the state becomes INPROCESS
	 * When the evaluation for every item is 'valid' or 'notrequired' then the state becomes COMPLETE
	 * When the Admission Office clicks on admit applicant, then the state becomes ADMITTED
	 * Finally the appicant is pushed to the Enrolled students DB, the the state becomes ENROLLED
	 * If an applicant initiates an application but never follows it up, AO can FREEZE the account
	 */
	
	PENDING, INPROCESS, COMPLETE, ADMITTED, ENROLLED, FROZEN;

//	HOME_ADDRESS("Home address"), WORK_ADDRESS("Work address"), 
//	MAILING_ADDRESS("Mailing address"), FOREIGN_COUNTRY_ADDRESS("Foreign country address");
	
//	private String nameAsString;
//	
//	private AccountState(String nameAsString) {
//		this.nameAsString = nameAsString;
//	}
//	
//	@Override
//	public String toString() {
//		return this.nameAsString;
//	}
	
}
