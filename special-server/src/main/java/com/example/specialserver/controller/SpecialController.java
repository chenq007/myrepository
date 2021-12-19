package com.example.specialserver.controller;

import org.apache.catalina.connector.Response;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("special")
public class SpecialController {

    @GetMapping("/query/{specialId}")
    public Map querySpecialById(@PathVariable(value = "specialId") String specialId){
            Map map=new HashMap();
                map.put(specialId,"广州车展专题");
        return  map;
    }

}
