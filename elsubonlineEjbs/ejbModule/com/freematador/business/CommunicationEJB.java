package com.freematador.business;

import java.util.List;

import javax.ejb.Local;

import com.freematador.domain.Notification;
import com.freematador.domain.User;

@Local
public interface CommunicationEJB {
	public List<Notification> getUserNotifications(User user);
}
