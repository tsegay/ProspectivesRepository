package com.prospectivestiles.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Source for the list of countries: http://www.geonames.org/countries/
 * @author danielanenia
 *
 */

@Entity
public class Country implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4109872053022804844L;
	// ======================================
	// =             Attributes             =
	// ======================================
	
	private Long id;
	private String iSO3166alpha2;
	private String iSO3166alpha3;
	private int iSO3166numeric;
	private String name;
	private String continent;
	
	// ======================================
    // =            Constructors            =
    // ======================================
	
	public Country() {
	}
	public Country(String iSO3166alpha2, String iSO3166alpha3,
			int iSO3166numeric, String name, String continent) {
		super();
		this.iSO3166alpha2 = iSO3166alpha2;
		this.iSO3166alpha3 = iSO3166alpha3;
		this.iSO3166numeric = iSO3166numeric;
		this.name = name;
		this.continent = continent;
	}
	
	// ======================================
    // =          Getters & Setters         =
    // ======================================
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() { return id; }
	public void setId(Long id) { this.id = id; }
	
	public String getiSO3166alpha2() {
		return iSO3166alpha2;
	}
	public void setiSO3166alpha2(String iSO3166alpha2) {
		this.iSO3166alpha2 = iSO3166alpha2;
	}
	public String getiSO3166alpha3() {
		return iSO3166alpha3;
	}
	public void setiSO3166alpha3(String iSO3166alpha3) {
		this.iSO3166alpha3 = iSO3166alpha3;
	}
	public int getiSO3166numeric() {
		return iSO3166numeric;
	}
	public void setiSO3166numeric(int iSO3166numeric) {
		this.iSO3166numeric = iSO3166numeric;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getContinent() {
		return continent;
	}
	public void setContinent(String continent) {
		this.continent = continent;
	}
	

}
