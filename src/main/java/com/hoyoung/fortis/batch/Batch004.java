package com.hoyoung.fortis.batch;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 */
//@Configuration
@Component
@PropertySource("classpath:config.properties")
public class Batch004 extends BaseBatch implements Batch {

	final static Logger log = Logger.getLogger(Batch004.class);

	@Autowired
	private Environment env;
	
	// 1.2.3.4
	
	//@Value("#{mongodb.url}")
	@Value("${fortis_wspy.urlPrefix}")
	public String urlPrefix;

	// hello
	//@Value("${mongodb.db}")
	//private String defaultDb;

	public Batch004() {
		
	}
	
	

	public void execute() {
		log.info("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		//String mongodbUrl = env.getProperty("mongodb.url");
		//String defaultDb = env.getProperty("mongodb.db");
		
		log.info(urlPrefix);
	}

}
