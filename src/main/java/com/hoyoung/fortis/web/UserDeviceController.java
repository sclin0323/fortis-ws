package com.hoyoung.fortis.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hoyoung.fortis.authorize.UserInfo;
import com.hoyoung.fortis.command.UserDeviceCommand;
import com.hoyoung.fortis.python.PythonResponse;
import com.hoyoung.fortis.services.RestTemplateService;
import com.hoyoung.fortis.services.UserDeviceLogService;
import com.hoyoung.fortis.services.UserDeviceService;

@Controller
@RequestMapping(value = "/userDevice")
public class UserDeviceController extends BaseController {
	final static Logger log = Logger.getLogger(UserDeviceController.class);

	@Autowired
	private UserDeviceService userDeviceService;

	@Autowired
	private UserDeviceLogService userDeviceLogService;

	@Autowired
	private RestTemplateService restTemplateService;

	@RequestMapping(value = "/read", method = RequestMethod.GET)
	public @ResponseBody ModelAndView read(ModelMap model, HttpServletRequest request, HttpServletResponse response) {

		String searchWord = request.getParameter("searchWord");
		int page = Integer.parseInt(request.getParameter("page"));
		int start = Integer.parseInt(request.getParameter("start"));
		int limit = Integer.parseInt(request.getParameter("limit"));

		List dataList = userDeviceService.fetchBySearchWord(searchWord, page, start, limit);
		long total = userDeviceService.fetchCountBySearchWord(searchWord, page, start, limit);

		return getSuccessModelAndView(model, dataList, total);
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public @ResponseBody ModelAndView add(ModelMap model, HttpServletRequest request,
			@RequestBody UserDeviceCommand cmd) {

		// 取得登入帳號 UserInfo
		UserInfo userInfo = getUserInfo(request);
		if (userInfo == null) {
			return getFailureModelAndView(model, "登入帳號資料有誤!!");
		} else {
			cmd.setCrtUid(userInfo.getSysUserId());
			cmd.setCrtName(userInfo.getName());
		}

		// 驗證新增資料
		try {
			userDeviceService.validateCreate(cmd);
		} catch (Exception e) {
			return getFailureModelAndView(model, e.getMessage());
		}

		// 新增 Fortinet : User Device and Group
		try {
			PythonResponse pr = restTemplateService.editConfigUserDevice(cmd.getDeviceName(), cmd.getMacAddress());
			// 檢查回傳的資料，使否出現網路卡號存在失敗
			if (restTemplateService.validErrorCode(pr, -15) == false) {
				return getFailureModelAndView(model, "該網卡網路設備已經存在，新增失敗。 [Return code -15]");
			}
			restTemplateService.appendConfigUserDeviceGroups(cmd.getDeviceName(), cmd.getDeviceGroup());

			restTemplateService.reenableSystemInterface();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("連線設備執行指令失敗!! ", e);
			return getFailureModelAndView(model, "連線設備執行指令失敗!! ");
		}

		// 新增 User Device
		Map map = userDeviceService.create(cmd);

		// 紀錄 Log
		userDeviceLogService.saveUserDeviceLog("CREATE", cmd.getCrtUid(), cmd.getCrtName(), cmd.getDeviceName());

		return getSuccessModelAndView(model, map);
	}

	@RequestMapping(value = "/update", method = RequestMethod.PUT)
	public @ResponseBody ModelAndView update(ModelMap model, HttpServletRequest request,
			@RequestBody UserDeviceCommand cmd) {
		// 取得登入帳號 UserInfo
		UserInfo userInfo = getUserInfo(request);
		if (userInfo == null) {
			return getFailureModelAndView(model, "登入帳號資料有誤!!");
		} else {
			cmd.setUpdUid(userInfo.getSysUserId());
			cmd.setUpdName(userInfo.getName());
		}

		// 驗證 - 網卡異動檢核網卡是否重複。
		try {
			userDeviceService.validateUpdate(cmd);
		} catch (Exception e) {
			return getFailureModelAndView(model, e.getMessage());
		}

		// 檢查網卡是否有異動，如果沒有異動，直接更新資料。
		if (userDeviceService.isUpdateMacAddress(cmd) == false) {
			// 更新人員訊息
			cmd.setUpdUid(userInfo.getSysUserId());
			cmd.setUpdName(userInfo.getName());
			Map map = userDeviceService.update(cmd);

			// 紀錄 Log
			userDeviceLogService.saveUserDeviceLog("UPDATE", cmd.getCrtUid(), cmd.getCrtName(), cmd.getDeviceName());

			return getSuccessModelAndView(model, map);
		}

		try {
			PythonResponse r1 = restTemplateService.editConfigUserDevice(cmd.getDeviceName(), cmd.getMacAddress());
			// 檢查回傳的資料，使否出現網路卡號存在失敗
			if (restTemplateService.validErrorCode(r1, -15) == false) {
				return getFailureModelAndView(model, "該網卡網路設備已經存在，新增失敗。 [Return code -15]");
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("連線設備執行指令失敗!! ", e);
			return getFailureModelAndView(model, "連線設備執行指令失敗!! ");
		}

		// 更新資料
		Map map = userDeviceService.update(cmd);

		// 紀錄 Log
		userDeviceLogService.saveUserDeviceLog("UPDATE", cmd.getCrtUid(), cmd.getCrtName(), cmd.getDeviceName());

		return getSuccessModelAndView(model, map);

	}

	@RequestMapping(value = "/delete", method = RequestMethod.DELETE)
	public @ResponseBody ModelAndView delete(ModelMap model, HttpServletRequest request) {
		// 取得登入帳號 UserInfo
		UserInfo userInfo = getUserInfo(request);
		if (userInfo == null) {
			return getFailureModelAndView(model, "登入帳號資料有誤!!");
		}

		String deviceName = request.getParameter("deviceName");
		String deviceGroup = request.getParameter("deviceGroup");

		try {
			restTemplateService.unselectConfigUserDeviceGroups(deviceName, deviceGroup);
			restTemplateService.deleteConfigUserDevice(deviceName);
			restTemplateService.reenableSystemInterface();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("連線設備執行指令失敗!! ", e);
			return getFailureModelAndView(model, "連線設備執行指令失敗!! ");
		}
		
		// 紀錄 Log
		userDeviceLogService.saveUserDeviceLog("DELETE", userInfo.getSysUserId(), userInfo.getName(), deviceName);

		Map map = userDeviceService.delete(deviceName);

		return getSuccessModelAndView(model, map);
	}
}
