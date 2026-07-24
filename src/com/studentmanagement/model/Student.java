package com.studentmanagement.model;

/**
 * Entity representing a Student, extending Person.
 */
public class Student extends Person {
    private static final long serialVersionUID = 1L;

    private String studentId;
    private String hometown;
    private String classId;
    private String advisorId;

    public Student() {
        super();
    }

    public Student(String id, String fullName, String gender, String dateOfBirth, String email, String phone,
                   String studentId, String hometown, String classId, String advisorId) {
        super(id, fullName, gender, dateOfBirth, email, phone);
        this.studentId = studentId;
        this.hometown = hometown;
        this.classId = classId;
        this.advisorId = advisorId;
    }

    @Override
    public String getRole() {
        return "Sinh Viên - Lớp " + classId;
    }

    @Override
    public String getDetailsSummary() {
        return String.format("Sinh Viên: %s (Mã SV: %s) | Lớp: %s | Cố Vấn: %s | Quê Quán: %s | SĐT: %s | Email: %s",
                getFullName(), studentId, classId, advisorId, hometown, getPhone(), getEmail());
    }

    // Getters and Setters
    public String getStudentId() { return studentId; }
    public void setStudentId(String studentId) { this.studentId = studentId; }

    public String getHometown() { return hometown; }
    public void setHometown(String hometown) { this.hometown = hometown; }

    public String getClassId() { return classId; }
    public void setClassId(String classId) { this.classId = classId; }

    public String getAdvisorId() { return advisorId; }
    public void setAdvisorId(String advisorId) { this.advisorId = advisorId; }

    @Override
    public String toString() {
        return getFullName() + " (" + studentId + ")";
    }
}
