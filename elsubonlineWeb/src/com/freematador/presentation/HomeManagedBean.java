package com.freematador.presentation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import com.freematador.business.ProductEJB;
import com.freematador.domain.Product;

@ManagedBean
@RequestScoped
public class HomeManagedBean implements Serializable{
	@EJB
	private ProductEJB productEjb;
	private List<Product> highlightedProducts = new ArrayList<Product>();
	@ManagedProperty(value = "#{productManagedBean}")
	private ProductManagedBean productManagedBean;
	
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

	public ProductManagedBean getProductManagedBean() {
		return productManagedBean;
	}

	public void setProductManagedBean(ProductManagedBean productManagedBean) {
		this.productManagedBean = productManagedBean;
	}
	
	public String viewProduct() {
		Map<String,String> params = 
				FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		String productId = params.get("productId");
		productManagedBean.viewProduct(new Integer(productId));
		return "product";
	}

}
