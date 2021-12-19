package com.example.content.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("content")
public class ContentController {

    @GetMapping("/query/{specialId}")
    public Map querySpecialById(@PathVariable(value = "specialId") String specialId){
        Map map=new HashMap();
        map.put(specialId,"文章内容");
        return  map;
    }
}
