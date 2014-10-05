package com.prospectivestiles.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Size;

@Entity
public class ResetPasswordEntity implements Serializable {

	// ======================================
    // =             Attributes             =
    // ======================================
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 727432098227059956L;
	
	private Long id;
	private String email;
	private String resetKey;
	private String password;
	private String confirmPassword;
	private Date dateCreated;
	private Date expireDate;
	
	// ======================================
    // =             Constructors             =
    // ======================================
	
	public ResetPasswordEntity() {
	}
	public ResetPasswordEntity(String email, String resetKey) {
		this.email = email;
		this.resetKey = resetKey;
	}
	
	// ======================================
    // =             Getters & Setters             =
    // ======================================
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getResetKey() {
		return resetKey;
	}
	public void setResetKey(String resetKey) {
		this.resetKey = resetKey;
	}
	
	@Size(min = 6, max = 70)
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	@Size(min = 6, max = 70)
	public String getConfirmPassword() {
		return confirmPassword;
	}
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
	
	public Date getDateCreated() {
		return dateCreated;
	}
	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}
	
	public Date getExpireDate() {
		return expireDate;
	}
	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}
	
}
