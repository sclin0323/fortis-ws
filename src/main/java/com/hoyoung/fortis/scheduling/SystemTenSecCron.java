package com.hoyoung.fortis.scheduling;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.scheduling.annotation.Scheduled;

//@Configuration
//@Component
//@PropertySource("classpath:config.properties")
public class SystemTenSecCron {
	private static final Log log = LogFactory.getLog(SystemTenSecCron.class);
	
	//1.2.3.4
	//	@Value("${mongodb.url}")
	//	private String mongodbUrl;

		//hello
	//	@Value("${mongodb.db}")
	//	private String defaultDb;
	
	@Scheduled(cron="*/30 * * * * ?")
    public void demoServiceMethod() {
		System.out.println("hello----------------------------------------------");
		log.info("1111111111111111111111111111111111111111");
		//log.info(mongodbUrl);

    }
}
