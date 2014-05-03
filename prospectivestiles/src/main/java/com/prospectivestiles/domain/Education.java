package com.prospectivestiles.domain;

import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@MappedSuperclass
@Inheritance(strategy = InheritanceType.JOINED)
public class Education {
	
	// ======================================
    // =             Attributes             =
    // ======================================
	
	private long id;
	private String name;
	private String country;
	private String state;
	private String zip;
	private String city;
	private Date attendedFrom;
	private Date attendedTo;
	
	
	
	// ======================================
    // =            Constructors            =
    // ======================================

	public Education(String name, String country, String state, String zip,
			String city, Date attendedFrom, Date attendedTo) {
		this.name = name;
		this.country = country;
		this.state = state;
		this.zip = zip;
		this.city = city;
		this.attendedFrom = attendedFrom;
		this.attendedTo = attendedTo;
	}
	public Education() {
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
	@NotNull
	@Size(min = 1, max = 50)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@NotNull
	@Size(min = 1, max = 50)
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	@NotNull
	public Date getAttendedFrom() {
		return attendedFrom;
	}
	public void setAttendedFrom(Date attendedFrom) {
		this.attendedFrom = attendedFrom;
	}
	@NotNull
	public Date getAttendedTo() {
		return attendedTo;
	}
	public void setAttendedTo(Date attendedTo) {
		this.attendedTo = attendedTo;
	}
	
	
}
