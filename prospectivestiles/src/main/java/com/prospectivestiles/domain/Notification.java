package com.prospectivestiles.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
@NamedQueries({
	@NamedQuery(
    		name = "findNotificationsByUserEntityId",
    		query = "FROM Notification WHERE student.id = :id"),
//	@NamedQuery(
//    		name = "findAccountsByTermStatusState",
//    		query = "SELECT u FROM UserEntity u INNER JOIN u.term t WHERE t.id = :tId AND u.international = :international AND u.accountState = :accountState ORDER BY u.lastName ASC"),
}) 
@Entity
public class Notification extends BaseEntity {
	
	// ======================================
    // =             Attributes             =
    // ======================================	
	
	
	// type of notice: message, status of application, change in personal info, uploaded file etc
	private String type;
	// Eg. John Smith sent a message, John Smith uploaded a file
	private String notice;
	/**
	 * Why this?? There is dateLastModified in parent class.
	 */
	private Date dateModified;

	private UserEntity student;

	/*private boolean read = false;*/
	private UserEntity readBy;
	private Date readOn;
	
	// ======================================
    // =            Constructors            =
    // ======================================

	public Notification() {
	}
	
	public Notification(String type, String notice) {
		this.type = type;
		this.notice = notice;
	}
	
	public Notification(String type, String notice, UserEntity student) {
		this.type = type;
		this.notice = notice;
		this.student = student;
	}
	// ======================================
    // =          Getters & Setters         =
    // ======================================

	@Size(max=100)
	public String getNotice() {
		return notice;
	}
	public void setNotice(String notice) {
		this.notice = notice;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Date getDateModified() {
		return dateModified;
	}
	public void setDateModified(Date dateModified) {
		this.dateModified = dateModified;
	}
	@ManyToOne
	public UserEntity getStudent() {
		return student;
	}
	public void setStudent(UserEntity student) {
		this.student = student;
	}
	@ManyToOne
	public UserEntity getReadBy() {
		return readBy;
	}
	public void setReadBy(UserEntity readBy) {
		this.readBy = readBy;
	}
	public Date getReadOn() {
		return readOn;
	}
	public void setReadOn(Date readOn) {
		this.readOn = readOn;
	}

}
