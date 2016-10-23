package com.hoyoung.fortis.web;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
import com.hoyoung.fortis.command.GuestCommand;
import com.hoyoung.fortis.command.SysEmailCommand;
import com.hoyoung.fortis.dao.SysEmail;
import com.hoyoung.fortis.python.PythonResponse;
import com.hoyoung.fortis.services.GuestLogService;
import com.hoyoung.fortis.services.GuestService;
import com.hoyoung.fortis.services.RestTemplateService;
import com.hoyoung.fortis.services.SysEmailService;

@Controller
@RequestMapping(value = "/guest")
public class GuestController extends BaseController {
	final static Logger log = Logger.getLogger(GuestController.class);

	@Autowired
	private SysEmailService sysEmailService;

	@Autowired
	private GuestService guestService;
	
	@Autowired
	private GuestLogService guestLogService;

	@Autowired
	private RestTemplateService restTemplateService;
	
	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public @ResponseBody ModelAndView test(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		
		List dataList = guestService.fetchByApplicantId("test1");
		
		return getSuccessModelAndView(model, dataList, dataList.size());
	}

	@RequestMapping(value = "/read", method = RequestMethod.GET)
	public @ResponseBody ModelAndView read(ModelMap model, HttpServletRequest request, HttpServletResponse response) {

		String searchWord = request.getParameter("searchWord");
		int page = Integer.parseInt(request.getParameter("page"));
		int start = Integer.parseInt(request.getParameter("start"));
		int limit = Integer.parseInt(request.getParameter("limit"));

		List dataList = guestService.fetchBySearchWord(searchWord, page, start, limit);
		long total = guestService.fetchCountBySearchWord(searchWord, page, start, limit);

		return getSuccessModelAndView(model, dataList, total);
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public @ResponseBody ModelAndView add(ModelMap model, HttpServletRequest request, @RequestBody GuestCommand cmd) {

		// 取得登入帳號 UserInfo
		UserInfo userInfo = getUserInfo(request);
		if (userInfo == null) {
			return getFailureModelAndView(model, "登入帳號資料有誤!!");
		} else {
			cmd.setCrtUid(userInfo.getSysUserId());
			cmd.setCrtName(userInfo.getName());
		}
		
		// 建立 guestId and guestPwd
		LocalDateTime date = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyMMddSSS");
		String guestId = date.format(formatter);
		cmd.setGuestId(cmd.getApplicantId()+"-"+guestId);
		cmd.setGuestPwd(guestId);

		// 驗證新增資料
		try {
			guestService.validateCreate(cmd);
		} catch(Exception e) {
			return getFailureModelAndView(model, e.getMessage());
		}

		// 新增 Fortinet : User Device and Group
		try {
			//PythonResponse pr = restTemplateService.editConfigUserLocal(cmd.getGuestId(), cmd.getGuestPwd());
			// 檢查回傳的資料，使否出現網路卡號存在失敗
			//if (restTemplateService.validErrorCode(pr, -15) == false) {
			//	return getFailureModelAndView(model, "該網卡網路設備已經存在，新增失敗。 [Return code -15]");
			//}
			//restTemplateService.appendConfigUserDeviceGroups(cmd.getDeviceName(), cmd.getDeviceGroup());

			//restTemplateService.reenableSystemInterface();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("連線設備執行指令失敗!! ", e);
			return getFailureModelAndView(model, "連線設備執行指令失敗!! ");
		}
		
		// 發送 Email
		SysEmailCommand sysEmailCommand = guestService.getSysEmailCommand(cmd);
		sysEmailService.sendEmail(sysEmailCommand.getSendTo(), sysEmailCommand.getSubject(), sysEmailCommand.getText());
		
		// 新增 Guest Appoint
		Map map = guestService.create(cmd);
		
		// 紀錄 Log
		guestLogService.saveGuestLog("CREATE", cmd.getCrtUid(), cmd.getCrtName(), cmd.getGuestId());
		
		return getSuccessModelAndView(model, map);
	}

	@RequestMapping(value = "/delete", method = RequestMethod.DELETE)
	public @ResponseBody ModelAndView delete(ModelMap model, HttpServletRequest request) {
		// 取得登入帳號 UserInfo
		UserInfo userInfo = getUserInfo(request);
		if (userInfo == null) {
			return getFailureModelAndView(model, "登入帳號資料有誤!!");
		}

		String guestId = request.getParameter("guestId");
		
		
		try {
			//restTemplateService.unselectConfigUserDeviceGroups(deviceName, deviceGroup);
			//restTemplateService.deleteConfigUserLocal(guestId);
			//restTemplateService.reenableSystemInterface();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("連線設備執行指令失敗!! ", e);
			return getFailureModelAndView(model, "連線設備執行指令失敗!! ");
		}

		// 紀錄 Log
		guestLogService.saveGuestLog("DELETE", userInfo.getSysUserId(), userInfo.getName(), guestId);
				
		Map map = guestService.delete(guestId);
		
		

		return getSuccessModelAndView(model, map);
	}
}
