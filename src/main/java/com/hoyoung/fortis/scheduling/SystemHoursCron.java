package com.hoyoung.fortis.scheduling;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import com.hoyoung.fortis.batch.Batch003;


public class SystemHoursCron {
	private static final Log log = LogFactory.getLog(SystemHoursCron.class);
	
	@Autowired
	Batch003 batch003;
	
	@Scheduled(cron="*/30 * * * * ?")
    public void demoServiceMethod() throws Exception {
		
		String recipients[] = {"sclin0323@gmail.com"};
		String from = "dialup@ncut.edu.tw";
		
		String subject =" subject";
		String message = "message";
		
		//Set the host smtp address
	    Properties props = new Properties();
	    props.put("mail.smtp.host", "spam.ncut.edu.tw");

	    // create some properties and get the default Session
	    Session session = Session.getDefaultInstance(props, null);
	    session.setDebug(false);

	    // create a message
	    Message msg = new MimeMessage(session);

	    // set the from and to address
	    InternetAddress addressFrom = new InternetAddress(from);
	    msg.setFrom(addressFrom);

	    InternetAddress[] addressTo = new InternetAddress[recipients.length]; 
	    for (int i = 0; i < recipients.length; i++) {
	        addressTo[i] = new InternetAddress(recipients[i]);
	    }
	    msg.setRecipients(Message.RecipientType.TO, addressTo);

	    // Optional : You can also set your custom headers in the Email if you Want
	    msg.addHeader("MyHeaderName", "myHeaderValue");

	    // Setting the Subject and Content Type
	    msg.setSubject(subject);
	    msg.setContent(message, "text/plain");
	    Transport.send(msg);
    }
}
