package com.cml.springcloud.api.filter;

import static com.netflix.zuul.context.RequestContext.getCurrentContext;
import static org.springframework.util.ReflectionUtils.rethrowRuntimeException;

import java.io.InputStream;
import java.nio.charset.Charset;

import org.springframework.cloud.netflix.zuul.filters.post.SendResponseFilter;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.util.StreamUtils;

import com.netflix.zuul.context.RequestContext;

public class ErrorFilter extends AbstractZuulFilter {

	@Override
	public boolean shouldFilter() {
		logger.info("ErrorFilter111==>shouldFilter");
		return true;
	}

	@Override
	public Object run() {

		try {
			RequestContext context = getCurrentContext();
			logger.info("ErrorFilter111==>run==>" + context.getThrowable().getMessage());
			InputStream stream = context.getResponseDataStream();
			String body = StreamUtils.copyToString(stream, Charset.forName("UTF-8"));
			// context.setSendZuulResponse(false);
			context.setResponseStatusCode(400);
			context.setResponseBody("some error occured!" + body);
			context.remove("throwable");
			context.put("isError", true);
			// context.getResponse().getWriter().write("xxxxxxxxxxxxxxxx");

			// context.put("error.status_code", 200);
			// context.put("error.error", "custom error");
			// context.put("error.message", "custom message");
			// context.put("error.exception", new
			// IllegalStateException("xxxx"));
			// context.setThrowable(new ZuulException("error message",
			// HttpServletResponse.SC_OK, "error cause!!!"));

			// SendResponseFilter
			// ZuulServerAutoConfiguration
			// SendErrorFilter
			// ZuulServletFilter
		} catch (Exception e) {
			rethrowRuntimeException(e);
		}
		return null;
	}

	@Override
	public String filterType() {
		logger.info("ErrorFilter111==>filterType");
		return "error";
	}

	@Override
	public int filterOrder() {
		logger.info("ErrorFilter==>filterOrder");
		return FilterConstants.SEND_ERROR_FILTER_ORDER - 1;
	}

}
