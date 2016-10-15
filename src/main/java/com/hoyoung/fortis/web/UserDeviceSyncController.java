package com.hoyoung.fortis.web;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hoyoung.fortis.services.UserDeviceSyncService;

@Controller
@RequestMapping(value = "/userDeviceSync")
public class UserDeviceSyncController extends BaseController {
	final static Logger log = Logger.getLogger(UserDeviceSyncController.class);

	@Autowired
	private UserDeviceSyncService userDeviceSyncService;
	
	@RequestMapping(value = "/read", method = RequestMethod.GET)
	public @ResponseBody ModelAndView read(ModelMap model, HttpServletRequest request, HttpServletResponse response) {

		List dataList = userDeviceSyncService.fetchAll();
		
		return getSuccessModelAndView(model, dataList);
	}

	@RequestMapping(value = "/test1", method = RequestMethod.GET)
	public @ResponseBody void test1(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		long startTime = System.currentTimeMillis();
		userDeviceSyncService.test1();
		long endTime = System.currentTimeMillis();	
		log.info("test1: "+ (startTime - endTime) / 1000);
	}
	
	@RequestMapping(value = "/test2", method = RequestMethod.GET)
	public @ResponseBody void test2(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		long startTime = System.currentTimeMillis();
		userDeviceSyncService.test2();
		long endTime = System.currentTimeMillis();	
		log.info("test2: "+ (startTime - endTime) / 1000);
	}
	
	@RequestMapping(value = "/test3", method = RequestMethod.GET)
	public @ResponseBody void test3(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		long startTime = System.currentTimeMillis();
		userDeviceSyncService.test3();
		long endTime = System.currentTimeMillis();	
		log.info("test3: "+ (startTime - endTime) / 1000);
	}
	
	@RequestMapping(value = "/test4", method = RequestMethod.GET)
	public @ResponseBody void test4(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		long startTime = System.currentTimeMillis();
		userDeviceSyncService.test4();
		long endTime = System.currentTimeMillis();	
		log.info("test4: "+ (startTime - endTime) / 1000);
	}
	
	@RequestMapping(value = "/test5", method = RequestMethod.GET)
	public @ResponseBody void test5(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		long startTime = System.currentTimeMillis();
		userDeviceSyncService.test5();
		long endTime = System.currentTimeMillis();	
		log.info("test5: "+ (startTime - endTime) / 1000);
	}
	
	@RequestMapping(value = "/test6", method = RequestMethod.GET)
	public @ResponseBody void test6(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		long startTime = System.currentTimeMillis();
		userDeviceSyncService.test6();
		long endTime = System.currentTimeMillis();	
		log.info("test6: "+ (startTime - endTime) / 1000);
	}
	
	@RequestMapping(value = "/test7", method = RequestMethod.GET)
	public @ResponseBody void test7(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		long startTime = System.currentTimeMillis();
		userDeviceSyncService.test7();
		long endTime = System.currentTimeMillis();	
		log.info("test7: "+ (startTime - endTime) / 1000);
	}

	@RequestMapping(value = "/test10", method = RequestMethod.GET)
	public @ResponseBody void test10(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		long startTime = System.currentTimeMillis();
		userDeviceSyncService.test10();
		long endTime = System.currentTimeMillis();	
		log.info("test10: "+ (startTime - endTime) / 1000);
	}
	
	@RequestMapping(value = "/test11", method = RequestMethod.GET)
	public @ResponseBody void test11(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		long startTime = System.currentTimeMillis();
		userDeviceSyncService.test11();
		long endTime = System.currentTimeMillis();	
		log.info("test11: "+ (startTime - endTime) / 1000);
	}
}
