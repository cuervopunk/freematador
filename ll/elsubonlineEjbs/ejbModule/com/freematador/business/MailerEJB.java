package com.freematador.business;

import javax.ejb.Local;

import com.freematador.domain.Product;
import com.freematador.domain.User;

@Local
public interface MailerEJB {
	public void mailNewUser(User u);
	public void mailBuyerUser(User buyer, User seller, Product product);
	public void mailRecoveryUser(User user, String newPassword);
}
