package com.freematador.business;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Schedule;
import javax.ejb.Singleton;

import com.freematador.domain.Product;
import com.freematador.domain.User;
import com.freematador.exceptions.BusinessException;
import com.freematador.mailer.JavaMailSender;
import com.freematador.mailer.Mail;
import com.freematador.persistence.MailDAO;

/**
 * Session Bean implementation class MailerEJBBean
 */
@Singleton
@LocalBean
public class MailerEJBBean implements MailerEJB {

	@EJB
	private MailDAO mailDAO;
	
    /**
     * Default constructor. 
     */
    public MailerEJBBean() {
        // TODO Auto-generated constructor stub
    }
    
    @Schedule(persistent=false, second="*/30", minute="*", hour="*")
    public void sendMails() throws BusinessException {
    	List<Mail> pendingEmails = this.getPendingMails();
		for(Mail m : pendingEmails) {
			JavaMailSender.send("elsubonline", "freematador", m.getRecipient(), m.getSubject(), m.getBody());
			this.setMailSent(m);
		}    	
    }
    
    private void setMailSent(Mail m) {
		m.setSent(true);
		mailDAO.update(m);
	}

	public void mailNewUser(User u) {
    	Mail newUserEmail = getNewUserMail(u);
    	addMailToQueue(newUserEmail);
    }
	
	public void mailBuyerUser(User buyer, User seller, Product product) {
		Mail buyerEmail = getBuyerEmail(buyer, seller, product);
		addMailToQueue(buyerEmail);
	}

    private Mail getBuyerEmail(User buyer, User seller, Product p) {
    	String subject = "Felicitaciones por tu compra en ElSubOnline!";
    	String body = "Eres el ganador del articulo: " + p.getShortDescription() + 
    			" Debes contactar al vendedor para concretar la operacion. \n" +
    			" Sus datos de contacto son los siguientes: \n"+
    			" Nombre: "+seller.getName()+
    			" \nE-mail: "+seller.getEmail()+
    			" \nTelefono: "+seller.getTelephone();
    	Mail mail = new Mail();
    	mail.setSubject(subject);
    	mail.setRecipient(buyer.getEmail());
    	mail.setBody(body);
    	return mail;
	}

	private Mail getNewUserMail(User u) {
		//TODO: Pasar dominio a Parameter
    	String confirmationUrl = "http://localhost:8080/elsubonlineWeb/guest/confirmRegistration.xhtml?id="+u.getId();
    	String subject = "Bienvenido a elsubonline";
    	String body = "Usted se ha registrado en ElSubOnline! " +
    			"Para confirmar su registro haga click en el enlace, " +
    			"de lo contrario simplemente ignore este correo. "+confirmationUrl;
    	Mail mail = new Mail();
    	mail.setSubject(subject);
    	mail.setRecipient(u.getEmail());
    	mail.setBody(body);
    	return mail;    	
    }
    
    private void addMailToQueue(Mail m) {
    	mailDAO.insert(m);
    }
    
    private List<Mail> getPendingMails() {
    	return mailDAO.getByStatus(false);
    }

	@Override
	public void mailRecoveryUser(User user, String newPassword) {
    	Mail newUserEmail = getRecoveryMail(user, newPassword);
    	addMailToQueue(newUserEmail);	
    }

	private Mail getRecoveryMail(User user, String newPassword) {
    	String subject = "Usted ha indicado que olvido su password en ElSubOnline!";
    	String body = "Su nueva password es: ";
    	body+=newPassword;
    	Mail mail = new Mail();
    	mail.setSubject(subject);
    	mail.setBody(body);
    	mail.setRecipient(user.getEmail());
    	return mail;
	}

}