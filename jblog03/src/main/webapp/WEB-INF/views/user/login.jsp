<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>JBlog</title>
<Link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/jblog.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/jquery/jquery-1.9.0.js"></script>
</head>
<body>
	<div class="center-content">
		<c:import url="/WEB-INF/views/includes/header.jsp" />
		
		<c:choose>
			<c:when test="${empty blogId }">
				<c:set var="blogPath" value=""/>
			</c:when>
			<c:otherwise>
				<c:set var="blogPath" value="/${blogId }"/>
			</c:otherwise>
		</c:choose>
		
		<form class="login-form" method="post" action="${pageContext.request.contextPath }/user/auth${blogPath }">
      		<label>아이디</label> <input type="text" name="id">
      		<label>패스워드</label> <input type="password" name="password">
      		<c:if test='${result == "fail" }'>
				<p class="errmsg">
					아이디 또는 비밀번호가 잘못 입력 되었습니다.<br/>
					아이디와 비밀번호를 정확히 입력해 주세요.
				</p>
			</c:if>
      		<input type="submit" value="로그인">
		</form>
	</div>
</body>
</html>
