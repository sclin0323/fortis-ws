package com.hoyoung.fortis.scheduling;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import com.hoyoung.fortis.batch.Batch001;


public class SystemTenSecCron {
	private static final Log log = LogFactory.getLog(SystemTenSecCron.class);
	
	@Autowired
	Batch001 batch001;
	
	
	@Scheduled(cron="*/30 * * * * ?")
    public void demoServiceMethod() throws Exception
    {
		//batch001.execute();
    }
}
