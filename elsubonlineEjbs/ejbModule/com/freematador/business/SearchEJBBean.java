package com.freematador.business;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import com.freematador.domain.Product;
import com.freematador.persistence.ProductDAO;

/**
 * Session Bean implementation class SearchEJBBean
 */
@Stateless
@LocalBean
public class SearchEJBBean implements SearchEJB {
	@EJB
	private ProductDAO productDAO;

    /**
     * Default constructor. 
     */
    public SearchEJBBean() {
        // TODO Auto-generated constructor stub
    	
    }
    
    public List<Product> searchProducts(String searchTerm) {
    	List<Product> searchResults = new ArrayList<Product>();
    	searchResults = productDAO.findByDescription(searchTerm);
		for(Product p : searchResults) p.getPictures().size();
		System.out.println(searchResults.size() + " products found");
		return searchResults;
    }

}
