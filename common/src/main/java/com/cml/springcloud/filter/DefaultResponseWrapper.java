package com.cml.springcloud.filter;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

public class DefaultResponseWrapper extends HttpServletResponseWrapper {
    private ByteArrayOutputStream bytes = new ByteArrayOutputStream();

    public DefaultResponseWrapper(HttpServletResponse response) {
        super(response);
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        return new MyServletOutputStream(super.getOutputStream()); // 将数据写到 byte 中
    }

    public String getResponseBody() throws UnsupportedEncodingException {
        return bytes.toString("UTF-8");
    }

    class MyServletOutputStream extends ServletOutputStream {
        private ServletOutputStream ostream;

        public MyServletOutputStream(ServletOutputStream ostream) {
            this.ostream = ostream;
        }

        @Override
        public void write(int b) throws IOException {
            ostream.write(b); // 将数据写到 stream　中
            bytes.write(b);//复制输出的消息
        }

        @Override
        public boolean isReady() {
            return true;
        }

        @Override
        public void setWriteListener(WriteListener listener) {

        }
    }
}
