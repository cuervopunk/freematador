package com.freematador.presentation;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.primefaces.context.RequestContext;

import com.freematador.business.SecurityEJB;
import com.freematador.domain.User;

@ManagedBean
@ViewScoped
public class Login implements Serializable{
	@EJB
	private SecurityEJB securityEjb;
	private User user;
	
	private static final long serialVersionUID = 1L;

	public Login() {
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
        boolean loggedIn = false;  
          
        if(user.getEmail() != null && 
        		user.getEmail().equals("admin") && 
        		user.getPassword() != null  && 
        		user.getPassword().equals("admin")) {  
            loggedIn = true;  
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Welcome", user.getName());  
        } else {  
            loggedIn = false;  
            msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Login Error", "Invalid credentials");  
        }  
          
        FacesContext.getCurrentInstance().addMessage(null, msg);  
        context.addCallbackParam("loggedIn", loggedIn);  
    }  

}
