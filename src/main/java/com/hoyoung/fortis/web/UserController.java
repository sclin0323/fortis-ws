package com.hoyoung.fortis.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.hoyoung.fortis.command.SingleSideOnCommand;
import com.hoyoung.fortis.command.UserDeviceCommand;
import com.hoyoung.fortis.dao.SysSetting;
import com.hoyoung.fortis.python.PythonResponse;
import com.hoyoung.fortis.services.RestTemplateService;
import com.hoyoung.fortis.services.SysSettingService;
import com.hoyoung.fortis.services.UserDeviceService;

@Controller
@RequestMapping(value = "/user")
public class UserController extends BaseController {
	final static Logger log = Logger.getLogger(User.class);

	@Autowired
	private SysSettingService sysSettingService;
	
	@Autowired
	private UserDeviceService userDeviceService;
	
	@RequestMapping(value = "/ssologin", method = RequestMethod.POST)
	public @ResponseBody ModelAndView ssologin(ModelMap model, HttpServletRequest request, @RequestBody SingleSideOnCommand cmd) {
		log.info("ssologin");
		
		Map<String, Object> sysSetting = sysSettingService.fetchById("SETTING001");
		if (sysSetting == null) {
			getFailureModelAndView(model, "設定載入失敗!! 請初始化設定");
		}
		
		//int deviceLimit = (Integer) sysSetting.get("deviceLimit");
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("deviceLimit",  sysSetting.get("deviceLimit"));
		
		

		return getSuccessModelAndView(model, map);
	}
	
	@RequestMapping(value = "/initial", method = RequestMethod.GET)
	public @ResponseBody ModelAndView initial(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		
		Map<String, Object> sysSetting = sysSettingService.fetchById("SETTING001");
		if (sysSetting == null) {
			getFailureModelAndView(model, "設定載入失敗!! 請初始化設定");
		}
		
		
		
		List datas = userDeviceService.fetchByApplicantId("guest-0");
		
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("deviceLimit",  sysSetting.get("deviceLimit"));
		map.put("userDevices",  datas);
		
		

		return getSuccessModelAndView(model, map);
	}

	

}
