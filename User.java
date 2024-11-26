package org.example;

public class User {
    private String username;
    private String password;
    private String email;
    private String email2;
    private String firstname;
    private String lastname;
    private String city;
    private String country;

    public User(String username, String password, String email, String email2, String firstname, String lastname, String city, String country) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.email2 = email2;
        this.firstname = firstname;
        this.lastname = lastname;
        this.city = city;
        this.country = country;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail2() {
        return email2;
    }

    public void setEmail2(String email2) {
        this.email2 = email2;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
