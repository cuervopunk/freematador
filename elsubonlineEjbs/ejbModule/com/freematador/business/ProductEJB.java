package com.freematador.business;

import java.util.List;

import javax.ejb.Remote;

import com.freematador.domain.Product;

@Remote
public interface ProductEJB {
	public List<Product> getHighlighedProducts();

}