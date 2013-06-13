package com.freematador.presentation;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.freematador.business.SecurityServer;
import com.freematador.business.SecurityServerImpl;
import com.freematador.utils.BaseController;
import com.freematador.utils.ExcepcionGuap;


@WebServlet(name="LoginController", urlPatterns={"/loginController"})
public class LoginController extends BaseController {

	private static final long serialVersionUID = 1L;

	private SecurityServer srvSeguridad = new SecurityServerImpl();
	
	@Override
	protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String action = req.getParameter("ACTION");
		if ("CONFIRMAR".equals(action)) {
			// Intentamos hacer el login
			String login = req.getParameter("LOGIN"); 
			String password = req.getParameter("PASSWORD"); 
			
			try {
				boolean exito = this.srvSeguridad.login(login, password);
				if (exito) {
					super.navigate("/menu.jsp",req,resp);			
				} else {
					super.showMessage("Usuario incorrecto", req);
					super.navigate("/login.jsp",req,resp);
				}
			} catch (ExcepcionGuap e) {
				super.showMessage("Ha ocurrido un error al ingresar al sistema", req);
				super.navigate("/login.jsp",req,resp);
			}
			
		} else {
			// Cualquier otra accion nos lleva de vuelta al login
			super.navigate("/login.jsp",req,resp);			
		}
	}
	

}
