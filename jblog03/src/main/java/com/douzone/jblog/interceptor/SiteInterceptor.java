package com.douzone.jblog.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.douzone.jblog.service.BlogService;
import com.douzone.jblog.service.UserService;
import com.douzone.jblog.vo.BlogVo;
import com.douzone.jblog.vo.UserVo;

public class SiteInterceptor extends HandlerInterceptorAdapter {
	@Autowired
	private UserService userService;

	@Autowired
	private BlogService blogService;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String servletPath = request.getServletPath();
		String lastServletPath = servletPath.substring(servletPath.lastIndexOf("/")+1);
		UserVo userVo = userService.getUser(lastServletPath);
		String idCheck = userVo.getId();

		if(idCheck != null || !("".equals(idCheck))) {
			BlogVo blog = (BlogVo) request.getServletContext().getAttribute("blogVo");
			if(blog == null || idCheck != blog.getId()) {
				blog = blogService.getBlogInfo(idCheck);
				request.getServletContext().setAttribute("blogVo", blog);
			}
		}
		
		return true;
	}
	
}
