package com.prospectivestiles.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.validation.constraints.Size;

@Entity
@NamedQueries(
		@NamedQuery(name = "findEvaluationByUserEntityId", 
		query = "FROM Evaluation WHERE userEntity.id = :id")
		)
public class Evaluation {

    // ======================================
    // =             Attributes             =
    // ======================================

	private long id;
	private String f1Visa;
	private String bankStmt;
	private String i20;
	private String passport;
	private String financialAffidavit;
//	private String applicationForm;
	private String applicationFee;
	private String transcript;
	private String diplome;
	/**
	 * remarks and comments in the evaluation process
	 */
	private String notes;
	
//	@OneToOne
	/**
	 * The student
	 */
	private UserEntity userEntity;
	
	private Date dateCreated;
	private Date dateLastModified;
	/**
	 * what are the qualifications the student has that enable him to get admission
	 */
//	@Size(max=1000)
	private String studentQualification;
	/**
	 * Admission Officer report
	 */
//	@Size(max=1000)
	private String admnOfficerReport;
	/**
	 * The admission officer evaluating the student's records
	 */
//	@Column(name="admissionOfficer")
	private UserEntity admissionOfficer; 
	
	// ======================================
    // =            Constructors            =
    // ======================================

	public Evaluation() {
	}
	public Evaluation(String f1Visa, 
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
	
	@Id @GeneratedValue
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getF1Visa() {
		return f1Visa;
	}
	public void setF1Visa(String f1Visa) {
		this.f1Visa = f1Visa;
	}
	public String getBankStmt() {
		return bankStmt;
	}
	public void setBankStmt(String bankStmt) {
		this.bankStmt = bankStmt;
	}
	public String getI20() {
		return i20;
	}
	public void setI20(String i20) {
		this.i20 = i20;
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
	@Size(max=255)
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
	
	public Date getDateCreated() {
		return dateCreated;
	}
	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}
	public Date getDateLastModified() {
		return dateLastModified;
	}
	public void setDateLastModified(Date dateLastModified) {
		this.dateLastModified = dateLastModified;
	}
	@Size(max=1000)
	public String getStudentQualification() {
		return studentQualification;
	}
	public void setStudentQualification(String studentQualification) {
		this.studentQualification = studentQualification;
	}
	@Size(max=1000)
	public String getAdmnOfficerReport() {
		return admnOfficerReport;
	}
	public void setAdmnOfficerReport(String admnOfficerReport) {
		this.admnOfficerReport = admnOfficerReport;
	}
	@OneToOne
	@JoinColumn(name="admissionOfficer")
	public UserEntity getAdmissionOfficer() {
		return admissionOfficer;
	}
	public void setAdmissionOfficer(UserEntity admissionOfficer) {
		this.admissionOfficer = admissionOfficer;
	}
			
}
