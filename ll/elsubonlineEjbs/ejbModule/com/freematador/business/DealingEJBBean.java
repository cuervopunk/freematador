package com.freematador.business;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.freematador.domain.Bid;
import com.freematador.domain.Fee;
import com.freematador.domain.Notification;
import com.freematador.domain.Operation;
import com.freematador.domain.Parameter;
import com.freematador.domain.Payment;
import com.freematador.domain.Picture;
import com.freematador.domain.Product;
import com.freematador.domain.SaleType;
import com.freematador.domain.Statement;
import com.freematador.domain.User;
import com.freematador.exceptions.BusinessException;
import com.freematador.persistence.BidDAO;
import com.freematador.persistence.FeeDAO;
import com.freematador.persistence.NotificationDAO;
import com.freematador.persistence.OperationDAO;
import com.freematador.persistence.PaymentDAO;
import com.freematador.persistence.PictureDAO;
import com.freematador.persistence.ProductDAO;
import com.freematador.persistence.StatementDAO;
import com.freematador.persistence.UserDAO;
import com.freematador.utils.DateUtils;

public @Stateless class DealingEJBBean implements DealingEJB {
	@EJB
	private ProductDAO productDAO;
	@EJB 
	private OperationDAO operationDAO;
	@EJB 
	private UserDAO userDAO;
	@EJB
	private FeeDAO feeDAO;
	@EJB
	private BidDAO bidDAO;
	@EJB
	private NotificationDAO notificationDAO;
	@EJB
	private StatementDAO statementDAO;
	@EJB
	private PaymentDAO paymentDAO;
	@EJB
	private PictureDAO pictureDAO;
	@EJB
	private SystemEJB systemEJB;
	
	public Product getProduct(int productId) {
		Product p = productDAO.findById(new Integer(productId));
		p.getPictures().size();
		p.getQuestions().size();
		return p;
	}
	
	public List<Product> getListingProducts(int categoryId) {
		System.out.println("ProductEJBBean - categoryId:"+categoryId);
		List<Product> listing = productDAO.getProductsByCategory(categoryId);
		for(Product p : listing) p.getPictures().size();
		return listing;
	}

	@Override
	public void publishProduct(Product product, User user) {
		Date today = DateUtils.getToday();

		
        Calendar calendar = Calendar.getInstance();  
        calendar.setTime(today);
		if(product.getSaleType()==SaleType.AUCTION) {
	        calendar.add(Calendar.DATE, 15);
	    }else{
	        calendar.add(Calendar.DATE, 30);	    	
	    }
        Date endingDate = calendar.getTime();
		product.setUser(user);
		product.setEndingDate(endingDate);
		List<Picture> pictures = product.getPictures();
		for(Picture p : pictures) {
			pictureDAO.insert(p);
		}
		product.setPictures(pictures);
		productDAO.insert(product);	
	}
	
	@Override
	public void editProduct(Product product, List<Picture> newPictures) {
		Product refreshedProduct = productDAO.findById(product.getId());
		refreshedProduct.setBasePrice(product.getBasePrice());
		refreshedProduct.setEndingDate(product.getEndingDate());
		refreshedProduct.setHighlight(product.isHighlight());
		refreshedProduct.setLongDescription(product.getLongDescription());
		refreshedProduct.setPictures(null);
		refreshedProduct.setSaleType(product.getSaleType());
		refreshedProduct.setShortDescription(product.getShortDescription());
		productDAO.update(refreshedProduct);

		refreshedProduct = productDAO.findById(product.getId());
		List<Picture> oldPictures = refreshedProduct.getPictures();
		if(oldPictures != null && oldPictures.size() > 0) {
			for(Picture p : oldPictures) {
				pictureDAO.delete(p.getId());
			}		
		}

		for(Picture p : newPictures) {
			pictureDAO.insert(p);
		}
		refreshedProduct.setPictures(newPictures);
		productDAO.update(refreshedProduct);
	}	
	
	public void buyProduct(Product product, User user) throws BusinessException {
		if(user.equals(product.getUser())) {
			throw new BusinessException("Usted no puede comprar sus propios productos.");
		}
		
    	Parameter parameter = systemEJB.getParameter("COMMISSION");
    	float commission = Float.parseFloat(parameter.getValue());

		Fee aFee = new Fee();
		aFee.setDescription("Comision por venta: "+product.getShortDescription());
		aFee.setAmount(new BigDecimal(product.getBasePrice()*commission)); 
		feeDAO.insert(aFee);

		Operation aOperation = new Operation();
		aOperation.setDate(new Date(System.currentTimeMillis()));
		aOperation.setProduct(product);
		aOperation.setBuyer(user);
		aOperation.setSeller(product.getUser());
		aOperation.setFee(aFee);
		operationDAO.insert(aOperation);
		
		Notification notification = new Notification();
		notification.setDescription("Indique si se concreto el intercambio con su contraparte");
		notification.setOutcome("feedback");
		notification.setName("Feedback");
		notification.setOwner(aOperation.getSeller());
		notification.setProduct(product);
		notificationDAO.insert(notification);	
		
		User reloadedUser = userDAO.findById(user.getId());
		reloadedUser.getNotifications().add(notification);
		List<Operation> userOperations = reloadedUser.getOperations();
		userOperations.add(aOperation);
		userDAO.update(reloadedUser);
	}

	public void bidProduct(Product product, User user, BigDecimal bid) throws BusinessException {
		Bid newBid = new Bid();
		newBid.setValue(bid.floatValue());
		newBid.setUser(user);
		newBid.setDate(new Date(System.currentTimeMillis()));
		bidDAO.insert(newBid);
		
		Product aProduct = productDAO.findById(product.getId());
		Bid highestBid = product.getHighestBid();

		if(user.equals(product.getUser())) {
			throw new BusinessException("Usted no puede comprar sus propios productos.");
		}
		
		if(highestBid==null || newBid.getValue()>highestBid.getValue()) {
			aProduct.setHighestBid(newBid);
			aProduct.getBids().add(newBid);
		}else{
			throw new BusinessException("Su oferta debe ser mayor que la actual.");
		}
	}
	
	public List<Operation> getUserShoppingHistory(User user) {
		List<Operation> shoppingHistory = new ArrayList<Operation>();
		shoppingHistory = operationDAO.findByBuyerId(user.getId());
		return shoppingHistory;
	}

	public List<Operation> getUserSalesHistory(User user) {
		List<Operation> salesHistory = new ArrayList<Operation>();
		salesHistory = operationDAO.findBySellerId(user.getId());
		return salesHistory;
	}
	
	public List<Product> getHighlightedProducts() {
		List<Product> highlightedProducts = new ArrayList<Product>();
		highlightedProducts = productDAO.findHighlighted(true);
		for(Product p : highlightedProducts) p.getPictures().size();
		return highlightedProducts;
	}

	@Override
	public List<Operation> getUserPendingOperations(User user) {
		List<Operation> pendingOperations = new ArrayList<Operation>();
		pendingOperations = operationDAO.findByFinishedStatus(user.getId(), false);
		return pendingOperations;
	}

	@Override
	public void finishOperations(List<Operation> pendingOperations) {
		System.out.println("Finishing operations...");
		for(Operation o : pendingOperations) {
			Operation refreshedOperation = operationDAO.findById(o.getId());
			refreshedOperation.setFinished(true);
			refreshedOperation.setSuccessful(o.isSuccessful());
			operationDAO.update(refreshedOperation);
			List<Notification> notifications = notificationDAO.findByProductId(o.getProduct().getId());
			for(Notification n : notifications) {
				notificationDAO.delete(n.getId());
			}
		}
	}

	@Override
	public List<Product> getActiveProducts(User user) {
		return productDAO.findByUserStatus(user.getId(), true);
	}

	@Override
	public List<Product> getInactiveProducts(User user) {
		return productDAO.findByUserStatus(user.getId(), false);
	}

	@Override
	public void statementPayment(int statementId) {
		System.out.println("Updating payment information for statement: "+statementId);
		Statement unpayedStatement = statementDAO.findById(statementId);
		Payment newPayment = new Payment();
		newPayment.setStatement(unpayedStatement);
		newPayment.setDate(DateUtils.getToday());
		newPayment.setAmount(unpayedStatement.getTotal());
		paymentDAO.insert(newPayment);
		unpayedStatement.setPayment(newPayment);
		statementDAO.update(unpayedStatement);
	}

	public BigDecimal getProductPrice(int productId) throws BusinessException {
		BigDecimal price = new BigDecimal(0);
		if(productId>0) {
			Product product=getProduct(productId);
			if(product.getHighestBid()!=null) {
				price=new BigDecimal(product.getHighestBid().getValue());
			}else{
				price=new BigDecimal(product.getBasePrice());
			}
		}
		if(price==null || price.floatValue()==0) {
			throw new BusinessException("Error calculando precio del producto");
		}
		return price;
	}
	
}
