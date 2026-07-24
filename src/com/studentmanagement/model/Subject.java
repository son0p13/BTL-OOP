package com.studentmanagement.model;

import java.io.Serializable;

/**
 * Entity representing a Course / Subject.
 */
public class Subject implements Serializable {
    private static final long serialVersionUID = 1L;

    private String subjectId;
    private String subjectName;
    private int credits;
    private int theoryHours;
    private int practiceHours;

    public Subject() {}

    public Subject(String subjectId, String subjectName, int credits, int theoryHours, int practiceHours) {
        this.subjectId = subjectId;
        this.subjectName = subjectName;
        this.credits = credits;
        this.theoryHours = theoryHours;
        this.practiceHours = practiceHours;
    }

    // Getters and Setters
    public String getSubjectId() { return subjectId; }
    public void setSubjectId(String subjectId) { this.subjectId = subjectId; }

    public String getSubjectName() { return subjectName; }
    public void setSubjectName(String subjectName) { this.subjectName = subjectName; }

    public int getCredits() { return credits; }
    public void setCredits(int credits) { this.credits = credits; }

    public int getTheoryHours() { return theoryHours; }
    public void setTheoryHours(int theoryHours) { this.theoryHours = theoryHours; }

    public int getPracticeHours() { return practiceHours; }
    public void setPracticeHours(int practiceHours) { this.practiceHours = practiceHours; }

    @Override
    public String toString() {
        return subjectName + " (" + subjectId + ")";
    }
}
