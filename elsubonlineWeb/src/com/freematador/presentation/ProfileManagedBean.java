package com.freematador.presentation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;

import com.freematador.business.AccountingEJB;
import com.freematador.business.CommunicationEJB;
import com.freematador.business.DealingEJB;
import com.freematador.business.SecurityEJB;
import com.freematador.business.SocialEJB;
import com.freematador.domain.Notification;
import com.freematador.domain.Operation;
import com.freematador.domain.Product;
import com.freematador.domain.Question;
import com.freematador.domain.Statement;
import com.freematador.exceptions.BusinessException;

@ManagedBean
@ViewScoped
public class ProfileManagedBean implements Serializable{
	@ManagedProperty(value = "#{loginManagedBean}")
	private LoginManagedBean loginManagedBean;
	@EJB
	private SecurityEJB securityEJB;
	@EJB
	private DealingEJB dealingEJB;
	@EJB
	private SocialEJB socialEJB;
	@EJB
	private CommunicationEJB communicationEJB;
	@EJB
	private AccountingEJB accountingEJB;
	
	private List<Operation> shoppingHistory = new ArrayList<Operation>();
	private List<Operation> salesHistory = new ArrayList<Operation>();
	private List<Question> questions = new ArrayList<Question>();
	private List<Notification> notifications = new ArrayList<Notification>();
	private List<Operation> pendingOperations = new ArrayList<Operation>();
	private List<Statement> pendingStatements = new ArrayList<Statement>();
	private List<Operation> unbilledOperations = new ArrayList<Operation>();
	private List<Statement> statementHistory = new ArrayList<Statement>();
	private List<Product> activeProducts = new ArrayList<Product>();
	private List<Product> inactiveProducts = new ArrayList<Product>();
	
	public ProfileManagedBean() {
	}
	
	@PostConstruct
	public void init() {
		shoppingHistory = dealingEJB.getUserShoppingHistory(loginManagedBean.getUser());
		salesHistory = dealingEJB.getUserSalesHistory(loginManagedBean.getUser());
		questions = socialEJB.getPendingQuestions(loginManagedBean.getUser());
		notifications = communicationEJB.getUserNotifications(loginManagedBean.getUser());
		pendingOperations = dealingEJB.getUserPendingOperations(loginManagedBean.getUser());
		pendingStatements = accountingEJB.getPendingStatements(loginManagedBean.getUser());
		unbilledOperations = accountingEJB.getUnbilledOperations(loginManagedBean.getUser());
		statementHistory = accountingEJB.getStatementHistory(loginManagedBean.getUser());
		activeProducts = dealingEJB.getActiveProducts(loginManagedBean.getUser());
		inactiveProducts = dealingEJB.getInactiveProducts(loginManagedBean.getUser());
	}

	public LoginManagedBean getLoginManagedBean() {
		return loginManagedBean;
	}

	public void setLoginManagedBean(LoginManagedBean loginManagedBean) {
		this.loginManagedBean = loginManagedBean;
	}
	
	public List<Operation> getShoppingHistory() {
		return shoppingHistory;
	}

	public void setShoppingHistory(List<Operation> shoppingHistory) {
		this.shoppingHistory = shoppingHistory;
	}

	public void updateUser() {
        FacesMessage message = null;
		try {
			System.out.println("Updating user...");
			securityEJB.updateUserProfile(this.loginManagedBean.getUser());
		    message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Perfil", "La informacion de su perfil fue actualizada.");  
	        RequestContext context = RequestContext.getCurrentInstance();  
	        context.update("registrationForm");
		} catch (BusinessException e) {
			message = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error", "Error interno del servidor.");
			e.printStackTrace();
		}
		FacesContext.getCurrentInstance().addMessage(null, message);
	}

	public List<Operation> getSalesHistory() {
		return salesHistory;
	}

	public void setSalesHistory(List<Operation> salesHistory) {
		this.salesHistory = salesHistory;
	}

	public List<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}
	
	public String saveAnswers() {
		String outcome = "profile";
		System.out.println("Saving answers...");
		socialEJB.saveAnswers(questions);
        RequestContext context = RequestContext.getCurrentInstance();  
        context.update("questionForm");
        context.update("questionsTab");
        context.update("profileAccordion");
        return outcome;
	}
	public List<Notification> getNotifications() {
		return notifications;
	}

	public void setNotifications(List<Notification> notifications) {
		this.notifications = notifications;
	}

	public List<Operation> getPendingOperations() {
		return pendingOperations;
	}

	public void setPendingOperations(List<Operation> pendingOperations) {
		this.pendingOperations = pendingOperations;
	}

	public List<Statement> getPendingStatements() {
		return pendingStatements;
	}

	public void setPendingStatements(List<Statement> pendingStatements) {
		this.pendingStatements = pendingStatements;
	}

	public List<Operation> getUnbilledOperations() {
		return unbilledOperations;
	}

	public void setUnbilledOperations(List<Operation> unbilledOperations) {
		this.unbilledOperations = unbilledOperations;
	}

	public List<Statement> getStatementHistory() {
		return statementHistory;
	}

	public void setStatementHistory(List<Statement> statementHistory) {
		this.statementHistory = statementHistory;
	}

	public List<Product> getActiveProducts() {
		return activeProducts;
	}

	public void setActiveProducts(List<Product> activeProducts) {
		this.activeProducts = activeProducts;
	}

	public List<Product> getInactiveProducts() {
		return inactiveProducts;
	}

	public void setInactiveProducts(List<Product> inactiveProducts) {
		this.inactiveProducts = inactiveProducts;
	}
	
	

}
