package com.maria_epikhova;

import static org.junit.jupiter.api.Assertions.assertNull;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.framework.models.Request;

import org.junit.jupiter.api.Test;

public class VoidTest {

    @Test
    void testVoid() throws JsonProcessingException {
        String req = "{\"action\":\"/users/ADD\"}}";
        Request<Void> request = Request.fromJson(req, Void.class);
        assertNull(request.getData());
    }
}
