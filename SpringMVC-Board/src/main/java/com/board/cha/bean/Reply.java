package com.board.cha.bean;

import java.sql.Timestamp;

public class Reply {
	private int r_bnum;//원글 번호
	private int r_num;//댓글 번호
	private String r_contents;//내용
	private Timestamp r_date; 
	private String r_id;
	public int getR_bnum() {
		return r_bnum;
	}
	public void setR_bnum(int r_bnum) {
		this.r_bnum = r_bnum;
	}
	public int getR_num() {
		return r_num;
	}
	public void setR_num(int r_num) {
		this.r_num = r_num;
	}
	public String getR_contents() {
		return r_contents;
	}
	public void setR_contents(String r_contents) {
		this.r_contents = r_contents;
	}
	public Timestamp getR_date() {
		return r_date;
	}
	public void setR_date(Timestamp r_date) {
		this.r_date = r_date;
	}
	public String getR_id() {
		return r_id;
	}
	public void setR_id(String r_id) {
		this.r_id = r_id;
	}
	
}
