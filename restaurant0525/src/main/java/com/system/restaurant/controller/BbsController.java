package com.system.restaurant.controller;


import com.system.restaurant.domain.Bbs;


import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;

@Controller
public class BbsController {

//    private static final Logger logger = LogFactory.getLogger(BbsController.class);

    @RequestMapping(value = "bbs", method = RequestMethod.POST)
    @ResponseBody
    public Bbs post(@RequestBody Bbs bbs) {
        return bbs;
    }

    @RequestMapping(value = "bbs2", method = RequestMethod.POST)
    @ResponseBody
    public Bbs post(@ModelAttribute Bbs bbs, HttpServletRequest request) throws IOException {
        MultipartFile file1 = bbs.getFile1();
        if(!file1.getOriginalFilename().isEmpty()) {
            file1.transferTo(new File("D:\\Downloads\\htmlcss", file1.getOriginalFilename()));
        }
        return bbs;
    }
}
