package com.studentmanagement.model;

import java.io.Serializable;

/**
 * Entity representing a Student Class / Major batch.
 */
public class StudentClass implements Serializable {
    private static final long serialVersionUID = 1L;

    private String classId;
    private String className;
    private String facultyId;
    private String academicYear;

    public StudentClass() {}

    public StudentClass(String classId, String className, String facultyId, String academicYear) {
        this.classId = classId;
        this.className = className;
        this.facultyId = facultyId;
        this.academicYear = academicYear;
    }

    // Getters and Setters
    public String getClassId() { return classId; }
    public void setClassId(String classId) { this.classId = classId; }

    public String getClassName() { return className; }
    public void setClassName(String className) { this.className = className; }

    public String getFacultyId() { return facultyId; }
    public void setFacultyId(String facultyId) { this.facultyId = facultyId; }

    public String getAcademicYear() { return academicYear; }
    public void setAcademicYear(String academicYear) { this.academicYear = academicYear; }

    @Override
    public String toString() {
        return className + " (" + classId + ")";
    }
}
