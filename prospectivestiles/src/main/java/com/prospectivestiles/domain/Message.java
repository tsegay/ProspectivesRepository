package com.prospectivestiles.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Entity
@NamedQueries(
		@NamedQuery(name = "findMessagesByUserEntityId", 
		query = "FROM Message WHERE student.id = :id")
		)
public class Message extends BaseEntity implements Serializable {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// ======================================
    // =             Attributes             =
    // ======================================
	
	private String subject;
	private String text;
	
	private UserEntity student;
	private UserEntity admissionOfficer;
	
//	private int messagesCount;
//	private Date lastMessageDate;
	
	public static final String EMAIL_SENDER = "test.prospectives@acct2day.org";
	public static final String EMAIL_BCC = "test.prospectives.backup@acct2day.org";
	
	
	// ======================================
    // =            Constructors            =
    // ======================================
	
	public Message() { }
	
	
	
	public Message(String subject,
		String text, boolean visible, UserEntity student,
		UserEntity admissionOfficer) {
	this.subject = subject;
	this.text = text;
	this.student = student;
	this.admissionOfficer = admissionOfficer;
}



	// ======================================
    // =          Getters & Setters         =
    // ======================================
	
	@Size(max=100)
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}

	@NotNull
	@Size(min = 1, max = 1000)
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}

	@ManyToOne
//	@JoinColumn(name = "author_id", nullable = false)
//	@JoinColumn(name = "student_id")
	public UserEntity getStudent() {
		return student;
	}
	public void setStudent(UserEntity student) {
		this.student = student;
	}

	
	@ManyToOne
//	@JoinColumn(name = "author_id", nullable = false)
//	@JoinColumn(name = "admissionOfficer_id")
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
