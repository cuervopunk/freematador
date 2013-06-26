package com.freematador.business;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.freematador.domain.User;

public @Stateless class SecurityEJBBean implements SecurityEJB {
	@PersistenceContext(unitName="ElSubOnlineUNIT")
	private EntityManager em;
	
	public void createUser(User u) {
		System.out.println("Se ha creado el usuario "+u.getName());
		em.persist(u);
	}
}
