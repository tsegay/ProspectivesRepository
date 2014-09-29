package com.prospectivestiles.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.validation.constraints.NotNull;

@Entity
@NamedQueries(
		@NamedQuery(name = "findEmployersByUserEntityId", 
		query = "FROM Employer WHERE userEntity.id = :id")
		)
public class Employer extends BaseEntity implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// ======================================
    // =             Attributes             =
    // ======================================
	
	private String employerName;
	private String companyName;
	private Date employedFrom;
	private Date employedTo;
	private String position;
	
	private UserEntity userEntity;
	
	// ======================================
    // =            Constructors            =
    // ======================================

	public Employer() {
	}
	public Employer(boolean employed, String employerName, String companyName,
			Date employedFrom, Date employedTo, String position) {
		this.employerName = employerName;
		this.companyName = companyName;
		this.employedFrom = employedFrom;
		this.employedTo = employedTo;
		this.position = position;
	}
	
	// ======================================
	// =          Getters & Setters         =
	// ======================================
	
	public String getEmployerName() {
		return employerName;
	}
	public void setEmployerName(String employerName) {
		this.employerName = employerName;
	}
	@NotNull
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public Date getEmployedFrom() {
		return employedFrom;
	}
	public void setEmployedFrom(Date employedFrom) {
		this.employedFrom = employedFrom;
	}
	public Date getEmployedTo() {
		return employedTo;
	}
	public void setEmployedTo(Date employedTo) {
		this.employedTo = employedTo;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	
	@ManyToOne
	public UserEntity getUserEntity() {
		return userEntity;
	}
	public void setUserEntity(UserEntity userEntity) {
		this.userEntity = userEntity;
	}
	

	



}
