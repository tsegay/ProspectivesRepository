package com.prospectivestiles.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@NamedQueries({
	@NamedQuery(
    		name = "findAddressesByUserEntityId",
    		query = "FROM Address WHERE userEntity.id = :id"),
	@NamedQuery(
    		name = "findAddressByUserEntityIdAndAddressType",
    		query = "FROM Address WHERE userEntity.id = :id AND addressType= :addressType")
}) 
public class Address extends BaseEntity implements Serializable  {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = -7777131449791659217L;
	// ======================================
    // =             Attributes             =
    // ======================================
	
	private AddressType addressType;
	private String address1;
	private String address2;
	private String city;
	private String state;
	private String zipcode;
	private Country country;
	private UserEntity userEntity = new UserEntity();
	
	// ======================================
    // =            Constructors            =
    // ======================================

	public Address() {
	}
	
	public Address(String address1, String address2, String city,
			String state, String zipcode, Country country) {
		this.address1 = address1;
		this.address2 = address2;
		this.city = city;
		this.state = state;
		this.zipcode = zipcode;
		this.country = country;
	}

	// ======================================
    // =          Getters & Setters         =
    // ======================================
	
	public AddressType getAddressType() {
		return addressType;
	}

	public void setAddressType(AddressType addressType) {
		this.addressType = addressType;
	}

	@NotNull
	@Size(min = 2, max = 50)
	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	@NotNull
	@Size(min = 2, max = 50)
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}
	
	@NotNull
	@ManyToOne
	public Country getCountry() {
		return country;
	}
	public void setCountry(Country country) {
		this.country = country;
	}
	
	@ManyToOne
	public UserEntity getUserEntity() {
		return userEntity;
	}

	public void setUserEntity(UserEntity userEntity) {
		this.userEntity = userEntity;
	}

	@Override
	public String toString() {
		return "Address [addressType=" + addressType + ", address1=" + address1
				+ ", address2=" + address2 + ", city=" + city + ", state="
				+ state + ", zipcode=" + zipcode + ", country=" + country + "]";
	}
	
	
	

	
}
