package com.britenet.repository;

import com.britenet.dto.Contact;
import com.britenet.dto.Customer;

import java.sql.*;
import java.util.Map;

public class CustomerRepository {

    private Connection connection;

    public CustomerRepository(Map<String, String> configuration) {
        System.out.println("Connecting to database...");
        try {
            connection = DriverManager.getConnection(configuration.get("path"), configuration.get("user"), configuration.get("password"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveCustomer(Customer customer) {
        try (Statement statement = connection.createStatement()) {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO customers(NAME, SURNAME, Age) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, customer.getName());
            preparedStatement.setString(2, customer.getSurname());
            preparedStatement.setString(3, customer.getAge());
            preparedStatement.executeUpdate();

            ResultSet rs = preparedStatement.getGeneratedKeys();
            if (rs.next()) {
                int newId = rs.getInt(1);
                customer.setId(newId);
            }
            preparedStatement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (Contact contact : customer.getContacts()) {
            saveContact(contact);
        }
    }

    private void saveContact(Contact contact) {
        try (Statement statement = connection.createStatement()) {
            PreparedStatement pstmt = connection.prepareStatement("INSERT INTO contacts(ID_CUSTOMER, TYPE, CONTACT) VALUES (?, ?, ?)");
            // set input parameters
            pstmt.setInt(1, contact.getCustomer().getId());
            pstmt.setInt(2, contact.getType());
            pstmt.setString(3, contact.getContact());
            pstmt.executeUpdate();

            pstmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
