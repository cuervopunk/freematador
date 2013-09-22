package com.freematador.presentation;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.event.ComponentSystemEvent;

import com.freematador.business.SearchEJB;

@ManagedBean
@RequestScoped
public class SearchManagedBean implements Serializable{
	private String searchTerm;
	
	public SearchManagedBean() {
	}
	
	public String getSearchTerm() {
		return searchTerm;
	}

	public void setSearchTerm(String searchTerm) {
		this.searchTerm = searchTerm;
	}

	public String searchProduct() {
		System.out.println("[SearchManagedBean] Redirecting to search view...");
		String outcome = "search";
		return outcome;
	}
	
}
