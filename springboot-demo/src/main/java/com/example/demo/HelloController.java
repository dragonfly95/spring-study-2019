package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class HelloController {

	@Autowired
	private PersonMapper personMapper;

	@RequestMapping(value = "/")
	public String message(ModelMap modelMap) {
		Person person = new Person("yu", "yong", "stree", "city", "state", "country");
		personMapper.insertUser(person);

		List<Person> personlist = personMapper.selectAllPerson();
		modelMap.put("list", personlist);

		return "hello";
	}

	@RequestMapping(value = "/list")
	public ResponseEntity<List<Person>> list() {
		return new ResponseEntity<>(personMapper.selectAllPerson(), HttpStatus.OK);
	}


	@PostMapping(value = "/person")
	public ResponseEntity<ResponseVO> insertPerson(@RequestBody Person person) {
		personMapper.insertUser(person);
		return new ResponseEntity<ResponseVO>(new ResponseVO("OK"), HttpStatus.OK);
	}
}
