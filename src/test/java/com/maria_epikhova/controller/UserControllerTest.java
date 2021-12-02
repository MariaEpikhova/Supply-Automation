package com.maria_epikhova.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.framework.EasyTcpApplication;
import com.framework.models.Response;
import com.maria_epikhova.App;
import com.maria_epikhova.auth.JwtUtils;
import com.maria_epikhova.exceptions.InvalidRequestException;
import com.maria_epikhova.models.User;
import com.maria_epikhova.repos.UsersRepo;

import org.junit.jupiter.api.Test;

import io.jsonwebtoken.Claims;

public class UserControllerTest {
    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    public UserControllerTest() throws UnknownHostException, IOException {
        EasyTcpApplication.run(App.class, 4321);
        socket = new Socket("localhost", 4321);

        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());

    }

    @Test
    void testUserAdd() throws IOException, ClassNotFoundException, NumberFormatException, InvalidRequestException {
        createUser();
        String json = (String) in.readObject();
        Response response = new ObjectMapper().readValue(json, Response.class);
        System.out.println(json);
        String token = (String) response.getData();
        assertNotNull(token);
        assertNull(response.getError());

        createUser();
        json = (String) in.readObject();
        response = new ObjectMapper().readValue(json, Response.class);

        assertNull(response.getData());
        assertEquals("Email arleady exists", response.getError());

        deleteUser(token);
    }

    @Test
    void testUserSignIn() throws IOException, ClassNotFoundException, NumberFormatException, InvalidRequestException {
        createUser();
        String json = (String) in.readObject();
        String req = "{\"action\": \"/users/SIGN_IN\",\"data\": {\"email\": \"some@gmail.com\",\"password\": \"12345\"}}";

        out.writeObject(req);
        json = (String) in.readObject();
        Response response = new ObjectMapper().readValue(json, Response.class);
        String token = (String) response.getData();
        assertNotNull(token);
        assertNull(response.getError());

        req = "{\"action\": \"/users/SIGN_IN\",\"data\": {\"email\": \"some@gmail.com\",\"password\": \"1233145\"}}";
        out.writeObject(req);
        json = (String) in.readObject();
        response = new ObjectMapper().readValue(json, Response.class);
        assertNull(response.getData());
        assertEquals("Invalid password", response.getError());

        deleteUser(token);
    }

    @Test
    void testGetSecretQuestion() throws IOException, ClassNotFoundException, InvalidRequestException {
        createUser();
        String json = (String) in.readObject();

        String req = "{\"action\": \"/users/GET_SECRET_QUESTION\",\"data\": \"some@gmail.com\"}";
        out.writeObject(req);
        json = (String) in.readObject();
        Response response = new ObjectMapper().readValue(json, Response.class);
        assertEquals("2+2", response.getData());

        deleteUserByEmail();
    }

    @Test
    void testRestorePassword() throws IOException, ClassNotFoundException, InvalidRequestException {
        createUser();
        String json = (String) in.readObject();

        // TEST CORRECT ANSWER
        String req = "{\"action\": \"/users/RESTORE_PASSWORD\",\"data\": {\"email\": \"some@gmail.com\", \"secretAnswer\": \"4\", \"newPassword\": \"54321\"}}";
        out.writeObject(req);
        json = (String) in.readObject();
        System.out.println(json);
        Response response = new ObjectMapper().readValue(json, Response.class);
        assertNotNull(response.getData());
        assertNull(response.getError());

        // TEST INCORRECT ANSWER
        req = "{\"action\": \"/users/RESTORE_PASSWORD\",\"data\": {\"email\": \"some@gmail.com\", \"secretAnswer\": \"5\", \"newPassword\": \"54321\"}}";
        out.writeObject(req);
        json = (String) in.readObject();
        response = new ObjectMapper().readValue(json, Response.class);
        assertNull(response.getData());
        assertEquals("Invalid secret answer", response.getError());

        // INCORRECT PASSWORD SIGN IN
        req = "{\"action\": \"/users/SIGN_IN\",\"data\": {\"email\": \"some@gmail.com\",\"password\": \"12345\"}}";
        out.writeObject(req);
        json = (String) in.readObject();
        response = new ObjectMapper().readValue(json, Response.class);
        String token = (String) response.getData();
        assertNull(token);
        assertEquals("Invalid password", response.getError());

        // CORRECT PASSWOER SIGN IN
        req = "{\"action\": \"/users/SIGN_IN\",\"data\": {\"email\": \"some@gmail.com\",\"password\": \"54321\"}}";
        out.writeObject(req);
        json = (String) in.readObject();
        response = new ObjectMapper().readValue(json, Response.class);
        token = (String) response.getData();
        assertNotNull(token);
        assertNull(response.getError());

        deleteUserByEmail();
    }

    @Test
    void testUpdatePersonInfo() throws IOException, ClassNotFoundException, InvalidRequestException {
        createUser();
        String json = (String) in.readObject();

        String req = "{\"action\": \"/users/SIGN_IN\",\"data\": {\"email\": \"some@gmail.com\",\"password\": \"12345\"}}";
        out.writeObject(req);
        json = (String) in.readObject();
        Response response = new ObjectMapper().readValue(json, Response.class);
        String token = (String) response.getData();

        System.out.println("TEST");
        req = "{\"action\": \"/users/GET_PERSON_INFO\", \"token\": \"" + token + "\"}";
        out.writeObject(req);
        json = (String) in.readObject();
        String city = new ObjectMapper().readTree(json).get("data").get("address").get("city").asText();
        assertEquals("null", city);

        req = "{\"action\": \"/users/UPDATE_PERSON_INFO\",\"data\": {\"address\": {\"city\": \"Minsk\",\"street\": \"Victory\",\"building\": \"25\",\"appartment\": \"123\"},\"contacts\": {\"phoneNumber\": \"375251231234\",\"skype\": \"sdg4efsdfsd\"},\"organisationName\": \"KIRMASH\",\"description\": \"DESCRIPTION\"}, \"token\": \""
                + token + "\"}";
        out.writeObject(req);
        json = (String) in.readObject();
        response = new ObjectMapper().readValue(json, Response.class);
        assertEquals(true, response.getData());
        assertNull(response.getError());

        req = "{\"action\": \"/users/GET_PERSON_INFO\", \"token\": \"" + token + "\"}";
        out.writeObject(req);
        json = (String) in.readObject();
        city = new ObjectMapper().readTree(json).get("data").get("address").get("city").asText();
        assertEquals("Minsk", city);

        deleteUserByEmail();
    }

    void deleteUser(String token) throws NumberFormatException, InvalidRequestException {
        Claims claims = JwtUtils.decodeJWT(token);
        new UsersRepo().deleteUser(Integer.parseInt(claims.get("user_id").toString()));
    }

    void deleteUserByEmail() throws InvalidRequestException {
        UsersRepo repo = new UsersRepo();

        User user = repo.getByEmail("some@gmail.com");

        repo.deleteUser(user.getId());
    }

    void createUser() throws IOException {
        String req = "{\"action\": \"/users/ADD\",\"data\": {\"email\": \"some@gmail.com\",\"password\": \"12345\",\"secretQuestion\": \"2+2\",\"secretAnswer\": \"4\",\"userRole\": \"SUPPLIER\"}}";
        out.writeObject(req);
    }
}
