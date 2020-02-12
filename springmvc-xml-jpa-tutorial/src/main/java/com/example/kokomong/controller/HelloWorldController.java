package com.example.kokomong.controller;


import com.example.kokomong.mapper.SampleMapper;
import com.example.kokomong.model.SampleTable;
import com.example.kokomong.repository.YourSampleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.List;

@Controller
public class HelloWorldController {

    private final static Logger log= LoggerFactory.getLogger(HelloWorldController.class);

    @Autowired
    private YourSampleRepository sampleRepository;


    @Autowired
    private SampleMapper sampleMapper;

    @RequestMapping("/hello.do")
    public String hello(){
        SampleTable sampleTable = new SampleTable();
        sampleTable.setCode("ttttttt");
        sampleTable.setSampleName(Math.random()+"@" + Math.random());
        sampleRepository.save(sampleTable);

        Iterable<SampleTable> all = sampleRepository.findAll();
        log.debug(String.valueOf(all));

        List<HashMap> hashMaps = sampleMapper.selectAll();
        log.debug(String.valueOf(hashMaps.size()));
        return "hello";
    }
}
