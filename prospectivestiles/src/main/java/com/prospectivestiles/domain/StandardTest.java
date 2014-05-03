package com.prospectivestiles.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
@NamedQueries(
		@NamedQuery(name = "findStandardTestsByUserEntityId", 
		query = "FROM StandardTest WHERE userEntity.id = :id")
		)
public class StandardTest {
	
    // ======================================
    // =             Attributes             =
    // ======================================	
	
	
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
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
