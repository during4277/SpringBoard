package com.board.cha.bean;

import java.sql.Timestamp;

public class Board {
	private int bnum; //sequence
	private String btitle;
	private String bcontents;
	private String bid;
	private String mname;
	private Timestamp bdate; //시 분 초까지 표현가능
	private int bview;//게시물을 조회를 할 때 아이피 검사를 한 후 한번만 조회수를 올리게 한다. 그리고 24시간에 한번 가능 
	//private int page;
	
	/*public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}*/
	public int getBnum() {
		return bnum;
	}
	public void setBnum(int bnum) {
		this.bnum = bnum;
	}
	public String getBtitle() {
		return btitle;
	}
	public void setBtitle(String btitle) {
		this.btitle = btitle;
	}
	public String getBcontents() {
		return bcontents;
	}
	public void setBcontents(String bcontents) {
		this.bcontents = bcontents;
	}
	public String getBid() {
		return bid;
	}
	public void setBid(String bid) {
		this.bid = bid;
	}
	
	public String getMname() {
		return mname;
	}
	public void setMname(String mname) {
		this.mname = mname;
	}
	public Timestamp getBdate() {
		return bdate;
	}
	public void setBdate(Timestamp bdate) {
		this.bdate = bdate;
	}
	public int getBview() {
		return bview;
	}
	public void setBview(int bview) {
		this.bview = bview;
	}
		
}