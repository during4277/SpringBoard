<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style>
html, body{height:100%;margin:0}
  #articleView_layer {
   display:none;position:fixed;
   position:absolute;top:0;left:0;
   width:100%;height:100%}
  #articleView_layer.open {display:block;color:red}
  #articleView_layer #bg_layer {
      position:absolute;top:0;left:0;
      width:100%;height:100%;background:#000;
      opacity:.5;filter:alpha(opacity=50);z-index:100}
  #contents_layer { position:absolute;top:40%;left:40%;
     width:400px;height:400px;margin:-150px 0 0 -194px;
      padding:28px 28px 0 28px;border:2px solid #555;
        background:#fff;font-size:12px;z-index:200;
     color:#767676;line-height:normal;white-space:normal;
     overflow:scroll}
</style>
<script src="resources/js/jquery-3.2.1.min.js"></script>
</head>
<body>
<h1>게시판 리스트</h1>
<div align="right">
	<a href="logout">로그아웃</a>
</div>
아이디:${member.m_id}<br/>
이름:${member.m_name}<br/>
포인트:${member.m_point}<br/>
등급:${member.g_name}
<h1>${test}</h1>
<h1>${bList}</h1>
<!-- 암호화 encoding -->
<!-- 복호화 decoding -->
<form action="writeFrm">
	<button>글쓰기</button>
</form>
<div align="center">
${paging}
</div>
<script>
function articleView(num){
	$('#articleView_layer').addClass('open');
	$.ajax({
		type: 'get',
		url: 'contents',
		data:{bnum:num},
		timeout: 3000,//서버 대기시간이 지날 경우 에러 상태
		dataType: 'html',
		success: function(data){
			//alert('TEST');
			$('#contents_layer').html(data);
		},
		error: function(error){
			alert('error');
			console.log(error);
		}
	}); //ajax End
}//function End

//LightBox 해제
$(function(){//$(document).ready(function(){}) //처음 jsp가 실행되면 바로 실행하는 함수
	var layerWindow = $('#articleView_layer');
	layerWindow.find('#bg_layer').mousedown(
		function(event){//키보드, 마우스 어떤 것을 눌렀는지 확인
			//console.log(event);
			layerWindow.removeClass('open');
			return;
		});//find end
		$(document).keydown(function(event){
			//console.log(event); //esc키 번호	
			if(event.keyCode!=27){//esc를 제외한 키는 모두 return
				return;
			}
			//keyCode가 27이면 실행
			if(layerWindow.hasClass('open')){//div에 open이라는 클래스가 있다면 true 아니면 false
				layerWindow.removeClass('open');
			}
		});//keydown End
});
</script>
</body>
<div id="articleView_layer">
	<div id="bg_layer"></div>
	<div id="contents_layer"></div>
</div>
</html>