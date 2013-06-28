package com.freematador.presentation;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import com.freematador.business.ProductEJB;
import com.freematador.domain.Product;

@ManagedBean
@RequestScoped
public class ProductManagedBean implements Serializable{
	@EJB
	private ProductEJB productEjb;
	private Product product = new Product();
	
	private static final long serialVersionUID = 1L;

	public ProductManagedBean() {
	}

	public ProductEJB getProductEjb() {
		return productEjb;
	}

	public void setProductEjb(ProductEJB productEjb) {
		this.productEjb = productEjb;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}
	
	public void viewProduct(int id) {
		System.out.println("Product "+id);
		this.product=productEjb.getProduct(id);
	}

}
