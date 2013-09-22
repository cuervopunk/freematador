package com.freematador.presentation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import com.freematador.business.DealingEJB;
import com.freematador.domain.Operation;

@ManagedBean
@ViewScoped
public class FeedbackManagedBean implements Serializable{
	@ManagedProperty(value = "#{loginManagedBean}")
	private LoginManagedBean loginManagedBean;
	private List<Operation> pendingOperations = new ArrayList<Operation>();
	@EJB
	private DealingEJB dealingEJB;
	
	public FeedbackManagedBean() {
	}
	
	@PostConstruct
	private void init() {
		pendingOperations = dealingEJB.getUserPendingOperations(loginManagedBean.getUser());
	}

	public List<Operation> getPendingOperations() {
		return pendingOperations;
	}

	public void setPendingOperations(List<Operation> pendingOperations) {
		this.pendingOperations = pendingOperations;
	}

	public LoginManagedBean getLoginManagedBean() {
		return loginManagedBean;
	}

	public void setLoginManagedBean(LoginManagedBean loginManagedBean) {
		this.loginManagedBean = loginManagedBean;
	}
	
	public String save() {
		String outcome="feedback";
		dealingEJB.finishOperations(pendingOperations);
		return outcome;
	}
	
}
