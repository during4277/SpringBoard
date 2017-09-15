package com.board.cha.dao;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.board.cha.bean.Board;
import com.board.cha.bean.Reply;

@Repository //@Component 가독성
public class BoardDao {
	@Autowired
	private SqlSessionTemplate sqlSession;
	public List<Board> getBoardList(int pageNum){
		return sqlSession.selectList("board.getBoardList", pageNum);
	}
	public int getBoardCount() {
		return sqlSession.selectOne("board.getBoardCount");
	}
	public Board getContents(int bnum) {
		return sqlSession.selectOne("board.getContents",bnum);
	}
	public List<Reply> getReplyList(int bnum) {
		return sqlSession.selectList("board.getReplyList", bnum);
	}
	public int getReplyMaxNum() {
		return sqlSession.selectOne("board.getReplyMaxNum");
	}
	public int replyInsert(Reply reply) {
		return sqlSession.insert("board.replyInsert", reply);
	}
	public int getReplyCount() {
		return sqlSession.selectOne("board.getReplyCount");
	}
	//service로 옮기기
	//@Transactional//모든 메소드 호출이 성공하면 자동으로 commit, 아니면 rollback 된다
	/*public int boardDelete(int bnum) {
		int r=replyDelete(bnum+1);
		int b=articleDelete(bnum);
		if(r!=0 && b!=0){ //댓글 및 원글 삭제 성공시
			return 1;
		}
		return 0;
	}*/
	public int replyDelete(int bnum) {
		return sqlSession.delete("board.replyDelete", bnum);
	}
	public int articleDelete(int bnum) {
		return sqlSession.delete("board.articleDelete", bnum);
	}
	@Transactional
	public int boardWrite(Board board, Map<String, String> fMap) {
		//게시판테이블에 글을 입력
		int b= boardInsert(board);
		//파일테이블에 파일을 입력
		int bnum = getBoardMaxNum();
		fMap.put("bnum",String.valueOf(bnum));
		int f = fileInsert(fMap);
		if(b!=0 && f!=0){
			return 1; //tx 성공시
		}
		return 0; //tx 실패시
	}
	private int boardInsert(Board board) {
		return sqlSession.insert("board.boardInsert", board);
	}
	private int getBoardMaxNum() {
		return sqlSession.selectOne("board.getBoardMaxNum");
	}
	private int fileInsert(Map<String, String> fMap) {
		return sqlSession.insert("board.fileInsert", fMap);
	}
}