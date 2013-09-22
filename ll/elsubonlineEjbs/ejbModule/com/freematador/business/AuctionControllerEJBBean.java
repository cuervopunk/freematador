package com.freematador.business;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Schedule;
import javax.ejb.Singleton;

import com.freematador.domain.Bid;
import com.freematador.domain.Fee;
import com.freematador.domain.Operation;
import com.freematador.domain.Parameter;
import com.freematador.domain.Product;
import com.freematador.domain.SaleType;
import com.freematador.domain.User;
import com.freematador.persistence.FeeDAO;
import com.freematador.persistence.OperationDAO;
import com.freematador.persistence.ProductDAO;

/**
 * Session Bean implementation class AuctionControllerEJBBean
 */
@Singleton
@LocalBean
public class AuctionControllerEJBBean implements AuctionControllerEJB {
	@EJB
	private ProductDAO productDAO;
	@EJB
	private FeeDAO feeDAO;
	@EJB
	private OperationDAO operationDAO;
	@EJB
	private MailerEJB mailerEJB;
	@EJB
	private SystemEJB systemEJB;
	
	
    /**
     * Default constructor. 
     */
    public AuctionControllerEJBBean() {
        // TODO Auto-generated constructor stub
    }
    
    @Schedule(persistent=false, minute="*/1", hour="*")
    public void closeAuctions() {
    	Parameter parameter = systemEJB.getParameter("AUCTION_CONTROLLER_ENABLED");
    	boolean auctionControllerEnabled = Boolean.parseBoolean(parameter.getValue());
    	parameter = systemEJB.getParameter("COMMISSION");
    	float commission = Float.parseFloat(parameter.getValue());
    	
    	if(auctionControllerEnabled) {
	    	Date currentDate = new Date(System.currentTimeMillis());
	    	List<Product> auctions = productDAO.findProductsBySaleTypeEndDate(SaleType.AUCTION, currentDate);
	    	for(Product p : auctions) {
	    		p.setActive(false);
	    		Bid highestBid = p.getHighestBid();
	    		User auctionWinner = highestBid.getUser();
	    		
	    		if(auctionWinner!=null) { //si hubo ofertas hay ganador
		    		Fee fee = new Fee();
		    		fee.setDescription("Comision por venta: "+p.getShortDescription());
		    		fee.setAmount(new BigDecimal(highestBid.getValue()*commission));
		    		feeDAO.insert(fee);
		    		
		    		Operation operation = new Operation();
		    		operation.setBuyer(auctionWinner);
		    		operation.setSeller(p.getUser());
		    		operation.setProduct(p);
		    		operation.setDate(currentDate);
		    		operation.setFee(fee);
		    		operationDAO.insert(operation);
	    		}
	    		
	    		productDAO.update(p);
	    		
	    		mailerEJB.mailBuyerUser(auctionWinner, p.getUser(), p);
	    	}
	    	System.out.println("[AUCTION_CONTROLLER_PROCESS]: "+auctions.size()+" products updated.");
    	}
    }

}
