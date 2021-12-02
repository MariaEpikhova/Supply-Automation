package com.maria_epikhova.auth;

import com.framework.annotations.SecurityConfig;
import com.framework.models.Request;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;

@SecurityConfig
public class SupplyAutomationSecurityConfig implements com.framework.interfaces.SecurityConfig {

    @Override
    public boolean auth(Request<?> request) {
        String token = request.getToken();
        if (token == null) {
            return false;
        } else {
            try {
                JwtUtils.decodeJWT(token);
                return true;
            } catch (Exception e) {
                return false;
            }
        }
    }

}
