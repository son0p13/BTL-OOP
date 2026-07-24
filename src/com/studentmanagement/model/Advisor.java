package com.studentmanagement.model;

/**
 * Entity representing an Academic Advisor / Lecturer, extending Person.
 */
public class Advisor extends Person {
    private static final long serialVersionUID = 1L;

    private String advisorId;
    private String facultyId;

    public Advisor() {
        super();
    }

    public Advisor(String id, String fullName, String gender, String dateOfBirth, String email, String phone,
                   String advisorId, String facultyId) {
        super(id, fullName, gender, dateOfBirth, email, phone);
        this.advisorId = advisorId;
        this.facultyId = facultyId;
    }

    @Override
    public String getRole() {
        return "Cố Vấn Học Tập";
    }

    @Override
    public String getDetailsSummary() {
        return String.format("Cố Vấn: %s (Mã: %s) | Khoa: %s | SĐT: %s | Email: %s",
                getFullName(), advisorId, facultyId, getPhone(), getEmail());
    }

    // Getters and Setters
    public String getAdvisorId() { return advisorId; }
    public void setAdvisorId(String advisorId) { this.advisorId = advisorId; }

    public String getFacultyId() { return facultyId; }
    public void setFacultyId(String facultyId) { this.facultyId = facultyId; }

    @Override
    public String toString() {
        return getFullName() + " (" + advisorId + ")";
    }
}
