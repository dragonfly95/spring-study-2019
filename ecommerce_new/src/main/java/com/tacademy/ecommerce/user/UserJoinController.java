package com.tacademy.ecommerce.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tacademy.ecommerce.domain.User;

@Controller
@RequestMapping("/user/join")
public class UserJoinController {

  @RequestMapping(method = RequestMethod.GET)
  public String join(User user) {
	  return "/user/join-form";
  }

}
