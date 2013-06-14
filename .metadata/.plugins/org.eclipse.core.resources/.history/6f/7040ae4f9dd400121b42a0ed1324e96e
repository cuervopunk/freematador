package com.freematador.presentation;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.freematador.utils.BaseController;


@WebServlet(name="MenuController", urlPatterns={"/menuController"})
public class MenuController extends BaseController {

	private static final long serialVersionUID = 1L;

	@Override
	protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String action = req.getParameter("ACTION");
		if ("SALIR".equals(action)) {
			super.navigate("/login.jsp",req,resp);
		} else if ("CREAR_USUARIO".equals(action)) {
			super.navigate("/usuarios/crear.jsp",req,resp);			
		} else {
			// Cualquier otra accion nos deja en el menu
			super.navigate("/menu.jsp",req,resp);			
		}
	}
	

}
