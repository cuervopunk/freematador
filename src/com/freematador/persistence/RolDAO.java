package com.freematador.persistence;

import java.util.List;

import com.freematador.domain.Rol;


public interface RolDAO {

	public void insert(Rol rol);
	public void update(Rol rol);
	public void delete(Integer id);
	public Rol findById(Integer id);
	
	public List<Rol> findAll();
	public List<Rol> findByName(String name);
	
}
