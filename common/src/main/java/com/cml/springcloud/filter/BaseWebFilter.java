package com.cml.springcloud.filter;

import com.cml.springcloud.log.LogPointer;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public abstract class BaseWebFilter implements Filter {
    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        DefaultRequestWrapper defaultRequestWrapper = new DefaultRequestWrapper((HttpServletRequest) request);
        DefaultResponseWrapper defaultResponseWrapper = new DefaultResponseWrapper((HttpServletResponse) response);

        long startTime = System.currentTimeMillis();
        LogPointer.init();

        RequestLog requestLog = new RequestLog();
        requestLog.setRemoteIp(request.getRemoteAddr());
        try {
            chain.doFilter(defaultRequestWrapper, defaultResponseWrapper);
            requestLog.setSuccessful(true);
        } catch (Exception e) {
            requestLog.setSuccessful(false);
            requestLog.setError(e);
        } finally {
            requestLog.setRequestBody(defaultRequestWrapper.getRequestBody());
            requestLog.setRequestBodyLength(defaultRequestWrapper.getContentLength());
            requestLog.setUrl(defaultRequestWrapper.getRequestURL().toString());
            requestLog.setName(defaultRequestWrapper.getRequestURI());
            requestLog.setInterval(System.currentTimeMillis() - startTime);

            String responseBody = defaultResponseWrapper.getResponseBody();
            requestLog.setResponse(responseBody);
            requestLog.setRequestBodyLength(StringUtils.length(responseBody));

            onRequestFinished(requestLog);

            LogPointer.clear();
        }

    }

    protected abstract void onRequestFinished(RequestLog requestLog);


    @Override
    public void destroy() {

    }
}
