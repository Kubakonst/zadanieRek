package com.britenet.converter;

import com.britenet.repository.CustomerRepository;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.io.InputStream;

public class XMLConverter implements Converter {

    private CustomerRepository customerRepository;

    public XMLConverter(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public void convert(InputStream inputStream) {
        SAXParserFactory parserFactor = SAXParserFactory.newInstance();
        SAXParser parser;
        XMLHandler handler = new XMLHandler(customerRepository);
        try {
            parser = parserFactor.newSAXParser();
            parser.parse(inputStream, handler);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
    }
}
