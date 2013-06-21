package com.freematador.presentation;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import com.freematador.business.SecurityEJB;
import com.freematador.domain.User;

@ManagedBean
@ViewScoped
public class RegisterManagedBean implements Serializable {
	@EJB
	private SecurityEJB securityEjb;
	
	private static final long serialVersionUID = 1L;

	private User user;

	public RegisterManagedBean() {
		user = new User();
	}

	public void submit() {
		securityEjb.createUser(user);
		FacesMessage message = new FacesMessage("Registration succesful!");
		FacesContext.getCurrentInstance().addMessage(null, message);
	}

	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	
}