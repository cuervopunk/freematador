package com.freematador.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.annotation.WebListener;

import com.freematador.business.SrvSeguridad;
import com.freematador.business.SrvSeguridadImpl;
import com.freematador.domain.Rol;
import com.freematador.domain.Usuario;
import com.freematador.utils.ExcepcionGuap;


@WebListener
public class ApplicationListener implements javax.servlet.ServletContextListener {
	
	private static SrvSeguridad srvSeguridad = new SrvSeguridadImpl(); 
	
	public void contextInitialized(ServletContextEvent sce) {
		// Cargamos roles por defecto en nuestra aplicacion
		try {
			Rol rol1 = new Rol();
			rol1.setId(1);
			rol1.setNombre("admin");
			srvSeguridad.crearRol(rol1);
			
			Rol rol2 = new Rol();
			rol2.setId(2);
			rol2.setNombre("usuario");
			srvSeguridad.crearRol(rol2);
			
			Usuario usuario = new Usuario();
			usuario.setId(1);
			usuario.setNombre("admin");
			usuario.setPassword("admin");
			usuario.getRoles().add(rol1);
			srvSeguridad.crearUsuario(usuario);			
			
		} catch (ExcepcionGuap e) {
			// No hacemos nada aca
		}
	}

	public void contextDestroyed(ServletContextEvent sce) {
	}
}