package com.freematador.business;

import java.util.List;

import javax.ejb.Local;

import com.freematador.domain.Product;

@Local
public interface SearchEJB {
	public List<Product> searchProducts(String searchTerm);
}
