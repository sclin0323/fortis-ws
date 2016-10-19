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


public class Batch003Cron {
	private static final Log log = LogFactory.getLog(Batch003Cron.class);
	
	@Autowired
	Batch003 batch003;
	
	
	//@Scheduled(cron="*/30 * * * * ?")  // 每30秒執行一次
	@Scheduled(cron="0 0 1 */1 * ?")		// 每天凌晨一點執行一次
    public void demoServiceMethod() throws Exception {
		batch003.execute();
    }
}
