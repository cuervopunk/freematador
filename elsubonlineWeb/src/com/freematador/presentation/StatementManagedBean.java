package com.freematador.presentation;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

import com.freematador.business.AccountingEJB;
import com.freematador.domain.Statement;

@ManagedBean
@RequestScoped
public class StatementManagedBean {
	@ManagedProperty(value = "#{loginManagedBean}")
	private LoginManagedBean loginManagedBean;
	@EJB
	private AccountingEJB accountingEJB;
	@ManagedProperty("#{param.id}")
	public Integer statementId;

	private Statement statement;
	
	@PostConstruct
	public void init() {
		statement = accountingEJB.getStatement(statementId);
	}
	
	
	public StatementManagedBean() {
		// TODO Auto-generated constructor stub
	}

	public Statement getStatement() {
		return statement;
	}

	public void setStatement(Statement statement) {
		this.statement = statement;
	}


	public LoginManagedBean getLoginManagedBean() {
		return loginManagedBean;
	}


	public void setLoginManagedBean(LoginManagedBean loginManagedBean) {
		this.loginManagedBean = loginManagedBean;
	}


	public Integer getStatementId() {
		return statementId;
	}


	public void setStatementId(Integer statementId) {
		this.statementId = statementId;
	}
	

}
