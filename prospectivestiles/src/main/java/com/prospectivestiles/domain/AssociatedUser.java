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
@Table(name = "associatedUser")
@NamedQueries(
		@NamedQuery(name = "findAssociatedUserByUserEntityId", 
		query = "FROM AssociatedUser WHERE student.id = :id")
		)
public class AssociatedUser extends BaseEntity implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// ======================================
    // =             Attributes             =
    // ======================================

	/*private long id;*/
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
	private String referrer;
	private String agent;
	/*private Date dateCreated;
	private Date dateLastModified;*/
	
	// ======================================
    // =            Constructors            =
    // ======================================

	public AssociatedUser() {
	}
	
	
	public AssociatedUser(String referrer, String agent, UserEntity student,
			UserEntity admissionOfficer) {
		this.referrer = referrer;
		this.agent = agent;
		this.student = student;
		this.admissionOfficer = admissionOfficer;
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

	@OneToOne
	public UserEntity getStudent() {
		return student;
	}
	public void setStudent(UserEntity student) {
		this.student = student;
	}

	@OneToOne
	public UserEntity getAdmissionOfficer() {
		return admissionOfficer;
	}
	public void setAdmissionOfficer(UserEntity admissionOfficer) {
		this.admissionOfficer = admissionOfficer;
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

	/*public Date getDateCreated() {
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
	}*/
		
}
