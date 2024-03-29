package com.britenet.dto;

public class Contact {

    private String id;
    private Customer customer;
    private int type;
    private String contact;

    public Contact() {
    }

    public Contact(String id, Customer customer, int type, String contact) {
        this.id = id;
        this.customer = customer;
        this.type = type;
        this.contact = contact;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
}
