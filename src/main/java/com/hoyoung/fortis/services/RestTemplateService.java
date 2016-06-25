package com.hoyoung.fortis.services;

import org.springframework.http.HttpEntity;

import com.hoyoung.fortis.python.PythonResponse;

public interface RestTemplateService {
	
	public PythonResponse editConfigUserDevice(String deviceName,String macAddress);
	
	public HttpEntity<PythonResponse> appendConfigUserDeviceGroups(String deviceName);

	public HttpEntity<PythonResponse> unselectConfigUserDeviceGroups(String deviceName);

	public HttpEntity<PythonResponse> deleteConfigUserDevice(String userDeviceId);

	public HttpEntity<PythonResponse> getSystemStatus();

	public boolean validErrorCode(PythonResponse response, int code);
}
