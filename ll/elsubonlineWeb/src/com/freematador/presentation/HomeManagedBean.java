package com.freematador.presentation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import com.freematador.business.DealingEJB;
import com.freematador.domain.Product;

@ManagedBean
@RequestScoped
public class HomeManagedBean implements Serializable{
	@EJB
	private DealingEJB dealingEJB;
	private List<Product> highlightedProducts = new ArrayList<Product>();
	private static final long serialVersionUID = 1L;

	public HomeManagedBean() {
	}

	public List<Product> getHighlightedProducts() {
		return highlightedProducts;
	}

	public void setHighlightedProducts(List<Product> highlightedProducts) {
		this.highlightedProducts = highlightedProducts;
	}
	
	@PostConstruct
	public void init() {
		this.highlightedProducts=dealingEJB.getHighlightedProducts();
	}
}
