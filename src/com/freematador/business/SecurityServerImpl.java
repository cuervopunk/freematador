package com.freematador.business;

import java.util.List;

import com.freematador.domain.Role;
import com.freematador.domain.User;
import com.freematador.persistence.RoleDAO;
import com.freematador.persistence.RoleDAOImpl;
import com.freematador.persistence.UserDAO;
import com.freematador.persistence.UserDAOImpl;
import com.freematador.utils.ExcepcionGuap;


public class SecurityServerImpl implements SecurityServer {

	private UserDAO usuarioDAO = new UserDAOImpl();
	private RoleDAO rolDAO = new RoleDAOImpl();
	
	@Override
	public boolean login(String login, String password) throws ExcepcionGuap {
		// Verificamos si encontramos un usuario con el nombre indicado
		List<User> usuarios = this.usuarioDAO.findByName(login);
		if (usuarios.size() > 0) {
			User usuario = usuarios.get(0);
			if (usuario.getPassword().equals(password)) {
				return true;
			}
		}
		
		return false;
	}
	
	@Override
	public void crearUsuario(User usuario) throws ExcepcionGuap {
		// Primero validamos el usuario
		if (usuario == null) {
			throw new ExcepcionGuap("El usuario no es valido");
		}
		if (usuario.getName() == null || usuario.getPassword() == null || usuario.getId() == null) {
			throw new ExcepcionGuap("El usuario no tiene todos sus datos cargados");
		}
		if (usuario.getRoles() == null || usuario.getRoles().size() == 0) {
			throw new ExcepcionGuap("El usuario no tiene roles asignados");
		}
		
		// Si esta todo bien, lo guardamos en la base
		this.usuarioDAO.insert(usuario);
	}

	@Override
	public void crearRol(Role rol) throws ExcepcionGuap {
		// Primero validamos el rol
		if (rol == null) {
			throw new ExcepcionGuap("El rol no es valido");
		}
		if (rol.getNombre() == null || rol.getId() == null) {
			throw new ExcepcionGuap("El rol no tiene todos sus datos cargados");
		}
		
		// Si esta todo bien, lo guardamos en la base
		this.rolDAO.insert(rol);
	}

	@Override
	public Role obtenerRol(String nombre) {
		List<Role> roles = this.rolDAO.findByName(nombre);
		if (roles != null && roles.size() > 0) {
			return roles.get(0);
		}
		return null;
	}
	
	
}
