package com.prospectivestiles.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity
@Table(name = "checklist")
@NamedQueries(
		@NamedQuery(name = "findChecklistByUserEntityId", 
		query = "FROM Checklist WHERE userEntity.id = :id")
		)
public class Checklist extends BaseEntity implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// ======================================
    // =             Attributes             =
    // ======================================

//	private long id;
	private String f1Visa;
	private String i20;
	private String bankStmt;
	private String passport;
	private String financialAffidavit;
	// Don't need application form!!
	private String applicationForm;
	private String enrollmentAgreement;
	private String grievancePolicy;
	private String recommendationLetter;
	private String applicationFee;
	private String transcript;
	private String diplome;
	private String notes;
//	@OneToOne
	private UserEntity userEntity;
	
	
	// ======================================
    // =            Constructors            =
    // ======================================

	public Checklist() {
	}
	public Checklist(String f1Visa, 
			String bankStmt, String i20,
			String notes) {
		this.f1Visa = f1Visa;
		this.bankStmt = bankStmt;
		this.i20 = i20;
		this.notes = notes;
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
	public String getF1Visa() {
		return f1Visa;
	}
	public void setF1Visa(String f1Visa) {
		this.f1Visa = f1Visa;
	}
	public String getI20() {
		return i20;
	}
	public void setI20(String i20) {
		this.i20 = i20;
	}
	public String getBankStmt() {
		return bankStmt;
	}
	public void setBankStmt(String bankStmt) {
		this.bankStmt = bankStmt;
	}
	public String getPassport() {
		return passport;
	}
	public void setPassport(String passport) {
		this.passport = passport;
	}
	public String getFinancialAffidavit() {
		return financialAffidavit;
	}
	public void setFinancialAffidavit(String financialAffidavit) {
		this.financialAffidavit = financialAffidavit;
	}
	public String getApplicationForm() {
		return applicationForm;
	}
	public void setApplicationForm(String applicationForm) {
		this.applicationForm = applicationForm;
	}
	public String getEnrollmentAgreement() {
		return enrollmentAgreement;
	}
	public void setEnrollmentAgreement(String enrollmentAgreement) {
		this.enrollmentAgreement = enrollmentAgreement;
	}
	public String getGrievancePolicy() {
		return grievancePolicy;
	}
	public void setGrievancePolicy(String grievancePolicy) {
		this.grievancePolicy = grievancePolicy;
	}
	public String getRecommendationLetter() {
		return recommendationLetter;
	}
	public void setRecommendationLetter(String recommendationLetter) {
		this.recommendationLetter = recommendationLetter;
	}
	public String getApplicationFee() {
		return applicationFee;
	}
	public void setApplicationFee(String applicationFee) {
		this.applicationFee = applicationFee;
	}
	public String getTranscript() {
		return transcript;
	}
	public void setTranscript(String transcript) {
		this.transcript = transcript;
	}
	public String getDiplome() {
		return diplome;
	}
	public void setDiplome(String diplome) {
		this.diplome = diplome;
	}
	@Size(max=200)
	public String getNotes() {
		return notes;
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
