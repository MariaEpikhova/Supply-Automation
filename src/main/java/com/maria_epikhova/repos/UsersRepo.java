package com.maria_epikhova.repos;

import com.framework.annotations.Component;
import com.maria_epikhova.dto.user.CreateUserDto;
import com.maria_epikhova.dto.user.UpdatePersonInfoDto;
import com.maria_epikhova.exceptions.InvalidRequestException;
import com.maria_epikhova.models.Address;
import com.maria_epikhova.models.Contacts;
import com.maria_epikhova.models.PersonInfo;
import com.maria_epikhova.models.User;
import com.maria_epikhova.utils.HibernateUtil;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;
import org.hibernate.Session;

@Component(name = "UsersRepo")
public class UsersRepo {
    public int addUser(CreateUserDto userDto) throws InvalidRequestException {
        Session session = HibernateUtil.getSession();

        if (getByEmail(userDto.getEmail()) != null) {
            throw new InvalidRequestException("Email arleady exists");
        }

        session.beginTransaction();
        userDto.setPassword(hashString(userDto.getPassword()));
        userDto.setSecretAnswer(hashString(userDto.getSecretAnswer()));

        Contacts contacts = new Contacts();
        session.save(contacts);

        Address address = new Address();
        session.save(address);

        PersonInfo personInfo = new PersonInfo();
        personInfo.setAddress(address);
        personInfo.setContacts(contacts);
        session.save(personInfo);

        User user = User.fromDto(userDto);
        user.setPersonInfo(personInfo);
        session.save(user);
        session.getTransaction().commit();

        return user.getId();
    }

    public void updateUser(int id, UpdatePersonInfoDto dto) throws InvalidRequestException {
        Session session = HibernateUtil.getSession();

        session.beginTransaction();
        User user = session.load(User.class, id);
        if (user == null) {
            throw new InvalidRequestException("User not exists");
        }

        PersonInfo personInfo = new PersonInfo();

        Address address = user.getPersonInfo().getAddress();
        address.setAppartment(dto.getAddress().getAppartment());
        address.setBuilding(dto.getAddress().getBuilding());
        address.setStreet(dto.getAddress().getStreet());
        address.setCity(dto.getAddress().getCity());

        Contacts contacts = user.getPersonInfo().getContacts();
        contacts.setSkype(dto.getContacts().getSkype());
        contacts.setPhoneNumber(dto.getContacts().getPhoneNumber());

        personInfo.setAddress(address);
        personInfo.setContacts(contacts);
        user.setPersonInfo(personInfo);

        session.update(user);
        session.getTransaction().commit();
    }

    private String hashString(String stringToHash) {
        MessageDigest mDigest;
        try {
            mDigest = MessageDigest.getInstance("SHA-256");
            mDigest.update(stringToHash.getBytes());
            return new String(mDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return stringToHash;
    }

    public User getByEmail(String email) {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        User user = session.byNaturalId(User.class).using("email", email).load();
        session.getTransaction().commit();
        return user;
    }

    public void deleteUser(int id) throws InvalidRequestException {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        User user = session.load(User.class, id);
        if (user == null) {
            throw new InvalidRequestException("Email not exists");
        }
        session.delete(user);
        session.getTransaction().commit();

    }

    public User signIn(String email, String password) throws InvalidRequestException {
        User user = getByEmail(email);

        if (user == null) {
            throw new InvalidRequestException("User not exists");
        }

        if (Objects.equals(user.getPassword(), hashString(password))) {
            return user;
        } else {
            throw new InvalidRequestException("Invalid password");
        }
    }

    public PersonInfo getPersonInfo(int id) throws InvalidRequestException {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        User user = session.load(User.class, id);
        if (user == null) {
            throw new InvalidRequestException("Email not exists");
        }
        return user.getPersonInfo();
    }

    public String getSecretQuestion(String email) throws InvalidRequestException {
        User user = getByEmail(email);

        if (user == null) {
            throw new InvalidRequestException("User not exists");
        }

        return user.getSecretQuestion();
    }

    public boolean restorePassword(String email, String secretAnswer, String newPassword)
            throws InvalidRequestException {
        User user = getByEmail(email);

        if (user == null) {
            throw new InvalidRequestException("User not exists");
        }

        if (Objects.equals(user.getSecretAnswer(), hashString(secretAnswer))) {
            user.setPassword(hashString(newPassword));
            Session session = HibernateUtil.getSession();
            session.beginTransaction();
            session.merge(user);
            session.getTransaction().commit();
            return true;
        } else {
            return false;
        }

    }
}
