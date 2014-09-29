package com.prospectivestiles.domain;

public enum LevelOfStudy {
	
	HIGHSCHOOL("High School"),
	CERTIFICATE("Certificate"),
	ASSOCIATES_OF_ARTS("Associate of Arts"),
	BACHELOR("Bachelor"),
	MASTERS("Masters"),
	POST_MASTERS("Post Masters");
	
	private String nameAsString;
	
	private LevelOfStudy(String nameAsString) {
		this.nameAsString = nameAsString;
	}
	
	@Override
	public String toString() {
		return this.nameAsString;
	}
	
}

