package com.freematador.domain;

import java.util.Date;

public class Payment {
	private float amount;
	private Date date;
	
	public float getAmount() {
		return amount;
	}
	public void setAmount(float amount) {
		this.amount = amount;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}

}
