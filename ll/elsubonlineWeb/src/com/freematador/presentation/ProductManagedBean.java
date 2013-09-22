package com.freematador.presentation;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;

import com.freematador.business.DealingEJB;
import com.freematador.business.SocialEJB;
import com.freematador.domain.Product;
import com.freematador.domain.Question;
import com.freematador.exceptions.BusinessException;

@ManagedBean
@RequestScoped
public class ProductManagedBean implements Serializable{
	@EJB
	private DealingEJB dealingEjb;
	@ManagedProperty("#{param.id}")
	public Integer productId;
	@ManagedProperty(value = "#{loginManagedBean}")
	private LoginManagedBean loginManagedBean;
	private BigDecimal minimumBid;
	private BigDecimal newBid;
	private String newQuestion;
	@EJB
	private SocialEJB socialEJB;
	
	private Product product = new Product();
	
	private static final long serialVersionUID = 1L;

	public ProductManagedBean() {
	}

	@PostConstruct
	public void init() { //solucion temporal debido a que al hacer login desde la pagina de producto se caia por no tener el productid
	    String contextPath = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
		try {
			if(productId!=null && productId>0) {
				System.out.println("ProductManagedBean - product: "+productId);
				System.out.println("questions: "+product.getQuestions().size());
				this.product=dealingEjb.getProduct(productId);
				this.minimumBid=dealingEjb.getProductPrice(productId);
			}else{
			    try {
					FacesContext.getCurrentInstance().getExternalContext().redirect(contextPath+"/index.xhtml");
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		} catch (BusinessException e) {
		    FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage());  
		    FacesContext.getCurrentInstance().addMessage(null, msg);
		    try {
				FacesContext.getCurrentInstance().getExternalContext().redirect(contextPath+"/index.xhtml");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		    e.printStackTrace();

		}
	}

	public DealingEJB getDealingEjb() {
		return dealingEjb;
	}

	public void setDealingEjb(DealingEJB dealingEjb) {
		this.dealingEjb = dealingEjb;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
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

	public BigDecimal getMinimumBid() {
		return minimumBid;
	}

	public void setMinimumBid(BigDecimal minimumBid) {
		this.minimumBid = minimumBid;
	}

	public BigDecimal getNewBid() {
		return newBid;
	}

	public void setNewBid(BigDecimal newBid) {
		this.newBid = newBid;
	}
	
	public String buyProduct(){
        System.out.println("Buying product");
		String outcome = "";
		try {
			if(loginManagedBean.getUser()!=null){
			    System.out.println(loginManagedBean);
			    System.out.println(product);
			    this.dealingEjb.buyProduct(product, loginManagedBean.getUser());
			    FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Felicitaciones", "Ya es propietario del producto!");  
			    FacesContext.getCurrentInstance().addMessage(null, msg);
			    outcome="congratulations";
			}else{
			    FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Debe ingresar al sistema para realizar compras");  
			    FacesContext.getCurrentInstance().addMessage(null, msg);
				outcome="index";
			}
		}catch(BusinessException e) {
		    FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage());  
		    FacesContext.getCurrentInstance().addMessage(null, msg);
			e.printStackTrace();
		}
		return outcome;
	}
	
	public String bidProduct() {
        System.out.println("Bidding on product");
		String outcome = "";
		try {
			if(loginManagedBean.getUser()!=null){
		        System.out.println(loginManagedBean);
		        System.out.println(product);
		        this.dealingEjb.bidProduct(product, loginManagedBean.getUser(), newBid);
		        outcome="highestbidder";
		        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Ahora eres el mejor oferente!");  
		        FacesContext.getCurrentInstance().addMessage(null, msg);
			}else{
		        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Debe ingresar al sistema para ofertar");  
		        FacesContext.getCurrentInstance().addMessage(null, msg);
				outcome="index";
			}
		} catch (BusinessException e) {
		    FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage());  
		    FacesContext.getCurrentInstance().addMessage(null, msg);
			e.printStackTrace();
		}
		return outcome;
	}
	
	public void askQuestion() {
		try {
			System.out.println("Guardando pregunta...");
			Question question = new Question();
			question.setQuestionText(newQuestion);
			socialEJB.askQuestion(product, question, loginManagedBean.getUser());
			newQuestion = "";
			this.product=dealingEjb.getProduct(productId);
	        RequestContext context = RequestContext.getCurrentInstance();  
	        context.update("questionForm");
	        context.update("questionsPanel");
		}catch(BusinessException e) {
	        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Ocurrio un problema al realizar la pregunta");  
	        FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}

	public String getNewQuestion() {
		return newQuestion;
	}

	public void setNewQuestion(String newQuestion) {
		this.newQuestion = newQuestion;
	}

	public SocialEJB getSocialEJB() {
		return socialEJB;
	}

	public void setSocialEJB(SocialEJB socialEJB) {
		this.socialEJB = socialEJB;
	}


	
}