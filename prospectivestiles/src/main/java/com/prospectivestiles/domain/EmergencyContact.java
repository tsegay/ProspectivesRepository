package com.prospectivestiles.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
@NamedQueries(
		@NamedQuery(name = "findEmergencyContactsByUserEntityId", 
		query = "FROM EmergencyContact WHERE userEntity.id = :id")
		)
public class EmergencyContact {
	
	// ======================================
    // =             Attributes             =
    // ======================================
	
	private long id;
	private String firstName;
	private String lastName;
	private String middleName;
	private String phone;
	private String email;
	private String relationship;
	
	private UserEntity userEntity;
	

	// ======================================
    // =            Constructors            =
    // ======================================


	public EmergencyContact() {
	}
	public EmergencyContact(String firstName, String lastName, String phone, String email,
			String relationship) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.phone = phone;
		this.email = email;
		this.relationship = relationship;
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
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getMiddleName() {
		return middleName;
	}
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getRelationship() {
		return relationship;
	}
	public void setRelationship(String relationship) {
		this.relationship = relationship;
	}
	@ManyToOne
	public UserEntity getUserEntity() {
		return userEntity;
	}
	public void setUserEntity(UserEntity userEntity) {
		this.userEntity = userEntity;
	}
	
	
	
}
