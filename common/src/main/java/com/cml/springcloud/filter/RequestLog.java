package com.cml.springcloud.filter;

public class RequestLog {
    private String requestBody;
    private int requestBodyLength;
    private String response;
    private String responseLength;
    private boolean successful;
    private String errorMessage;

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

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
