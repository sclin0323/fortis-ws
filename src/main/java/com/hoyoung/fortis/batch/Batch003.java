package com.hoyoung.fortis.batch;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.google.gson.Gson;
import com.hoyoung.fortis.services.RestTemplateService;
import com.hoyoung.fortis.services.UserDeviceLogService;
import com.hoyoung.fortis.services.UserDeviceService;

/**
 * @author A8303
 * 每隔20秒執行一次將當天準備中的List 新增寫入 Fortinet
 * 同時，變更狀態
 */
@Component
public class Batch003 extends BaseBatch implements Batch{
	
	final static Logger log = Logger.getLogger(Batch003.class);
	
	Gson gson = new Gson();
	
	RestTemplate restTemplate = new RestTemplate();
	
	String httpUrl = "http://net.ncut.edu.tw/netservices/api/GetAccounts";
	
	@Autowired
	UserDeviceService userDeviceService;
	
	@Autowired
	RestTemplateService restTemplateService;
	
	@Autowired
	UserDeviceLogService userDeviceLogService;
	
	public Batch003() {
		
	}
	
	public void execute() {
		log.info("=============== BATCH003 開始 ===============");
		
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		HttpEntity<?> entity = new HttpEntity<>(headers);
		
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(httpUrl);
		
		ResponseEntity<String[]> responseEntity = restTemplate.exchange(builder.build().encode().toUri(), HttpMethod.GET, entity, String[].class);
		
		HttpStatus status = responseEntity.getStatusCode();
		
		if(status.is2xxSuccessful()) {
			
			String[] employeeIds = responseEntity.getBody();
			
			// 取回的 employeeIds 要很小心處理，否則可能會造成資料被無故清除
			log.info(employeeIds.length);
			if(employeeIds.length < 3000) {
				// 在職清單可能有問題，暫時不清資料
				log.info("在職清單取得可以有異常: "+employeeIds.toString());
				return; 
			}
			
			List<Map<String, Object>> maps = userDeviceService.fetchAll();
			
			for(Map<String, Object> map : maps) {
				
				String applicantId = (String) map.get("applicantId");
				
				if(!Arrays.asList(employeeIds).contains(applicantId)){
					// applicantId 不在在職清單中，自動移除
					log.info(applicantId);
					
					String deviceName = (String) map.get("deviceName");
					String deviceGroup = (String) map.get("deviceGroup");

					try {
						restTemplateService.unselectConfigUserDeviceGroups(deviceName, deviceGroup);
						restTemplateService.deleteConfigUserDevice(deviceName);
						restTemplateService.reenableSystemInterface();
					} catch (Exception e) {
						e.printStackTrace();
						log.error("連線設備執行指令失敗!! ", e);
						//return getFailureModelAndView(model, "連線設備執行指令失敗!! ");
					}

					// 紀錄 Log
					userDeviceLogService.saveUserDeviceLog("DELETE", "BATCH003", "離職自動化", deviceName);

					userDeviceService.delete(deviceName);
					
					
					
				} 
				
			}
			
		}
		log.info("=============== BATCH003 結束 ===============");
	}


}
