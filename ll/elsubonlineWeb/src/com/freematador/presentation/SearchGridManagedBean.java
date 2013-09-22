package com.freematador.presentation;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ComponentSystemEvent;

import com.freematador.business.SearchEJB;
import com.freematador.domain.Product;

@ManagedBean
@ViewScoped
public class SearchGridManagedBean implements Serializable {
	private List<Product> searchResults;
	@EJB 
	private SearchEJB searchEJB;
	private String searchTerm;

	public SearchGridManagedBean() {
	}

	public List<Product> getSearchResults() {
		return searchResults;
	}

	public void setSearchResults(List<Product> searchResults) {
		this.searchResults = searchResults;
	}

	public void init(ComponentSystemEvent event) {
		searchTerm = (String) event.getComponent().getAttributes().get("searchTerm");
		if(searchTerm!=null) {
			System.out.println("[SearchGridManagedBean] Searching...");					
			System.out.println("[SearchGridManagedBean] Search Term: "+this.searchTerm);
			searchResults = searchEJB.searchProducts(searchTerm);
		}
	}

	public String getSearchTerm() {
		return searchTerm;
	}

	public void setSearchTerm(String searchTerm) {
		this.searchTerm = searchTerm;
	}
	
	
}
