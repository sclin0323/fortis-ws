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


public class SystemHoursCron {
	private static final Log log = LogFactory.getLog(SystemHoursCron.class);
	
	@Autowired
	Batch003 batch003;
	
	@Scheduled(cron="0 */1 * * * ?")
    public void demoServiceMethod() throws Exception {
		batch003.execute();
    }
}
