<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="resources/js/jquery-3.2.1.min.js"></script>
<title>Insert title here</title>
</head>
<body>
<h1>Board & reply Contents</h1>
<a href="boardDelete?bnum=${board.bnum}"><button>삭제</button></a>
<table>
	<tr height="30">
		<td width="100" bgcolor="lightgrey" align="center">NUM</td>
		<td colspan="5">${board.bnum}</td>	
	</tr>
	<tr height="30">
		<td bgcolor="lightgrey" align="center">WRITER</td>
		<td width="150">${board.bid}</td> 
		<td bgcolor="lightgrey" align="center">DATE</td>
		<td width="150">${board.bdate}</td> 
		<td bgcolor="lightgrey" align="center">VIEWS</td>
		<td width="150">${board.bview}</td> 
	</tr>
	<tr height="30">
		<td bgcolor="lightgrey" align="center">TITLE</td>
		<td colspan="5">${board.btitle}</td>		 
	</tr>
	<tr height="200">
		<td bgcolor="lightgrey" align="center">CONTENTS</td>
		<td colspan="5">${board.bcontents}</td>				
	</tr>
</table>
<table >
	<tr bgcolor="lightgrey" align="center" height="30">
		<td width="100">WRITER</td>
		<td width="200">CONTENTS</td> 
		<td width="150">DATE</td>
	</tr>
</table>
<form name="rFrm" id="rFrm">
	<table>
		<tr>
			<td>
				<textarea rows="3" cols="50" id="comment"></textarea>
			</td>
			<td>
				<input type="button" value="댓글입력" onclick="replyInsert(${board.bnum})" 
				style="width:70px; height:50px;"/>
			</td>
		</tr>
	</table>
</form>
<table id="rTable">
	<c:forEach var="reply" items="${rList}">
		<tr height="25" align="center">
			<td width="100">${reply.r_id}</td>
			<td width="200">${reply.r_contents}</td>
			<td width="150">${reply.r_date}</td> 
		</tr>
	</c:forEach>
</table>
</body>
<script>
	function replyInsert(bnum){
		//$('#articleView_layer').addClass('open');
		$.ajax({
			type: 'get',
			url: 'ajax/replyInsert',//restful 형식
			data:{bnum:bnum, comment:$("#comment").val()},
			//$('#rFrm').serialize(), 폼 전체  데이터 전송
			//timeout: 3000,//서버 대기시간이 지날 경우 에러 상태
			dataType: 'json',
			success: function(data){//댓글 리스트 json형태로 반환
				//alert('TEST');
				//console.log(data); //json 구조파악하기
				console.log(data); //map -> json 구조 파악
				//$('#rTable').html(data);
				
				//gson방식
			 	var rlistStr = '';
				for(var i=0;i<data.length;i++){
					rlistStr+='<tr height="25" align="center">'
					+'<td width="100">'+data[i].r_id+'</td>'
					+'<td width="200">'+data[i].r_contents+'</td>'
					+'<td width="150">'+data[i].r_date+'</td></tr>'
				}
				$('#rTable').html(rlistStr);
				
				/*
				jackson방식
				var rlistStr = '';
				for(var i=0; i<data.rlist.length; i++){
					rlistStr+='<tr height="25" align="center">'
					+'<td width="100">'+data.rlist[i].r_id+'</td>'
					+'<td width="200">'+data.rlist[i].r_contents+'</td>'
					+'<td width="150">'+data.rlist[i].r_date+'</td></tr>'
				}
				$('#rTable').html(rlistStr); */
			},
			error: function(error){
				alert('error');
				console.log(error);
			}
		}); //ajax End
	}
</script>
</html>