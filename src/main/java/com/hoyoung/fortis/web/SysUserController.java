package com.hoyoung.fortis.web;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
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
import com.hoyoung.fortis.authorize.UserInfo;
import com.hoyoung.fortis.command.SysSettingCommand;
import com.hoyoung.fortis.command.SysUserCommand;
import com.hoyoung.fortis.python.PythonResponse;
import com.hoyoung.fortis.services.RestTemplateService;
import com.hoyoung.fortis.services.SysSettingService;
import com.hoyoung.fortis.services.SysUserService;
import com.hoyoung.fortis.util.Md5Util;

@Controller
@RequestMapping(value = "/sysUser")
public class SysUserController extends BaseController {

	final static Logger log = Logger.getLogger(SysUserController.class);

	@RequestMapping(value = "/getUserInfo", method = RequestMethod.GET)
	public @ResponseBody ModelAndView getUserInfo(ModelMap model, HttpServletRequest request,
			HttpServletResponse response) {

		UserInfo userInfo = getUserInfo(request);
		if (userInfo == null) {
			return getFailureModelAndView(model, "查無使用者資訊，請確認已登入系統");
		}

		Map map = new HashMap();
		map.put("sysUserId", userInfo.getSysUserId());
		map.put("sysUserName", userInfo.getName());
		
		log.info(userInfo.getSysUserId()+" "+userInfo.getName());

		return getSuccessModelAndView(model, map);
	}

	@RequestMapping(value = "/read", method = RequestMethod.GET)
	public @ResponseBody ModelAndView read(ModelMap model, HttpServletRequest request, HttpServletResponse response) {

		String searchWord = request.getParameter("searchWord");
		int page = Integer.parseInt(request.getParameter("page"));
		int start = Integer.parseInt(request.getParameter("start"));
		int limit = Integer.parseInt(request.getParameter("limit"));

		List dataList = sysUserService.fetchBySearchWord(searchWord, page, start, limit);
		long total = sysUserService.fetchCountBySearchWord(searchWord, page, start, limit);

		return getSuccessModelAndView(model, dataList, total);
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public @ResponseBody ModelAndView add(ModelMap model, HttpServletRequest request, @RequestBody SysUserCommand cmd) {

		UserInfo userInfo = getUserInfo(request);
		if (userInfo == null) {
			return getFailureModelAndView(model, "查無使用者資訊，請確認已登入系統");
		} else {
			cmd.setCrtUid(userInfo.getSysUserId());
			cmd.setCrtName(userInfo.getName());
		}

		// MD5 編碼
		try {
			cmd.setPassword(Md5Util.toMd5(cmd.getPassword()));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return getFailureModelAndView(model, "密碼編碼程序失敗 MD5 無法新增!!");
		}

		Map map = sysUserService.create(cmd);

		return getSuccessModelAndView(model, map);
	}

	@RequestMapping(value = "/update", method = RequestMethod.PUT)
	public @ResponseBody ModelAndView update(ModelMap model, HttpServletRequest request,
			@RequestBody SysUserCommand cmd) {

		UserInfo userInfo = getUserInfo(request);
		if (userInfo == null) {
			return getFailureModelAndView(model, "查無使用者資訊，請確認已登入系統");
		} else {
			cmd.setUpdUid(userInfo.getSysUserId());
			cmd.setUpdName(userInfo.getName());
		}
		
		
		String originPwd = sysUserService.fetchPasswordById(cmd.getSysUserId());
		// 沒有變更密碼，直接更新資料
		if(cmd.isChangePassword() == false) {
			
			cmd.setPassword(originPwd);
			Map map = sysUserService.update(cmd);
			return getSuccessModelAndView(model, map);
			
		} else {
			
			// 檢核原密碼輸入是否正確
			try {
				if(originPwd.equals(Md5Util.toMd5(cmd.getPassword())) == false) {
					return getFailureModelAndView(model, "原密碼輸入不正確，請重新確認。");
				}
				
				// 設定新密碼
				cmd.setPassword(Md5Util.toMd5(cmd.getNewPassword()));
				
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
				return getFailureModelAndView(model, "密碼編碼程序失敗 MD5 無法更新!!");
			}
			
			Map map = sysUserService.update(cmd);
			return getSuccessModelAndView(model, map);	
		}
	}

	@RequestMapping(value = "/delete", method = RequestMethod.DELETE)
	public @ResponseBody ModelAndView delete(ModelMap model, HttpServletRequest request) {
		String sysUserId = request.getParameter("sysUserId");

		Map map = sysUserService.delete(sysUserId);

		return getSuccessModelAndView(model, map);
	}

}
