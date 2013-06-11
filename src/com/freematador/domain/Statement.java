package com.freematador.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Statement {
	private List<Fee> fees = new ArrayList<Fee>();
	private List<Payment> payments = new ArrayList<Payment>();
	private Date due = new Date();
	
	public List<Fee> getFees() {
		return fees;
	}
	public void setFees(List<Fee> fees) {
		this.fees = fees;
	}
	public List<Payment> getPayments() {
		return payments;
	}
	public void setPayments(List<Payment> payments) {
		this.payments = payments;
	}
	public Date getDue() {
		return due;
	}
	public void setDue(Date due) {
		this.due = due;
	}
	
}
