package com.prospectivestiles.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity
@Table(name = "evaluation")
@NamedQueries({
	@NamedQuery(
    		name = "findEvaluationByUserEntityId",
    		query = "FROM Evaluation WHERE userEntity.id = :id"),
	@NamedQuery(
    		name = "findEvaluationsByStatus",
    		query = "FROM Evaluation WHERE status = :status"),
}) 
public class Evaluation extends BaseEntity implements Serializable {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// ======================================
    // =             Attributes             =
    // ======================================

	/**
	 * Values for the ff are: valid, invalid, incomplete, not reviewed
	 */
	private String f1Visa;
	private String bankStmt;
	private String i20;
	private String passport;
	private String financialAffidavit;
	private String applicationFee;
	private String transcript;
	private String diplome;
	private String applicationForm;
	private String enrollmentAgreement;
	private String grievancePolicy;
	private String recommendationLetter;
	/**
	 * Sources of money are: Family, Personal
	 */
	private String sourceOfMoney;
	private String amountOfMoney;
	/**
	 * remarks and comments in the evaluation process
	 */
	private String notes;
	
//	@OneToOne
	/**
	 * The student
	 */
	private UserEntity userEntity;
	
	/**
	 * what are the qualifications the student has that enable him to get admission
	 */
//	@Size(max=1000)
//	private String studentQualification;
	/**
	 * Admission Officer report
	 */
//	@Size(max=1000)
	private String admnOfficerReport;
	/**
	 * NO LONGER NEEDED
	 * Using this.createdBy and associatedUser.admissionOfficer
	 * 
	 * The admission officer evaluating the student's records
	 */
//	private UserEntity admissionOfficer; 
	
	/**
	 * Status values are pending, in process, complete, admitted, (transfered to enrolled students portal)
	 */
//	private String status;
	/**
	 * Date admission was approved
	 */
	private Date dateAdmitted;
	private UserEntity admittedBy;
	
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
	public String getSourceOfMoney() {
		return sourceOfMoney;
	}
	public void setSourceOfMoney(String sourceOfMoney) {
		this.sourceOfMoney = sourceOfMoney;
	}
	public String getAmountOfMoney() {
		return amountOfMoney;
	}
	public void setAmountOfMoney(String amountOfMoney) {
		this.amountOfMoney = amountOfMoney;
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
	
//	@Size(max=1000)
//	public String getStudentQualification() {
//		return studentQualification;
//	}
//	public void setStudentQualification(String studentQualification) {
//		this.studentQualification = studentQualification;
//	}
	@Size(max=1000)
	public String getAdmnOfficerReport() {
		return admnOfficerReport;
	}
	public void setAdmnOfficerReport(String admnOfficerReport) {
		this.admnOfficerReport = admnOfficerReport;
	}
//	public String getStatus() {
//		return status;
//	}
//	public void setStatus(String status) {
//		this.status = status;
//	}
	public Date getDateAdmitted() {
		return dateAdmitted;
	}
	public void setDateAdmitted(Date dateAdmitted) {
		this.dateAdmitted = dateAdmitted;
	}
	@OneToOne
	@JoinColumn(name="admittedBy")
	public UserEntity getAdmittedBy() {
		return admittedBy;
	}
	public void setAdmittedBy(UserEntity admittedBy) {
		this.admittedBy = admittedBy;
	}
		
}
