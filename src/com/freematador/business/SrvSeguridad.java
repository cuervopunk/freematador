
package com.freematador.business;
import com.freematador.domain.Rol;
import com.freematador.domain.Usuario;
import com.freematador.utils.ExcepcionGuap;


public interface SrvSeguridad {

	public boolean login(String login, String password) throws ExcepcionGuap ;
	public void crearUsuario(Usuario usuario) throws ExcepcionGuap;
	public void crearRol(Rol rol) throws ExcepcionGuap;
	public Rol obtenerRol(String nombre);
	
}
