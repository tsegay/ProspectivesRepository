package com.prospectivestiles.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "standardTest")
@NamedQueries(
		@NamedQuery(name = "findStandardTestsByUserEntityId", 
		query = "FROM StandardTest WHERE userEntity.id = :id")
		)
public class StandardTest implements Serializable {
	
    // ======================================
    // =             Attributes             =
    // ======================================	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long id;
	private String name;
	private double score;
	private Date validTill;
	
	private UserEntity userEntity;
	
	// ======================================
    // =            Constructors            =
    // ======================================

	public StandardTest() {
	}
	public StandardTest(String name, double score, Date validTill) {
		this.name = name;
		this.score = score;
		this.validTill = validTill;
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
	
	@Size(min = 2, max = 25)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@NotNull
	public double getScore() {
		return score;
	}
	public void setScore(double score) {
		this.score = score;
	}
	public Date getValidTill() {
		return validTill;
	}
	public void setValidTill(Date validTill) {
		this.validTill = validTill;
	}
	@ManyToOne
	public UserEntity getUserEntity() {
		return userEntity;
	}
	public void setUserEntity(UserEntity userEntity) {
		this.userEntity = userEntity;
	}
	

}
