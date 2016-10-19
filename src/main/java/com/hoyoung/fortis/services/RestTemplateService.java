package com.hoyoung.fortis.services;

import org.springframework.http.HttpEntity;

import com.hoyoung.fortis.python.PythonResponse;

public interface RestTemplateService {
	
	public PythonResponse editConfigUserLocal(String account,String password);
	
	public PythonResponse deleteConfigUserLocal(String account);
	
	public PythonResponse editConfigUserGroup(String account,String userGroup);
	
	public PythonResponse reenableSystemInterface();
	
	public PythonResponse showUserDeviceGroupByUserDeviceGroup(String deviceGroup);
	
	public PythonResponse showUserDeviceByUserDevice(String deviceName);
	
	public PythonResponse editConfigUserDevice(String deviceName,String macAddress);
	
	public HttpEntity<PythonResponse> appendConfigUserDeviceGroups(String deviceName,String deviceGroup);

	public HttpEntity<PythonResponse> unselectConfigUserDeviceGroups(String deviceName, String deviceGroup);

	public HttpEntity<PythonResponse> deleteConfigUserDevice(String userDeviceId);

	public HttpEntity<PythonResponse> getSystemStatus();

	public boolean validErrorCode(PythonResponse response, int code);
	
	// For Fortinet Commands
	
	// 新增或編輯 User Device
	public String getConfigUserDeviceSetMac(String deviceName, String macAddress);
	
	// 新增特定 User Device to 特定的 User Device Group
	public String getConfigUserDeviceGroupAppendMember(String deviceGroup, String deviceName);
	
	// 移除特定 User Device 由特定 User Device Group
	public String getConfigUserDeviceGroupUnselectMember(String deviceGroup, String deviceName);
	
	// 刪除特定的 User Device
	public String getConfigUserDeviceDelete(String deviceName);
	
	// 重新 config system interface and reenable device identification
	public String getConfigSystemInterfaceDeviceIdentification();
	
	// 新增或編輯 Local User
	public String getConfigUserLocalSetPassword(String userName, String password);
	
	// 新增特定 Local User to 特定的 User Group
	public String getConfigUserGroupAppendMember(String userGroup, String userName);
	
	// 移除特定 Local User 來自特定的 User Group
	public String getConfigUserGroupUnselectMember(String userGroup, String userName);
		
	// 刪除特定的 Local User 
	public String getConfigUserLocalDelete(String userName);
	
	// 查詢特定 User Device
	public String getShowUserDevice(String deviceName);
		
	// 查詢特定 User Device Group
	public String getShowUserDeviceGroup(String deviceGroup);
}
