package com.maria_epikhova.controllers;

import com.framework.annotations.Autowired;
import com.framework.annotations.Controller;
import com.framework.annotations.Entrypoint;
import com.framework.annotations.Secured;
import com.framework.models.Request;
import com.maria_epikhova.auth.JwtUtils;
import com.maria_epikhova.dto.user.CreateUserDto;
import com.maria_epikhova.dto.user.RestorePasswordDto;
import com.maria_epikhova.dto.user.SignInUserDto;
import com.maria_epikhova.dto.user.UpdatePersonInfoDto;
import com.maria_epikhova.exceptions.InvalidRequestException;
import com.maria_epikhova.models.PersonInfo;
import com.maria_epikhova.services.UserService;

import io.jsonwebtoken.Claims;
import java.util.Objects;

@Controller(path = "/users/")
public class UsersController {
    @Autowired
    private UserService userService;

    @Entrypoint(actionType = "ADD")
    public String addUser(Request<CreateUserDto> req) throws InvalidRequestException {
        return userService.register(req.getData());
    }

    @Entrypoint(actionType = "SIGN_IN")
    public String signIn(Request<SignInUserDto> req) throws InvalidRequestException {
        return userService.signIn(req.getData());
    }

    @Entrypoint(actionType = "GET_SECRET_QUESTION")
    public String getSecretQuestion(Request<String> req) throws InvalidRequestException {
        return userService.getSecretQuestion(req.getData());
    }

    @Entrypoint(actionType = "RESTORE_PASSWORD")
    public String restorePassword(Request<RestorePasswordDto> req) throws InvalidRequestException {
        return userService.restorePassword(req.getData());
    }

    @Secured
    @Entrypoint(actionType = "UPDATE_PERSON_INFO")
    public boolean updateUserInfo(Request<UpdatePersonInfoDto> req)
            throws NumberFormatException, InvalidRequestException {
        String userId = (String) JwtUtils.decodeJWT(req.getToken()).get("user_id");
        return userService.updateUserInfo(req.getData(), Integer.parseInt(userId));
    }

    @Secured
    @Entrypoint(actionType = "GET_PERSON_INFO")
    public UpdatePersonInfoDto getPersonInfo(Request<Void> req) throws InvalidRequestException {
        String userId = (String) JwtUtils.decodeJWT(req.getToken()).get("user_id");

        return userService.getPersonInfo(Integer.parseInt(userId));
    }
}
