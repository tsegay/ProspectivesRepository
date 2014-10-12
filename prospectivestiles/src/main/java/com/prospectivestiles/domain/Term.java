package com.prospectivestiles.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonManagedReference;

/**
 * 
 * Can't use findProgramOfStudiesByUserEntityId
 */
@Entity
@NamedQuery(name = "findTermByName", query = "from Term where name= :name")
public class Term extends BaseEntity implements Serializable {
	
	// ======================================
    // =             Attributes             =
    // ======================================
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4261787246010488602L;
	private String name;
	private Date startDate;
	private Date endDate;
	private int duration; // in weeks
	@JsonManagedReference
	private Collection<UserEntity> listOfUserEntity = new ArrayList<UserEntity>();
	
	// ======================================
    // =            Constructors            =
    // ======================================

	public Term() {
	}
	public Term(String name, Date startDate, Date endDate, int duration) {
		this.name = name;
		this.startDate = startDate;
		this.endDate = endDate;
		this.duration = duration;
	}
	public Term(String name) {
		this.name = name;
	}

	// ======================================
    // =          Getters & Setters         =
    // ======================================

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public int getDuration() {
		return duration;
	}
	public void setDuration(int duration) {
		this.duration = duration;
	}
	@JsonManagedReference
	@OneToMany(mappedBy = "term", cascade = CascadeType.ALL)
	public Collection<UserEntity> getListOfUserEntity() {
		return listOfUserEntity;
	}
	public void setListOfUserEntity(Collection<UserEntity> listOfUserEntity) {
		this.listOfUserEntity = listOfUserEntity;
	}
	
	// ======================================
    // =                   =
    // ======================================
	
	/*public boolean equals(Object object) {
        return ((object instanceof Term) && (id != null)) 
             ? id.equals(((Term) object).id) 
             : (object == this);
    }*/

}
