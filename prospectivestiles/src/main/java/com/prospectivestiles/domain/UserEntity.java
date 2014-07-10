package com.prospectivestiles.domain;

/**
 * I used @JsonIgnore to fix 
 * org.springframework.http.converter.HttpMessageNotWritableException: 
 * Could not write JSON: Infinite recursion (StackOverflowError)
 * http://stackoverflow.com/questions/3325387/infinite-recursion-with-jackson-json-and-hibernate-jpa-issue
 * 
 */

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.validator.constraints.Email;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.UserDetails;


@NamedQueries({
	@NamedQuery(
    		name = "findUserEntityByUsername",
    		query = "from UserEntity where username = :username"),
	@NamedQuery(
    		name = "findUserEntitiesByRole",
    		query = "select u from UserEntity u inner join u.roles r where r.id = :roleID"),
}) 
@Entity
@Table(name = "userEntity")
@SuppressWarnings("serial")
public class UserEntity implements UserDetails {
	
	// ======================================
    // =             Attributes             =
    // ======================================
	
	public static final UserEntity USERENTITY = new UserEntity("anonymous");
	
	private Long id;
//	private Long studentId;
	private String username, firstName, lastName, middleName, email;
	private String password;
	private String confirmPassword;
	private String homePhone;
	private String cellPhone;
	private String ssn;
	private String citizenship;
	private String ethnicity;
	private String sevisNumber;
	private boolean marketingOk = true, acceptTerms = false, enabled = true;
	private Date dateCreated;
	private Date dob;
	private String gender;
	/*
	if true - student is transferee
	if false - student is new entrant
	*/
	private boolean transferee;
	/*
	if true - student is international
	if false - student is domestic
	*/
	private boolean international;
	private Date dateLastModified;
	/*private UserEntity createdBy;
	private UserEntity lastModifiedBy;*/
	
	private Set<Role> roles = new HashSet<Role>();
	
	@JsonIgnore
	private Checklist checklist;
	@JsonIgnore
	private Evaluation evaluation;
	@JsonIgnore
	private Collection<HighSchool> listOfHighSchools = new ArrayList<HighSchool>();
	@JsonIgnore
	private Collection<Institute> listOfInstitutes = new ArrayList<Institute>();
	@JsonIgnore
	private Collection<Address> listOfAddresses = new ArrayList<Address>();
	@JsonIgnore
	private UploadedFiles uploadedFiles;
	@JsonIgnore
	private Collection<EmergencyContact> listOfEmergencyContacts = new ArrayList<EmergencyContact>();
	@JsonIgnore
	private Collection<Employer> listOfEmployers = new ArrayList<Employer>();
	@JsonIgnore
	private Collection<StandardTest> listOfStandardTests = new ArrayList<StandardTest>();
	private ProgramOfStudy programOfStudy;
	private Term term;
	
	// ======================================
    // =            Constructors            =
    // ======================================
	public UserEntity() {
	}
	
	public UserEntity(String username) {
		this.username = username;
	}

	// ======================================
    // =          Getters & Setters         =
    // ======================================
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	public Long getId() { return id; }
	
	// why is this private???
	@SuppressWarnings("unused")
	public void setId(Long id) { this.id = id; }
//	private void setId(Long id) { this.id = id; }
	
	@NotNull
	@Size(min = 1, max = 50)
	@Column(name = "username")
	public String getUsername() { return username; }

	public void setUsername(String username) { this.username = username; }
	
	@NotNull
	@Size(min = 1, max = 50)
	@Column(name = "first_name")
	public String getFirstName() { return firstName; }

	public void setFirstName(String firstName) { this.firstName = firstName; }
	
	@NotNull
	@Size(min = 1, max = 50)
	@Column(name = "last_name")
	public String getLastName() { return lastName; }

	public void setLastName(String lastName) { this.lastName = lastName; }
	
	@Column(name = "middle_name")
	public String getMiddleName() {
		return middleName;
	}
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}
	@Transient
	public String getFullName() { return firstName + " " + lastName; }
	
	@NotNull
	@Size(min = 6, max = 50)
	@Email
	@Column(name = "email")
	public String getEmail() { return email; }

	public void setEmail(String email) { this.email = email; }
	
	@NotNull
	@Size(min = 6, max = 70)
	@Column(name = "password")
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	@Transient
	@Size(min = 6, max = 70)
	public String getConfirmPassword() {
		return confirmPassword;
	}
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	@Column(name = "marketing_ok")
	public boolean isMarketingOk() { return marketingOk; }
	
	public void setMarketingOk(boolean marketingOk) { this.marketingOk = marketingOk; }
	
	@AssertTrue(message = "{userEntity.acceptTerms.assertTrue.message}")
	@Column(name = "accept_terms")
	public boolean getAcceptTerms() { return acceptTerms; }
	
	public void setAcceptTerms(boolean acceptTerms) { this.acceptTerms = acceptTerms; }
	
	@Column(name = "enabled")
	public boolean isEnabled() { return enabled; }

	public void setEnabled(boolean enabled) { this.enabled = enabled; }
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
		name = "userEntity_role",
		joinColumns = { @JoinColumn(name = "userEntity_id") },
		inverseJoinColumns = { @JoinColumn(name = "role_id") })
	public Set<Role> getRoles() { return roles; }
	
	public void setRoles(Set<Role> roles) { this.roles = roles; }
	

	@Column(name = "date_created")
	public Date getDateCreated() { return dateCreated; }
	
	public void setDateCreated(Date dateCreated) { this.dateCreated = dateCreated; }
	
	@NotNull(message = "{userEntity.dob.message}")
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

	public Date getDateLastModified() {
		return dateLastModified;
	}

	public void setDateLastModified(Date dateLastModified) {
		this.dateLastModified = dateLastModified;
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

	@JsonIgnore
	@OneToOne(mappedBy = "userEntity", cascade = CascadeType.ALL)
	public Checklist getChecklist() {
		return checklist;
	}

	public void setChecklist(Checklist checklist) {
		this.checklist = checklist;
	}
	
	@JsonIgnore
	@OneToOne(mappedBy = "userEntity", cascade = CascadeType.ALL)
	public Evaluation getEvaluation() {
		return evaluation;
	}
	public void setEvaluation(Evaluation evaluation) {
		this.evaluation = evaluation;
	}

	@JsonIgnore
	@OneToMany(mappedBy = "userEntity", cascade = CascadeType.ALL)
	public Collection<HighSchool> getListOfHighSchools() {
		return listOfHighSchools;
	}
	public void setListOfHighSchools(Collection<HighSchool> listOfHighSchools) {
		this.listOfHighSchools = listOfHighSchools;
	}
	
	@JsonIgnore
	@OneToMany(mappedBy = "userEntity", cascade = CascadeType.ALL)
	public Collection<Institute> getListOfInstitutes() {
		return listOfInstitutes;
	}
	public void setListOfInstitutes(Collection<Institute> listOfInstitutes) {
		this.listOfInstitutes = listOfInstitutes;
	}
	@JsonIgnore
	@OneToMany(mappedBy = "userEntity", cascade = CascadeType.ALL)
	public Collection<Address> getListOfAddresses() {
		return listOfAddresses;
	}
	public void setListOfAddresses(Collection<Address> listOfAddresses) {
		this.listOfAddresses = listOfAddresses;
	}
	@JsonIgnore
	@OneToOne(mappedBy = "userEntity", cascade = CascadeType.ALL)
	public UploadedFiles getUploadedFiles() {
		return uploadedFiles;
	}
	public void setUploadedFiles(UploadedFiles uploadedFiles) {
		this.uploadedFiles = uploadedFiles;
	}
	@JsonIgnore
	@OneToMany(mappedBy = "userEntity", cascade = CascadeType.ALL)
	public Collection<EmergencyContact> getListOfEmergencyContacts() {
		return listOfEmergencyContacts;
	}
	public void setListOfEmergencyContacts(
			Collection<EmergencyContact> listOfEmergencyContacts) {
		this.listOfEmergencyContacts = listOfEmergencyContacts;
	}
	@JsonIgnore
	@OneToMany(mappedBy = "userEntity", cascade = CascadeType.ALL)
	public Collection<Employer> getListOfEmployers() {
		return listOfEmployers;
	}
	public void setListOfEmployers(Collection<Employer> listOfEmployers) {
		this.listOfEmployers = listOfEmployers;
	}
	@JsonIgnore
	@OneToMany(mappedBy = "userEntity", cascade = CascadeType.ALL)
	public Collection<StandardTest> getListOfStandardTests() {
		return listOfStandardTests;
	}
	public void setListOfStandardTests(
			Collection<StandardTest> listOfStandardTests) {
		this.listOfStandardTests = listOfStandardTests;
	}

	@JsonIgnore
	@ManyToOne
	public ProgramOfStudy getProgramOfStudy() {
		return programOfStudy;
	}
	public void setProgramOfStudy(ProgramOfStudy programOfStudy) {
		this.programOfStudy = programOfStudy;
	}
	@JsonIgnore
	@ManyToOne
	public Term getTerm() {
		return term;
	}
	public void setTerm(Term term) {
		this.term = term;
	}
	
	
	// ======================================
    // =              UserDetails methods     =
    // ======================================



	@Transient
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Transient
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Transient
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}
	
	@Transient
	@Override
	public Collection<GrantedAuthority> getAuthorities() {
		Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
		for (Role role : getRoles()) {
			authorities.add(new GrantedAuthorityImpl(role.getName()));
		}
		return authorities;
	}
	
	public String toString() { return username; }

	/*@Override
	public String toString() {
		return "UserEntity [id=" + id + ", username=" + username
				+ ", firstName=" + firstName + ", lastName=" + lastName
				+ ", email=" + email + ", password=" + password
				+ ", marketingOk=" + marketingOk + ", acceptTerms="
				+ acceptTerms + ", enabled=" + enabled + ", dateCreated="
				+ dateCreated + ", roles=" + roles + ", listOfOffers="
				+ listOfOffers + "]";
	}*/
	
	

}
