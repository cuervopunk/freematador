package com.freematador.domain;

public class Role {
	private Integer id;
	private String nombre;
	
	public Role() {
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

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Role)) {
			return false;
		}
		
		Role otro = (Role)obj;
		
		if (this.id != null) {
			return this.id.equals(otro.id);
		} else {
			return this.id == otro.id;
		}
		
	}

	 
}
