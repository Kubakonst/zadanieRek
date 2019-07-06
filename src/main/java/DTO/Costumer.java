package DTO;

public class Costumer {

    private String city;
    private String name;
    private String surname;
    private String age;

    public Costumer() {
    }

    public Costumer(String city, String name, String surname, String age) {
        this.city = city;
        this.name = name;
        this.surname = surname;
        this.age = age;
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
}
