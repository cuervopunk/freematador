package com.freematador.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.freematador.exceptions.BusinessException;

@Entity
public class Product implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private String shortDescription;
	private String longDescription;
	private float basePrice;
	@OneToOne
	private Bid highestBid;
	private Date startingDate;
	private Date endingDate;
	@OneToMany(fetch=FetchType.LAZY)
	private List<Picture> pictures = new ArrayList<Picture>();
	@ManyToOne
	@JoinColumn(name="category")
	private Category category = new Category();
	@ManyToOne
	private Store store;
	@OneToMany
	private List<Bid> bids = new ArrayList<Bid>();
	@OneToMany
	private List<Question> questions = new ArrayList<Question>();
	private int saleType = 1;
	@OneToOne
	private User user;
	private boolean active = true;
	private boolean highlight = false;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getShortDescription() {
		return shortDescription;
	}
	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}
	public String getLongDescription() {
		return longDescription;
	}
	public void setLongDescription(String longDescription) {
		this.longDescription = longDescription;
	}
	public float getBasePrice() {
		return basePrice;
	}
	public void setBasePrice(float basePrice) {
		this.basePrice = basePrice;
	}
	public Date getEndingDate() {
		return endingDate;
	}
	public void setEndingDate(Date endingDate) {
		this.endingDate = endingDate;
	}
	public Date getStartingDate() {
		return startingDate;
	}
	public void setStartingDate(Date startingDate) {
		this.startingDate = startingDate;
	}
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	public Store getStore() {
		return store;
	}
	public void setStore(Store store) {
		this.store = store;
	}
	public List<Bid> getBids() {
		return bids;
	}
	public void setBids(List<Bid> bids) {
		this.bids = bids;
	}
	public List<Question> getQuestions() {
		return questions;
	}
	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}
	public List<Picture> getPictures() {
		return pictures;
	}
	public void setPictures(List<Picture> pictures) {
		this.pictures = pictures;
	}
	public int getSaleType() {
		return saleType;
	}
	public void setSaleType(int saleType) {
		this.saleType = saleType;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Bid getHighestBid() {
		return highestBid;
	}
	public void setHighestBid(Bid highestBid) {
		this.highestBid = highestBid;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public boolean isHighlight() {
		return highlight;
	}
	public void setHighlight(boolean highlight) {
		this.highlight = highlight;
	}
	public BigDecimal getProductPrice() throws BusinessException {
		BigDecimal price = new BigDecimal(0);
		if(id>0) {
			if(getHighestBid()!=null) {
				price=new BigDecimal(getHighestBid().getValue());
			}else{
				price=new BigDecimal(getBasePrice());
			}
		}
		if(price==null || price.floatValue()==0) {
			throw new BusinessException("Error calculando precio del producto");
		}
		return price;
	}
}
