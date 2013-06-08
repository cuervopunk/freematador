package com.freematador.presentation.usuarios;

import java.io.IOException;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.freematador.business.SrvSeguridad;
import com.freematador.business.SrvSeguridadImpl;
import com.freematador.domain.Rol;
import com.freematador.domain.Usuario;
import com.freematador.utils.BaseController;
import com.freematador.utils.ExcepcionGuap;


@WebServlet(name="CrearUsuarioController", urlPatterns={"/crearUsuarioController"})
public class CrearController extends BaseController {

	private static final long serialVersionUID = 1L;

	private SrvSeguridad srvSeguridad = new SrvSeguridadImpl();

	@Override
	protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String action = req.getParameter("ACTION");
		if ("CANCELAR".equals(action)) {
			super.navigate("/menu.jsp",req,resp);
		} else if ("CONFIRMAR".equals(action)) {
			
			String login = req.getParameter("LOGIN");
			String password = req.getParameter("PASSWORD");

			// Creamos un usuario y le asignamos un rol por defecto
			Usuario usuario = new Usuario();
			usuario.setId(Math.abs(new Random().nextInt()));
			usuario.setNombre(login);
			usuario.setPassword(password);
			
			// Traemos el rol con el nombre usuario
			Rol rol = this.srvSeguridad.obtenerRol("usuario");
			usuario.getRoles().add(rol);
			
			try {
				this.srvSeguridad.crearUsuario(usuario);
			} catch (ExcepcionGuap e) {
				super.showMessage(e.getMessage(), req);
				super.navigate("/usuarios/crear.jsp",req,resp);			
			}
			
			super.navigate("/menu.jsp",req,resp);			
		} else {
			// Cualquier otra accion nos deja en donde estamos
			super.navigate("/usuarios/crear.jsp",req,resp);			
		}
	}
	

}
