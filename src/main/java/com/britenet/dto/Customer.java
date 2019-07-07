package com.britenet.dto;

import java.util.ArrayList;
import java.util.List;

public class Customer {

    private String city;
    private String name;
    private String surname;
    private String age;
    private int id;
    private List<Contact> contacts;

    public Customer() {
        contacts = new ArrayList<>();
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

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

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Contact> getContacts() {
        return contacts;
    }

}
