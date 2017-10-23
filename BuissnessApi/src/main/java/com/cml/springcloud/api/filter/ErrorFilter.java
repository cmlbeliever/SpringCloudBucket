package com.cml.springcloud.api.filter;

import org.springframework.stereotype.Component;

@Component
public class ErrorFilter extends AbstractZuulFilter {

	@Override
	public boolean shouldFilter() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Object run() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String filterType() {
		return "error";
	}

	@Override
	public int filterOrder() {
		return FilterOrders.ORDER_HIGHLY;
	}

}
