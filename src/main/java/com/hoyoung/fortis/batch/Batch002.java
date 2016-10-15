package com.hoyoung.fortis.batch;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hoyoung.fortis.services.GuestService;

/**
 * @author A8303
 * 每隔20秒執行一次將當天準備中的List 新增寫入 Fortinet
 * 同時，變更狀態
 */
@Component
public class Batch002 extends BaseBatch implements Batch{
	
	final static Logger log = Logger.getLogger(Batch002.class);
	
	@Autowired
	private GuestService guestService;
	
	public Batch002() {
		
	}
	
	public void execute() {
		//log.info("== Batch2 ==");
		/*
		List<GuestAppoint> dataList = guestAppointService.fetchByStatusAndStartDate(GuestAppointServiceImpl.STATUS_PREPARE, new Date());
		
		log.info(dataList.size());
		for(GuestAppoint guestAppoint: dataList) {
			log.info(guestAppoint.getApplicantId());
		}
		*/
		
		//log.info("==================");
		
	}

	
	

}
