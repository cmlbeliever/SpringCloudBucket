package com.cml.springcloud.api.filter;

/**
 * zuul 拦截器等级
 * 
 * @author cml
 *
 */
public interface FilterOrders {
	int ORDER_LOWEST = 200;
	int ORDER_MEDIUM = 100;
	int ORDER_HIGHLY = 100;
}
