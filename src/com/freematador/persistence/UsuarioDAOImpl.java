package com.freematador.persistence;

import java.util.ArrayList;
import java.util.List;

import com.freematador.domain.Usuario;
import com.freematador.utils.GuapMemoryDB;


public class UsuarioDAOImpl implements UsuarioDAO {

	@Override
	public void insert(Usuario Usuario) {
		GuapMemoryDB.insert(Usuario.class.getName(), Usuario.getId().toString(), Usuario);
	}

	@Override
	public void update(Usuario Usuario) {
		GuapMemoryDB.update(Usuario.class.getName(), Usuario.getId().toString(), Usuario);
	}

	@Override
	public void delete(Integer id) {
		GuapMemoryDB.delete(Usuario.class.getName(), id.toString());
	}

	@Override
	public Usuario findById(Integer id) {
		Usuario Usuario = (Usuario)GuapMemoryDB.findByPk(Usuario.class.getName(), id.toString());
		return Usuario;
	}

	@Override
	public List<Usuario> findAll() {
		List<Usuario> result = new ArrayList<Usuario>();

		List<?> auxlist = GuapMemoryDB.findAll(Usuario.class.getName());
		if (auxlist != null) {
			for (int i = 0; i < auxlist.size(); i++) {
				Usuario Usuario = (Usuario)auxlist.get(i);
				result.add(Usuario);
			}
		}
		
		return result;
	}

	@Override
	public List<Usuario> findByName(String name) {
		List<Usuario> result = new ArrayList<Usuario>();

		List<?> auxlist = GuapMemoryDB.findAll(Usuario.class.getName());
		if (auxlist != null) {
			for (int i = 0; i < auxlist.size(); i++) {
				Usuario Usuario = (Usuario)auxlist.get(i);
				if (Usuario.getNombre().equals(name)) {
					result.add(Usuario);
				}
			}
		}
		
		return result;
	}

}
