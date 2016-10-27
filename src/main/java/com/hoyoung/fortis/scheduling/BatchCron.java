package com.hoyoung.fortis.scheduling;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import com.hoyoung.fortis.batch.Batch002;
import com.hoyoung.fortis.batch.Batch003;
import com.hoyoung.fortis.batch.Batch004;
import com.hoyoung.fortis.services.SysSettingService;


public class BatchCron {
	private static final Log log = LogFactory.getLog(BatchCron.class);
	
	@Autowired
	Batch002 batch002;
	
	@Autowired
	Batch003 batch003;
	
	@Autowired
	Batch004 batch004;
	
	@Autowired
	SysSettingService sysSettingService;

	//@Scheduled(cron="*/30 * * * * ?")
	@Scheduled(cron="0 5 0 */1 * ?")		// 每天凌晨0點5分執行一次
    public void demoServiceMethod() throws Exception {
		
		Map map = sysSettingService.fetchById("SETTING001");
		boolean  enableUserDevice = (boolean) map.get("enableUserDevice");
		boolean  enableGuest = (boolean) map.get("enableGuest");
		
		if(enableUserDevice) {
			batch003.execute();
		}
		
		if(enableGuest) {
			batch002.execute();
		}
		
		//batch004.execute();
		
    }
}
