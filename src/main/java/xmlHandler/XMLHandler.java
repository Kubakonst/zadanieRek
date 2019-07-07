package xmlHandler;

import DTO.Contact;
import DTO.Costumer;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

public class XMLHandler extends DefaultHandler {

    List<Costumer> customerList = new ArrayList<>();
    List<Contact> contactsList = new ArrayList<>();
    Costumer customer = null;
    Contact contact = null;
    String content = null;
    int i = 0;
    @Override
    public void startElement(String uri, String localName,
                             String qName, Attributes attributes)
            throws SAXException {

        switch(qName){
            //Create a new Employee object when the start tag is found
            case "person":
                customer = new Costumer();
                customer.setId(i);
                i=i+1;
                break;
        }
    }

    @Override
    public void endElement(String uri, String localName,
                           String qName) throws SAXException {
        switch(qName){
            //Add the employee to list once end tag is found
            case "person":
                customerList.add(customer);
                break;
            //For all other end tags the employee has to be updated.
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
                contact = new Contact();
                contactsList.add(contact);
                contact.setContact(content);
                contact.setType(2);
                contact.setCustomer(customer);
                break;
            case "email":
                contact = new Contact();
                contactsList.add(contact);
                contact.setContact(content);
                contact.setType(1);
                contact.setCustomer(customer);
                break;
            case "jabber":
                contact = new Contact();
                contactsList.add(contact);
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
                contactsList.add(contact);
                contact.setContact(content);
                contact.setType(0);
                contact.setCustomer(customer);
                break;
        }
    }

    @Override
    public void characters(char[] ch, int start, int length)
            throws SAXException {
        content = String.copyValueOf(ch, start, length).trim();
    }

    public List<Costumer> getCustomerList() {
        return customerList;
    }

    public List<Contact> getContactsList() {
        return contactsList;
    }
}