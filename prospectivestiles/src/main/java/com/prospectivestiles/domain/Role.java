package com.prospectivestiles.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


@Entity
@Table(name = "role")
@NamedQuery(name = "findRoleByName", query = "from Role where name= :name")
//public class Role implements GrantedAuthority {
public class Role implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private String name;
	/*private Set<Permission> permissions = new HashSet<Permission>();*/
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	public Long getId() { return id; }
	
	@SuppressWarnings("unused")
	private void setId(Long id) { this.id = id; }
	
	public String getName() { return name; }

	public void setName(String name) { this.name = name; }
	
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
