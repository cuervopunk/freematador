package com.freematador.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
@Entity
public class Category implements Serializable{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private String name;
	@ManyToMany(fetch=FetchType.EAGER, cascade = CascadeType.ALL)
	private List<Category> subcategories = new ArrayList<Category>();
	@OneToMany
	private List<Product> products = new ArrayList<Product>();
	private int nodeHeight;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<Category> getSubcategories() {
		return subcategories;
	}
	public void setSubcategories(List<Category> subcategories) {
		this.subcategories = subcategories;
	}
	public List<Product> getProducts() {
		return products;
	}
	public void setProducts(List<Product> products) {
		this.products = products;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getNodeHeight() {
		return nodeHeight;
	}
	public void setNodeHeight(int nodeHeight) {
		this.nodeHeight = nodeHeight;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Category)) {
			return false;
		}		
		Category otro = (Category)obj;
		return this.id == otro.id;
	}
	
}
