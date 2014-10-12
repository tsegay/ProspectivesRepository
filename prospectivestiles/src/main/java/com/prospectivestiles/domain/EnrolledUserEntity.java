package com.prospectivestiles.domain;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

/**
 * This class is not persisted to the DB.
 * It is used to represent a userEntity for WebServices
 * 
 * @author danielanenia
 *
 */
//@Entity
//@SuppressWarnings("serial")
public class EnrolledUserEntity {
	
	// ======================================
    // =             Attributes             =
    // ======================================
	
	private Long id;
	private String applicantId;
	private String firstName, lastName, middleName, email;
	private String homePhone;
	private String cellPhone;
	private String ssn;
	private String citizenship;
	private String countryOfBirth;
	private String ethnicity;
	private String sevisNumber;
	private Date dateCreated;
	private Date dob;
	private String gender;
	private boolean transferee = false;
	private boolean international = false;
	private Date dateEnrolled;
	private String programOfStudy;
	private String term;
	private String accountState;
	
	private Collection<HighSchool> listOfHighSchools = new ArrayList<HighSchool>();
	private Collection<Institute> listOfInstitutes = new ArrayList<Institute>();
	private Collection<Address> listOfAddresses = new ArrayList<Address>();
	
//	private Collection<String> listOfHighSchools = new ArrayList<String>();
//	private Collection<String> listOfInstitutes = new ArrayList<String>();
//	private Collection<String> listOfAddresses = new ArrayList<String>();
	
	// ======================================
    // =            Constructors            =
    // ======================================
	
	public EnrolledUserEntity() {
	}
	
	public EnrolledUserEntity(Long id, String applicantId, String firstName,
			String lastName, String middleName, String email, String homePhone,
			String cellPhone, String ssn, String citizenship,
			String countryOfBirth, String ethnicity, String sevisNumber,
			Date dateCreated, Date dob, String gender, boolean transferee,
			boolean international, Date dateEnrolled, String programOfStudy,
			String term, String accountState,
			Collection<HighSchool> listOfHighSchools,
			Collection<Institute> listOfInstitutes,
			Collection<Address> listOfAddresses) {
		this.id = id;
		this.applicantId = applicantId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.middleName = middleName;
		this.email = email;
		this.homePhone = homePhone;
		this.cellPhone = cellPhone;
		this.ssn = ssn;
		this.citizenship = citizenship;
		this.countryOfBirth = countryOfBirth;
		this.ethnicity = ethnicity;
		this.sevisNumber = sevisNumber;
		this.dateCreated = dateCreated;
		this.dob = dob;
		this.gender = gender;
		this.transferee = transferee;
		this.international = international;
		this.dateEnrolled = dateEnrolled;
		this.programOfStudy = programOfStudy;
		this.term = term;
		this.accountState = accountState;
		this.listOfHighSchools = listOfHighSchools;
		this.listOfInstitutes = listOfInstitutes;
		this.listOfAddresses = listOfAddresses;
	}

	// ======================================
	// =          Getters & Setters         =
	// ======================================
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getApplicantId() {
		return applicantId;
	}

	public void setApplicantId(String applicantId) {
		this.applicantId = applicantId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getHomePhone() {
		return homePhone;
	}

	public void setHomePhone(String homePhone) {
		this.homePhone = homePhone;
	}

	public String getCellPhone() {
		return cellPhone;
	}

	public void setCellPhone(String cellPhone) {
		this.cellPhone = cellPhone;
	}

	public String getSsn() {
		return ssn;
	}

	public void setSsn(String ssn) {
		this.ssn = ssn;
	}

	public String getCitizenship() {
		return citizenship;
	}

	public void setCitizenship(String citizenship) {
		this.citizenship = citizenship;
	}

	public String getCountryOfBirth() {
		return countryOfBirth;
	}

	public void setCountryOfBirth(String countryOfBirth) {
		this.countryOfBirth = countryOfBirth;
	}

	public String getEthnicity() {
		return ethnicity;
	}

	public void setEthnicity(String ethnicity) {
		this.ethnicity = ethnicity;
	}

	public String getSevisNumber() {
		return sevisNumber;
	}

	public void setSevisNumber(String sevisNumber) {
		this.sevisNumber = sevisNumber;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public boolean isTransferee() {
		return transferee;
	}

	public void setTransferee(boolean transferee) {
		this.transferee = transferee;
	}

	public boolean isInternational() {
		return international;
	}

	public void setInternational(boolean international) {
		this.international = international;
	}

	public Date getDateEnrolled() {
		return dateEnrolled;
	}

	public void setDateEnrolled(Date dateEnrolled) {
		this.dateEnrolled = dateEnrolled;
	}

	public String getProgramOfStudy() {
		return programOfStudy;
	}

	public void setProgramOfStudy(String programOfStudy) {
		this.programOfStudy = programOfStudy;
	}

	public String getTerm() {
		return term;
	}

	public void setTerm(String term) {
		this.term = term;
	}

	public String getAccountState() {
		return accountState;
	}

	public void setAccountState(String accountState) {
		this.accountState = accountState;
	}

	public Collection<HighSchool> getListOfHighSchools() {
		return listOfHighSchools;
	}

	public void setListOfHighSchools(Collection<HighSchool> listOfHighSchools) {
		this.listOfHighSchools = listOfHighSchools;
	}

	public Collection<Institute> getListOfInstitutes() {
		return listOfInstitutes;
	}

	public void setListOfInstitutes(Collection<Institute> listOfInstitutes) {
		this.listOfInstitutes = listOfInstitutes;
	}

	public Collection<Address> getListOfAddresses() {
		return listOfAddresses;
	}

	public void setListOfAddresses(Collection<Address> listOfAddresses) {
		this.listOfAddresses = listOfAddresses;
	}

}
