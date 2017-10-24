package com.cml.springcloud.api.filter;

import static com.netflix.zuul.context.RequestContext.getCurrentContext;
import static org.springframework.util.ReflectionUtils.rethrowRuntimeException;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.StreamUtils;

import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.http.ServletInputStreamWrapper;

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

	@Override
	public Object run() {
		try {
			RequestContext context = getCurrentContext();

			InputStream in = (InputStream) context.get("requestEntity");
			if (in == null) {
				in = context.getRequest().getInputStream();
			}
			String body = StreamUtils.copyToString(in, Charset.forName("UTF-8"));
			// body = "request body modified via set('requestEntity'): "+ body;
			String reqBody = body + "-appendUserSuffix";
			final byte[] reqBodyBytes = reqBody.getBytes();
			logger.info("accessToken:" + reqBody);

			// context.set("requestEntity", new
			// ByteArrayInputStream(body.getBytes("UTF-8")));
			context.setRequest(new HttpServletRequestWrapper(getCurrentContext().getRequest()) {
				@Override
				public ServletInputStream getInputStream() throws IOException {
					return new ServletInputStreamWrapper(reqBodyBytes);
				}

				@Override
				public int getContentLength() {
					return reqBodyBytes.length;
				}

				@Override
				public long getContentLengthLong() {
					return reqBodyBytes.length;
				}
			});
		} catch (IOException e) {
			rethrowRuntimeException(e);
		}
		return null;
	}

	@Override
	public boolean shouldFilter() {
		return StringUtils.equalsIgnoreCase(RequestContext.getCurrentContext().getRequest().getMethod(), "post");
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
