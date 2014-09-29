package com.prospectivestiles.domain;

public enum Ethnicity {
	WHITE("White"),
	HISPANIC("Hispanic"),
	BLACK_OR_AFRICAN_AMERICAN("Black/African American"),
	ASIAN_PACIFIC_ISLANDER("Asian/Pacific Islander"),
	NATIVE_AMERICAN_OR_ALASKAN("Native American/Alaskan");

	private String nameAsString;
	
	private Ethnicity(String nameAsString) {
		this.nameAsString = nameAsString;
	}
	
	@Override
	public String toString() {
		return this.nameAsString;
	}
	
}

//@Entity
//public class Ethnicity implements Serializable {
//	
//	/**
//	 * 
//	 */
//	private static final long serialVersionUID = -6763477106660012852L;
//	// ======================================
//	// =             Attributes             =
//	// ======================================
//	private Long id;
//	private String name;
//	private String shortName;
//	private int description;
//	
//	// ======================================
//  // =            Constructors            =
//  // ======================================
//	
//	public Ethnicity() {
//	}
//	public Ethnicity(String name, String shortName, int description) {
//		this.name = name;
//		this.shortName = shortName;
//		this.description = description;
//	}
//	// ======================================
//  // =          Getters & Setters         =
//  // ======================================
//	@Id
//	@GeneratedValue(strategy = GenerationType.AUTO)
//	public Long getId() { 
//		return id; 
//	}
//	public void setId(Long id) { 
//		this.id = id; 
//	}
//	public String getName() {
//		return name;
//	}
//	public void setName(String name) {
//		this.name = name;
//	}
//	public String getShortName() {
//		return shortName;
//	}
//	public void setShortName(String shortName) {
//		this.shortName = shortName;
//	}
//	public int getDescription() {
//		return description;
//	}
//	public void setDescription(int description) {
//		this.description = description;
//	}