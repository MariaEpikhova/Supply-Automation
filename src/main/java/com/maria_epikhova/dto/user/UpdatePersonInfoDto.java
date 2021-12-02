package com.maria_epikhova.dto.user;

import java.util.Map;

import com.maria_epikhova.exceptions.InvalidRequestException;
import com.maria_epikhova.models.Address;
import com.maria_epikhova.models.Contacts;
import com.maria_epikhova.models.PersonInfo;

public class UpdatePersonInfoDto {

    public class Address {
        private String city;
        private String street;
        private String building;
        private String appartment;

        public Address() {

        }

        public Address(String city, String street, String building, String appartment) {
            this.city = city;
            this.street = street;
            this.building = building;
            this.appartment = appartment;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getStreet() {
            return street;
        }

        public void setStreet(String street) {
            this.street = street;
        }

        public String getBuilding() {
            return building;
        }

        public void setBuilding(String building) {
            this.building = building;
        }

        public String getAppartment() {
            return appartment;
        }

        public void setAppartment(String appartment) {
            this.appartment = appartment;
        }

    }

    public class Contacts {
        private String phoneNumber;
        private String skype;

        public Contacts() {

        }

        public Contacts(String phoneNumber, String skype) {
            this.phoneNumber = phoneNumber;
            this.skype = skype;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }

        public String getSkype() {
            return skype;
        }

        public void setSkype(String skype) {
            this.skype = skype;
        }
    }

    private Address address;
    private Contacts contacts;
    private String organisationName;
    private String description;

    public UpdatePersonInfoDto() {
    }

    public UpdatePersonInfoDto(Address address, Contacts contacts, String organisationName, String description) {
        this.address = address;
        this.contacts = contacts;
        this.organisationName = organisationName;
        this.description = description;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Contacts getContacts() {
        return contacts;
    }

    public void setContacts(Contacts contacts) {
        this.contacts = contacts;
    }

    public String getOrganisationName() {
        return organisationName;
    }

    public void setOrganisationName(String organisationName) {
        this.organisationName = organisationName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static UpdatePersonInfoDto fromEntity(PersonInfo personInfo) {
        UpdatePersonInfoDto dto = new UpdatePersonInfoDto();
        dto.setAddress(dto.new Address(personInfo.getAddress().getCity(), personInfo.getAddress().getStreet(),
                personInfo.getAddress().getBuilding(), personInfo.getAddress().getAppartment()));
        dto.setContacts(dto.new Contacts(personInfo.getContacts().getPhoneNumber(),
                personInfo.getContacts().getSkype()));
        dto.setDescription(personInfo.getDescription());
        dto.setOrganisationName(personInfo.getOrganisationName());
        return dto;
    }
}
