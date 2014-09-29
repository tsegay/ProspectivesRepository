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
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

/**
 * 
 * Can't use findProgramOfStudiesByUserEntityId
 */
@Entity
@NamedQuery(name = "findProgramOfStudyByName", query = "from ProgramOfStudy where name= :name")
public class ProgramOfStudy extends BaseEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5053033525545730990L;
	// ======================================
    // =             Attributes             =
    // ======================================
	
	private String name;
	private String shortName;
	private String description;
	/*private Collection<String> courses;*/
	
	private Collection<UserEntity> listOfUserEntity = new ArrayList<UserEntity>();
	
	
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

	
}
