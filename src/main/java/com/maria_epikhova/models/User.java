package com.maria_epikhova.models;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import com.maria_epikhova.dto.user.CreateUserDto;
import com.maria_epikhova.exceptions.InvalidRequestException;

import org.hibernate.annotations.NaturalId;

@Entity
@Table(name = "USERS")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(unique = true)
    @NaturalId
    private String email;
    private String password;
    private String secretQuestion;
    private String secretAnswer;
    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @PrimaryKeyJoinColumn
    private PersonInfo personInfo;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private Set<Product> products;

    public static User fromDto(CreateUserDto userDto) throws InvalidRequestException {
        User user = new User();
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setSecretQuestion(userDto.getSecretQuestion());
        user.setSecretAnswer(userDto.getSecretAnswer());
        UserRole userRole;
        String userRoleString = userDto.getUserRole();
        if (Objects.equals(userRoleString, User.UserRole.CUSTOMER.toString())) {
            userRole = UserRole.CUSTOMER;
        } else if (Objects.equals(userRoleString, User.UserRole.SUPPLIER.toString())) {
            userRole = UserRole.SUPPLIER;
        } else {
            throw new InvalidRequestException("Invalid userRole");
        }
        user.setUserRole(userRole);

        return user;
    }

    public enum UserRole {
        SUPPLIER, CUSTOMER
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getSecretQuestion() {
        return secretQuestion;
    }

    public void setSecretQuestion(String secretQuestion) {
        this.secretQuestion = secretQuestion;
    }

    public String getSecretAnswer() {
        return secretAnswer;
    }

    public void setSecretAnswer(String secretAnswer) {
        this.secretAnswer = secretAnswer;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    public PersonInfo getPersonInfo() {
        return personInfo;
    }

    public void setPersonInfo(PersonInfo personInfo) {
        this.personInfo = personInfo;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }
}
