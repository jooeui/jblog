package com.douzone.jblog.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.douzone.jblog.service.BlogService;
import com.douzone.jblog.vo.BlogVo;

public class SiteInterceptor extends HandlerInterceptorAdapter {
	@Autowired
	private BlogService blogService;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String servletPath = request.getServletPath();
		String pathId = "";
//		System.out.println(servletPath);
		if(servletPath == "/") {
			return true;
		}
		if(servletPath.indexOf("/", 1) < 0) {
			pathId = servletPath.substring(servletPath.indexOf("/", 0)+1);
		} else {
			pathId = servletPath.substring(servletPath.indexOf("/", 0)+1, servletPath.indexOf("/", 1));
		}
//		System.out.println(pathId);
		BlogVo blog = (BlogVo) request.getServletContext().getAttribute("blogVo");
		if(blog == null || !(pathId.equals(blog.getId()))) {
			blog = blogService.getBlogInfo(pathId);
			request.getServletContext().setAttribute("blogVo", blog);
		}
		
		return true;
	}
	
}
