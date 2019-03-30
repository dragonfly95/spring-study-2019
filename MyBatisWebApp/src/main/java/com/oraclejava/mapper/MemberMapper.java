package com.oraclejava.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import com.oraclejava.model.Member;



public interface MemberMapper {	
	
	// 모든 회원 정보를 불러오기
	@Select("select id, password password, name, lev, regdate from TBL_MEMBER")
	public List<Member> selectAll();
	
	//회원 추가
	@Insert("insert into tbl_member(id, password, name) values(#{id}, #{password}, #{name})")
	public void insert(Member member);

}
