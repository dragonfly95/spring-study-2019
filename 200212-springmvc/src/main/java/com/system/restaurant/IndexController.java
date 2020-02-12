package com.system.restaurant;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

    @RequestMapping(value = "/menulist.do")
    public String index() {
        return "menulist";
    }
}