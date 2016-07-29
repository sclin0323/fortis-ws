package com.hoyoung.fortis.filter;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.*;

import org.apache.log4j.Logger;

import com.hoyoung.fortis.web.UserDeviceController;

@WebFilter(filterName = "performance", urlPatterns = { "/*" })
public class PerformanceFilter implements Filter {
	//final static Logger log = Logger.getLogger(PerformanceFilter.class);
	private FilterConfig filterConfig;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		System.out.println("========================================");
		
		filterConfig.getServletContext().log(request.getParameter("cn"));
		filterConfig.getServletContext().log(request.getParameter("givenName"));
		filterConfig.getServletContext().log(request.getContentType());
		filterConfig.getServletContext().log(request.getProtocol());
		
		
		
		long begin = System.currentTimeMillis();
		chain.doFilter(request, response);
		filterConfig.getServletContext().log("Request process in " + (System.currentTimeMillis() - begin) + " milliseconds");
	}

	@Override
	public void destroy() {
	}
}
