package com.freematador.business;

import java.util.List;

import javax.ejb.Local;

import com.freematador.domain.Operation;
import com.freematador.domain.Statement;
import com.freematador.domain.User;

@Local
public interface AccountingEJB {
	public List<Statement> getPendingStatements(User user);
	public List<Operation> getUnbilledOperations(User user);
	public List<Statement> getStatementHistory(User user);
	public Statement getStatement(Integer statementId);
}
