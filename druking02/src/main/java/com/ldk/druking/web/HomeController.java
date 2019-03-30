package com.ldk.druking.web;

import com.ldk.druking.service.DBService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Controller
public class HomeController {

	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@Autowired
	DBService dbService;

	/**
	 * 로그인 화면 jsp
	 */
	@RequestMapping(value="/login", method = RequestMethod.GET)  
	public String login(ModelMap model) {
		return "login"; 
	}


  /**
   * 로그인 처리
   * spring-security.xml 설정 처리해줌.
   * service/impl/UserServiceImpl.java
   */



	/**
	 * 회원가입 화면 jsp
	 */
	@RequestMapping(value="/register", method = RequestMethod.POST)  
	public String register(ModelMap model) { 
		return "register"; 
	}


  /**
   * 회원가입 처리
   * @param data
   * @return
   */
  @RequestMapping(value = "user/registerUser", method = RequestMethod.POST)
  public ResponseEntity<String> registerUser(@RequestParam Map<String, String> data) {

    String userId = data.get("user_id");
    String userName = data.get("user_name");
    String birth = data.get("user_birth");
    String password = data.get("password");

    if (dbService.checkAlreadyUserId(userId)) {
      return new ResponseEntity<String>("ALREADY", HttpStatus.OK);
    }


    dbService.saveUser(userId, userName, birth, password);

    ResponseEntity<String> entity = new ResponseEntity<String>("OK", HttpStatus.OK);
    return entity;
  }



  /**
	 * 로그인 에러 화면
	 */
	@RequestMapping(value="/loginError", method = RequestMethod.GET)  
	public String loginError(ModelMap model) {  
		model.addAttribute("error", "true");  
		return "login";
	}


	/**
	 * 접근 오류 메시지
	 */
	private ModelAndView accessDeniedView(Principal user) {
		ModelAndView model = new ModelAndView();  
		if (user != null) {  
			model.addObject("msg", "Hi " + user.getName() + ", You can not access this page!");  
		} else {  
			model.addObject("msg", "You can not access this page!");  
		}  

		model.setViewName("403");  
		return model;
	}



	@RequestMapping(value = "/403", method = RequestMethod.GET)  
	public ModelAndView accesssDenied(Principal user) {  
		return this.accessDeniedView(user);
	}


  /**
   * home.jsp
   */
  @RequestMapping(value = "/", method = RequestMethod.GET)
  public ModelAndView home(Locale locale, Principal principal) {
    Authentication auth =  SecurityContextHolder.getContext().getAuthentication();
    String loggedInUserName = auth.getName();
    List<GrantedAuthority> as = (List<GrantedAuthority>)auth.getAuthorities();
    GrantedAuthority grantedAuthority = as.get(0);

    ModelAndView mav = new ModelAndView("home");
    mav.addObject("userName", loggedInUserName);
    mav.addObject("roles", grantedAuthority.getAuthority());

    return mav;
  }


  /**
   * viewteam.jsp
   */
  @RequestMapping(value = "/viewteam", method = RequestMethod.GET)
  public ModelAndView viewteam(Locale locale, Principal user) {
    Authentication auth =  SecurityContextHolder.getContext().getAuthentication();
    String loggedInUserName = auth.getName();
    List<GrantedAuthority> as = (List<GrantedAuthority>)auth.getAuthorities();
    GrantedAuthority grantedAuthority = as.get(0);

    if (!grantedAuthority.toString().equals("ROLE_ADMIN") &&
        !grantedAuthority.toString().equals("ROLE_INPUT")) {
      return this.accessDeniedView(user);
    }

    ModelAndView mav = new ModelAndView("viewteam");
    mav.addObject("userName", loggedInUserName);
    mav.addObject("roles", grantedAuthority.getAuthority());

    return mav;
  }


}
