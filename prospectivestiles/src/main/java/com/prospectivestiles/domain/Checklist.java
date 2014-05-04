package com.prospectivestiles.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "checklist")
@NamedQueries(
		@NamedQuery(name = "findChecklistByUserEntityId", 
		query = "FROM Checklist WHERE userEntity.id = :id")
		)
public class Checklist implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// ======================================
    // =             Attributes             =
    // ======================================

	private long id;
	private boolean f1Visa;
	private boolean i20;
	private boolean bankStmt;
	private boolean passport;
	private boolean financialAffidavit;
	// Don't need application form!!
//	private boolean applicationForm;
	private boolean applicationFee;
	private boolean transcript;
	private boolean diplome;
	private String notes;
//	@OneToOne
	private UserEntity userEntity;
	
	
	// ======================================
    // =            Constructors            =
    // ======================================

	public Checklist() {
	}
	public Checklist(boolean f1Visa, 
			boolean bankStmt, boolean i20,
			String notes) {
		this.f1Visa = f1Visa;
		this.bankStmt = bankStmt;
		this.i20 = i20;
		this.notes = notes;
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
	public boolean isF1Visa() {
		return f1Visa;
	}
	public void setF1Visa(boolean f1Visa) {
		this.f1Visa = f1Visa;
	}
	public boolean isBankStmt() {
		return bankStmt;
	}
	public void setBankStmt(boolean bankStmt) {
		this.bankStmt = bankStmt;
	}
	public boolean isI20() {
		return i20;
	}
	public void setI20(boolean i20) {
		this.i20 = i20;
	}
	public String getNotes() {
		return notes;
	}
	public boolean isPassport() {
		return passport;
	}
	public void setPassport(boolean passport) {
		this.passport = passport;
	}
	public boolean isFinancialAffidavit() {
		return financialAffidavit;
	}
	public void setFinancialAffidavit(boolean financialAffidavit) {
		this.financialAffidavit = financialAffidavit;
	}
	public boolean isApplicationFee() {
		return applicationFee;
	}
	public void setApplicationFee(boolean applicationFee) {
		this.applicationFee = applicationFee;
	}
	public boolean isTranscript() {
		return transcript;
	}
	public void setTranscript(boolean transcript) {
		this.transcript = transcript;
	}
	public boolean isDiplome() {
		return diplome;
	}
	public void setDiplome(boolean diplome) {
		this.diplome = diplome;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	@OneToOne
	public UserEntity getUserEntity() {
		return userEntity;
	}
	public void setUserEntity(UserEntity userEntity) {
		this.userEntity = userEntity;
	}
	
	
}
