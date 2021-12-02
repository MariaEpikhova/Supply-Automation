package com.maria_epikhova;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.maria_epikhova.auth.JwtUtils;

import org.junit.jupiter.api.Test;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.SignatureException;

public class JwtTest {

    @Test
    void testJwt() {
        String token = JwtUtils.createJWT("5", "SUPLIER", 1000000);
        Claims claims = JwtUtils.decodeJWT(token);

        assertEquals("5", claims.get("user_id"));
        assertEquals("SUPLIER", claims.get("role"));

        try {
            Claims errorClaims = JwtUtils.decodeJWT(
                    "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c");
        } catch (SignatureException e) {
            assertEquals(
                    "JWT signature does not match locally computed signature. JWT validity cannot be asserted and should not be trusted.",
                    e.getMessage());
        }
    }
}
