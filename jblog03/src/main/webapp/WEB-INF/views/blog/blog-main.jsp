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
</head>
<script type="text/javascript" src="${pageContext.request.contextPath }/assets/js/jquery/jquery-1.9.0.js"></script>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/blog-header.jsp" />
		<div id="wrapper">
			<div id="content">
				<div class="blog-content">
					<c:choose>
						<c:when test="${empty infoMap.postVo }">
							<p><strong>아직 작성된 글이 없습니다.</strong><p>
						</c:when>
						<c:otherwise>
							<h4>${infoMap.postVo.title }</h4>
							<p>${infoMap.postVo.contents }<p>
						</c:otherwise>
					</c:choose>
				</div>
				<ul class="blog-list">
					<c:forEach items="${infoMap.postList }" var="postList" varStatus="status">
						<li>
							<a href="${pageContext.request.contextPath}/${blogVo.id }/${postList.categoryNo }/${postList.no}">${postList.title }</a>
							<span>${postList.regDate}</span>
						</li>
					</c:forEach>
				</ul>
			</div>
		</div>

		<div id="extra">
			<div class="blog-logo">
				<img src="${pageContext.request.contextPath}${blogVo.logo}">
			</div>
		</div>

		<div id="navigation">
			<h2>카테고리</h2>
			<ul>
				<c:choose>
					<c:when test="${infoMap.currentCatNo == 0 }">
						<li class="selected"><a href="${pageContext.request.contextPath }/${blogVo.id }">전체보기</a></li>
					</c:when>
					<c:otherwise>
						<li><a href="${pageContext.request.contextPath }/${blogVo.id }">전체보기</a></li>
					</c:otherwise>
				</c:choose>
				
				<c:forEach items="${infoMap.categoryList }" var="categoryList" varStatus="status">
					<c:choose>
						<c:when test="${categoryList.no == infoMap.currentCatNo }">
							<li class="selected"><a href="${pageContext.request.contextPath}/${blogVo.id }/${categoryList.no }">${categoryList.name }</a></li>
						</c:when>
						<c:otherwise>
							<li><a href="${pageContext.request.contextPath}/${blogVo.id }/${categoryList.no }">${categoryList.name }</a></li>
						</c:otherwise>
					</c:choose>
					<%-- <c:if test="${categoryList.name ne '미분류' }">
						<li><a href="${pageContext.request.contextPath}/${infoMap.blogVo.id }/${categoryList.no }">${categoryList.name }</a></li>
					</c:if> --%>
				</c:forEach>
			</ul>
		</div>
				
		
		<c:import url="/WEB-INF/views/includes/blog-footer.jsp" />
	</div>
</body>
</html>