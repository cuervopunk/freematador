package com.freematador.business;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.freematador.domain.Category;
import com.freematador.domain.Picture;
import com.freematador.domain.Product;
import com.freematador.persistence.CategoryDAO;

/**
 * Session Bean implementation class CategoryTreeEJBBean
 */
@Stateless
public class CategoryTreeEJBBean implements CategoryTreeEJB {
	@EJB
	private CategoryDAO categoryDAO;
	
    /**
     * Default constructor. 
     */
    public CategoryTreeEJBBean() {
    }
    
    public List<Category> getFullCategoryTree() {
    	return categoryDAO.findByNodeHeight(0);
    }

    public List<Category> getCategoriesByNodeHeight(int nodeHeight) {
    	List<Category> categories = categoryDAO.findByNodeHeight(nodeHeight);
    	return categories;
    }
    
    public Category getCategory(int categoryId) {
    	Category aCategory = categoryDAO.findById(categoryId);
    	//esto es una manganeta porque no esta funcionando FetchType.LAZY y 
    	//ademas no puedo usar dos FetchType.EAGER en la misma clase 
    	
    	//la regla general es evitar las relaciones xxToMany
    	//solución 1: levantarlos de esta manera: en realidad se usa el .size
    	if(aCategory!=null) {
    	List<Product> products = aCategory.getProducts();
    	if(products!=null && products.size()>0) {
	    	Product aProduct = products.get(0);
	    	List<Picture> pictures = (List<Picture>) aProduct.getPictures();
	    	if(pictures.size()>0) {
	    		Picture aPicture = pictures.get(0);
	    		aPicture.getFileName();
	    	}
    	}
    	}
    	//solución 2: entrarle por producto con una consulta de este tipo:
    	//select p from Product p where p.category.id = idCat
    	return aCategory;
    }

	@Override
	public void addCategory(Category selectedCategory, String newCategoryName, int nodeHeight) {
		Category newCategory = new Category();
		newCategory.setName(newCategoryName);
		newCategory.setNodeHeight(nodeHeight);
		categoryDAO.insert(newCategory);
		
		if(selectedCategory!=null && selectedCategory.getId()>0) {
			Category refreshedSelectedCategory = categoryDAO.findById(selectedCategory.getId());
			if(refreshedSelectedCategory.getSubcategories()==null) {
				refreshedSelectedCategory.setSubcategories(new ArrayList<Category>());
			}
			refreshedSelectedCategory.getSubcategories().add(newCategory);
			categoryDAO.update(refreshedSelectedCategory);
		}
	}

	@Override
	public void deleteCategory(Category selectedCategory) {
		if(selectedCategory!=null) {
			Category refreshedSelectedCategory = categoryDAO.findById(selectedCategory.getId());
			categoryDAO.delete(refreshedSelectedCategory);
		}
	}
	
}
