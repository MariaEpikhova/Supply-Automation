package com.framework.modules;

import com.framework.models.Request;
import com.maria_epikhova.exceptions.InvalidRequestException;
import com.maria_epikhova.exceptions.ServerException;

@FunctionalInterface
public interface OnEntrypointCall<T> {
    public Object call(Request<T> request) throws ServerException;
}