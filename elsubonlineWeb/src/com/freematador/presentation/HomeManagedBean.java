package com.freematador.presentation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.freematador.business.ProductEJB;
import com.freematador.domain.Product;

@ManagedBean
@ViewScoped
public class HomeManagedBean implements Serializable{
	@EJB
	private ProductEJB productEjb;
	private List<Product> highlightedProducts = new ArrayList<Product>();
	
	private static final long serialVersionUID = 1L;

	public HomeManagedBean() {
	}

	public List<Product> getHighlightedProducts() {
		this.highlightedProducts=productEjb.getHighlighedProducts();
		return highlightedProducts;
	}

	public void setHighlightedProducts(List<Product> highlightedProducts) {
		this.highlightedProducts = highlightedProducts;
	}
	

}