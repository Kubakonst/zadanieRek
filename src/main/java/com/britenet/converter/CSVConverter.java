package com.britenet.converter;

import com.britenet.checker.ContactTypeChecker;
import com.britenet.dto.Customer;
import com.britenet.repository.CustomerRepository;

import java.io.InputStream;

public class CSVConverter implements Converter {

    private ContactTypeChecker contactTypeChecker;
    private CustomerRepository customerRepository;

    public CSVConverter(ContactTypeChecker contactTypeChecker, CustomerRepository customerRepository) {
        this.contactTypeChecker = contactTypeChecker;
        this.customerRepository = customerRepository;
    }

    @Override
    public void convert(InputStream inputStream) {
        CSVFileIterator fileIterator = new CSVFileIterator(inputStream, contactTypeChecker);
        for (Customer customer : fileIterator) {
            customerRepository.saveCustomer(customer);
        }
    }
}