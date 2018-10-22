package com.cml.springcloud.log;

public class RequestLog {
    private String requestBody;
    private int requestBodyLength;
    private String response;
    private String responseLength;
    private boolean successful;
    private String url;
    private Long interval;
    private String name;
    private Throwable error;
    private transient String errorMsg;
    private String remoteIp;


    public String getError() {
        if (null != error && null == errorMsg) {
            StringBuffer sb = new StringBuffer();
            StackTraceElement[] stackTraces = error.getStackTrace();
            for (StackTraceElement stackTraceElement : stackTraces) {
                sb.append(stackTraceElement).append("\r\n");
            }
            errorMsg = sb.toString();
        }
        return errorMsg;
    }

    public String getRemoteIp() {
        return remoteIp;
    }

    public void setRemoteIp(String remoteIp) {
        this.remoteIp = remoteIp;
    }

    public void setError(Throwable error) {
        this.error = error;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getInterval() {
        return interval;
    }

    public void setInterval(Long interval) {
        this.interval = interval;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getRequestBody() {
        return requestBody;
    }

    public void setRequestBody(String requestBody) {
        this.requestBody = requestBody;
    }

    public int getRequestBodyLength() {
        return requestBodyLength;
    }

    public void setRequestBodyLength(int requestBodyLength) {
        this.requestBodyLength = requestBodyLength;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getResponseLength() {
        return responseLength;
    }

    public void setResponseLength(String responseLength) {
        this.responseLength = responseLength;
    }

    public boolean isSuccessful() {
        return successful;
    }

    public void setSuccessful(boolean successful) {
        this.successful = successful;
    }

}
