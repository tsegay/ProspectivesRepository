package com.prospectivestiles.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;

@Entity
@NamedQuery(
		name = "findInstitutesByUserEntityId",
		query = "from Institute where userEntity.id = :id")
public class Institute extends Education {
	
	// ======================================
	// =             Attributes             =
	// ======================================

	private String programOfStudy;
	private String levelOfStudy;
	private Date graduationDate;
//	@ManyToOne
//	private ProStudent proStudentInstitute = new ProStudent();
	private UserEntity userEntity = new UserEntity();
	
	// ======================================
    // =            Constructors            =
    // ======================================

	public Institute() {
		super();
	}
	public Institute(String name, String country, String state, String zip,
			String city, Date attendedFrom, Date attendedTo) {
		super(name, country, state, zip, city, attendedFrom, attendedTo);
	}
	public Institute(String name, String country, String state, String zip,
			String city, Date attendedFrom, Date attendedTo,
			String programOfStudy, String levelOfStudy, Date graduationDate) {
		super(name, country, state, zip, city, attendedFrom, attendedTo);
		this.programOfStudy = programOfStudy;
		this.levelOfStudy = levelOfStudy;
		this.graduationDate = graduationDate;
	}

	// ======================================
    // =          Getters & Setters         =
    // ======================================
	
	public String getProgramOfStudy() {
		return programOfStudy;
	}
	public void setProgramOfStudy(String programOfStudy) {
		this.programOfStudy = programOfStudy;
	}
	public String getLevelOfStudy() {
		return levelOfStudy;
	}
	public void setLevelOfStudy(String levelOfStudy) {
		this.levelOfStudy = levelOfStudy;
	}
	public Date getGraduationDate() {
		return graduationDate;
	}
	public void setGraduationDate(Date graduationDate) {
		this.graduationDate = graduationDate;
	}
	@ManyToOne
	public UserEntity getUserEntity() {
		return userEntity;
	}
	public void setUserEntity(UserEntity userEntity) {
		this.userEntity = userEntity;
	}
	
}
