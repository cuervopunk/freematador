package com.freematador.domain;

import java.util.ArrayList;
import java.util.List;

public class User {
	
	private Integer id;
	private String email;
	private String name;
	private String surname;
	private String telephone;
	private String password;
	private List<Role> roles;
	private Store store;
	
	public User() {
		this.roles = new ArrayList<Role>();
	}
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getNanme() {
		return name;
	}
	
	public void setNombre(String name) {
		this.name = name;
	}
	
	public List<Role> getRoles() {
		return roles;
	}
	
	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	
	public Store getStore() {
		return store;
	}

	public void setStore(Store store) {
		this.store = store;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof User)) {
			return false;
		}
		
		User otro = (User)obj;
		
		if (this.id != null) {
			return this.id.equals(otro.id);
		} else {
			return this.id == otro.id;
		}
		
	}
	 
}