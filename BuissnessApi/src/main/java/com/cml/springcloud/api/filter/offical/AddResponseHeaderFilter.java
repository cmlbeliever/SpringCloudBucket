package com.cml.springcloud.api.filter.offical;

import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

/**
 * @author Spencer Gibb
 */
public class AddResponseHeaderFilter extends ZuulFilter {
	public String filterType() {
		return "post";
	}

	public int filterOrder() {
		return 999;
	}

	public boolean shouldFilter() {
		return true;
	}

	public Object run() {
		RequestContext context = RequestContext.getCurrentContext();
		HttpServletResponse servletResponse = context.getResponse();
		servletResponse.addHeader("X-Foo",
				UUID.randomUUID().toString());
		return null;
	}
}
