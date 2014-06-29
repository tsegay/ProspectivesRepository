package com.prospectivestiles.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

@Entity
@Table(name = "associatedUser")
@NamedQueries(
		@NamedQuery(name = "findAssociatedUserByUserEntityId", 
		query = "FROM AssociatedUser WHERE student.id = :id")
		)
public class AssociatedUser extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	// ======================================
    // =             Attributes             =
    // ======================================

	/**
	 * The student
	 * @OneToOne
	 */
	private UserEntity student;
	/**
	 * The admission officer/counselor delegated for the student
	 * @OneToOne
	 */
	private UserEntity admissionOfficer; 
	/**
	 * object references an unsaved transient instance - 
	 * save the transient instance before flushing: com.prospectivestiles.domain.UserEntity
	 */
	private long aoId;
	private String referrer;
	private String agent;
	
	// ======================================
    // =            Constructors            =
    // ======================================

	public AssociatedUser() {
	}
	
	public AssociatedUser(String referrer, String agent) {
		this.referrer = referrer;
		this.agent = agent;
	}

	// ======================================
    // =          Getters & Setters         =
    // ======================================
	
	@OneToOne
	@JoinColumn(name="student")
	public UserEntity getStudent() {
		return student;
	}
	public void setStudent(UserEntity student) {
		this.student = student;
	}

//	@OneToOne(cascade = CascadeType.ALL)
	@OneToOne
	@JoinColumn(name="admissionOfficer")
//	@JoinColumn(name="admissionOfficer", unique=false, nullable=true, insertable=true, updatable=true)
	public UserEntity getAdmissionOfficer() {
		return admissionOfficer;
	}
	public void setAdmissionOfficer(UserEntity admissionOfficer) {
		this.admissionOfficer = admissionOfficer;
	}
	
	@Transient
	public long getAoId() {
		return aoId;
	}
	public void setAoId(long aoId) {
		this.aoId = aoId;
	}

	public String getReferrer() {
		return referrer;
	}
	public void setReferrer(String referrer) {
		this.referrer = referrer;
	}

	public String getAgent() {
		return agent;
	}
	public void setAgent(String agent) {
		this.agent = agent;
	}
	
		
}
