package com.cml.springcloud.api.filter;

import static com.netflix.zuul.context.RequestContext.getCurrentContext;
import static org.springframework.util.ReflectionUtils.rethrowRuntimeException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;

import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

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
			// InputStream stream = context.getResponseDataStream();
			// String body = StreamUtils.copyToString(stream,
			// Charset.forName("UTF-8"));
			// context.setSendZuulResponse(false);
			// context.setResponseStatusCode(200);
			// context.setResponseBody("some error occured!");

			// context.put("error.status_code", 200);
			// context.put("error.error", "custom error");
			// context.put("error.message", "custom message");
			// context.put("error.exception", new
			// IllegalStateException("xxxx"));
			context.setThrowable(new ZuulException("error message", HttpServletResponse.SC_OK, "error cause!!!"));

			// SendErrorFilter
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
