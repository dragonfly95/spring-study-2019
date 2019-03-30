package com.oraclejava.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oraclejava.mapper.MemberMapper;
import com.oraclejava.model.Member;

@Service                                // 트랜젝션 때문에 만들어줘야 한다
public class MemberService {
	
	@Autowired                          // Mapper 인터페이스를 인식하게 하려고
	private MemberMapper mapper;
	
	
	public List<Member> findAll() {
		return mapper.selectAll();
	}
	
	// 회원 가입
	@Transactional
	public void insertMember(Member member) {
		mapper.insert(member);
		
	}

}
