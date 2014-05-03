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
import javax.validation.constraints.Size;


@Entity
@NamedQueries(
		@NamedQuery(name = "findMessagesByUserEntityId", 
		query = "FROM Message WHERE student.id = :id")
		)
public class Message {
	
    // ======================================
    // =             Attributes             =
    // ======================================
	
	private Long id;
	private String subject;
	private String text;
	// use this one to block messages
	private boolean visible = true;
	private Date dateCreated;
	private Date dateModified;
	
	private UserEntity student;
	private UserEntity admissionOfficer;
	
//	private int messagesCount;
//	private Date lastMessageDate;
	
	// ======================================
    // =            Constructors            =
    // ======================================
	
	public Message() { }
	
	public Message(Long id) { this.id = id; }
	
	// ======================================
    // =          Getters & Setters         =
    // ======================================
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}

	@Size(max=1000)
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}

	public boolean isVisible() {
		return visible;
	}
	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public Date getDateCreated() {
		return dateCreated;
	}
	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public Date getDateModified() {
		return dateModified;
	}
	public void setDateModified(Date dateModified) {
		this.dateModified = dateModified;
	}
	
	@ManyToOne
//	@JoinColumn(name = "author_id", nullable = false)
	@JoinColumn(name = "student_id")
	public UserEntity getStudent() {
		return student;
	}
	public void setStudent(UserEntity student) {
		this.student = student;
	}

	
	@ManyToOne
//	@JoinColumn(name = "author_id", nullable = false)
	@JoinColumn(name = "admissionOfficer_id")
	public UserEntity getAdmissionOfficer() {
		return admissionOfficer;
	}
	public void setAdmissionOfficer(UserEntity admissionOfficer) {
		this.admissionOfficer = admissionOfficer;
	}

	
	// goes to controller
	/*@Transient
	public int getNumVisibleMessages() {
		if (calculateMessageStats) {
			int count = 0;
			for (Message message : messages) {
				if (message.isVisible()) { count++; }
			}
			return count;
	}
	
	public void setNumVisibleMessages(int n) {
		this.numVisibleMessages = n;
	}*/

	/*@Transient
	public Date getLastVisibleMessageDate() {
		if (calculateMessageStats) {
			Date date = null;
			for (Message message : messages) {
				if (message.isVisible()) {
					Date dateCreated = message.getDateCreated();
					if (date == null || date.compareTo(dateCreated) < 0) {
						date = message.getDateCreated();
					}
				}
			}
			return date;
		} else {
			return lastVisibleMessageDate;
		}
	}*/
	
	
}
