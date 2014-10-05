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
import javax.validation.constraints.NotNull;

@Entity
@NamedQueries({
	@NamedQuery(
    		name = "findStudentAgreementsByUserEntityId",
    		query = "FROM StudentAgreement WHERE userEntity.id = :id"),
	@NamedQuery(
    		name = "findStudentAgreementByUserEntityIdAndAgreementName",
    		query = "FROM StudentAgreement WHERE userEntity.id = :id AND agreementName= :agreementName")
}) 
public class StudentAgreement extends BaseEntity {

	// ======================================
    // =             Attributes             =
    // ======================================	
	
	private AgreementName agreementName;
	private String signature;
	private boolean agree;
	private UserEntity userEntity;
	
	// ======================================
    // =            Constructors            =
    // ======================================

	public StudentAgreement() {
	}
	public StudentAgreement(boolean agree, String signature
			) {
		this.agree = agree;
		this.signature = signature;
	}
	
	// ======================================
    // =          Getters & Setters         =
    // ======================================
	public AgreementName getAgreementName() {
		return agreementName;
	}
	public void setAgreementName(AgreementName agreementName) {
		this.agreementName = agreementName;
	}
	@NotNull
	public String getSignature() {
		return signature;
	}
	public void setSignature(String signature) {
		this.signature = signature;
	}
	public boolean isAgree() {
		return agree;
	}
	public void setAgree(boolean agree) {
		this.agree = agree;
	}
	@ManyToOne
	public UserEntity getUserEntity() {
		return userEntity;
	}
	public void setUserEntity(UserEntity userEntity) {
		this.userEntity = userEntity;
	}

}
