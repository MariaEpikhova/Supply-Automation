package com.framework.models;

import java.util.Map;

public class Response {
    private String action;
    private Object data;
    private String error;

    public Response() {

    }

    public Response(String action, Object data, String error) {
        this.action = action;
        this.data = data;
        this.error = error;
    }

    public Response(String action, Object data) {
        this.action = action;
        this.data = data;
    }

    public Object getData() {
        return data;
    }

    public String getAction() {
        return action;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

}
