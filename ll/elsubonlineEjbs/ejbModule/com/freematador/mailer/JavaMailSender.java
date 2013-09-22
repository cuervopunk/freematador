package com.freematador.mailer;


import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


public class JavaMailSender {
    private JavaMailSender() {
    }

	public static void send(String user, String password, String destination, String subject, String body) {
		 
		final String username = user+"@gmail.com";
		//TODO: Pasar props a Parameter
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
 
		Session session = Session.getInstance(props,
		  new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, "freematador");
			}
		  });
 
		try {
 
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(username));
			message.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse(destination));
			message.setSubject(subject);
			message.setText(body);
 
			Transport.send(message);
 
			System.out.println("[MAILING_PROCESS] Sending e-mail to: "+destination);
 
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
}