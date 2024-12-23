package com.mashibing.servicepassengeruser.apipassenger.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
* @author 马佳健
* @date 2024/12/16
* @description
*/
@RestController
public class TestController {

    @GetMapping("/test")
    public String test(){

        return "test api passenger";
    }
}
