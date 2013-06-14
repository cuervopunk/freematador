package com.freematador.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.annotation.WebListener;

import com.freematador.business.SecurityServer;
import com.freematador.business.SecurityServerImpl;
import com.freematador.domain.Role;
import com.freematador.domain.User;
import com.freematador.utils.ExcepcionGuap;


@WebListener
public class ApplicationListener implements javax.servlet.ServletContextListener {
	
	private static SecurityServer srvSeguridad = new SecurityServerImpl(); 
	
	public void contextInitialized(ServletContextEvent sce) {
		// Cargamos roles por defecto en nuestra aplicacion
		try {
			Role rol1 = new Role();
			rol1.setId(1);
			rol1.setNombre("admin");
			srvSeguridad.crearRol(rol1);
			
			Role rol2 = new Role();
			rol2.setId(2);
			rol2.setNombre("usuario");
			srvSeguridad.crearRol(rol2);
			
			User usuario = new User();
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