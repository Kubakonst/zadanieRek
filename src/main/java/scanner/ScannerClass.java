package scanner;

import DTO.Contact;
import DTO.Costumer;
import xmlHandler.XMLHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.beans.Customizer;
import java.io.*;
import java.nio.charset.Charset;
import java.sql.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ScannerClass {

//    static final String DB_URL = "jdbc:mysql://localhost/zadanie?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
//    static final String USER = "root";
//    static final String PASS = "h2vxs5xj";

    public ScannerClass() {
        String s;
        Scanner sc = new Scanner(System.in);
        System.out.println("Wprowadź ścieżkę do pliku, który ma być wczytany");
        s = sc.nextLine();
        try {
            InputStream inputStream = new FileInputStream(new File(s));
            System.out.println("Plik wczytany haraszo");
            String ext = "";

            int i = s.lastIndexOf('.');
            if (i > 0) {
                ext = s.substring(i+1);
            }
            if (ext.equals("txt")){
                try {
                    convertFromCSV(inputStream, Charset.defaultCharset());
                } catch (IOException ex) {
                }
            } else if (ext.equals("xml")){
                try {
                    convertFromXML(inputStream);
                } catch (Exception ex) {
                }
            } else {
                System.out.println("Zły format/rodzaj pliku");
            }
        } catch (FileNotFoundException e) {
            System.out.println("Nie znaleziono pliku");
        }
    }

    public static ScannerClass scannerClass() {
        return new ScannerClass();
    }

    public void convertFromCSV(InputStream inputStream, Charset charset) throws IOException {

        List<Contact> contactsList = new ArrayList<Contact>();
        List<Costumer> customersList = new ArrayList<Costumer>();
        String line = null;

        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, charset))) {
            while ((line = bufferedReader.readLine()) != null) {
                Costumer customer = new Costumer();
                String[] customerDetails = line.split(",");
                customer.setName(customerDetails[0]);
                customer.setSurname(customerDetails[1]);
                customer.setAge(customerDetails[2]);
                customer.setCity(customerDetails[3]);
                customersList.add(customer);
                for (int i = 4; customerDetails.length > i; i++) {
                    Contact contact = new Contact();
                    if (isPhone(customerDetails[i])) {
                        contact.setType(1);
                    } else if (isEmail(customerDetails[i])) {
                        contact.setType(2);
                    } else {
                        contact.setType(0);
                    }
                    contact.setContact(customerDetails[i]);
                    contact.setCustomer(customer);
                    contactsList.add(contact);
                }
            }
        }
        for (Costumer costumer : customersList){
            saveCustomer(costumer);
            System.out.println(costumer.getId());
        }
        for (Contact contact : contactsList){
            saveContact(contact);
        }
        System.out.println("khhhh");
    }

    public void convertFromXML(InputStream inputStream) throws Exception {
        SAXParserFactory parserFactor = SAXParserFactory.newInstance();
        SAXParser parser = parserFactor.newSAXParser();
        XMLHandler handler = new XMLHandler();
        parser.parse(inputStream, handler);
        for (Costumer emp : handler.getCustomerList()){
            saveCustomer(emp);
            System.out.println(emp.getId());
        }
        for (Contact emp : handler.getContactsList()){
            saveContact(emp);
        }
    }

    public static boolean isPhone(String phone) {
        Pattern p = Pattern.compile("(?:\\(\\d{3}\\)|\\d{3}[-]*)\\d{3}[-]*\\d{4}");
        return p.matcher(phone).matches();
    }

    public static boolean isEmail(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        Pattern p = java.util.regex.Pattern.compile(ePattern);
        Matcher m = p.matcher(email);
        return m.matches();
    }

    public void saveCustomer(Costumer costumer) {
                Connection conn = null;
                Statement stmt = null;
//                String DB_URL = "jdbc:mysql://localhost/zadanie?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
//                String USER = "root";
//                String PASS = "h2vxs5xj";
                try{
                    System.out.println("Connecting to database...");
                    String connectionPath = configureConnection();
                    conn = DriverManager.getConnection(connectionPath);
                    stmt = conn.createStatement();
                    PreparedStatement pstmt = conn.prepareStatement("INSERT INTO customers(NAME, SURNAME, Age) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
                    // set input parameters
                    pstmt.setString(1, costumer.getName());
                    pstmt.setString(2, costumer.getSurname());
                    pstmt.setString(3, costumer.getAge());
                    pstmt.executeUpdate();

                    ResultSet rs = pstmt.getGeneratedKeys();
                    if (rs.next()) {
                        int newId = rs.getInt(1);
                        costumer.setId(newId);
                    }

                    pstmt.close();
                }catch(SQLException se){
                    se.printStackTrace();
                }catch(Exception e){
                    e.printStackTrace();
                }finally{
                    try{
                        if(stmt!=null)
                            stmt.close();
                    }catch(SQLException se2){
                    }
                    try{
                        if(conn!=null)
                            conn.close();
                    }catch(SQLException se){
                        se.printStackTrace();
                    }
                }
                System.out.println("Goodbye!");
            }

    public void saveContact(Contact contact) {
        Connection conn = null;
        Statement stmt = null;
        try{
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(configureConnection());
            stmt = conn.createStatement();
            PreparedStatement pstmt = conn.prepareStatement("INSERT INTO contacts(ID_CUSTOMER, TYPE, CONTACT) VALUES (?, ?, ?)");
            // set input parameters
            pstmt.setInt(1, contact.getCustomer().getId());
            pstmt.setInt(2, contact.getType());
            pstmt.setString(3, contact.getContact());
            pstmt.executeUpdate();

            pstmt.close();
        }catch(SQLException se){
            se.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            try{
                if(stmt!=null)
                    stmt.close();
            }catch(SQLException se2){
            }
            try{
                if(conn!=null)
                    conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }
        }
        System.out.println("Goodbye!");
    }

//    public Boolean checkIfUserExists(String user) throws XMPPException{
//        UserSearchManager search = new UserSearchManager(xmppConnection);
//        Form searchForm = search.getSearchForm("search."+xmppConnection.getServiceName());
//        Normalizer.Form answerForm = searchForm.createAnswerForm();
//        answerForm.setAnswer("Username", true);
//        answerForm.setAnswer("search", user);
//        ReportedData data = search.getSearchResults(answerForm,"search."+xmppConnection.getServiceName());
//        if (data.getRows() != null) {
//            Iterator<Row> it = data.getRows();
//            while (it.hasNext()) {
//                Row row = it.next();
//                Iterator iterator = row.getValues("jid");
//                if (iterator.hasNext()) {
//                    String value = iterator.next().toString();
//                    System.out.println("Iteartor values...... " + value);
//                }
//            }
//            return true;
//        }
//        return false;
//    }

    public String configureConnection(){
        String configuration = new String();
        String line;
        try {
            InputStream inputStream = new FileInputStream(new File("./configuration.txt"));
            try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, Charset.defaultCharset()))) {
                while ((line = bufferedReader.readLine()) != null) {
                    if (!configuration.isEmpty()){
                        configuration = configuration + ", " + line;}
                    else {
                        configuration = line;
                    }
                }
            }catch (IOException e){
            }
        } catch (FileNotFoundException e){
        }
        return configuration;
    }
}