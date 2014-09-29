package com.prospectivestiles.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;

import org.codehaus.jackson.annotate.JsonIgnore;

@Entity
@NamedQuery(
		name = "findHighSchoolsByUserEntityId",
		query = "from HighSchool where userEntity.id = :id")
public class HighSchool extends Education {

	// ======================================
	// =             Attributes             =
	// ======================================
	
	private boolean diplome;
	private Date diplomeAwardedDate;
	private boolean gED;
	private Date gEDAwardedDate;
	private UserEntity userEntity = new UserEntity();
	
	// ======================================
    // =            Constructors            =
    // ======================================

	public HighSchool() {
		super();
	}
	public HighSchool(String name, Country country, String state, String zip,
			String city, Date attendedFrom, Date attendedTo) {
		super(name, country, state, zip, city, attendedFrom, attendedTo);
	}
	public HighSchool(String name, Country country, String state, String zip,
			String city, Date attendedFrom, Date attendedTo, boolean diplome,
			Date diplomeAwardedDate, boolean gED, Date gEDAwardedDate) {
		super(name, country, state, zip, city, attendedFrom, attendedTo);
		this.diplome = diplome;
		this.diplomeAwardedDate = diplomeAwardedDate;
		this.gED = gED;
		this.gEDAwardedDate = gEDAwardedDate;
	}

	// ======================================
    // =          Getters & Setters         =
    // ======================================
	
	public boolean isDiplome() {
		return diplome;
	}
	public void setDiplome(boolean diplome) {
		this.diplome = diplome;
	}
	public Date getDiplomeAwardedDate() {
		return diplomeAwardedDate;
	}
	public void setDiplomeAwardedDate(Date diplomeAwardedDate) {
		this.diplomeAwardedDate = diplomeAwardedDate;
	}
	public boolean isgED() {
		return gED;
	}
	public void setgED(boolean gED) {
		this.gED = gED;
	}
	public Date getgEDAwardedDate() {
		return gEDAwardedDate;
	}
	public void setgEDAwardedDate(Date gEDAwardedDate) {
		this.gEDAwardedDate = gEDAwardedDate;
	}
	@ManyToOne
	public UserEntity getUserEntity() {
		return userEntity;
	}
	public void setUserEntity(UserEntity userEntity) {
		this.userEntity = userEntity;
	}


}
