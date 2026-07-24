package com.studentmanagement.model;

import java.io.Serializable;

/**
 * Abstract Person class demonstrating OOP Abstraction & Encapsulation.
 */
public abstract class Person implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private String fullName;
    private String gender;
    private String dateOfBirth;
    private String email;
    private String phone;

    public Person() {}

    public Person(String id, String fullName, String gender, String dateOfBirth, String email, String phone) {
        this.id = id;
        this.fullName = fullName;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
        this.phone = phone;
    }

    public abstract String getRole();
    public abstract String getDetailsSummary();

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public String getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(String dateOfBirth) { this.dateOfBirth = dateOfBirth; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
}
