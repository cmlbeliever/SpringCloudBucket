package com.cml.springcloud.api.filter;

import static com.netflix.zuul.context.RequestContext.getCurrentContext;
import static org.springframework.util.ReflectionUtils.rethrowRuntimeException;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StreamUtils;

import com.netflix.zuul.context.RequestContext;

/**
 * 登录拦截器,未登录的用户直接返回未登录数据。
 * <p>
 * 只拦截post方式的请求
 * </p>
 * 
 * @author cml
 *
 */
public class AccessTokenFilter extends AbstractZuulFilter {

	@Value("system.config.accessTokenFilter.ignore")
	private String ignoreUrl;

	@Override
	public Object run() {
		try {
			RequestContext context = getCurrentContext();

			InputStream in = (InputStream) context.get("requestEntity");
			if (in == null) {
				in = context.getRequest().getInputStream();
			}
			String body = StreamUtils.copyToString(in, Charset.forName("UTF-8"));

			String token = context.getRequest().getParameter("accessToken");

			logger.info("accessToken:" + token);
			logger.info("requestBody:" + body);
			logger.info("params:" + context.getRequestQueryParams());
			logger.info("contentLength:" + context.getRequest().getContentLength());
			logger.info("contentType:" + context.getRequest().getContentType());

			// body = "request body modified via set('requestEntity'): "+ body;
			// String reqBody = body + "-appendUserSuffix";
			// final byte[] reqBodyBytes = reqBody.getBytes();
			// logger.info("accessToken:" + reqBody);
			//
			// // context.set("requestEntity", new
			// // ByteArrayInputStream(body.getBytes("UTF-8")));
			// context.setRequest(new
			// HttpServletRequestWrapper(getCurrentContext().getRequest()) {
			// @Override
			// public ServletInputStream getInputStream() throws IOException {
			// return new ServletInputStreamWrapper(reqBodyBytes);
			// }
			//
			// @Override
			// public int getContentLength() {
			// return reqBodyBytes.length;
			// }
			//
			// @Override
			// public long getContentLengthLong() {
			// return reqBodyBytes.length;
			// }
			// });
		} catch (IOException e) {
			rethrowRuntimeException(e);
		}
		return null;
	}

	@Override
	public boolean shouldFilter() {
		HttpServletRequest req = RequestContext.getCurrentContext().getRequest();
		return StringUtils.equalsIgnoreCase(req.getMethod(), "post") && !StringUtils.contains(ignoreUrl, req.getRequestURI().toString());
	}

	@Override
	public int filterOrder() {
		return 0;
	}

	@Override
	public String filterType() {
		return "pre";
	}

}
