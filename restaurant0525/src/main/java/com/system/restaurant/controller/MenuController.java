package com.system.restaurant.controller;

import com.system.restaurant.domain.Menu;
import com.system.restaurant.service.MenuService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


import javax.annotation.Resource;
import java.util.ArrayList;

//아래의 Class 를 controller 로 모듈화 시키는 Tag
@Controller
public class MenuController {

    @Resource
            MenuService menuService;

    /**
     * 메뉴 목록 jsp
     * @param model
     * @return
     */
    @RequestMapping(value = "/menu.do", method = RequestMethod.GET)
    // '/menu' 라는 요청이 들어왔을 때 'menulist' method 를 실행한다
    //public String menulist(Model, model, HttpServletRequest request){
    public String menulist(ModelMap model) {
        /*Menu 클래스의 'ModelMap model' -> menulist() method 실행 후
         View 에서 활용되는 데이터 담고 있는 object*/

        /*menulist 라는 command object 를 생성, 이 object 에 getter 를 이용해서 service 에 전달하는 작업
        view, controller 등에서 파일에서 menulist.menu_name 등으로 객체의 data 를 가져와서 사용할 수 있다.
        Spring container 안에서 위와 같은 기능이 동작하도록 지원한다.
        */

        /* 개발자는 Model object 에 데이터를 담아서 DispatcherServlet 에 전달한다.
            DispatcherServlet 에 전달된 Model 데이터는 View 에서 가공되어 client 에게
            응답처리된다.
         */

        /*Menu 클래스 속성을 갖는 menulist object를 생성한 뒤
         getter로 받은 Menu class의 variable들을 menulist를 통해 menuService로 전달한다.
        */


        /*@ModelAttribute 를 사용하면 command object 의 이름을 변경할 수 있고, 이렇게 변경된 이름은
        view 에서 command object 를참조할 때 사용된다.
        public String menuRemove(@ModelAttribueAttribute('menu') Member member)
        */
        ArrayList<Menu> menulist = menuService.menulist(); //== (menu.getMenuName(),,,,);
        model.addAttribute("menulist", menulist);
        /*addAttribute 속성을 추가해주는 method.
          MenuService class 의 menulist 객체의 속성을 주입한다.
         */

        return "menulist";
        /* mvc-config.xml 의 코드에서 '.jsp' 와 return 값 "menulist" 가 합쳐져 응답해주는
        view 페이지 주소를 나타낸다. 'menulist.jsp'
        <bean class="org.springframework.web.servlet.view.InternalResourceViewResolver">
        <property name="prefix" value="/WEB-INF/view/"/>
        <property name="suffix" value=".jsp"/>
        </bean>
         */
    }

    /**
     * 메뉴 목록 api
     * @return
     */
    @RequestMapping(value = "/menus")
    public ResponseEntity<ArrayList<Menu>> menulist2() {
        ArrayList<Menu> menulist = menuService.menulist();
        return new ResponseEntity<ArrayList<Menu>>(menulist, HttpStatus.ACCEPTED);
    }

    // 메뉴 1건 조회 시
    //menus?id=1
    //menus/1
    @RequestMapping(value = "/menus/{menu_id}")
    public ResponseEntity<Menu> menuDetail(@PathVariable("menu_id") int menu_id) {
        Menu menu = menuService.findById(menu_id);
        return new ResponseEntity<>(menu, HttpStatus.ACCEPTED);
    }


    // 메뉴 등록
    // formdata submit => @ModelAttribute Menu menudata
    // json => @RequestBody Menu menudata 테이터의 흐름
    @RequestMapping(value = "/menus", method = RequestMethod.POST)
    public ResponseEntity<Menu> menuPost(@ModelAttribute Menu menudata) {
        int affected = menuService.post(menudata);
        return new ResponseEntity<>(new Menu(), HttpStatus.ACCEPTED);
    }


    // 수정
    @RequestMapping(value = "/menus/{menu_id}", method = RequestMethod.PUT)
    public ResponseEntity<Menu> menuPut(@RequestBody Menu menudata) {
        int affected = menuService.put(menudata);
        return new ResponseEntity<>(new Menu(), HttpStatus.ACCEPTED);
    }

    // 삭제
    @RequestMapping(value = "/menus/{menu_id}", method = RequestMethod.DELETE)
    public ResponseEntity<Menu> menuDelete(@PathVariable("menu_id") int menu_id) {
        int affected = menuService.delete(menu_id);
        return new ResponseEntity<>(new Menu(), HttpStatus.ACCEPTED);
    }
}
