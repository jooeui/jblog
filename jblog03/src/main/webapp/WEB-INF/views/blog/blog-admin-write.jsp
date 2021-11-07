<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>JBlog</title>
<Link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/jblog.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/jquery/jquery-1.9.0.js"></script>
<script>
window.onload = function(){
	$("input[type=submit]").click(function(){
		var title = $("input[name='title']").val();
		var contents = $("textarea[name='contents']").val();
		
		if(title == ''){
			alert("제목을 입력해주세요");
			return;
		}
		
		if(contents == ''){
			alert("내용을 입력해주세요");
			return;
		}
	});
}
</script>
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/blog-header.jsp" />
		<div id="wrapper">
			<div id="content" class="full-screen">
				<ul class="admin-menu">
					<li><a href="${pageContext.request.contextPath}/${authUser.id}/admin/basic">기본설정</a></li>
					<li><a href="${pageContext.request.contextPath}/${authUser.id}/admin/category">카테고리</a></li>
					<li class="selected">글작성</li>
				</ul>
				
				<form:form
					modelAttribute="postVo"
					id="write-form"
					name="writeForm"
					method="post"
					action="${pageContext.request.contextPath }/${authUser.id}/admin/write">
					
			      	<table class="admin-cat-write">
			      		<tr>
			      			<td class="t">제목</td>
			      			<td>
			      				<form:input path="title" size="60" />
				      			<select name="categoryNo">
				      				<c:forEach items="${categoryList }" var="category" varStatus="status">
				      					<option value="${category.no }">${category.name }</option>
				      				</c:forEach>
				      			</select>
			      				<c:forEach items="${categoryList }" var="category" varStatus="status">
			      					<input type="hidden" name="categoryList[${status.index }].no" value="${category.no }">
			      					<input type="hidden" name="categoryList[${status.index }].name" value="${category.name }">
			      				</c:forEach>
				      		</td>
			      		</tr>
			      		<tr>
			      			<td class="t">내용</td>
			      			<td><form:textarea path="contents"></form:textarea><%-- <textarea name="content"></textarea>--%></td>
			      		</tr>
			      		<tr>
			      			<td>&nbsp;</td>
			      			<td class="s"><input type="submit" value="포스트하기"></td>
			      		</tr>
			      	</table>
				</form:form>
				
			</div>
		</div>
		
		<c:import url="/WEB-INF/views/includes/blog-footer.jsp" />
	</div>
</body>
</html>