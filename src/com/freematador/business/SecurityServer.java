
package com.freematador.business;
import com.freematador.domain.Role;
import com.freematador.domain.User;
import com.freematador.utils.ExcepcionGuap;


public interface SecurityServer {

	public boolean login(String login, String password) throws ExcepcionGuap ;
	public void crearUsuario(User usuario) throws ExcepcionGuap;
	public void crearRol(Role rol) throws ExcepcionGuap;
	public Role obtenerRol(String nombre);
	
}
