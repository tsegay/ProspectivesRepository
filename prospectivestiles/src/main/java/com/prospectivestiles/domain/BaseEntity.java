package com.prospectivestiles.domain;

import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@MappedSuperclass
@Inheritance(strategy = InheritanceType.JOINED)
public class BaseEntity {
	
	// ======================================
    // =             Attributes             =
    // ======================================
	
	private long id;
	private UserEntity createdBy;
	private Date dateCreated;
	private UserEntity lastModifiedBy;
	private Date dateLastModified;
	private boolean visible = true;
	
	
	// ======================================
    // =            Constructors            =
    // ======================================

	public BaseEntity(UserEntity createdBy, Date dateCreated,
			UserEntity lastModifiedBy, Date dateLastModified) {
		this.createdBy = createdBy;
		this.dateCreated = dateCreated;
		this.lastModifiedBy = lastModifiedBy;
		this.dateLastModified = dateLastModified;
	}

	public BaseEntity() {
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
	
	@ManyToOne
	public UserEntity getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(UserEntity createdBy) {
		this.createdBy = createdBy;
	}
	public Date getDateCreated() {
		return dateCreated;
	}
	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}
	@ManyToOne
	public UserEntity getLastModifiedBy() {
		return lastModifiedBy;
	}
	public void setLastModifiedBy(UserEntity lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}
	public Date getDateLastModified() {
		return dateLastModified;
	}
	public void setDateLastModified(Date dateLastModified) {
		this.dateLastModified = dateLastModified;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	
	
	
}
