package com.freematador.business;

import javax.ejb.Remote;

import com.freematador.domain.User;

@Remote
public interface SecurityEJB {
	public void createUser(User u);

}
