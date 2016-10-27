package com.hoyoung.fortis.batch;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hoyoung.fortis.services.GuestLogService;
import com.hoyoung.fortis.services.GuestService;
import com.hoyoung.fortis.services.RestTemplateService;

/**
 * @author A8303
 * 每日將已過期的訪客名單自動刪除
 */
@Component
public class Batch002 extends BaseBatch implements Batch{
	
	final static Logger log = Logger.getLogger(Batch002.class);
	
	@Autowired
	private GuestService guestService;
	
	@Autowired
	private GuestLogService guestLogService;
	
	@Autowired
	private RestTemplateService restTemplateService;
	
	
	public Batch002() {
		
	}
	
	public void execute() {
		log.info("=============== BATCH002 開始 ===============");
		
		List<Map<String, Object>> guestList = guestService.fetchAll();
		
		for(Map<String, Object> map : guestList) {
			String guestId = (String) map.get("guestId");
			String guestGroup = (String) map.get("guestGroup");
			
			try {
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
				Date date = formatter.parse((String) map.get("endDate"));
				if(new Date().after(date)) {
					// 使用日過期，自動刪除帳號
					restTemplateService.unselectConfigUserGroups(guestId, guestGroup);
					restTemplateService.deleteConfigUserLocal(guestId);
					restTemplateService.reenableSystemInterface();
					
					// 紀錄 Log
					guestLogService.saveGuestLog("DELETE", "BATCH002", "自動刪除", guestId);
							
					guestService.delete(guestId);
					
				}
				
			} catch (ParseException e) {
				log.error("連線設備執行指令失敗!! ", e);
			}
		}
		
		log.info("=============== BATCH002 結束 ===============");
		
	}

	private boolean checkDateExpire(Date endDate) {
		return new Date().after(endDate);
	}

	
	

}
