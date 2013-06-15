package com.freematador.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Store implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private List<Product> products = new ArrayList<Product>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}	

}
