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
				<div class="blog-post-list">
					<c:if test="${not empty infoMap.postVo }">
						<div class="category-info">
							<a href="${pageContext.request.contextPath}/${blogVo.id }/${infoMap.currentCategory.no }">${infoMap.currentCategory.name }</a>
							<span>${infoMap.currentCategory.postCount }개의 글</span>
						</div>
						<ul class="blog-list">
							<li class="list-head">
								<span>글제목</span>
								<span>작성일</span>
							</li>
							<c:forEach items="${infoMap.postList }" var="postList" varStatus="status">
								<li>
									<a href="${pageContext.request.contextPath}/${blogVo.id }/${postList.categoryNo }/${postList.no}">${postList.title }</a>
									<span>${postList.regDate}</span>
								</li>
							</c:forEach>
						</ul>
					</c:if>
				</div>
				<div class="blog-content">
					<c:choose>
						<c:when test="${empty infoMap.postVo }">
							<p><strong>아직 작성된 글이 없습니다.</strong><p>
						</c:when>
						<c:otherwise>
							<h4 class="post-title">${infoMap.postVo.title }</h4>
							<p class="post-contents">${infoMap.postVo.contents }</p>
							<c:if test="${authUser.id eq blogVo.id}">
								<a class="btn-cat-del" href="${pageContext.request.contextPath}/${blogVo.id }/post/delete/${infoMap.postVo.no}">
									삭제하기
								</a>
								<a class="btn-cat-del" href="${pageContext.request.contextPath}/${blogVo.id }/post/update/${infoMap.postVo.no}">
									수정하기
								</a>
							</c:if>
						</c:otherwise>
					</c:choose>
				</div>
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
					<c:when test="${infoMap.currentCategory.no == 0 }">
						<li class="selected"><a href="${pageContext.request.contextPath }/${blogVo.id }">전체보기</a></li>
					</c:when>
					<c:otherwise>
						<li><a href="${pageContext.request.contextPath }/${blogVo.id }">전체보기</a></li>
					</c:otherwise>
				</c:choose>
				
				<c:forEach items="${infoMap.categoryList }" var="categoryList" varStatus="status">
					<c:choose>
						<c:when test="${categoryList.no == infoMap.currentCategory.no}">
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