package com.freematador.persistence;

import java.util.ArrayList;
import java.util.List;

import com.freematador.domain.Rol;
import com.freematador.utils.GuapMemoryDB;


public class RolDAOImpl implements RolDAO {

	@Override
	public void insert(Rol rol) {
		GuapMemoryDB.insert(Rol.class.getName(), rol.getId().toString(), rol);
	}

	@Override
	public void update(Rol rol) {
		GuapMemoryDB.update(Rol.class.getName(), rol.getId().toString(), rol);
	}

	@Override
	public void delete(Integer id) {
		GuapMemoryDB.delete(Rol.class.getName(), id.toString());
	}

	@Override
	public Rol findById(Integer id) {
		Rol rol = (Rol)GuapMemoryDB.findByPk(Rol.class.getName(), id.toString());
		return rol;
	}

	@Override
	public List<Rol> findAll() {
		List<Rol> result = new ArrayList<Rol>();

		List<?> auxlist = GuapMemoryDB.findAll(Rol.class.getName());
		if (auxlist != null) {
			for (int i = 0; i < auxlist.size(); i++) {
				Rol rol = (Rol)auxlist.get(i);
				result.add(rol);
			}
		}
		
		return result;
	}

	@Override
	public List<Rol> findByName(String name) {
		List<Rol> result = new ArrayList<Rol>();

		List<?> auxlist = GuapMemoryDB.findAll(Rol.class.getName());
		if (auxlist != null) {
			for (int i = 0; i < auxlist.size(); i++) {
				Rol rol = (Rol)auxlist.get(i);
				if (rol.getNombre().equals(name)) {
					result.add(rol);
				}
			}
		}
		
		return result;
	}

}
