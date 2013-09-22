package com.freematador.business;

import java.math.BigDecimal;
import java.util.List;

import javax.ejb.Remote;

import com.freematador.domain.Operation;
import com.freematador.domain.Picture;
import com.freematador.domain.Product;
import com.freematador.domain.User;
import com.freematador.exceptions.BusinessException;

@Remote
public interface DealingEJB {
	public void publishProduct(Product product, User user);
	public Product getProduct(int productId);
	public List<Product> getListingProducts(int categoryId);
	public void buyProduct(Product product, User user) throws BusinessException;
	public void bidProduct(Product product, User user, BigDecimal bid)  throws BusinessException;
	public List<Operation> getUserShoppingHistory(User user);
	public List<Operation> getUserSalesHistory(User user);
	public List<Product> getHighlightedProducts();
	public List<Operation> getUserPendingOperations(User user);
	public void finishOperations(List<Operation> pendingOperations);
	public List<Product> getActiveProducts(User user);
	public List<Product> getInactiveProducts(User user);
	public void editProduct(Product product, List<Picture> newPictures);
	public void statementPayment(int statementId); 
	public BigDecimal getProductPrice(int productId) throws BusinessException;
}
