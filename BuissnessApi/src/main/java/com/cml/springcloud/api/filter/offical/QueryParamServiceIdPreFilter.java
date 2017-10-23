package com.cml.springcloud.api.filter.offical;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

import static com.netflix.zuul.context.RequestContext.getCurrentContext;

/**
 * @author Spencer Gibb
 */
@Component
public class QueryParamServiceIdPreFilter extends ZuulFilter {

	public int filterOrder() {
		// run before PreDecorationFilter
		return 5 - 1;
	}

	public String filterType() {
		return "pre";
	}

	@Override
	public boolean shouldFilter() {
		RequestContext ctx = getCurrentContext();
		return ctx.getRequest().getParameter("service") != null;
	}

	public Object run() {
		RequestContext ctx = getCurrentContext();
		HttpServletRequest request = ctx.getRequest();
		// put the serviceId in `RequestContext`
		ctx.put("serviceId", request.getParameter("service"));
		return null;
	}
}
