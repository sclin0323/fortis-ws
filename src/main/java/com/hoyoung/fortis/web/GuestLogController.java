package com.hoyoung.fortis.web;

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

import com.hoyoung.fortis.services.GuestLogService;

@Controller
@RequestMapping(value = "/guestLog")
public class GuestLogController extends BaseController {
	final static Logger log = Logger.getLogger(GuestLogController.class);

	@Autowired
	private GuestLogService guestLogService;

	@RequestMapping(value = "/read", method = RequestMethod.GET)
	public @ResponseBody ModelAndView read(ModelMap model, HttpServletRequest request, HttpServletResponse response) {

		String searchWord = request.getParameter("searchWord");
		int page = Integer.parseInt(request.getParameter("page"));
		int start = Integer.parseInt(request.getParameter("start"));
		int limit = Integer.parseInt(request.getParameter("limit"));

		List dataList = guestLogService.fetchBySearchWord(searchWord, page, start, limit);
		long total = guestLogService.fetchCountBySearchWord(searchWord, page, start, limit);

		return getSuccessModelAndView(model, dataList, total);
	}

}
