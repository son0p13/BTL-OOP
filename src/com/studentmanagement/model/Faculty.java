package com.studentmanagement.model;

import java.io.Serializable;

/**
 * Entity representing a Faculty / Department.
 */
public class Faculty implements Serializable {
    private static final long serialVersionUID = 1L;

    private String facultyId;
    private String facultyName;
    private String deanName;
    private String phone;

    public Faculty() {}

    public Faculty(String facultyId, String facultyName, String deanName, String phone) {
        this.facultyId = facultyId;
        this.facultyName = facultyName;
        this.deanName = deanName;
        this.phone = phone;
    }

    // Getters and Setters
    public String getFacultyId() { return facultyId; }
    public void setFacultyId(String facultyId) { this.facultyId = facultyId; }

    public String getFacultyName() { return facultyName; }
    public void setFacultyName(String facultyName) { this.facultyName = facultyName; }

    public String getDeanName() { return deanName; }
    public void setDeanName(String deanName) { this.deanName = deanName; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    @Override
    public String toString() {
        return facultyName + " (" + facultyId + ")";
    }
}
