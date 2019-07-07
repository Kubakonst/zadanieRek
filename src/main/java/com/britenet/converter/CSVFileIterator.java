package com.britenet.converter;

import com.britenet.checker.ContactTypeChecker;
import com.britenet.dto.Contact;
import com.britenet.dto.Customer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Consumer;

class CSVFileIterator implements Iterable<Customer>, Iterator<Customer> {

    private BufferedReader bufferedReader;
    private ContactTypeChecker contactTypeChecker;
    private String line;

    CSVFileIterator(InputStream inputStream, ContactTypeChecker contactTypeChecker) {
        this.bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        this.contactTypeChecker = contactTypeChecker;
    }

    @Override
    public boolean hasNext() {
        if (line == null) {
            try {
                line = bufferedReader.readLine();
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
        return line != null;
    }

    @Override
    public Customer next() {
        Customer customer = new Customer();
        String[] customerDetails = line.split(",");
        customer.setName(customerDetails[0]);
        customer.setSurname(customerDetails[1]);
        customer.setAge(customerDetails[2]);
        customer.setCity(customerDetails[3]);
        for (int i = 4; customerDetails.length > i; i++) {
            Contact contact = new Contact();
            contact.setType(contactTypeChecker.getType(customerDetails[i]).ordinal());
            contact.setContact(customerDetails[i]);
            contact.setCustomer(customer);
            customer.getContacts().add(contact);
        }
        line = null;
        return customer;
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void forEachRemaining(Consumer<? super Customer> action) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<Customer> iterator() {
        return this;
    }

    @Override
    public void forEach(Consumer<? super Customer> action) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Spliterator<Customer> spliterator() {
        throw new UnsupportedOperationException();
    }
}
