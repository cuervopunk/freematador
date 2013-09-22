package com.freematador.business;

import java.util.List;

import javax.ejb.Local;

import com.freematador.domain.Category;

@Local
public interface CategoryTreeEJB {
    public List<Category> getFullCategoryTree();
    public Category getCategory(int categoryId);
    public List<Category> getCategoriesByNodeHeight(int nodeHeight);
	public void addCategory(Category selectedCategory, String newCategory, int nodeHeight);
	public void deleteCategory(Category selectedCategory);
    
}
