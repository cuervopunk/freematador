package com.freematador.presentation;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.faces.bean.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.primefaces.context.RequestContext;

import com.freematador.business.SecurityEJB;
import com.freematador.domain.User;
import com.freematador.exceptions.BusinessException;

@SessionScoped
@ManagedBean
public class LoginManagedBean implements Serializable{
	@EJB
	private SecurityEJB securityEjb;
	private User user;
	private boolean loggedIn;
	private String email;
	private String password;

	public LoginManagedBean() {
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}


	public boolean isLoggedIn() {
		return loggedIn;
	}

	public void setLoggedIn(boolean loggedIn) {
		this.loggedIn = loggedIn;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void submit(ActionEvent actionEvent) {  
        FacesMessage msg = null;
       
        try {
        	user = new User();
        	user.setPassword(password);
        	user.setEmail(email);
        	
			if(securityEjb.loginUser(user)){
			    loggedIn = true;  
				user = securityEjb.getUserProfile(email);
			    msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Welcome", user.getName());           
		        RequestContext context = RequestContext.getCurrentInstance();  
		        context.update("headerMenu");
		        context.update("logginName");
		        context.addCallbackParam("loggedIn", loggedIn);  
		     
			} else {  
			    msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Login Error", "Invalid credentials");  
			}
		} catch (BusinessException e) {
	        FacesContext.getCurrentInstance().addMessage(null, msg);  
			e.printStackTrace();
		}  
          
    }

	public String logout() {
		System.out.println("Closing session...");
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "/index.xhtml?faces-redirect=true";
	}
}
