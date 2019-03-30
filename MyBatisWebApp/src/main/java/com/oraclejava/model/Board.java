package com.oraclejava.model;

import java.util.Date;

public class Board {

	// 아이디
	private int id;

	// 글제목
	private String title;

	// 내용
	private String content;

	// 등록일자
	private Date regdate;

	// 조회횟수
	private int readcount;


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getRegdate() {
		return regdate;
	}

	public void setRegdate(Date regdate) {
		this.regdate = regdate;
	}

	public int getReadcount() {
		return readcount;
	}

	public void setReadcount(int readcount) {
		this.readcount = readcount;
	}

	@Override
	public String toString() {
		return "Board{" +
				"id=" + id +
				", title='" + title + '\'' +
				", content='" + content + '\'' +
				", regdate=" + regdate +
				", readcount=" + readcount +
				'}';
	}
}
