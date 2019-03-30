package com.oraclejava.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.oraclejava.model.Member;
import com.oraclejava.service.MemberService;

@Controller
public class MemberController {
			
		@Autowired
		private MemberService memberService;
		
		@RequestMapping("/findAllMember") 
		public String findAll(Model model) {
			List<Member> memList = memberService.findAll();
			model.addAttribute("memList", memList);
			return "showAllMember";			
			
		}
		
		@RequestMapping(value="/register", method=RequestMethod.GET)
		public String insert(Model model) {
			Member member = new Member();
			model.addAttribute("member",member);
			return "registerform";
		}
		
		@RequestMapping(value="/register", method=RequestMethod.POST)
		public String insertform(@ModelAttribute Member member, Model model) {
			memberService.insertMember(member);
			return "redirect:/findAllMember";
		}
		
		
	}
