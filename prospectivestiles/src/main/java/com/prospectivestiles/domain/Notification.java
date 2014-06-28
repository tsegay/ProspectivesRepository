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

@Entity
@Table(name = "notification")
@NamedQueries(
@NamedQuery(name = "findNotificationsByUserEntityId", 
query = "FROM Notification WHERE student.id = :id")
)
public class Notification extends BaseEntity {
	
	// ======================================
    // =             Attributes             =
    // ======================================	
	
	
//	private long id;
	// type of notice: message, status of application, change in personal info, uploaded file etc
	private String type;
	// Eg. John Smith sent a message, John Smith uploaded a file
	private String notice;
	/*private Date dateCreated;*/
	private Date dateModified;
	// use this one to hide the visible from view
	private boolean visible = true;

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



	/*@Id 
	@GeneratedValue(strategy = GenerationType.AUTO)
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}*/
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
	/*public Date getDateCreated() {
		return dateCreated;
	}
	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}*/
	public Date getDateModified() {
		return dateModified;
	}
	public void setDateModified(Date dateModified) {
		this.dateModified = dateModified;
	}
	public boolean isVisible() {
		return visible;
	}
	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	@ManyToOne
	@JoinColumn(name = "studentId")
	public UserEntity getStudent() {
		return student;
	}
	public void setStudent(UserEntity student) {
		this.student = student;
	}
	/*public boolean isRead() {
		return read;
	}
	public void setRead(boolean read) {
		this.read = read;
	}*/
	@ManyToOne
	@JoinColumn(name = "readById")
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
