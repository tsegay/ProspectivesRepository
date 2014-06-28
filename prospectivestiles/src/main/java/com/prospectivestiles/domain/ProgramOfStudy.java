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
import javax.persistence.OneToMany;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

/**
 * 
 * Can't use findProgramOfStudiesByUserEntityId
 */
@Entity
/*@NamedQueries(
		@NamedQuery(name = "findProgramOfStudiesByUserEntityId", 
		query = "FROM ProgramOfStudy WHERE userEntity.id = :id")
		)*/
public class ProgramOfStudy extends BaseEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5053033525545730990L;
	// ======================================
    // =             Attributes             =
    // ======================================
	
//	private long id;
	private String name;
	private String shortName;
	private String description;
	/*private Collection<String> courses;*/
	
	private Collection<UserEntity> listOfUserEntity = new ArrayList<UserEntity>();
	
	private Collection<TestA> listOfTestA = new ArrayList<TestA>();
	
	// ======================================
    // =            Constructors            =
    // ======================================
	
	public ProgramOfStudy() {
	}
	public ProgramOfStudy(String name, String shortName, String description) {
		this.name = name;
		this.shortName = shortName;
		this.description = description;
	}
	public ProgramOfStudy(String name) {
		this.name = name;
	}
	
	// ======================================
    // =          Getters & Setters         =
    // ======================================

	/*@Id @GeneratedValue
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}*/
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getShortName() {
		return shortName;
	}
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
//	@ManyToMany
//	@LazyCollection(LazyCollectionOption.FALSE)
//	@ManyToMany(mappedBy = "listOfProgramOfStudy", cascade = CascadeType.ALL)
	@OneToMany(mappedBy = "programOfStudy", cascade = CascadeType.ALL)
	public Collection<UserEntity> getListOfUserEntity() {
		return listOfUserEntity;
	}
	public void setListOfUserEntity(Collection<UserEntity> listOfUserEntity) {
		this.listOfUserEntity = listOfUserEntity;
	}
//	@ManyToMany
	@ManyToMany(mappedBy = "listOfProgramOfStudy", cascade = CascadeType.ALL, fetch=FetchType.EAGER)
	public Collection<TestA> getListOfTestA() {
		return listOfTestA;
	}
	public void setListOfTestA(Collection<TestA> listOfTestA) {
		this.listOfTestA = listOfTestA;
	}

	
}
