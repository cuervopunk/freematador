package com.freematador.presentation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import org.primefaces.component.menuitem.MenuItem;
import org.primefaces.component.submenu.Submenu;
import org.primefaces.model.DefaultMenuModel;
import org.primefaces.model.MenuModel;

import com.freematador.business.CategoryTreeEJB;
import com.freematador.domain.Category;

@ManagedBean
@RequestScoped
public class CategoryTreeManagedBean implements Serializable{
	@EJB
	private CategoryTreeEJB categoryTreeEJB;
	private List<Category> categoryTree = new ArrayList<Category>();
	private MenuModel model;
	
	public CategoryTreeManagedBean() {
	}

	public List<Category> getCategoryTree() {
		setCategoryTree(categoryTreeEJB.getFullCategoryTree());
		return categoryTree;
	}

	public void setCategoryTree(List<Category> categoryTree) {
		this.categoryTree = categoryTree;
	}

	public void setModel(MenuModel model) {
		this.model = model;
	}

	//TODO: Cambiar esta aberracion por metodo recursivo
	public MenuModel getModel() {
		categoryTree = categoryTreeEJB.getFullCategoryTree();
		model = new DefaultMenuModel();  
		for(Category c0 : categoryTree) {
			if(c0.getSubcategories().size()>0){
		        Submenu submenu = new Submenu();  
		        submenu.setLabel(c0.getName());
				model.addSubmenu(submenu);
				for(Category c1 : c0.getSubcategories()) {
					if(c1.getSubcategories().size()>0){
				        Submenu submenu2 = new Submenu();  
				        submenu2.setLabel(c1.getName());
						submenu.getChildren().add(submenu2);
						for(Category c2 : c1.getSubcategories()) {
							if(c2.getSubcategories().size()>0){
						        Submenu submenu3 = new Submenu();  
						        submenu3.setLabel(c2.getName());
								submenu.getChildren().add(submenu3);
							}else{ //hojas
						        MenuItem item = new MenuItem();  
						        item.setValue(c2.getName());  
						        item.setId("cat"+c2.getId());
						        item.setUrl("/guest/categoryListing.xhtml?id="+c2.getId());
						        submenu2.getChildren().add(item);
							}
						}
					}else{ //hojas
				        MenuItem item = new MenuItem();  
				        item.setValue(c1.getName());  
				        item.setId("cat"+c1.getId());
				        item.setUrl("/guest/categoryListing.xhtml?id="+c1.getId());
				        submenu.getChildren().add(item);
					}
				}	        
			}else{ //hojas
		        MenuItem item = new MenuItem();  
		        item.setValue(c0.getName());  
		        item.setId("cat"+c0.getId());
		        item.setUrl("/guest/categoryListing.xhtml?id="+c0.getId());
		        model.addMenuItem(item);
			}	
		}
		return model;
	}

}