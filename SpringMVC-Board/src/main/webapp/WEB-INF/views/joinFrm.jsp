<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style>
	.subject{text-align:center; height:50px}
</style>
</head>
<body>
	<form name="joinFrm" action="memberInsert" method="post" onsubmit="return check()">
		<table border="1" cellspacing="0">
			<tr>
				<td colspan="2" class="subject">회원가입</td>
			</tr>
			<tr>
				<td width="100"> ID</td>
				<td><input type="text" name="m_id" /></td>
			</tr>
			<tr>
				<td width="100"> PW</td>
				<td><input type="password" name="m_pw" /></td>
			</tr>
			<tr>
				<td width="100"> NAME</td>
				<td><input type="text" name="m_name" /></td>
			</tr>
			<tr>
				<td width="100"> BIRTH</td>
				<td><input type="text" name="m_birth" /></td>
			</tr>
			<tr>
				<td width="100"> ADDR</td>
				<td><input type="text" name="m_addr" /></td>
			</tr>
			<tr>
				<td width="100"> PHONE</td>
				<td><input type="text" name="m_phone" /></td>
			</tr>
			<tr>
				<td colspan="2" class="subject">
					<input type="submit" value="회원가입" />
				</td>
			</tr>
		</table>
	</form>
	<script>
		function check(){
			var frm=document.joinFrm;//엘리먼트 6개를 받는다?
			var length=frm.length-1; //마지막 전송버튼은 필요없으므로 
			for(var i=0; i<length; i++){
				if(frm[i].value==""||frm[i].value==null){
					alert(frm[i].name+"을 입력하세요!!");
					frm[i].focus();
					return false;//실패면 submit안됨
				}
			}
			return true;//성공
		}
	</script>
</body>
</html>