package com.board.cha.service;

import org.springframework.web.servlet.ModelAndView;

import com.board.cha.bean.Member;

public interface Action {
	public ModelAndView execute(int cmd); //BoardMM 구현
	public ModelAndView execute(Member mb, int cmd); //MemberMM 구현
}
