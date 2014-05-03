package com.prospectivestiles.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@NamedQueries(
		@NamedQuery(name = "findTestAByUserEntityId", 
		query = "FROM TestA WHERE userEntity.id = :id")
		)
public class TestA implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// ======================================
    // =             Attributes             =
    // ======================================
	
	private long id;
	private String address1;
	private String address2;
	private String city;
	
	private UserEntity userEntity = new UserEntity();
	
	private Collection<ProgramOfStudy> listOfProgramOfStudy = new ArrayList<ProgramOfStudy>();
	
	private Term term;
	
	// this is transient to get program of form and add it to the list
	ProgramOfStudy programOfStudy = new ProgramOfStudy();
	
	// ======================================
    // =            Constructors            =
    // ======================================

	public TestA() {
	}
	
	public TestA(String address1, String address2, String city,
			String state, String zipcode, String country) {
		this.address1 = address1;
		this.address2 = address2;
		this.city = city;
	}

	// ======================================
    // =          Getters & Setters         =
    // ======================================
	
	@Id @GeneratedValue
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}


	@NotNull
	@Size(min = 1, max = 50)
	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	@NotNull
	@Size(min = 1, max = 50)
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@ManyToOne
	public UserEntity getUserEntity() {
		return userEntity;
	}

	public void setUserEntity(UserEntity userEntity) {
		this.userEntity = userEntity;
	}
	
//	@ManyToMany(mappedBy = "listOfTestA", cascade = CascadeType.ALL, fetch=FetchType.EAGER)
	@ManyToMany()
	public Collection<ProgramOfStudy> getListOfProgramOfStudy() {
		return listOfProgramOfStudy;
	}
	public void setListOfProgramOfStudy(
			Collection<ProgramOfStudy> listOfProgramOfStudy) {
		this.listOfProgramOfStudy = listOfProgramOfStudy;
	}
	@ManyToOne()
//	@ManyToOne(cascade = CascadeType.ALL)
	public Term getTerm() {
		return term;
	}
	public void setTerm(Term term) {
		this.term = term;
	}
	
	@Transient
	public ProgramOfStudy getProgramOfStudy() {
		return programOfStudy;
	}
	public void setProgramOfStudy(ProgramOfStudy programOfStudy) {
		this.programOfStudy = programOfStudy;
	}


	
}
