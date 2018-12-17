package com.cml.springcloud.api.filter;

import static com.netflix.zuul.context.RequestContext.getCurrentContext;
import static org.springframework.util.ReflectionUtils.rethrowRuntimeException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;

import com.netflix.zuul.context.RequestContext;

/**
 * zuul 错误处理，可以通过设置ErrorHandler自定义返回错误信息和错误码
 * 
 * @author cml
 *
 */
public class ErrorFilter extends AbstractZuulFilter {

	public static final String KEY_ERROR = "hasError";

	private ResponseHandler errorHandler;

	@Override
	public boolean shouldFilter() {
		return true;
	}

	@Override
	public Object run() {

		try {
			RequestContext context = getCurrentContext();
			context.getResponse().setCharacterEncoding("UTF-8");

			logger.error("error", context.getThrowable());

			if (null != errorHandler) {
				context.setResponseStatusCode(errorHandler.getResponseCode());
				String body = errorHandler.getResponseBody(null, context.getThrowable());
				context.setResponseBody(body);
				// context.setResponseDataStream(new
				// ByteArrayInputStream(errorHandler.getResponseBody(context.getThrowable()).getBytes("UTF-8")));
			} else {
				context.setResponseStatusCode(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				context.setResponseBody(context.getThrowable().getMessage());
			}
			context.remove("throwable");
			context.put(KEY_ERROR, true);
		} catch (Exception e) {
			rethrowRuntimeException(e);
		}
		return null;
	}

	@Override
	public String filterType() {
		return "error";
	}

	@Override
	public int filterOrder() {
		return FilterConstants.SEND_ERROR_FILTER_ORDER - 1;
	}

	public ResponseHandler getErrorHandler() {
		return errorHandler;
	}

	public void setErrorHandler(ResponseHandler errorHandler) {
		this.errorHandler = errorHandler;
	}

}
