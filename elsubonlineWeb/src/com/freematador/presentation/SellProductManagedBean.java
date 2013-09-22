package com.freematador.presentation;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.FlowEvent;

import com.freematador.business.CategoryTreeEJB;
import com.freematador.business.DealingEJB;
import com.freematador.business.SystemEJB;
import com.freematador.domain.Category;
import com.freematador.domain.Parameter;
import com.freematador.domain.Picture;
import com.freematador.domain.Product;
import com.freematador.domain.SaleType;
import com.freematador.utils.FileUtils;

@ManagedBean
@ViewScoped
public class SellProductManagedBean implements Serializable {
	@EJB
	private CategoryTreeEJB categoryEJB;
	@EJB
	private DealingEJB productEJB;
	@EJB
	private SystemEJB systemEJB;
	@ManagedProperty(value = "#{loginManagedBean}")
	private LoginManagedBean loginManagedBean;
	private SaleType saleType;
	
	private int actualNodeHeight;
	private Category selectedCategory;
	
	private List<Category> subcategoriesFirstLevel=new ArrayList<Category>();
	private List<Category> subcategoriesSecondLevel=new ArrayList<Category>();
	
	private int categoryId;
	private int subcategoryId1;
	private int subcategoryId2;
	private boolean listingSelected=false;
	
	private Product product = new Product();
	
	public SellProductManagedBean() {
	}
	
	@PostConstruct
	private void init() {
		product = new Product();
		product.setPictures(new ArrayList<Picture>());
	}

	public List<Category> getCategoriesByNodeHeight() {
		return categoryEJB.getCategoriesByNodeHeight(actualNodeHeight);
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
	
	 public void handleCategoryChange() {  
		 System.out.println("handleCategoryChange");
	        if(selectedCategory !=null) {
	            subcategoriesFirstLevel = selectedCategory.getSubcategories();
	        }
	 }

	 public void handleSubcategoryChange() {  
		 System.out.println("handleSubcategoryChange");
	        if(selectedCategory !=null) {
	            subcategoriesSecondLevel = selectedCategory.getSubcategories();
	        }
	 }

	public List<Category> getSubcategoriesFirstLevel() {
		Category c=categoryEJB.getCategory(categoryId);
		if(c!=null){		
			subcategoriesFirstLevel=c.getSubcategories();
		}
		return subcategoriesFirstLevel;
	}

	public void setSubcategoriesFirstLevel(List<Category> subcategoriesFirstLevel) {
		this.subcategoriesFirstLevel = subcategoriesFirstLevel;
	}

	public List<Category> getSubcategoriesSecondLevel() {
		Category c=categoryEJB.getCategory(subcategoryId1);
		if(c!=null){		
			subcategoriesSecondLevel=c.getSubcategories();
		}
		return subcategoriesSecondLevel;
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

	public String onFlowProcess(FlowEvent event) {
		System.out.println("Current wizard step:"+event.getOldStep());
		System.out.println("Next step:" + event.getNewStep());
		if(!isListingSelected()) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Debe seleccionar una categoria.", null);
	        FacesContext.getCurrentInstance().addMessage(null, msg);
	        return event.getOldStep();
		}
		return event.getNewStep();  
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	} 
	
	public String save() {
		String outcome = "productSellConfirmed";
		productEJB.publishProduct(product, loginManagedBean.getUser());
		return outcome;
	}
	
	public void categoryChange(ValueChangeEvent event) {
		if (event.getNewValue()!=null) {
			System.out.println("categoryChange! new Category is: "+ event.getNewValue());
			Category newCategory = categoryEJB.getCategory((Integer)event.getNewValue());
			if(newCategory.getSubcategories().size()==0) {
				product.setCategory(newCategory);
				listingSelected=true;
				System.out.println("final category, setting in product!");
			}else{
				listingSelected=false;
				System.out.println("still has subcategories, select another one!");
			}
		}
	}

	public boolean isListingSelected() {
		return listingSelected;
	}

	public void setListingSelected(boolean listingSelected) {
		this.listingSelected = listingSelected;
	}

	public LoginManagedBean getLoginManagedBean() {
		return loginManagedBean;
	}

	public void setLoginManagedBean(LoginManagedBean loginManagedBean) {
		this.loginManagedBean = loginManagedBean;
	}
	
    public void handleFileUpload(FileUploadEvent event) {  
        try {
			FacesMessage msg = new FacesMessage("Succesful", event.getFile().getFileName() + " is uploaded.");  
			FacesContext.getCurrentInstance().addMessage(null, msg);  
			String guid = UUID.randomUUID().toString();
			String fileName = guid+"_"+event.getFile().getFileName();
			Parameter parameter = systemEJB.getParameter("IMAGE_PATH");
			String path = parameter.getValue()+"/";

			FileUtils.saveInputStream(event.getFile().getInputstream(), path + fileName);
			Picture uploadedPicture = new Picture();
			uploadedPicture.setDescription(product.getShortDescription());
			uploadedPicture.setFileName(fileName);
			
			List<Picture> pictures = product.getPictures();
			pictures.add(uploadedPicture);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

	public SaleType getSaleType() {
		return saleType;
	}

	public void setSaleType(SaleType saleType) {
		this.saleType = saleType;
	}

}
