package com.board.cha;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.board.cha.bean.Reply;
import com.board.cha.service.BoardManagement;

//Board의 ajax용 컨트롤러
//restpful형식
@Controller
@RequestMapping(value="/ajax")
public class BoardAjaxController {
	@Autowired
	private BoardManagement bm;
	
	private ModelAndView mav; //싱글톤으로 사용하면 안된다 요청 응답을 하면 사라져야 하기 때문에
	//redirect는 get방식만 사용
	@RequestMapping(value = "/replyInsert", method = RequestMethod.GET)
	//@ResponseBody: response객체를 이용해서 요청페이지로 응답함.
	public @ResponseBody String replyInsert(){ //
		//DB에서 게시판 리스트 가져오는 작업
		//System.out.println("ajax/replyInsert");
		//mav = new ModelAndView();
		String jsonStr = bm.executeAjax(1);
		return jsonStr;
		 
		//Httprequestservlet 방식
		//response.setContentType("text/html; charset=UTF-8");
		//PrintWriter out=response.getWriter();
		//out.print(jsonStr);
	}
	//jackson방식
	/*@RequestMapping(value="/replyInsert")
	public @ResponseBody Map<String, List<Reply>> replyInsert(){
		Map<String, List<Reply>> rMap=bm.executeAjax(1);
		return rMap; //jackson Map-->json형태로 변환해줌
		//{'rlist', rlist} 속성, 댓글리스트
	}*/
}
