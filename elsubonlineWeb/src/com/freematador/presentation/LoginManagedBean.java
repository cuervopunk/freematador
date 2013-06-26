package com.freematador.presentation;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.primefaces.context.RequestContext;

import com.freematador.business.SecurityEJB;
import com.freematador.domain.User;
import com.freematador.exceptions.BusinessException;

@ManagedBean
@SessionScoped
public class LoginManagedBean implements Serializable{
	@EJB
	private SecurityEJB securityEjb;
	private User user;
	private boolean loggedIn = false;
	private String loggedUser = "";
	
	private static final long serialVersionUID = 1L;

	public LoginManagedBean() {
		this.user=new User();
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void submit(ActionEvent actionEvent) {  
        RequestContext context = RequestContext.getCurrentInstance();  
        FacesMessage msg = null;  
        try {
			if(securityEjb.loginUser(user)){
				user = securityEjb.getUserProfile(user.getEmail());
			    loggedIn = true;  
			    loggedUser = user.getEmail();
			    msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Welcome", user.getName());           
		        context.update("logginName");
		        context.addCallbackParam("loggedIn", loggedIn);  
			} else {  
			    loggedUser = "";
			    loggedIn = false;  
			    msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Login Error", "Invalid credentials");  
			}
		} catch (BusinessException e) {
	        FacesContext.getCurrentInstance().addMessage(null, msg);  
			e.printStackTrace();
		}  
          
    }

	public boolean isLoggedIn() {
		return loggedIn;
	}

	public void setLoggedIn(boolean loggedIn) {
		this.loggedIn = loggedIn;
	}

	public String getLoggedUser() {
		return loggedUser;
	}

	public void setLoggedUser(String loggedUser) {
		this.loggedUser = loggedUser;
	}  
	
}
