package com.prospectivestiles.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@NamedQuery(name = "findRoleByName", query = "from Role where name= :name")
//public class Role implements GrantedAuthority {
public class Role implements Serializable {
	
	// ======================================
    // =             Attributes             =
    // ======================================
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private String name;
	/*private Set<Permission> permissions = new HashSet<Permission>();*/
	private Collection<UserEntity> listOfUserEntity = new ArrayList<UserEntity>();
	
	// ======================================
    // =          Getters & Setters         =
    // ======================================
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
//	@Column(name = "id")
	public Long getId() { return id; }
	
	@SuppressWarnings("unused")
	private void setId(Long id) { this.id = id; }
	
	public String getName() { return name; }

	public void setName(String name) { this.name = name; }
	
	@OneToMany(mappedBy = "role", cascade = CascadeType.ALL)
	public Collection<UserEntity> getListOfUserEntity() {
		return listOfUserEntity;
	}
	public void setListOfUserEntity(Collection<UserEntity> listOfUserEntity) {
		this.listOfUserEntity = listOfUserEntity;
	}

	// ======================================
    // =                   =
    // ======================================
	
	/*@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
		name = "role_permission",
		joinColumns = { @JoinColumn(name = "role_id") },
		inverseJoinColumns = { @JoinColumn(name = "permission_id") })
	public Set<Permission> getPermissions() { return permissions; }
	
	public void setPermissions(Set<Permission> permissions) {
		this.permissions = permissions;
	}*/

	/*@Override
	@Transient
	public String getAuthority() {
		return name;
	}
	
	@Override
	public boolean equals(Object o) {
		GrantedAuthority ga = (GrantedAuthority) o;
		return (getAuthority().equals(ga.getAuthority()));
	}
	
	@Override
	public int hashCode() {
		return getAuthority().hashCode();
	}*/
}
