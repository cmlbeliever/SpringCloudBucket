package com.cml.springcloud.api.filter;

import static com.netflix.zuul.context.RequestContext.getCurrentContext;
import static org.springframework.util.ReflectionUtils.rethrowRuntimeException;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StreamUtils;

import com.cml.springcloud.api.AuthApi;
import com.cml.springcloud.model.result.AuthResult;
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

	private static final String PARAM_TOKEN = "token";

	@Value("${system.config.accessTokenFilter.ignore}")
	private String ignoreUrl;

	@Autowired
	private AuthApi authApi;

	private ResponseHandler responseHandler;

	@Override
	public Object run() {
		try {
			RequestContext context = getCurrentContext();

			InputStream in = (InputStream) context.get("requestEntity");
			if (in == null) {
				in = context.getRequest().getInputStream();
			}

			String token = context.getRequest().getParameter(PARAM_TOKEN);

			logger.info("accessToken:" + token);
			logger.info("params:" + context.getRequestQueryParams());
			logger.info("contentLength:" + context.getRequest().getContentLength());
			logger.info("contentType:" + context.getRequest().getContentType());

			// token 为空不处理
			if (StringUtils.isNotBlank(token)) {
				AuthResult authResult = authApi.parseToken(token);
				// 校验成功
				if (authResult.isSuccess()) {
					String body = StreamUtils.copyToString(in, Charset.forName("UTF-8"));
					logger.info("body:" + body);
					body = StringUtils.replace(body, PARAM_TOKEN + "=" + token, PARAM_TOKEN + "=" + authResult.getToken());
					logger.info("转换后的body：" + body);
					// context.set("requestEntity", new
					// ByteArrayInputStream(body.getBytes("UTF-8")));
					final byte[] reqBodyBytes = body.getBytes();
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
					return null;
				}
			}
			if (responseHandler != null) {
				context.getResponse().setCharacterEncoding("UTF-8");
				context.setResponseStatusCode(responseHandler.getResponseCode());
				context.setResponseBody(responseHandler.getResponseBody(null, null));
			}

			context.setSendZuulResponse(false);

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
		logger.info("" + ignoreUrl + "," + req.getRequestURI().toString());
		return StringUtils.equalsIgnoreCase(req.getMethod(), "post") && !StringUtils.contains(ignoreUrl, req.getRequestURI().toString());
	}

	@Override
	public int filterOrder() {
		return 0;
	}

	public ResponseHandler getResponseHandler() {
		return responseHandler;
	}

	public void setResponseHandler(ResponseHandler responseHandler) {
		this.responseHandler = responseHandler;
	}

	@Override
	public String filterType() {
		return "pre";
	}

}
