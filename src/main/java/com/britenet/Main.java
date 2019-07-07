package com.britenet;

import com.britenet.checker.ContactTypeChecker;
import com.britenet.converter.CSVConverter;
import com.britenet.converter.Converter;
import com.britenet.converter.XMLConverter;
import com.britenet.repository.Configuration;
import com.britenet.repository.CustomerRepository;

import java.util.HashMap;
import java.util.Map;


public class Main {

    public static void main(String[] args) {
        Map<String, String> configuration = new Configuration().configureConnection();
        CustomerRepository customerRepository = new CustomerRepository(configuration);
        ContactTypeChecker contactTypeChecker = new ContactTypeChecker();
        Map<String, Converter> converters = new HashMap<>();
        converters.put("txt", new CSVConverter(contactTypeChecker, customerRepository));
        converters.put("xml", new XMLConverter(customerRepository));
        Application application = new Application(converters);
        application.convert();
    }
}