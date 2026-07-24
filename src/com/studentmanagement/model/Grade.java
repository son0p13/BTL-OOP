package com.studentmanagement.model;

import java.io.Serializable;

public class Grade implements Serializable {
    private static final long serialVersionUID = 1L;

    private String gradeId;
    private String studentId;
    private String subjectId;
    private double attendanceScore; // 10%
    private double midtermScore;    // 30%
    private double finalScore;      // 60%

    public Grade() {}

    public Grade(String gradeId, String studentId, String subjectId, double attendanceScore, double midtermScore, double finalScore) {
        this.gradeId = gradeId;
        this.studentId = studentId;
        this.subjectId = subjectId;
        this.attendanceScore = attendanceScore;
        this.midtermScore = midtermScore;
        this.finalScore = finalScore;
    }

    public double calculateTotal10() {
        double score = (attendanceScore * 0.10) + (midtermScore * 0.30) + (finalScore * 0.60);
        return Math.round(score * 100.0) / 100.0;
    }

    public double calculateTotal4() {
        double t10 = calculateTotal10();
        if (t10 >= 8.5) return 4.0;
        if (t10 >= 7.0) return 3.0;
        if (t10 >= 5.5) return 2.0;
        if (t10 >= 4.0) return 1.0;
        return 0.0;
    }

    public String getLetterGrade() {
        double t10 = calculateTotal10();
        if (t10 >= 8.5) return "A";
        if (t10 >= 7.0) return "B";
        if (t10 >= 5.5) return "C";
        if (t10 >= 4.0) return "D";
        return "F";
    }

    public String getRank() {
        double t10 = calculateTotal10();
        if (t10 >= 8.5) return "Xuất Sắc";
        if (t10 >= 7.0) return "Giỏi";
        if (t10 >= 5.5) return "Khá";
        if (t10 >= 4.0) return "Trung Bình";
        return "Yếu";
    }

    // Getters and Setters
    public String getGradeId() { return gradeId; }
    public void setGradeId(String gradeId) { this.gradeId = gradeId; }

    public String getStudentId() { return studentId; }
    public void setStudentId(String studentId) { this.studentId = studentId; }

    public String getSubjectId() { return subjectId; }
    public void setSubjectId(String subjectId) { this.subjectId = subjectId; }

    public double getAttendanceScore() { return attendanceScore; }
    public void setAttendanceScore(double attendanceScore) { this.attendanceScore = attendanceScore; }

    public double getMidtermScore() { return midtermScore; }
    public void setMidtermScore(double midtermScore) { this.midtermScore = midtermScore; }

    public double getFinalScore() { return finalScore; }
    public void setFinalScore(double finalScore) { this.finalScore = finalScore; }
}
