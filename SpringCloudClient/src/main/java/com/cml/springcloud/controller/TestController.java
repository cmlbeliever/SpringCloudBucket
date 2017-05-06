package com.cml.springcloud.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

	@Value("${mysqldb.datasource.username:unknown}")
	private String dbUserName;

	@RequestMapping("/")
	@ResponseBody
	public String home() {
		return "Hello World!dbUserName:" + dbUserName;
	}

}
