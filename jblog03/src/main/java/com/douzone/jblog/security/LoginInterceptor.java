package com.douzone.jblog.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.douzone.jblog.service.UserService;
import com.douzone.jblog.vo.UserVo;

public class LoginInterceptor extends HandlerInterceptorAdapter {
	@Autowired
	private UserService userService;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String id = request.getParameter("id");
		String password = request.getParameter("password");
		
		UserVo authUser = userService.getUser(id, password);
		if(authUser == null) {
			request.setAttribute("result", "fail");
			request.getRequestDispatcher("/WEB-INF/views/user/login.jsp").forward(request, response);
			return false;
		}
		
		// session 처리
//		System.out.println(authUser);
		HttpSession session = request.getSession(true);
		session.setAttribute("authUser", authUser);
		
		String servletPath = request.getServletPath();
		String lastServletPath = servletPath.substring(servletPath.lastIndexOf("/")+1);
		if("auth".equals(lastServletPath)) {
			response.sendRedirect(request.getContextPath());
		} else {
			response.sendRedirect(request.getContextPath() + "/"+ lastServletPath);
		}
		
		return false;
	}
	
}
