package com.freematador.presentation;


import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.FlowEvent;

import com.freematador.business.DealingEJB;
import com.freematador.business.SystemEJB;
import com.freematador.domain.Parameter;
import com.freematador.domain.Picture;
import com.freematador.domain.Product;
import com.freematador.utils.FileUtils;

@ManagedBean
@ViewScoped
public class ProductEditionManagedBean implements Serializable {
	private Product product;
	private List<Picture> pictures = new ArrayList<Picture>();
	@EJB
	private DealingEJB dealingEJB;
	@EJB
	private SystemEJB systemEJB;
	public int productId;
	@ManagedProperty(value = "#{loginManagedBean}")
	private LoginManagedBean loginManagedBean;
	
	public ProductEditionManagedBean() {
	}

	//no inicializo con @PostConstruct porque es ViewScoped y no puedo obtener el request parameter
	public String init(ComponentSystemEvent event) {
		String productId = (String) event.getComponent().getAttributes().get("productId");
		if(this.productId==0) {
			this.productId=Integer.parseInt(productId);
			this.product = dealingEJB.getProduct(this.productId);
			System.out.println("ProductEditionMB: "+this.productId);
		}
		return "";
	}
	
	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}
	
	public String update() {
		String outcome = "productSellConfirmed";
		System.out.println("Actualizando info del producto...");
		dealingEJB.editProduct(product, pictures);
		return outcome;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public LoginManagedBean getLoginManagedBean() {
		return loginManagedBean;
	}

	public void setLoginManagedBean(LoginManagedBean loginManagedBean) {
		this.loginManagedBean = loginManagedBean;
	}

	public String onFlowProcess(FlowEvent event) {
		System.out.println("Current wizard step:"+event.getOldStep());
		System.out.println("Next step:" + event.getNewStep());
		return event.getNewStep();  
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
			
			pictures.add(uploadedPicture);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }  	 
}
