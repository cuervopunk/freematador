package com.freematador.business;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.persistence.PersistenceException;

import com.freematador.domain.Fee;
import com.freematador.domain.Operation;
import com.freematador.domain.Parameter;
import com.freematador.domain.Statement;
import com.freematador.domain.User;
import com.freematador.persistence.FeeDAO;
import com.freematador.persistence.OperationDAO;
import com.freematador.persistence.StatementDAO;
import com.freematador.persistence.UserDAO;
import com.freematador.utils.DateUtils;

/**
 * Session Bean implementation class AccountingEJBBean
 */
@Singleton
@LocalBean
public class AccountingEJBBean implements AccountingEJB {
	@EJB
	private OperationDAO operationDAO;
	@EJB
	private UserDAO userDAO;
	@EJB
	private StatementDAO statementDAO;
	@EJB
	private FeeDAO feeDAO;
	@EJB
	private SystemEJB systemEJB;
	
    /**
     * Default constructor. 
     */
    public AccountingEJBBean() {
    }
    
    @Schedule(persistent=false, second="*/30", minute="*", hour="*")
    public void billingMonthEnd() {
    	Parameter parameter = systemEJB.getParameter("BILLING_ENABLED");
    	boolean billingEnabled = Boolean.parseBoolean(parameter.getValue());
    	
    	if(billingEnabled) {
	    	List<User> activeUsers = userDAO.findAll();
	    	Date start = DateUtils.getFirstDayMonth();
	    	Date end = DateUtils.getLastDayMonth();
	    	System.out.println("[BILLING_PROCESS] Starting process.");
	    	System.out.println("[BILLING_PROCESS] From: "+start);
	    	System.out.println("[BILLING_PROCESS] To: "+end);
	    	
	    	for(User u : activeUsers) {
	    		try {
					List<Operation> monthOperations = operationDAO.findBySellerDateRange(start, end, u.getId());
					Statement theStatement = getMonthStatement(monthOperations);
					System.out.println("["+u.getEmail()+"] Month operations: "+monthOperations.size());
					System.out.println("["+u.getEmail()+"] Total Month Charges: "+theStatement.getTotal());
					User refreshedUser = userDAO.findById(u.getId());
					theStatement.setUser(refreshedUser);	    	
					
					statementDAO.truncate(); //a los efectos de realizar pruebas, la fact se ejecuta cada 30segundos y se truncan las tablas c/vez
					statementDAO.insert(theStatement);
				} catch (PersistenceException e) {
					e.printStackTrace();
				}	
	    	}
    	}
    }
    
    public Statement getMonthStatement(List<Operation> operations) {
    	BigDecimal total =  BigDecimal.ZERO;
    	Statement statement = new Statement();
    	for(Operation o : operations) {
    		Fee aFee = o.getFee();
    		BigDecimal feeAmount =  aFee.getAmount();
    		total = total.add(feeAmount);
    		Calendar cal = Calendar.getInstance();
    		cal.setTime(DateUtils.getToday());
    		cal.add(Calendar.DAY_OF_MONTH, 15);
    		statement.setTotal(total);
    		statement.getFees().add(aFee);
    		statement.setDue(cal.getTime());
    	}
    	return statement;
    }

	@Override
	public List<Statement> getPendingStatements(User user) {
		return statementDAO.findByUserNullPayment(user.getId());		
	}
	
	@Override
	public List<Operation> getUnbilledOperations(User user) {
    	Date start = DateUtils.getFirstDayMonth();
    	Date end = DateUtils.getLastDayMonth();
		
    	List<Operation> operations = operationDAO.findBySellerDateRange(start, end, user.getId());
    	return operations;
	}

	@Override
	public List<Statement> getStatementHistory(User user) {
		List<Statement> statementHistory = statementDAO.findByUser(user.getId());
		return statementHistory;
	}

	@Override
	public Statement getStatement(Integer statementId) {
		Statement aStatement = statementDAO.findById(statementId);
		aStatement.getFees().size();
		return aStatement;
	}

}
