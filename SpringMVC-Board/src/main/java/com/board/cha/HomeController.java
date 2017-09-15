package com.board.cha;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.board.cha.bean.Member;
import com.board.cha.service.MemberManagement;

/*spring 작동 원리
controller에 url이 들어오면 dispatcgherservlet에 요청을 한다.
servlet-context에서 ViewResolver에 의해 home.jsp로 응답합니다.
*/
@Controller
//member로 모델객체를 생성하면 request영역과 session영역에 저장한다.
@SessionAttributes("member")
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	@Autowired
	private MemberManagement mm;//회원관리 서비스
	
	private ModelAndView mav; //싱글톤으로 사용하면 안된다 요청 응답을 하면 사라져야 하기 때문에
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView home() {
		mav = new ModelAndView();
		//모델에는 객체만 저장할 수 있다. 그래서 모델객체라고 불린다.
		//모델은 인스턴스라 객체로 사용할 수 없다.
		//ModelAndView를 사용하는 것이 좋다.
		mav.setViewName("home");//Model보다 업그레이드 된 클래스 뷰 제공
		return mav;
	}
	@RequestMapping(value = "/access", method = RequestMethod.POST)
	public ModelAndView access(Member mb) {
		mav = mm.execute(mb, 1); //로그인
		//mav.setViewName("boardList");
		return mav;
	}
	@RequestMapping(value = "/joinFrm", method = RequestMethod.GET)
	public ModelAndView joinFrm() {
		mav = new ModelAndView();
		mav.setViewName("joinFrm");//.jsp 회원가입 페이지 
		return mav;
	}
	
	@RequestMapping(value = "/memberInsert", method = RequestMethod.POST)
									//빈의 필드 이름과 input태그의 name이 같아야 값을 가져온다.//
	public ModelAndView memberInsert(@ModelAttribute("mb") Member mb) {//requestParam으로 오는 값을 받는 어노테이션
		/*Member mb = new Member();
		model.addAttribute("mb", mb);*/
		//mm.execute("/memberInsert");
		System.out.println("name="+mb.getM_name());
		mav = mm.execute(mb,2);
		mav = new ModelAndView();
		mav.setViewName("home");
		return mav;
	}
}
