package com.board.cha.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.board.cha.bean.Board;
import com.board.cha.bean.Member;
import com.board.cha.bean.Reply;
import com.board.cha.dao.BoardDao;
import com.board.cha.userClass.Paging;
import com.board.cha.userClass.UploadFile;
import com.google.gson.Gson;
//컨트롤러 서비스 Dao 전부 같은 Component인데 가독성을 주기 위해 @Controller @Service @Repository로 구분한다. 
@Service//@Component//DI영역에 저장하기 위해 
public class BoardManagement implements Action{
	@Autowired
	private BoardDao bDao;
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpSession session; //스프링이 request.getSession();을 넣는다.
	
	private String jsonStr;
	//private Map<String, List<Reply>> jsonObj;//jackson 방식
	private ModelAndView mav;
	@Override
	public ModelAndView execute(int code){
		switch(code){
		case 1:
			getBoardList();
			break;
		case 2:
			getContents();
			break;
		case 3:
			boardDelete();
			break;
		}
		return mav;
	}
	@Transactional
	//모든 메소드호출이 성공하면 자동으로 commit,
	//하나라도 예외(Exception)가 발생하면 rollback 됨
	//@Transactional 메소드에서는 같은 클래스 안에 메소드만 
	//트랜잭션이 걸린다. 바로 dao로 호출하면 실행이 되지 않는다.
	private void boardDelete() {
		String view = null;
		mav=new ModelAndView();
		if(session!=null && session.getAttribute("id")!=null){//세션검사
			int bnum = Integer.parseInt(request.getParameter("bnum"));
			System.out.println("bnum="+bnum);
			int r = replyDelete(bnum);
			int b = articleDelete(bnum);
			if(r!=0 && b!=0){//댓글 및 원글 삭제 성공시
			//if(bDao.getBoardCount()!=0){
				System.out.println("삭제 트랜잭션 성공");
				//bDao.boardDelete(bnum);
				view = "redirect:/boardList";
			}else{
				System.out.println("삭제 드랜잭션 실패");
				view = "redirect:/boardList";
			}
		}
		mav.setViewName(view);
	}
	private int articleDelete(int bnum) {
		return bDao.articleDelete(bnum);
	}
	private int replyDelete(int bnum) {
		return bDao.replyDelete(bnum);
	}
	/*public Map<String, List<Reply>> executeAjax(int cmd){
		switch(cmd){
		case 1:
			replyInsert();
			break;
		}
		return jsonObj;
	}*/
	
	public String executeAjax(int code){
		switch(code){
		case 1:
			replyInsert();
			break;
		}
		return jsonStr;
	}
	private void replyInsert() {
		String view = null;
		mav = new ModelAndView();
		if(session!=null && session.getAttribute("id")!=null){
			Reply reply = new Reply();
			int bnum = Integer.parseInt(request.getParameter("bnum"));
			reply.setR_bnum(bnum);
			//시퀀스 없이 댓글 번호 구하기
			if(bDao.getReplyCount()!=0){				
				reply.setR_num(bDao.getReplyMaxNum()+1);
			}else{
				reply.setR_num(1);
			}
			reply.setR_id(session.getAttribute("id").toString());
			reply.setR_contents(request.getParameter("comment"));
			if(bDao.replyInsert(reply)!=0){
				//입력 후 댓글리스트를 반환
				List<Reply> rList = bDao.getReplyList(bnum);
				Gson jsonObj = new Gson();
				jsonStr = jsonObj.toJson(rList);
				System.out.println("jsonStr="+jsonStr);
				//jackson
				//jsonObj = new HashMap<String,List<Reply>>();
				//jsonObj.put("rlist", rList);
				//System.out.println("rlist="+jsonObj);
			}else{
				view="home";
			}
			mav.setViewName(view);
		}
		
	}
	private void getContents() {
		String view=null;
		mav = new ModelAndView();
		int bnum=Integer.parseInt(request.getParameter("bnum"));
		if(session!=null && session.getAttribute("id")!=null){
			mav.addObject("board",bDao.getContents(bnum));
			List<Reply> rlist = bDao.getReplyList(bnum);
			System.out.println("rlist="+ rlist.size());
			mav.addObject("rList",rlist);
			view = "boardContentsAjax";//ajax용 jsp
		}else{
			view="errorAjax";//Ajax용 jsp
		}
		mav.setViewName(view);
		
	}
	private void getBoardList() {
		String view = null;
		mav = new ModelAndView();
		List<Board> bList = null;
		String makeBoardList = null;
		if(session != null && session.getAttribute("id")!=null){
			int pageNum=(request.getParameter("pageNum")!=null)
					? Integer.parseInt(request.getParameter("pageNum")):1;//
			bList = bDao.getBoardList(pageNum);
			makeBoardList = makeBoardList(bList);
			mav.addObject("paging", getPaging(pageNum));
			mav.addObject("bList", makeBoardList);
			//String pagingHtml = getPaging(pageNum);
			view = "boardList";
		}else{
			view="home";
		}
		mav.setViewName(view);
	}
	private String getPaging(int pageNum) {//현재 페이지번호
		int maxNum=bDao.getBoardCount();//글의 수
		System.out.println(maxNum);
		int listCount = 10; //페이지당 글의 수
		int pageCount = 2; //그룹당 페이지 수
		String boardName = "boardList"; //게시판의 종류  //게시판이 여러개일 때 의미
		Paging paging = new Paging(maxNum, pageNum, listCount, pageCount, boardName);
		//String pagingHtml = paging.makeHtmlPaging();
		return paging.makeHtmlPaging();
	}
	private String makeBoardList(List<Board> bList) {
		StringBuilder sb = new StringBuilder();
		Board board = null;
		sb.append("<table border='1'>");
		sb.append("<tr><td>글 번호</td><td>제목</td><td>작성자</td><td>날짜</td><td>조회수</td></tr>");
		for(int i=0; i<bList.size(); i++){
			board = bList.get(i);
			sb.append("<tr><td>"+board.getBnum()+"</td>");
			sb.append("<td><a href='#' onclick='articleView("+board.getBnum()+")'>"+board.getBtitle()+"</a></td>");
			sb.append("<td>"+board.getBid()+"</td>");
			sb.append("<td>"+board.getBdate()+"</td>");
			sb.append("<td>"+board.getBview()+"</td></tr>");
		}
		sb.append("</table>");
		return sb.toString();
	}
	@Override
	public ModelAndView execute(Member mb, int cmd) {
		return null;
	}
	public ModelAndView execute(MultipartHttpServletRequest multi, int cmd) {
		switch(cmd){
		case 1:
			boardWrite(multi);
			break;
		}
	
		return mav;
	}
	private void boardWrite(MultipartHttpServletRequest multi) {
		String title = multi.getParameter("btitle");
		String contents = multi.getParameter("bcontents");
		String id = session.getAttribute("id").toString();
		int check = Integer.parseInt(multi.getParameter("fileCheck"));
		System.out.println("check="+check);//1이면 첨부됨
		Map<String, String> fMap = null;
		if(check==1){
			UploadFile upload = new UploadFile();
			//서버에 파일을 업로드 한 뒤, 
			//오리지널 파일명, 시스템파일명을 리턴 후 맵에 저장
			fMap = upload.fileUp(multi);
		}
		Board board = new Board();
		board.setBtitle(title);
		board.setBcontents(contents);
		board.setBid(id);
		//board.setBfile(fMap.get("sysFileNamew"));
		mav = new ModelAndView();
		String view = null;
		String msg = null;
		if(bDao.boardWrite(board, fMap)==1){//insert에 성공하면
			view = "redirect:boardList";
			msg="파일쓰기 성공";//주소창에 확인
		}else{
			view="writeFrm";
			msg="파일쓰기 실패";
		}
		mav.addObject("msg",msg);
		mav.setViewName(view);
	}
}
