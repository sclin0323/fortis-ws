package com.hoyoung.fortis.services;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import com.hoyoung.fortis.dao.FortisDAO;
import com.hoyoung.fortis.dao.SysSetting;
import com.hoyoung.fortis.python.PythonResponse;

public class RestTemplateServiceImpl implements RestTemplateService {
	final static Logger log = Logger.getLogger(RestTemplateServiceImpl.class);
	
	private final static String urlPrefix = "http://140.128.71.35:8000/fortinet/";

	@Autowired(required = true)
	protected FortisDAO fortisDAO;

	RestTemplate rt = new RestTemplate();

	@Override
	public PythonResponse editConfigUserDevice(String deviceName, String macAddress) {

		SysSetting setting = (SysSetting) fortisDAO.findById(SysSetting.class, "SETTING001");

		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);

		UriComponentsBuilder builder = UriComponentsBuilder
				.fromHttpUrl(urlPrefix+"editConfigUserDevice/")
				.queryParam("ip", setting.getHostname()).queryParam("port", setting.getPort())
				.queryParam("userName", setting.getLoginName()).queryParam("password", setting.getPassword())
				.queryParam("deviceName", deviceName).queryParam("macAddress", macAddress);

		HttpEntity<?> entity = new HttpEntity<>(headers);

		HttpEntity<PythonResponse> pythonResponse = rt.exchange(builder.build().encode().toUri(), HttpMethod.GET,
				entity, PythonResponse.class);

		return pythonResponse.getBody();
	}

	@Override
	public HttpEntity<PythonResponse> appendConfigUserDeviceGroups(String deviceName) {

		SysSetting setting = (SysSetting) fortisDAO.findById(SysSetting.class, "SETTING001");

		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);

		UriComponentsBuilder builder = UriComponentsBuilder
				.fromHttpUrl(urlPrefix+"appendConfigUserDeviceGroups/")
				.queryParam("ip", setting.getHostname()).queryParam("port", setting.getPort())
				.queryParam("userName", setting.getLoginName()).queryParam("password", setting.getPassword())
				.queryParam("deviceName", deviceName).queryParam("groupName", setting.getDeviceGroup());

		HttpEntity<?> entity = new HttpEntity<>(headers);

		HttpEntity<PythonResponse> pythonResponse = rt.exchange(builder.build().encode().toUri(), HttpMethod.GET,
				entity, PythonResponse.class);

		log.info(pythonResponse.toString());
		log.info(pythonResponse.getBody().isStatus());
		log.info(pythonResponse.getBody().getMessage());

		return pythonResponse;

	}

	@Override
	public HttpEntity<PythonResponse> unselectConfigUserDeviceGroups(String deviceName) {
		SysSetting setting = (SysSetting) fortisDAO.findById(SysSetting.class, "SETTING001");

		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);

		UriComponentsBuilder builder = UriComponentsBuilder
				.fromHttpUrl(urlPrefix+"unselectConfigUserDeviceGroups/")
				.queryParam("ip", setting.getHostname()).queryParam("port", setting.getPort())
				.queryParam("userName", setting.getLoginName()).queryParam("password", setting.getPassword())
				.queryParam("deviceName", deviceName).queryParam("groupName", setting.getDeviceGroup());

		HttpEntity<?> entity = new HttpEntity<>(headers);

		HttpEntity<PythonResponse> pythonResponse = rt.exchange(builder.build().encode().toUri(), HttpMethod.GET,
				entity, PythonResponse.class);

		log.info(pythonResponse.toString());
		log.info(pythonResponse.getBody().isStatus());
		log.info(pythonResponse.getBody().getMessage());

		return pythonResponse;
	}

	@Override
	public HttpEntity<PythonResponse> deleteConfigUserDevice(String userDeviceId) {
		SysSetting setting = (SysSetting) fortisDAO.findById(SysSetting.class, "SETTING001");

		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);

		UriComponentsBuilder builder = UriComponentsBuilder
				.fromHttpUrl(urlPrefix+"deleteConfigUserDevice/")
				.queryParam("ip", setting.getHostname()).queryParam("port", setting.getPort())
				.queryParam("userName", setting.getLoginName()).queryParam("password", setting.getPassword())
				.queryParam("deviceName", userDeviceId);

		HttpEntity<?> entity = new HttpEntity<>(headers);

		HttpEntity<PythonResponse> pythonResponse = rt.exchange(builder.build().encode().toUri(), HttpMethod.GET,
				entity, PythonResponse.class);

		log.info(pythonResponse.toString());
		log.info(pythonResponse.getBody().isStatus());
		log.info(pythonResponse.getBody().getMessage());

		return pythonResponse;
	}

	@Override
	public HttpEntity<PythonResponse> getSystemStatus() {
		SysSetting setting = (SysSetting) fortisDAO.findById(SysSetting.class, "SETTING001");

		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);

		UriComponentsBuilder builder = UriComponentsBuilder
				.fromHttpUrl(urlPrefix+"getSystemStatus/")
				.queryParam("ip", setting.getHostname()).queryParam("port", setting.getPort())
				.queryParam("userName", setting.getLoginName()).queryParam("password", setting.getPassword());

		HttpEntity<?> entity = new HttpEntity<>(headers);

		HttpEntity<PythonResponse> pythonResponse = rt.exchange(builder.build().encode().toUri(), HttpMethod.GET,
				entity, PythonResponse.class);

		return pythonResponse;
	}

	@Override
	public boolean validErrorCode(PythonResponse response, int code) {
		String command = "Return code "+code;
		
		for(String line : response.getData()) {
			if(line.contains(command) == true) {
				return false;
			}
		}
		
		return true;
	}

}
