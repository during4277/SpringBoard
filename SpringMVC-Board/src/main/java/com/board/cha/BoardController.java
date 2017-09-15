package com.board.cha;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.board.cha.bean.Board;
import com.board.cha.service.BoardManagement;
import com.board.cha.service.MemberManagement;

@Controller
public class BoardController {
	@Autowired
	private BoardManagement bm;
	@Autowired
	private MemberManagement mm;
	//@Autowired
	//@Resource(name="boardMM")//@qualifier("boardMM")
	//private Action boardMM;
	private ModelAndView mav; //싱글톤으로 사용하면 안된다 요청 응답을 하면 사라져야 하기 때문에
	//redirect는 get방식만 사용
	@RequestMapping(value = "/boardList", method = RequestMethod.GET)
	//@ResponseBody: response객체를 이용해서 요청페이지로 응답함.
	public ModelAndView boardList(Board board) { //
		//DB에서 게시판 리스트 가져오는 작업
		System.out.println("boardList입니다.");
		mav = bm.execute(1);
		
		return mav;
	
	}
	@RequestMapping(value = "/contents", method = RequestMethod.GET)
	public ModelAndView contents(){//@RequestParam int num) { //
		//DB에서 게시판 리스트 가져오는 작업
		mav = bm.execute(2);
		return mav;
	}
	@RequestMapping(value="/boardDelete", method=RequestMethod.GET)
	public ModelAndView boardDelet(){
		System.out.println("boardDelete");
		mav=new ModelAndView();
		mav = bm.execute(3);
		return mav;
	}
	@RequestMapping(value="/writeFrm", method=RequestMethod.GET)
	public ModelAndView writeFrm(){
		System.out.println("writeFrm");
		//간단한 페이지 전환은 컨트롤러에서..
		mav=new ModelAndView();
		mav.setViewName("writeFrm");//글쓰기 화면
		return mav;
	}
	@RequestMapping(value="/boardWrite")
	public ModelAndView boardWrite(MultipartHttpServletRequest multi){//Board board){ //파마리터 한개만 받을 때에는 @
		System.out.println("boardWrite");
		mav=new ModelAndView();
		/*System.out.println("btitle="+multi.getParameter("btitle"));
		MultipartFile mfile = multi.getFile("bfile");//파일을 관리하는 클래스//파일의 정보를 가져온다
		System.out.println("file name="+mfile.getOriginalFilename());
		System.out.println("file name="+mfile.getName());//파일의 태그네임
		System.out.println("bfile="+multi.getFile("bfile"));*/
		mav= bm.execute(multi,1);
		return mav;
	}
}