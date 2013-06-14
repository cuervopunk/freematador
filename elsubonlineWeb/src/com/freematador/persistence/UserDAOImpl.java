package com.freematador.persistence;

import java.util.ArrayList;
import java.util.List;

import com.freematador.domain.User;
import com.freematador.utils.GuapMemoryDB;


public class UserDAOImpl implements UserDAO {

	@Override
	public void insert(User Usuario) {
		GuapMemoryDB.insert(User.class.getName(), Usuario.getId().toString(), Usuario);
	}

	@Override
	public void update(User Usuario) {
		GuapMemoryDB.update(User.class.getName(), Usuario.getId().toString(), Usuario);
	}

	@Override
	public void delete(Integer id) {
		GuapMemoryDB.delete(User.class.getName(), id.toString());
	}

	@Override
	public User findById(Integer id) {
		User Usuario = (User)GuapMemoryDB.findByPk(User.class.getName(), id.toString());
		return Usuario;
	}

	@Override
	public List<User> findAll() {
		List<User> result = new ArrayList<User>();

		List<?> auxlist = GuapMemoryDB.findAll(User.class.getName());
		if (auxlist != null) {
			for (int i = 0; i < auxlist.size(); i++) {
				User Usuario = (User)auxlist.get(i);
				result.add(Usuario);
			}
		}
		
		return result;
	}

	@Override
	public List<User> findByName(String name) {
		List<User> result = new ArrayList<User>();

		List<?> auxlist = GuapMemoryDB.findAll(User.class.getName());
		if (auxlist != null) {
			for (int i = 0; i < auxlist.size(); i++) {
				User Usuario = (User)auxlist.get(i);
				if (Usuario.getName().equals(name)) {
					result.add(Usuario);
				}
			}
		}
		
		return result;
	}

}
