package com.freematador.persistence;

import java.util.ArrayList;
import java.util.List;

import com.freematador.domain.Role;
import com.freematador.utils.GuapMemoryDB;


public class RoleDAOImpl implements RoleDAO {

	@Override
	public void insert(Role rol) {
		GuapMemoryDB.insert(Role.class.getName(), rol.getId().toString(), rol);
	}

	@Override
	public void update(Role rol) {
		GuapMemoryDB.update(Role.class.getName(), rol.getId().toString(), rol);
	}

	@Override
	public void delete(Integer id) {
		GuapMemoryDB.delete(Role.class.getName(), id.toString());
	}

	@Override
	public Role findById(Integer id) {
		Role rol = (Role)GuapMemoryDB.findByPk(Role.class.getName(), id.toString());
		return rol;
	}

	@Override
	public List<Role> findAll() {
		List<Role> result = new ArrayList<Role>();

		List<?> auxlist = GuapMemoryDB.findAll(Role.class.getName());
		if (auxlist != null) {
			for (int i = 0; i < auxlist.size(); i++) {
				Role rol = (Role)auxlist.get(i);
				result.add(rol);
			}
		}
		
		return result;
	}

	@Override
	public List<Role> findByName(String name) {
		List<Role> result = new ArrayList<Role>();

		List<?> auxlist = GuapMemoryDB.findAll(Role.class.getName());
		if (auxlist != null) {
			for (int i = 0; i < auxlist.size(); i++) {
				Role rol = (Role)auxlist.get(i);
				if (rol.getNombre().equals(name)) {
					result.add(rol);
				}
			}
		}
		
		return result;
	}

}
