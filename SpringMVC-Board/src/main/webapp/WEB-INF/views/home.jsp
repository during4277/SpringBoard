<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page session="false" %>
<html>
<head>
	<title>Home</title>
	<script>
		function joinLoginCheck(check){
			if(check==1){
			alert("회원가입 성공");
			}
			if(check==2){
				alert("로그인 실패");
			}
		}
	</script>
</head>
<body onLoad="joinLoginCheck(${check})">
<h1>home.jsp(로그인 페이지)</h1>
<form action="access" name="logFrm" method="post">
	<table border="1">
		<tr>
			<td colspan="2" align='center' bgcolor="skyblue">로그인</td>
		</tr>
		<tr>
			<td><input type="text" name="m_id" /></td>
			<td rowspan="2"><button>로그인</button></td>
		</tr>
		<tr>
			<td><input type="password" name="m_pw" /></td>
		</tr>
		<tr>
			<td colspan="2" align="center" bgcolor="skyblue">com.board.cha</td>	
		</tr>
		<tr>
			<td colspan="2" align="center"><a href="joinFrm">회원가입</a></td>	
		</tr>
	</table>
</form>
</body>
</html>
