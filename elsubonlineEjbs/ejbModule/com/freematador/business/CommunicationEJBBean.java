package com.freematador.business;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import com.freematador.domain.Notification;
import com.freematador.domain.User;
import com.freematador.persistence.NotificationDAO;

/**
 * Session Bean implementation class CommunicationEJBBean
 */
@Stateless
@LocalBean
public class CommunicationEJBBean implements CommunicationEJB {
	@EJB
	private NotificationDAO notificationDAO;
    
	/**
     * Default constructor. 
     */
    public CommunicationEJBBean() {
        // TODO Auto-generated constructor stub
    }
    
	@Override
	public List<Notification> getUserNotifications(User user) {
		return notificationDAO.findByUserId(user.getId());
	}
    
    

}
