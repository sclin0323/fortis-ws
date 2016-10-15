package com.hoyoung.test;

import java.io.IOException;
import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class TestSmtp {

	public static void main(String[] args) throws Exception {
		
		Properties properties = new Properties();
	    properties.put("mail.smtp.host", "spam.ncut.edu.tw");
	    properties.put("mail.smtp.port", 587);
	    properties.put("mail.smtp.auth", "true");
	    properties.put("mail.smtp.starttls.enable", "true");
	    properties.put("mail.user", "test2@student.ncut.edu.tw");
	    properties.put("mail.password", "z27763");
	    
	    
	 // creates a new session with an authenticator
	    Authenticator auth = new Authenticator() {
	        public PasswordAuthentication getPasswordAuthentication() {
	            return new PasswordAuthentication("test2@student.ncut.edu.tw", "z27763");
	        }
	    };
	    Session session = Session.getInstance(properties, auth);

	    // creates a new e-mail message
	    Message msg = new MimeMessage(session);

	    msg.setFrom(new InternetAddress("test2@student.ncut.edu.tw"));
	    InternetAddress[] toAddresses = { new InternetAddress("sclin0323@gmail.com") };
	    msg.setRecipients(Message.RecipientType.TO, toAddresses);
	    msg.setSubject("Hello");
	    msg.setSentDate(new Date());

	    // creates message part
	    MimeBodyPart messageBodyPart = new MimeBodyPart();
	    messageBodyPart.setContent("hihi", "text/html");

	    // creates multi-part
	    Multipart multipart = new MimeMultipart();
	    multipart.addBodyPart(messageBodyPart);

	    // sets the multi-part as e-mail's content
	    msg.setContent(multipart);

	    // sends the e-mail
	    Transport.send(msg);
		
	}

}
