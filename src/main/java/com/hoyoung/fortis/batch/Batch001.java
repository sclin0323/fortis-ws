package com.hoyoung.fortis.batch;

import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hoyoung.fortis.command.UserDeviceSyncCommand;
import com.hoyoung.fortis.python.PythonResponse;
import com.hoyoung.fortis.services.RestTemplateService;
import com.hoyoung.fortis.services.UserDeviceSyncService;

@Component
public class Batch001 extends BaseBatch implements Batch{
	
	final static Logger log = Logger.getLogger(Batch001.class);

	@Autowired
	private RestTemplateService restTemplateService;
	
	@Autowired
	private UserDeviceSyncService userDeviceSyncService;
	
	ArrayList<String> groups = new ArrayList<String>();
	
	public Batch001() {
		groups.add("none-auth-group-0");
		groups.add("none-auth-group-1");
		groups.add("none-auth-group-2");
		groups.add("none-auth-group-3");
		groups.add("none-auth-group-4");
		groups.add("none-auth-group-5");
		groups.add("none-auth-group-6");
		groups.add("none-auth-group-7");
		groups.add("none-auth-group-8");
		groups.add("none-auth-group-9");
	}
	
	public void execute() {
		log.info("==================================================");
		
		
		/*
		ArrayList<UserDeviceSyncCommand> commands = new ArrayList<UserDeviceSyncCommand>();

		for(String userDeviceGroup : groups) {
			PythonResponse userDeviceGroupRes = restTemplateService.showUserDeviceGroupByUserDeviceGroup(userDeviceGroup);
			
			if(userDeviceGroupRes.isStatus() == true){
				// 取得 Fortinet User Devices 
				for(String line : userDeviceGroupRes.getData()) {
					if(line.contains("set member") == true) {
						ArrayList<UserDeviceSyncCommand> tmps = createFortinetUserDeviceCommand(line, userDeviceGroup);
						commands.addAll(tmps);
					}
				}
				
			} else {
				// 同步出現異常中斷同步
				log.info("同步異常，中斷同步作業!!");
				return;
			}
		}
		
		// 取得 Fortinet Mac Address
		for(UserDeviceSyncCommand command : commands){
			PythonResponse userDeviceRes = restTemplateService.showUserDeviceByUserDevice(command.getUserDevice());
			
			for(String line : userDeviceRes.getData()) {
				if(line.contains("set mac") == true) {
					String macAddress = line.trim().replaceAll("set mac", "").trim();
					command.setMacAddress(macAddress);
				}
			}
		}
		
		// testing
		for(UserDeviceSyncCommand cmd : commands){
			log.info(cmd.getUserDevice() +" "+cmd.getUserDeviceGroup()+" "+cmd.getMacAddress());
		}
		
		*/
		
	}

	private ArrayList<UserDeviceSyncCommand> createFortinetUserDeviceCommand(String line, String userDeviceGroup) {
		
		ArrayList<UserDeviceSyncCommand> cmds = new ArrayList<UserDeviceSyncCommand>();
		
		for(String userDevice :  line.trim().replaceAll("set member", "").trim().replaceAll("\"", "").trim().split(" ")) {
			UserDeviceSyncCommand cmd = new UserDeviceSyncCommand();
			
			cmd.setUserDevice(userDevice);
			cmd.setUserDeviceGroup(userDeviceGroup);
			
			cmds.add(cmd);
		}
		
		return cmds;
	}
	
	
	

}
