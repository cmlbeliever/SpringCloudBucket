package com.cml.springcloud.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: cml
 * @Date: 2019-01-31 13:58
 * @Description:
 */
@RestController
@RequestMapping("/user2")
public class UserGatewayController {

    @GetMapping("/testGet")
    public String test(@RequestParam() String extraParameter, @RequestParam(required = false, defaultValue = "none") String name) {
        return "welcome to user service name:" + name + ",extraParameter:" + extraParameter;
    }
}
