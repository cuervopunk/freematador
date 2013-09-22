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
	private boolean admin;
	
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

	public String submit(ActionEvent actionEvent) {  
        String outcome  = "index";
		FacesMessage msg = null;
        loggedIn = false;
        
        try {
        	user = new User();
        	user.setPassword(password);
        	user.setEmail(email);
	        RequestContext context = RequestContext.getCurrentInstance();  
        	
			if(securityEjb.loginUser(user)){
			    loggedIn = true;  
				user = securityEjb.getUserProfile(email);
		        setAdmin(securityEjb.isAdmin(user));
			    msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Welcome", user.getName());           
		        context.update("topMenu");
		        context.update("headerMenu");
		        context.update("logginName");
		        context.update("logginMessage");
		        context.update("welcomeMessage");
		     
			} else {  
				user = null;
			    msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Login Error", "Invalid credentials");  
			}
	        context.addCallbackParam("loggedIn", loggedIn);  
		} catch (BusinessException e) {
	        FacesContext.getCurrentInstance().addMessage(null, msg);  
			e.printStackTrace();
		}  
        return outcome;
          
    }

	public void recover(ActionEvent actionEvent) {  
        RequestContext context = RequestContext.getCurrentInstance();  
        FacesMessage msg = null;
        boolean recoverySent = false;
		try {
		    msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Password Recovery", "Se ha enviado un mail con una nueva password.");           
			System.out.println("Password recovery...");
			securityEjb.recoverPassword(email);
			recoverySent=true;
		} catch (BusinessException e) {
	        FacesContext.getCurrentInstance().addMessage(null, msg);  
			e.printStackTrace();
		}
        context.update("logginMessage");
        context.addCallbackParam("recoverySent", recoverySent);  
	}
	
	public SecurityEJB getSecurityEjb() {
		return securityEjb;
	}

	public void setSecurityEjb(SecurityEJB securityEjb) {
		this.securityEjb = securityEjb;
	}

	public String logout() {
		System.out.println("Closing session...");
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "/index.xhtml?faces-redirect=true";
	}

	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	
}
