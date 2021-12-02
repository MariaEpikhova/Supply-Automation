package com.maria_epikhova.services;

import com.framework.annotations.Autowired;
import com.framework.annotations.Component;
import com.maria_epikhova.auth.JwtUtils;
import com.maria_epikhova.dto.user.CreateUserDto;
import com.maria_epikhova.dto.user.RestorePasswordDto;
import com.maria_epikhova.dto.user.SignInUserDto;
import com.maria_epikhova.dto.user.UpdatePersonInfoDto;
import com.maria_epikhova.exceptions.InvalidRequestException;
import com.maria_epikhova.models.Address;
import com.maria_epikhova.models.Contacts;
import com.maria_epikhova.models.PersonInfo;
import com.maria_epikhova.models.User;
import com.maria_epikhova.repos.UsersRepo;

@Component(name = "UserService")
public class UserService {
    @Autowired
    private UsersRepo usersRepo;

    public String register(CreateUserDto dto) throws InvalidRequestException {
        int userId = usersRepo.addUser(dto);
        return JwtUtils.createJWT(String.valueOf(userId), dto.getUserRole(), 1000000);
    }

    public String signIn(SignInUserDto data) throws InvalidRequestException {
        User user = usersRepo.signIn(data.getEmail(), data.getPassword());
        return JwtUtils.createJWT(String.valueOf(user.getId()), user.getUserRole().toString(), 1000000);
    }

    public String getSecretQuestion(String email) throws InvalidRequestException {
        return usersRepo.getSecretQuestion(email);
    }

    public String restorePassword(RestorePasswordDto data) throws InvalidRequestException {
        boolean result = usersRepo.restorePassword(data.getEmail(), data.getSecretAnswer(), data.getNewPassword());

        if (!result) {
            throw new InvalidRequestException("Invalid secret answer");
        }

        User user = usersRepo.getByEmail(data.getEmail());
        return JwtUtils.createJWT(String.valueOf(user.getId()), user.getUserRole().toString(), 1000000);
    }

    public boolean updateUserInfo(UpdatePersonInfoDto data, int id) throws InvalidRequestException {
        usersRepo.updateUser(id, data);
        return true;
    }

    public UpdatePersonInfoDto getPersonInfo(Integer id) throws InvalidRequestException {
        PersonInfo personInfo = usersRepo.getPersonInfo(id);
        return UpdatePersonInfoDto.fromEntity(personInfo);
    }
}
