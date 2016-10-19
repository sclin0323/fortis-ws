package com.hoyoung.fortis.scheduling;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.scheduling.annotation.Scheduled;

import com.hoyoung.fortis.batch.Batch001;
import com.hoyoung.fortis.batch.Batch002;
import com.hoyoung.fortis.batch.Batch003;


public class SystemTenSecCron {
	private static final Log log = LogFactory.getLog(SystemTenSecCron.class);
	
	@Autowired
	Batch001 batch001;
	
	@Autowired
	Batch002 batch002;
	
	@Autowired
	MailSender mailSender;
	
	@Autowired
	Batch003 batch003;
	
	
	@Scheduled(cron="*/30 * * * * ?")
    public void demoServiceMethod() throws Exception
    {
		
		SimpleMailMessage message = new SimpleMailMessage();
		
		message.setTo("sclin0323@gmail.com");
		message.setSubject("hello....");
		message.setText("hihi");
		mailSender.send(message);
		
		//batch003.execute();

    }
}
