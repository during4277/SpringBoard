package com.board.cha.dao;

import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.board.cha.bean.Member;

@Repository//@Component
public class MemberDao {
	@Autowired
	private SqlSessionTemplate sqlSession; //servlet-context.xml에 작성한 id로 필드명으로 한다.
	public int memberInsert(Member mb) {
		int result = sqlSession.insert("member.memberInsert", mb);
		return result;
	}
	//마이바티스 설정
	public int getLoginResult(Member mb) {
		//마이바티스는 매개값으로 2개까지만 넘길 수 있다.
		return sqlSession.selectOne("member.getLoginResult",mb);
	}
	public Member getMemberInfo(String m_id) {
		return sqlSession.selectOne("member.getMemberInfo",m_id);
	}
	public String getSecurityPw(String m_id) {
		return sqlSession.selectOne("member.getSecurityPw",m_id);
	}
	
	public int hashMapTest(Map<String, String> hmap) {
		return sqlSession.selectOne("member.hashMapTest", hmap);
	}
}
