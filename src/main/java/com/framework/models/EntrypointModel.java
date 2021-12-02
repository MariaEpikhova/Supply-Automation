package com.framework.models;

import com.framework.modules.OnEntrypointCall;

public class EntrypointModel {
    private final String action;
    private final Class<?> requestDataType;
    private final OnEntrypointCall onCall;
    private final boolean secured;

    public EntrypointModel(String action, Class<?> requestDataType, boolean secured, OnEntrypointCall callable) {
        this.action = action;
        this.onCall = callable;
        this.requestDataType = requestDataType;
        this.secured = secured;
    }

    public OnEntrypointCall getOnCall() {
        return onCall;
    }

    public String getAction() {
        return action;
    }

    public Class<?> getRequestDataType() {
        return requestDataType;
    }

    public boolean isSecured() {
        return secured;
    }

}
