package com.maria_epikhova.dto.user;

public class SignInUserDto {
    private String email;
    private String password;

    public SignInUserDto() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
