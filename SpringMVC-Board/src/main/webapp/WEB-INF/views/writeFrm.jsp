<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script src="resources/js/jquery-3.2.1.min.js"></script>
</head>
<body>
<!-- 파일업로드는 post방식으로 -->
<form action="boardWrite" method="post" enctype="multipart/form-data">
<table border="1">
	<tr>
		<td>제목</td>
		<td><input type="text" name="btitle" /></td>
	</tr>
	<tr>	
		<td>내용</td>
		<td><textarea name="bcontents" rows="20" ></textarea></td>
	</tr>
	<tr>	
		<td>파일첨부</td>
		<!-- onchange 속성은 값이 바뀔 때 실행된다. -->
		<td><input type="file" name="bfile" onchange="fileChk(this)" />
			<input id="fileCheck" type="hidden" name="fileCheck" /></td>
	</tr>
	<tr>	
		<td colsapn="2">
			<input type="submit" value="글작성" />
			<input type="reset" value="취소" />
			<input type="button" onclick="location.href='./boardList'" value="리스트 보기" />
		</td>
	</tr>
</table>	
</form>
<script>
	function fileChk(elem){//파일 엘리먼트
		//파라미터로 잘 넘어왔는지 확인
		console.log(elem.value);
		if(elem.value==""){//파일을 선택하지 않았다면
			console.log("empty");
			$('#fileCheck').val(0);//파일첨부 안함
		}else{
			$('#fileCheck').val(1);//파일첨부 함
		}
	}//function End	
</script>
</body>
</html>