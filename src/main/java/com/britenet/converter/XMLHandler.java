package com.britenet.converter;

import com.britenet.dto.Contact;
import com.britenet.dto.Customer;
import com.britenet.repository.CustomerRepository;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

class XMLHandler extends DefaultHandler {

    private Customer customer = null;
    private String content = null;
    private CustomerRepository customerRepository;

    XMLHandler(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public void startElement(String uri, String localName,
                             String qName, Attributes attributes) {

        if ("person".equals(qName)) {
            customer = new Customer();
        }
    }

    @Override
    public void endElement(String uri, String localName,
                           String qName) {
        switch (qName) {
            case "person":
                customerRepository.saveCustomer(customer);
                break;
            case "name":
                customer.setName(content);
                break;
            case "surname":
                customer.setSurname(content);
                break;
            case "age":
                customer.setAge(content);
                break;
            case "city":
                customer.setCity(content);
                break;
            case "phone":
                Contact contact = new Contact();
                customer.getContacts().add(contact);
                contact.setContact(content);
                contact.setType(2);
                contact.setCustomer(customer);
                break;
            case "email":
                contact = new Contact();
                customer.getContacts().add(contact);
                contact.setContact(content);
                contact.setType(1);
                contact.setCustomer(customer);
                break;
            case "jabber":
                contact = new Contact();
                customer.getContacts().add(contact);
                contact.setContact(content);
                contact.setType(3);
                contact.setCustomer(customer);
                break;
            case "contacts":
                break;
            case "persons":
                break;
            default:
                contact = new Contact();
                customer.getContacts().add(contact);
                contact.setContact(content);
                contact.setType(0);
                contact.setCustomer(customer);
                break;
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) {
        content = String.copyValueOf(ch, start, length).trim();
    }
}