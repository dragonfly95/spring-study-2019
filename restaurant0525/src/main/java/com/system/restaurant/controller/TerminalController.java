package com.system.restaurant.controller;

import com.system.restaurant.domain.Menu;
import com.system.restaurant.domain.Terminal;
import com.system.restaurant.service.MenuService;
import com.system.restaurant.service.TerminalService;
import com.system.restaurant.service.TerminalServiceIF;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import java.util.ArrayList;

@Controller
public class TerminalController {

    @Autowired
    TerminalServiceIF terminalService;

    @Autowired
    MenuService menuService;
    /**
     * 메뉴 목록 jsp
     * @param model
     * @return
     */
    @RequestMapping(value = "/terminal.do", method = RequestMethod.GET)
    public String terminalList(ModelMap model) {
        ArrayList<Terminal> terminalList = terminalService.terminalList();
        model.addAttribute("terminalList", terminalList);
        ArrayList<Menu> menulist = menuService.menulist();
        model.addAttribute("menulist", menulist);


        return "terminalList";
    }

    /**
     * 메뉴 목록 api
     * @return
     */
    @RequestMapping(value = "/terminals")
    public ResponseEntity<ArrayList<Terminal>> terminalList2() {
        ArrayList<Terminal> terminalList = terminalService.terminalList();
        return new ResponseEntity<ArrayList<Terminal>>(terminalList, HttpStatus.ACCEPTED);
        /* ResponseEntity : 개발자가 직접 result 데이터와 HTTP 상태코드 제어하는 클래스.
           JASON View 를 설정하기 위한 방법으로 ResponseEntity 사용.
            @RestController 가 별도의 View 를 제공하지 않는 형태로 서비스 실행하기 때문에,
            Terminal 의 Object(;ArrayList)를 MAP 형태로 Serializer(String 으로 변환) 한 뒤
            Header 에 JSON 형태로 명시하여 JSON View 로 인식시킨다.
         */
    }


    /**
     * Terminal 에서의 order 등록
     */
    @RequestMapping(value = "/terminals", method = RequestMethod.POST)
    public ResponseEntity<Terminal> terminalPost (@RequestBody OrderRequestVO orderRequestVO)  {
        try {
            terminalService.post(orderRequestVO);
        } catch (Exception e) {
            return null;
        }
        return new ResponseEntity<>(new Terminal(), HttpStatus.ACCEPTED);
    }
}

