package com.board.cha.service;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.board.cha.bean.Member;
import com.board.cha.dao.MemberDao;

//추상클래스가 되면 new로 생성하지 못 한다.
@Service("boardmm")
public class MemberManagement implements Action{
	@Autowired
	private MemberDao mDao;
	//인터페이스는 bean태그로 등록을 안해도 DI컴포넌트에 자동으로 주입된다.
	@Autowired
	private HttpSession session;

	private ModelAndView mav; //= new ModelAndView(); 클래스가 싱글톤이기 때문에 필드에 객체를 생성한다면 
	@Override
	public ModelAndView execute(Member mb, int cmd){
		switch(cmd){
		case 1:
			memberAccess(mb);
			break;
		case 2:
			memberInsert(mb);
			break;
		}
		return mav;
	}
	//마이바티스는 hashmap을 지원한다.
	//빈에 담을 건지 hashmap에 담을 건지 선택한다.
	private void hashMapTest(String id, String pw){//(Member mb) {
		Map<String, String> hmap = new HashMap<String, String>();
		hmap.put("id", id);
		hmap.put("pw", pw);
		mDao.hashMapTest(hmap);
		
	}
	private void memberAccess(Member mb) {
		mav = new ModelAndView();
		String view = null;
		BCryptPasswordEncoder pwEncoder = new BCryptPasswordEncoder();
		String pwEncode = mDao.getSecurityPw(mb.getM_id());
		System.out.println("pw="+pwEncode);

		if(pwEncode!= null){
			if(pwEncoder.matches(mb.getM_pw(),pwEncode)){
				session.setAttribute("id", mb.getM_id());

				//비번, 아이디 일치시 멤버의 정보를 반환
				mb = mDao.getMemberInfo(mb.getM_id());
				//로그인 되어있는 동안 회원정보를 화면출력
				//session.setAttribute("member", mb);
				//Controller에 @SessionAttributes('member')를 추가하면
				//request영역이 아닌 session영역에 저장한다.
				mav.addObject("member",mb);
				//mav.addObject("mb",mb);
				mav.addObject("test", "redirectTest");//redirect이기 때문에 찍히지 않는다.
				view = "redirect:boardList";//redirect:url, GET방식만 가능
				//view = "forward:boardList";//forward:url, POST방식만 가능				
				/*view = "boardList";*/ //.jsp //id, pw, .... , gname까지 출력할 것
				mav.setViewName(view);
				return;
			}
		}
		mav.addObject("check",2);
		view="home";
		mav.setViewName(view);
	}

	private void memberInsert(Member mb) {
		mav = new ModelAndView();
		String view = null;
		//비번을 암호화(Encoding) 할 수 있지만 복호화(Decoding)는 불가능하다.
		BCryptPasswordEncoder pwEncoder = new BCryptPasswordEncoder();
		//비번을 스프링시큐리티로 암호화한 코드를 변환해서 저장
		mb.setM_pw(pwEncoder.encode(mb.getM_pw()));
		if(mDao.memberInsert(mb)!=0){
			view = "home"; //성공시 로그인 페이지
			mav.addObject("check",1);//회원가입 성공
		}else{
			view = "joinFrm"; //실패시 회원가입페이지
		}
		mav.setViewName(view);
	}
	@Override
	public ModelAndView execute(int cmd) {
		// TODO Auto-generated method stub
		return null;
	}

}
