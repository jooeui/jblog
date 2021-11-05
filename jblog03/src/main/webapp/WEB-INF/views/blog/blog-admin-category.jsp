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
<script type="text/javascript" src="${pageContext.request.contextPath }/assets/js/jquery/jquery-1.9.0.js"></script>
<script>
window.onload = function() {
	// 카테고리 추가
	$("#btn-cat-add").click(function(){
		var name = $("#name").val();
		var desc = $("#desc").val();
		if(name == ''){
			return;
		}
		console.log(name);
		
		$.ajax({
			url: "${pageContext.request.contextPath }/category/api/checkName?name="+name+"&desc="+desc,
			type: "get",
			dataType: "json",
			error: function(xhr, status, e) {
				console.log(status, e);
			},
			success: function(response) {
				console.log(response);
				if(response.result != "success"){
					console.error(response.message);
					return;
				}
				if(response.data){
					alert("이미 존재하는 카테고리입니다.");
					$("#name")
						.val("")
						.focus();
					return;
				}
				
				alert("카테고리가 추가되었습니다.");
				$("#name").val("");
				$("#desc").val("");
				
				$(".admin-cat").load("${pageContext.request.contextPath }/${authUser.id}/admin/category .admin-cat");
			}
		})
	});
	
	// 카테고리 삭제
	//$(".btn-cat-del").click(function(){
	$(document).on("click", ".btn-cat-del", function(){
		var no = $(this).nextAll(".del-cat-no").val();
		var postcount = $(this).nextAll(".del-cat-postcount").val();
		console.log(no);
		console.log(postcount);
		
		if(postcount > 0){
			if(!window.confirm("해당 카테고리에 속한 글이 모두 삭제됩니다.\n카테고리를 삭제하시겠습니까?")){
				return;
			} 
		} else {
			if(!window.confirm("카테고리를 삭제하시겠습니까?")){
				return;
			} 
		}
		
		$.ajax({
			url: "${pageContext.request.contextPath }/category/api/delete/"+no+"?postcount="+postcount,
			type: "get",
			dataType: "json",
			error: function(xhr, status, e) {
				console.log(status, e);
			},
			success: function(response) {
				console.log(response);
				
				if(response.result != "success") {
					console.error(response.message);
					return;
				}
				
				alert("카테고리가 삭제되었습니다.");

				$(".admin-cat").load("${pageContext.request.contextPath }/${authUser.id}/admin/category .admin-cat");
			}
		})
	});
};
</script>
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/blog-header.jsp" />
		<div id="wrapper">
			<div id="content" class="full-screen">
				<ul class="admin-menu">
					<li><a href="${pageContext.request.contextPath}/${authUser.id}/admin/basic">기본설정</a></li>
					<li class="selected">카테고리</li>
					<li><a href="${pageContext.request.contextPath}/${authUser.id}/admin/write">글작성</a></li>
				</ul>
		      	<table class="admin-cat">
		      		<tr>
		      			<th>번호</th>
		      			<th>카테고리명</th>
		      			<th>포스트 수</th>
		      			<th>설명</th>
		      			<th>삭제</th>      			
		      		</tr>
	      			<c:forEach items="${categoryVo }" var="vo" varStatus='status'>
						<tr>
							<td>${status.count }</td>
							<td>${vo.name }</td>
							<td>${vo.postCount} </td>
							<td>${vo.desc }</td>
							<td>
								<c:if test="${vo.name ne '미분류'}">
									<input class="btn-cat-del" type="image" src="${pageContext.request.contextPath}/assets/images/delete.jpg" alt="삭제">
									<input class="del-cat-no" type="hidden" value="${vo.no }" >
									<input class="del-cat-postcount" type="hidden" value="${vo.postCount }" >
								</c:if>
							</td>
						</tr>  
	      			</c:forEach>
				</table>
      	
      			<h4 class="n-c">새로운 카테고리 추가</h4>
		      	<table id="admin-cat-add">
		      		<tr>
		      			<td class="t">카테고리명</td>
		      			<td><input id="name" type="text" name="name"></td>
		      		</tr>
		      		<tr>
		      			<td class="t">설명</td>
		      			<td><input id="desc" type="text" name="desc"></td>
		      		</tr>
		      		<tr>
		      			<td class="s">&nbsp;</td>
		      			<td><input id="btn-cat-add" type="submit" value="카테고리 추가"></td>
		      		</tr>      		      		
		      	</table> 
			</div>
		</div>
		
		<c:import url="/WEB-INF/views/includes/blog-footer.jsp" />
	</div>
</body>
</html>