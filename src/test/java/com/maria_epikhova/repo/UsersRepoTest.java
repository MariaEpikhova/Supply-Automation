package com.maria_epikhova.repo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.maria_epikhova.dto.user.CreateUserDto;
import com.maria_epikhova.dto.user.UpdatePersonInfoDto;
import com.maria_epikhova.exceptions.InvalidRequestException;
import com.maria_epikhova.models.User;
import com.maria_epikhova.repos.UsersRepo;

import org.junit.jupiter.api.Test;

public class UsersRepoTest {

    void addUser() {
        UsersRepo repo = new UsersRepo();
        try {
            repo.addUser(new CreateUserDto("test1@gmail.com", "12345", "What does the fox say?", "bla", "SUPPLIER"));
        } catch (InvalidRequestException e) {
            assertEquals("Email arleady exists", e.getMessage());
        }
    }

    void deleteUser() {
        UsersRepo repo = new UsersRepo();

        User user = repo.getByEmail("test1@gmail.com");
        try {
            repo.deleteUser(user.getId());
        } catch (InvalidRequestException e) {
            assertEquals("User not exists", e.getMessage());
        }
    }

    @Test
    void testAddingAndFinding() {
        UsersRepo repo = new UsersRepo();
        addUser();
        User user = repo.getByEmail("test1@gmail.com");
        deleteUser();
        assertEquals("What does the fox say?", user.getSecretQuestion());
    }

    @Test
    void testPersonInfoUpdate() throws JsonMappingException, JsonProcessingException, InvalidRequestException {
        String json = "{\"address\": {\"city\": \"Minsk\",\"street\": \"Victory\",\"building\": \"25\",\"appartment\": \"123\"},\"contacts\": {\"phoneNumber\": \"375251231234\",\"skype\": \"sdg4efsdfsd\"},\"organisationName\": \"KIRMASH\",\"description\": \"DESCRIPTION\"}";

        UsersRepo repo = new UsersRepo();
        addUser();
        User user = repo.getByEmail("test1@gmail.com");
        assertNull(user.getPersonInfo().getDescription());
        repo.updateUser(user.getId(), new ObjectMapper().readValue(json, UpdatePersonInfoDto.class));
        user = repo.getByEmail("test1@gmail.com");
        assertEquals("Minsk", user.getPersonInfo().getAddress().getCity());
        deleteUser();
    }

    @Test
    void testDeleting() {
        UsersRepo repo = new UsersRepo();
        addUser();

        User user = repo.getByEmail("test1@gmail.com");
        deleteUser();
        user = repo.getByEmail("test1@gmail.com");
        assertNull(user);
    }

    @Test
    void testSignIn() {
        UsersRepo repo = new UsersRepo();
        addUser();

        try {
            User user = repo.signIn("test1@gmail.com", "12345");
            assertNotNull(user);
            user = repo.signIn("test1@gmail.com", "124345");
            assertNull(user);
            deleteUser();
        } catch (InvalidRequestException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testGettingSecretQuestion() {
        UsersRepo repo = new UsersRepo();
        addUser();
        try {
            String secretQuestion = repo.getSecretQuestion("test1@gmail.com");
            assertEquals("What does the fox say?", secretQuestion);
            deleteUser();
        } catch (InvalidRequestException e) {
            assertEquals("User not exists", e.getMessage());
        }
    }

    @Test
    void testPasswordRestoring() {
        UsersRepo repo = new UsersRepo();
        addUser();
        try {
            boolean restoreResult = repo.restorePassword("test1@gmail.com", "bla", "123456");
            assertEquals(true, restoreResult);
            User user = repo.signIn("test1@gmail.com", "123456");
            assertNotNull(user);
            user = repo.signIn("test1@gmail.com", "asdasd");
            assertNull(user);
            restoreResult = repo.restorePassword("test1@gmail.com", "asdas", "123456");
            assertEquals(false, restoreResult);
            deleteUser();
        } catch (InvalidRequestException e) {
            assertEquals("Invalid password", e.getMessage());
        }
    }

}
