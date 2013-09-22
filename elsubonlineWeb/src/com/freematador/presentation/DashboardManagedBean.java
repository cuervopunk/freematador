package com.freematador.presentation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

import org.primefaces.event.CellEditEvent;

import com.freematador.business.CategoryTreeEJB;
import com.freematador.business.SystemEJB;
import com.freematador.domain.Category;
import com.freematador.domain.Parameter;

@ManagedBean
@ViewScoped
public class DashboardManagedBean implements Serializable {
	private List<Parameter> parameters;
	@EJB 
	private SystemEJB systemEJB;
	@EJB
	private CategoryTreeEJB categoryEJB;

	private int actualNodeHeight;
	private Category selectedCategory;
	
	private List<Category> subcategoriesFirstLevel=new ArrayList<Category>();
	private List<Category> subcategoriesSecondLevel=new ArrayList<Category>();
	
	private int categoryId;
	private int subcategoryId1;
	private int subcategoryId2;
	private boolean listingSelected=false;

	private String newCategoryName;
	private String categoryPath = "";
	private Category[] categoryStack = new Category[3];
	
	public DashboardManagedBean() {
	}

	@PostConstruct
	private void init() {
		parameters = systemEJB.getParameters(); 
	}
	
	public void onCellEdit(CellEditEvent event) {  
        Object oldValue = event.getOldValue();  
        Object newValue = event.getNewValue();  
          
        if(newValue != null && !newValue.equals(oldValue)) {  
            //FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Cell Changed", "Old: " + oldValue + ", New:" + newValue);  
            //FacesContext.getCurrentInstance().addMessage(null, msg);  
        }  
    } 
	
	public void save() {
		System.out.println("Saving parameters...");
		systemEJB.saveParameters(parameters);
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Configuracion", "Se ha guardado la configuracion");  
        FacesContext.getCurrentInstance().addMessage(null, msg);  
	}
	public List<Category> getCategoriesByNodeHeight() {
		subcategoriesFirstLevel=new ArrayList<Category>();
		subcategoriesSecondLevel=new ArrayList<Category>();
		subcategoryId1=0;
		List<Category> firstLevelCategories = categoryEJB.getCategoriesByNodeHeight(actualNodeHeight);
		Category defaultCategoryOption = new Category();
		defaultCategoryOption.setId(0);
		defaultCategoryOption.setName("Seleccione...");
		firstLevelCategories.add(0, defaultCategoryOption);
		return firstLevelCategories;
	}

	public void categoryChange(ValueChangeEvent event) {
		categoryPath="";
		if (event.getNewValue()!=null) {
			Category newCategory = categoryEJB.getCategory((Integer)event.getNewValue());
			System.out.println("Category Change: selected category is: "+ newCategory.getName() + "[" +event.getNewValue()+"]");
			selectedCategory = newCategory;
			subcategoriesSecondLevel=new ArrayList<Category>();
			
			if(newCategory!=null && newCategory.getSubcategories().size()==0) {
				listingSelected=true;
			}else{
				listingSelected=false;
			}
			
			categoryStack[newCategory.getNodeHeight()]=newCategory;
			for(int i=newCategory.getNodeHeight()+1; i<categoryStack.length; i++){
				categoryStack[i] = null;
			}
			
			for(int i=0; i<categoryStack.length; i++){
				if(categoryStack[i]!=null) {
					categoryPath+=categoryStack[i].getName()+ " > ";
				}
			}
			System.out.println("Category Path: "+categoryPath);
		}
	}
	
	public List<Category> getSubcategoriesFirstLevel() {
		Category c=categoryEJB.getCategory(categoryId);
		if(c!=null){		
			subcategoriesFirstLevel=c.getSubcategories();
		}
		Category defaultCategoryOption = new Category();
		defaultCategoryOption.setId(0);
		defaultCategoryOption.setName("Seleccione...");
		subcategoriesFirstLevel.add(0, defaultCategoryOption);
		return subcategoriesFirstLevel;
	}

	public void setSubcategoriesFirstLevel(List<Category> subcategoriesFirstLevel) {
		this.subcategoriesFirstLevel = subcategoriesFirstLevel;
	}

	public List<Category> getSubcategoriesSecondLevel() {
		System.out.println("Cargando subcategorias de 2do nivel: "+subcategoryId1);
		Category c=categoryEJB.getCategory(subcategoryId1);
		if(c!=null){		
			subcategoriesSecondLevel=c.getSubcategories();
		}
		Category defaultCategoryOption = new Category();
		defaultCategoryOption.setId(0);
		defaultCategoryOption.setName("Seleccione...");
		subcategoriesSecondLevel.add(0, defaultCategoryOption);
		return subcategoriesSecondLevel;
	}
	
	public void deleteCategory() {
		System.out.println("Eliminando categoria: "+selectedCategory.getName());
		categoryEJB.deleteCategory(selectedCategory);
	}
	
	public void addCategory() {
		int nodeHeight=0;
		if(selectedCategory!=null && selectedCategory.getId()>0) {
			nodeHeight=selectedCategory.getNodeHeight()+1;
		}
		categoryEJB.addCategory(selectedCategory, newCategoryName, nodeHeight);		
	}

	public int getActualNodeHeight() {
		return actualNodeHeight;
	}

	public void setActualNodeHeight(int actualNodeHeight) {
		this.actualNodeHeight = actualNodeHeight;
	}

	public Category getSelectedCategory() {
		return selectedCategory;
	}

	public void setSelectedCategory(Category selectedCategory) {
		this.selectedCategory = selectedCategory;
	}

	public void setSubcategoriesSecondLevel(List<Category> subcategoriesSecondLevel) {
		this.subcategoriesSecondLevel = subcategoriesSecondLevel;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public int getSubcategoryId1() {
		return subcategoryId1;
	}

	public void setSubcategoryId1(int subcategoryId1) {
		this.subcategoryId1 = subcategoryId1;
	}

	public int getSubcategoryId2() {
		return subcategoryId2;
	}

	public void setSubcategoryId2(int subcategoryId2) {
		this.subcategoryId2 = subcategoryId2;
	}

	public boolean isListingSelected() {
		return listingSelected;
	}

	public void setListingSelected(boolean listingSelected) {
		this.listingSelected = listingSelected;
	}
	
	public String getCategoryPath() {
		return categoryPath;
	}

	public void setCategoryPath(String categoryPath) {
		this.categoryPath = categoryPath;
	}

	public Category[] getCategoryStack() {
		return categoryStack;
	}

	public void setCategoryStack(Category[] categoryStack) {
		this.categoryStack = categoryStack;
	}

	public String getNewCategoryName() {
		return newCategoryName;
	}

	public void setNewCategoryName(String newCategoryName) {
		this.newCategoryName = newCategoryName;
	}

	public List<Parameter> getParameters() {
		return parameters;
	}

	public void setParameters(List<Parameter> parameters) {
		this.parameters = parameters;
	}

}