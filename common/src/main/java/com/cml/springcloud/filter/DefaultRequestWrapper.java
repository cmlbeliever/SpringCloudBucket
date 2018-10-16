package com.cml.springcloud.filter;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.*;

public class DefaultRequestWrapper extends HttpServletRequestWrapper {

    private String requestBody;
    private byte[] requestBodyByte;
    private ServletRequest request;

    /**
     * Constructs a request object wrapping the given request.
     *
     * @param request The request to wrap
     * @throws IllegalArgumentException if the request is null
     */
    public DefaultRequestWrapper(HttpServletRequest request) throws IOException {
        super(request);
        this.request = request;
        requestBodyByte = IOUtils.toByteArray(request.getInputStream());
        this.requestBody = new String(requestBodyByte, request.getCharacterEncoding());
    }


    public String getRequestBody() throws IOException {
        return requestBody;
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(this.requestBodyByte);
        return new ServletInputStream() {
            public boolean isFinished() {
                return byteArrayInputStream.available() == 0;
            }

            public boolean isReady() {
                return byteArrayInputStream.available() > 0;
            }

            public void setReadListener(ReadListener readListener) {
            }

            public int read() throws IOException {
                return byteArrayInputStream.read();
            }
        };
    }

    @Override
    public int getContentLength() {
        return StringUtils.length(requestBody);
    }

    @Override
    public long getContentLengthLong() {
        return StringUtils.length(requestBody);
    }

    @Override
    public ServletRequest getRequest() {
        return super.getRequest();
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return IOUtils.toBufferedReader(new StringReader(requestBody));
    }

}
