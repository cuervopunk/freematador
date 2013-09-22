package com.freematador.presentation;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ComponentSystemEvent;

import com.freematador.business.CategoryTreeEJB;
import com.freematador.business.DealingEJB;
import com.freematador.domain.Category;
import com.freematador.domain.Product;

@ManagedBean
@ViewScoped
public class CategoryListingManagedBean implements Serializable{
	@EJB
	private CategoryTreeEJB categoryEJB;
	@EJB
	private DealingEJB productEJB;

	private Category category;
	private List<Product> products;
	private int categoryId;

	public CategoryListingManagedBean() {
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}
		
	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public String init(ComponentSystemEvent event) {
		String categoryId = (String) event.getComponent().getAttributes().get("categoryId");
		if(this.categoryId==0) {
			this.categoryId=Integer.parseInt(categoryId);
		}
		System.out.println("CategoryListingManagedBean - viewCategory: "+this.categoryId);
		this.category=categoryEJB.getCategory(this.categoryId);
		this.products=productEJB.getListingProducts(this.categoryId);
		return "product";
	}

}
