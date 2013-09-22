package com.freematador.webservices;

import javax.ejb.EJB;
import javax.jws.WebMethod;
import javax.jws.WebService;

import com.freematador.business.DealingEJB;

@WebService
public class ProviderIntegration {
	@EJB
	private DealingEJB dealingEJB;
	
	public ProviderIntegration() {
		// TODO Auto-generated constructor stub		
	}
	
	@WebMethod
	public void abitabPayment(int statementId) {
		System.out.println("Payment Method: Abitab...");
		dealingEJB.statementPayment(statementId);
	}

}
