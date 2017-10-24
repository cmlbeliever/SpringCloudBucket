package com.cml.springcloud.api.filter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomZuulFilterConfiguration {

	@Bean
	public AccessTokenFilter accessTokenFilter() {
		return new AccessTokenFilter();
	}

	@Bean
	public ErrorFilter errorFilter() {
		return new ErrorFilter();
	}

	@Bean
	public GetRequestAccessTokenFilter getRequestAccessTokenFilter() {
		return new GetRequestAccessTokenFilter();
	}

	@Bean
	public ResponseFilter responseFilter() {
		return new ResponseFilter();
	}

}
