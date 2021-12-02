package com.framework.models;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Request<T extends Object> {
    private String action;
    private T data;
    private String token;

    public Request(String action, T data, String token) {
        this.action = action;
        this.data = data;
        this.token = token;
    }

    public T getData() {
        return data;
    }

    public String getAction() {
        return action;
    }

    public static <T> Request<T> fromJson(String json, Class<T> dataType) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();

        JsonNode jsonNode = objectMapper.readTree(json);
        String action = jsonNode.get("action").asText();
        T data = objectMapper.treeToValue(jsonNode.get("data"), dataType);
        String token = jsonNode.get("token") != null ? jsonNode.get("token").asText() : null;

        return new Request<>(action, data, token);
    }

    public void setAction(String action) {
        this.action = action;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
