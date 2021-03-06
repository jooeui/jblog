package com.douzone.jblog.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.douzone.jblog.vo.UserVo;

public class AuthInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// 1. handler 종류 확인
		// 받아온 handler가 HandlerMethod 타입인지 체크(Controller에 있는 메서드)
		if(handler instanceof HandlerMethod == false) {
			// 없으면 그대로 진행
			return true;
		}
		
		// 2. casting
		HandlerMethod handlerMethod = (HandlerMethod)handler;
		
		// 3. Handler Method의 @Auth 받아오기
		Auth auth = handlerMethod.getMethodAnnotation(Auth.class);
		
		// 4. Handler Method에 @Auth가 없으면 Type에 있는지 확인
		if(auth == null) {
			// 과제
			auth = handlerMethod.getMethod().getDeclaringClass().getAnnotation(Auth.class);
		}
		
		// 5. Type과 Method에 @Auth가 적용이 안 되어 있는 경우
		// 인증이 필요 없는 요청
		if(auth == null) {
			return true;
		}
		
		// 6. @Auth가 적용이 되어 있기 때문에 인증(Authenfitation) 여부 확인
		HttpSession session = request.getSession();
		if(session == null) {
			response.sendRedirect(request.getContextPath() + "/user/login");
			return false;
		}
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		if(authUser == null) {
			response.sendRedirect(request.getContextPath() + "/user/login");
			return false;
		}
		
		// 8-2. @Auth의 role이 ADMIN인 경우, authUser의 role이 ADMIN이어야 함
		// authUser의 role이 ADMIN이 아닐 경우 권한 xxxxxx false 반환!!
		String servletPath = request.getServletPath();
		
		
		if(!(authUser.getId()).equals(servletPath.substring(servletPath.indexOf("/", 0)+1, servletPath.indexOf("/", 1)))) {
			response.sendRedirect(request.getContextPath() + "/" + servletPath.substring(servletPath.indexOf("/", 0)+1, servletPath.indexOf("/", 1)));
			return false;
		}
		
		// @Auth의 role이 ADMIN이고 authUser의 role이 ADMIN이면 true 반환!!!!
		return true;
	}

}
