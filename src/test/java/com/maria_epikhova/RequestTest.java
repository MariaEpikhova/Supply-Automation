package com.maria_epikhova;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.framework.models.Request;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class Person {
    private String name;
    private String surname;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }
}

public class RequestTest {
    @Test
    void testRequestFrom() throws JsonProcessingException {
        String req = "{\"action\":\"/users/ADD\",\"data\":{\"name\":\"MARIA\",\"surname\":\"PASSWORD\"}}";
        Request<Person> request = Request.fromJson(req, Person.class);
        assertEquals("/users/ADD", request.getAction());
        assertEquals("MARIA", request.getData().getName());
        assertNull(request.getToken());
        req = "{\"action\":\"/users/ADD\",\"data\":{\"name\":\"MARIA\",\"surname\":\"PASSWORD\"}, \"token\": \"asdasdadwdas\"}";
        request = Request.fromJson(req, Person.class);
        assertEquals("/users/ADD", request.getAction());
        assertEquals("MARIA", request.getData().getName());
        assertEquals("asdasdadwdas", request.getToken());
    }
}
