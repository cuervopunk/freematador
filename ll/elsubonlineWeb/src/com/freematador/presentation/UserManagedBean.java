package com.freematador.presentation;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;

import com.freematador.business.SecurityEJB;

@ManagedBean
@RequestScoped
public class UserManagedBean {
	@EJB
	private SecurityEJB securityEJB;
	
	public UserManagedBean() {
		// TODO Auto-generated constructor stub
	}

	public String confirmRegistration(ComponentSystemEvent event) {
		String userId = (String) event.getComponent().getAttributes().get("userId");
		int id = new Integer(userId);
		securityEJB.activateUser(id);
		FacesMessage message = new FacesMessage("Se ha confirmado su registro en ElSubOnline!");
		FacesContext.getCurrentInstance().addMessage(null, message);
		return "index";
	}	
}
