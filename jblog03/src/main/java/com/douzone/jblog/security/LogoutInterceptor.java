package com.douzone.jblog.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class LogoutInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		HttpSession session = request.getSession();
		if(session == null) {
			return false;
		}
		
		session.removeAttribute("authUser");
		session.invalidate();
		
		String servletPath = request.getServletPath();
		String lastServletPath = servletPath.substring(servletPath.lastIndexOf("/")+1);
		if("logout".equals(lastServletPath)) {
			response.sendRedirect(request.getContextPath());
		} else {
			response.sendRedirect(request.getContextPath() + "/"+ lastServletPath);
		}
		
		return false;
	}

}
