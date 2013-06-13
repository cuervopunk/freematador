package com.freematador.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class BaseController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.process(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.process(req, resp);
	}

	protected abstract void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException;

	//////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public void navigate(String path, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		RequestDispatcher rd = this.getServletContext().getRequestDispatcher(path);
		rd.forward(req, resp);
	}

	public void showMessage(String message, HttpServletRequest req) {
		List<String> messages = (List<String>)req.getAttribute("MENSAJES");
		if (messages == null) {
			messages = new ArrayList<String>();
			req.setAttribute("MENSAJES", messages);
		}
		messages.add(message);
	}

}
