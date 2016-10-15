package com.hoyoung.fortis.web;

import java.sql.Time;
import java.time.LocalTime;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hoyoung.fortis.command.SysSettingCommand;
import com.hoyoung.fortis.python.PythonResponse;
import com.hoyoung.fortis.services.RestTemplateService;
import com.hoyoung.fortis.services.SysSettingService;

@Controller
@RequestMapping(value = "/sysSetting")
public class SysSettingController extends BaseController {

	final static Logger log = Logger.getLogger(UserDeviceController.class);

	@Autowired
	private SysSettingService sysSettingService;

	@Autowired
	private RestTemplateService restTemplateService;

	@RequestMapping(value = "/read", method = RequestMethod.GET)
	public @ResponseBody ModelAndView read(ModelMap model, HttpServletRequest request, HttpServletResponse response) {

		log.info("test.......");
		Map map = sysSettingService.fetchById("SETTING001");
		if (map == null) {
			getFailureModelAndView(model, "設定載入失敗!! 請初始化設定");
		}

		return getSuccessModelAndView(model, map);
	}

	@RequestMapping(value = "/getSystemStatus", method = RequestMethod.POST)
	public @ResponseBody ModelAndView getSystemStatus(ModelMap model, HttpServletRequest request,
			HttpServletResponse response, @RequestBody SysSettingCommand cmd) {

		try {
			HttpEntity<PythonResponse> pr = restTemplateService.getSystemStatus();
			model.put("data", pr.getBody());
			model.put("status", BaseController.STATUS_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("連線存取設備狀態失敗!! ", e);
			return getFailureModelAndView(model, "連線存取設備狀態失敗!! ");
		}

		ModelAndView mav = new ModelAndView("jsonView", model);
		return mav;
	}



	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public @ResponseBody ModelAndView add(ModelMap model, HttpServletRequest request, HttpServletResponse response) {

		sysSettingService.delete("SETTING001");

		SysSettingCommand cmd = new SysSettingCommand();
		cmd.setSysSettingId("SETTING001");
		cmd.setName("fortinet 50d");
		cmd.setHostname("127.0.0.1");
		cmd.setPort(22);
		cmd.setLoginName("admin");
		cmd.setPassword("password");
		cmd.setDeviceLimit(0);
		
		cmd.setGuestLimit(0);
		cmd.setGuestStart(Time.valueOf(LocalTime.of(7, 0)));
		cmd.setGuestEnd(Time.valueOf(LocalTime.of(19, 0)));
		
		cmd.setCrtUid("sysadmin");
		cmd.setCrtName("sysadmin");
		
		cmd.setEnableGuest(true);
		cmd.setEnableUserDevice(true);

		Map map = sysSettingService.create(cmd);

		return getSuccessModelAndView(model, map);
	}

	@RequestMapping(value = "/update", method = RequestMethod.PUT)
	public @ResponseBody ModelAndView update(ModelMap model, @RequestBody SysSettingCommand cmd) {

		log.info(cmd.getGuestLimit());
		log.info(cmd.getGuestStart());
		log.info(cmd.getGuestEnd());
		
		Map map = sysSettingService.update(cmd);

		return getSuccessModelAndView(model, map);
	}

}
