package com.prospectivestiles.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

//@Entity
//@NamedQueries(
//		@NamedQuery(name = "findPoliciesByUserEntityId", 
//		query = "FROM Policies WHERE userEntity.id = :id")
//		)
public class Policies extends BaseEntity {

	// ======================================
    // =             Attributes             =
    // ======================================	
	
	
//	private long id;
	private boolean agree;
	private Date entryDate;
	
//	private Collection<UserEntity> listOfUserEntity = new ArrayList<UserEntity>();
	
	// ======================================
    // =            Constructors            =
    // ======================================

	public Policies() {
	}
	public Policies(boolean agree, Date entryDate
			) {
		this.agree = agree;
		this.entryDate = entryDate;
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
	public boolean isAgree() {
		return agree;
	}
	public void setAgree(boolean agree) {
		this.agree = agree;
	}
	public Date getEntryDate() {
		return entryDate;
	}
	public void setEntryDate(Date entryDate) {
		this.entryDate = entryDate;
	}
//	@OneToMany(mappedBy = "", cascade = CascadeType.ALL)
//	public Collection<UserEntity> getListOfUserEntity() {
//		return listOfUserEntity;
//	}
//	public void setListOfUserEntity(Collection<UserEntity> listOfUserEntity) {
//		this.listOfUserEntity = listOfUserEntity;
//	}
	

	


}
