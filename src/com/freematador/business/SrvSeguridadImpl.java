package com.freematador.business;

import java.util.List;

import com.freematador.domain.Rol;
import com.freematador.domain.Usuario;
import com.freematador.persistence.RolDAO;
import com.freematador.persistence.RolDAOImpl;
import com.freematador.persistence.UsuarioDAO;
import com.freematador.persistence.UsuarioDAOImpl;
import com.freematador.utils.ExcepcionGuap;


public class SrvSeguridadImpl implements SrvSeguridad {

	private UsuarioDAO usuarioDAO = new UsuarioDAOImpl();
	private RolDAO rolDAO = new RolDAOImpl();
	
	@Override
	public boolean login(String login, String password) throws ExcepcionGuap {
		// Verificamos si encontramos un usuario con el nombre indicado
		List<Usuario> usuarios = this.usuarioDAO.findByName(login);
		if (usuarios.size() > 0) {
			Usuario usuario = usuarios.get(0);
			if (usuario.getPassword().equals(password)) {
				return true;
			}
		}
		
		return false;
	}
	
	@Override
	public void crearUsuario(Usuario usuario) throws ExcepcionGuap {
		// Primero validamos el usuario
		if (usuario == null) {
			throw new ExcepcionGuap("El usuario no es valido");
		}
		if (usuario.getNombre() == null || usuario.getPassword() == null || usuario.getId() == null) {
			throw new ExcepcionGuap("El usuario no tiene todos sus datos cargados");
		}
		if (usuario.getRoles() == null || usuario.getRoles().size() == 0) {
			throw new ExcepcionGuap("El usuario no tiene roles asignados");
		}
		
		// Si esta todo bien, lo guardamos en la base
		this.usuarioDAO.insert(usuario);
	}

	@Override
	public void crearRol(Rol rol) throws ExcepcionGuap {
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
	public Rol obtenerRol(String nombre) {
		List<Rol> roles = this.rolDAO.findByName(nombre);
		if (roles != null && roles.size() > 0) {
			return roles.get(0);
		}
		return null;
	}
	
	
}
