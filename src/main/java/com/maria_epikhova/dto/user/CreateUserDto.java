package com.maria_epikhova.dto.user;

public class CreateUserDto {
    private String email;
    private String password;
    private String secretQuestion;
    private String secretAnswer;
    private String userRole;

    public CreateUserDto() {

    }

    public CreateUserDto(String email, String password, String secretQuestion, String secretAnswer, String userRole) {
        this.email = email;
        this.password = password;
        this.secretQuestion = secretQuestion;
        this.secretAnswer = secretAnswer;
        this.userRole = userRole;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getSecretQuestion() {
        return secretQuestion;
    }

    public String getSecretAnswer() {
        return secretAnswer;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setSecretQuestion(String secretQuestion) {
        this.secretQuestion = secretQuestion;
    }

    public void setSecretAnswer(String secretAnswer) {
        this.secretAnswer = secretAnswer;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }
}
