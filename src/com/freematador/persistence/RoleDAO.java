package com.freematador.persistence;

import java.util.List;

import com.freematador.domain.Role;


public interface RoleDAO {

	public void insert(Role rol);
	public void update(Role rol);
	public void delete(Integer id);
	public Role findById(Integer id);
	
	public List<Role> findAll();
	public List<Role> findByName(String name);
	
}
