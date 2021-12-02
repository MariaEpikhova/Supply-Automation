package com.framework.interfaces;

import com.framework.models.Request;

public interface SecurityConfig {
    public boolean auth(Request<?> request);
}
