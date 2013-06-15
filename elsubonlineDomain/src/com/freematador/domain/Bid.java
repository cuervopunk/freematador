package com.freematador.domain;

import java.io.Serializable;
import java.util.Date;

public class Bid implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private float value;
	private User user;
	private Date date;
	public float getValue() {
		return value;
	}
	public void setValue(float value) {
		this.value = value;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
}
