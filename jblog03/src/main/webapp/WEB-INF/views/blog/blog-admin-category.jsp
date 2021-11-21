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
<script type="text/javascript" src="${pageContext.request.contextPath }/ejs/ejs.js"></script>
<script>
var listEJS = new EJS({
	url: "${pageContext.request.contextPath }/ejs/list-template.ejs"
});

var listItemEJS = new EJS({
	url: "${pageContext.request.contextPath }/ejs/listitem-template.ejs"
});

var list = function(){
	$.ajax({
		url: "${pageContext.request.contextPath }/category/api/list/${authUser.id }",
		dataType: "json",
		type: "get",
		error: function(xhr, status, error) {
			console.log(status + ":" + error);
		},
		success: function(response){
			console.log(response);
			
			var html = listEJS.render(response, {path : '${pageContext.request.contextPath }'});
			$(".admin-cat").append(html);
		}
	});
};

$(function() {
	// 카테고리 리스트
	list();
	
	var nameCheck = false;

	// 카테고리 입력 시 중복 있는지 바로 검사
	$("#name").focusout( function() {
		nameCheck = false;
		// var name = $(this).val();
		
		var check = {};
		
		check.blogId = "${authUser.id}";
		check.name = $(this).val();
		
		if (check.name == '') {
			$("#focusout-chk-name").text("카테고리명은 필수입니다.").removeClass().addClass("errmsg");
			return;
		}
		

		$.ajax({
			url: "${pageContext.request.contextPath }/category/api/checkName",
			type: "post",
			data: JSON.stringify(check),
			dataType: "json",
			contentType: "application/json",
			error: function(xhr, status, e) {
				console.log(status, e);
			},
			success: function(response) {
				if (response.result != "success") {
					console.error(response.message);
					return;
				}
				
				console.log(response);
				
				if (response.data) {
					$("#focusout-chk-name").text("이미 존재하는 카테고리명입니다.").removeClass().addClass("errmsg");
					return;
				}
				$("#focusout-chk-name").text("사용가능한 카테고리명입니다.").removeClass().addClass("successmsg");
				nameCheck = true;
			}
		})
	});

	// 카테고리 추가
	$(document).on("click","#btn-cat-add", function(event) {
		// var name = $("#name").val();
		// var desc = $("#desc").val();
		if (nameCheck == false) {
			alert(errMsg);
			return;
		}

		var category = {};
		
		category.name = $("#name").val();
		category.desc = $("#desc").val();
		category.blogId = "${authUser.id}";

		$.ajax({
			url: "${pageContext.request.contextPath }/category/api/insert",
			type: "post",
			data: JSON.stringify(category),
			dataType: "json",
			contentType: "application/json",
			error: function(xhr, status, e) {
				console.log(status, e);
			},
			success: function(response) {
				if (response.result != "success") {
					console.error(response.message);
					return;
				}
				
				/* $(".admin-cat").load("${pageContext.request.contextPath }/${authUser.id}/admin/category .admin-cat"); */

				var listCount = $(".admin-cat tr:gt(0)").length + 1;
				
				var html = listItemEJS.render(response, {path: '${pageContext.request.contextPath }', listCount: listCount});

				$(".admin-cat tbody").append(html); 
				
				alert("카테고리가 추가되었습니다.");
				$("#name").val("");
				$("#desc").val("");
				$("#focusout-chk-name").removeClass().remove();
			}
		})
	});

	// 카테고리 삭제
	$(document).on("click", ".btn-cat-del", function(event){
		/* var no = $(this).nextAll(".del-cat-no").val();
		var postcount = $(this).nextAll(".del-cat-postcount").val(); */
		// event.preventDefault();
		
		var no = $(this).data("no");
		console.log(no);
		var category = {}
		
		category.postCount = $(this).data("count");
		category.blogId = "${authUser.id}";

		if (category.postCount > 0) {
			if (!window.confirm("해당 카테고리에 속한 글이 모두 삭제됩니다.\n카테고리를 삭제하시겠습니까?")) {
				return;
			}
		} else {
			if (!window.confirm("카테고리를 삭제하시겠습니까?")) {
				return;
			}
		}

		$.ajax({
			url: "${pageContext.request.contextPath }/category/api/delete/" + no,
			type: "post",
			contentType: "application/json",
			data: JSON.stringify(category),
			dataType: "json",
			error: function(xhr, status, e) {
				console.log(status, e);
			},
			success: function(response) {
				if (response.result != "success") {
					console.error(response.message);
					return;
				}
				
				alert("카테고리가 삭제되었습니다.");
			
				/* $(".admin-cat").load("${pageContext.request.contextPath }/${authUser.id}/admin/category .admin-cat"); */
				
				console.log(response.data);
				console.log($(".admin-cat tbody tr").length);
				
				var removeIndex = $("tr[data-no=" + response.data + "] td:first").text();
				var endIndex = $("tbody tr").length;

				console.log(removeIndex);
				console.log($("tr[data-no=" + response.data + "]").next().find("td:first").text());
				
				for(i = removeIndex; i <= endIndex; i++ ) {
					$(".admin-cat tr:nth("+ i +")").next().find("td:first").text(i);
				}
				$(".admin-cat tr[data-no=" + response.data + "]").remove();
			}
		})
	});
});
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
			      	<thead>
			      		<tr>
			      			<th>번호</th>
			      			<th>카테고리명</th>
			      			<th>포스트 수</th>
			      			<th>설명</th>
			      			<th>삭제</th>      			
			      		</tr>
			      	</thead>
				</table>
      	
      			<h4 class="n-c">새로운 카테고리 추가</h4>
		      	<table id="admin-cat-add">
		      		<tr>
		      			<td class="t">카테고리명</td>
		      			<td>
		      				<input id="name" type="text" name="name">
		      				<p id="focusout-chk-name"></p>
		      			</td>
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