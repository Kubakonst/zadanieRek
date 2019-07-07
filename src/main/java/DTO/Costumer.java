package DTO;

public class Costumer {

    private String city;
    private String name;
    private String surname;
    private String age;
    private int id;

    public Costumer() {
    }

    public Costumer(String city, String name, String surname, String age, int id) {
        this.city = city;
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.id = id;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
