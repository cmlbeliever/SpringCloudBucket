package com.cml.springcloud;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ServerTest implements InitializingBean {

	@Value("${test.application:none}")
	private String testValue;

	@Value("${server.port}")
	private String port;

	@Override
	public void afterPropertiesSet() throws Exception {
		System.out.println("==================>" + testValue + "," + port);
	}

}
