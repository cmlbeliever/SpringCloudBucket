package com.cml.springcloud.api.filter;

import org.springframework.stereotype.Component;

@Component
public class ErrorFilter extends AbstractZuulFilter {

	@Override
	public boolean shouldFilter() {
		logger.info("ErrorFilter==>shouldFilter");
		return false;
	}

	@Override
	public Object run() {
		logger.info("ErrorFilter==>run");
		System.out.println("ErrorFilter=sys=>run");
		return null;
	}

	@Override
	public String filterType() {
		logger.info("ErrorFilter==>filterType");
		return "error";
	}

	@Override
	public int filterOrder() {
		logger.info("ErrorFilter==>filterOrder");
		return FilterOrders.ORDER_HIGHLY;
	}

}
