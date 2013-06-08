package com.freematador.domain;

import java.util.ArrayList;
import java.util.List;

public class Usuario {
	
	private Integer id;
	private String nombre;
	private String password;
	private List<Rol> roles;
	
	public Usuario() {
		this.roles = new ArrayList<Rol>();
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public List<Rol> getRoles() {
		return roles;
	}
	public void setRoles(List<Rol> roles) {
		this.roles = roles;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Usuario)) {
			return false;
		}
		
		Usuario otro = (Usuario)obj;
		
		if (this.id != null) {
			return this.id.equals(otro.id);
		} else {
			return this.id == otro.id;
		}
		
	}

	 
}
