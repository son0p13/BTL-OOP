package com.studentmanagement.util;

import com.studentmanagement.model.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility for reading and writing entities in CSV format.
 */
public class CsvUtil {

    private CsvUtil() {}

    // Users
    public static List<User> readUsers(File file) throws IOException {
        List<User> list = new ArrayList<>();
        if (!file.exists()) return list;
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("Username")) continue;
                String[] p = line.split(",", -1);
                if (p.length >= 4) {
                    list.add(new User(p[0].trim(), p[1].trim(), p[2].trim(), p[3].trim()));
                }
            }
        }
        return list;
    }

    public static void writeUsers(File file, List<User> users) throws IOException {
        ensureParentDir(file);
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8))) {
            writer.write("Username,Password,FullName,Role");
            writer.newLine();
            for (User u : users) {
                writer.write(String.format("%s,%s,%s,%s", escapeCsv(u.getUsername()), escapeCsv(u.getPassword()), escapeCsv(u.getFullName()), escapeCsv(u.getRole())));
                writer.newLine();
            }
        }
    }

    // Faculties
    public static List<Faculty> readFaculties(File file) throws IOException {
        List<Faculty> list = new ArrayList<>();
        if (!file.exists()) return list;
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("FacultyId")) continue;
                String[] p = line.split(",", -1);
                if (p.length >= 4) {
                    list.add(new Faculty(p[0].trim(), p[1].trim(), p[2].trim(), p[3].trim()));
                }
            }
        }
        return list;
    }

    public static void writeFaculties(File file, List<Faculty> list) throws IOException {
        ensureParentDir(file);
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8))) {
            writer.write("FacultyId,FacultyName,DeanName,Phone");
            writer.newLine();
            for (Faculty f : list) {
                writer.write(String.format("%s,%s,%s,%s", escapeCsv(f.getFacultyId()), escapeCsv(f.getFacultyName()), escapeCsv(f.getDeanName()), escapeCsv(f.getPhone())));
                writer.newLine();
            }
        }
    }

    // Student Classes
    public static List<StudentClass> readClasses(File file) throws IOException {
        List<StudentClass> list = new ArrayList<>();
        if (!file.exists()) return list;
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("ClassId")) continue;
                String[] p = line.split(",", -1);
                if (p.length >= 4) {
                    list.add(new StudentClass(p[0].trim(), p[1].trim(), p[2].trim(), p[3].trim()));
                }
            }
        }
        return list;
    }

    public static void writeClasses(File file, List<StudentClass> list) throws IOException {
        ensureParentDir(file);
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8))) {
            writer.write("ClassId,ClassName,FacultyId,AcademicYear");
            writer.newLine();
            for (StudentClass c : list) {
                writer.write(String.format("%s,%s,%s,%s", escapeCsv(c.getClassId()), escapeCsv(c.getClassName()), escapeCsv(c.getFacultyId()), escapeCsv(c.getAcademicYear())));
                writer.newLine();
            }
        }
    }

    // Advisors
    public static List<Advisor> readAdvisors(File file) throws IOException {
        List<Advisor> list = new ArrayList<>();
        if (!file.exists()) return list;
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("AdvisorId")) continue;
                String[] p = line.split(",", -1);
                if (p.length >= 8) {
                    list.add(new Advisor(p[0].trim(), p[1].trim(), p[2].trim(), p[3].trim(), p[4].trim(), p[5].trim(), p[6].trim(), p[7].trim()));
                }
            }
        }
        return list;
    }

    public static void writeAdvisors(File file, List<Advisor> list) throws IOException {
        ensureParentDir(file);
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8))) {
            writer.write("Id,FullName,Gender,DateOfBirth,Email,Phone,AdvisorId,FacultyId");
            writer.newLine();
            for (Advisor a : list) {
                writer.write(String.format("%s,%s,%s,%s,%s,%s,%s,%s",
                        escapeCsv(a.getId()), escapeCsv(a.getFullName()), escapeCsv(a.getGender()), escapeCsv(a.getDateOfBirth()),
                        escapeCsv(a.getEmail()), escapeCsv(a.getPhone()), escapeCsv(a.getAdvisorId()), escapeCsv(a.getFacultyId())));
                writer.newLine();
            }
        }
    }

    // Subjects
    public static List<Subject> readSubjects(File file) throws IOException {
        List<Subject> list = new ArrayList<>();
        if (!file.exists()) return list;
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("SubjectId")) continue;
                String[] p = line.split(",", -1);
                if (p.length >= 5) {
                    list.add(new Subject(p[0].trim(), p[1].trim(), Integer.parseInt(p[2].trim()), Integer.parseInt(p[3].trim()), Integer.parseInt(p[4].trim())));
                }
            }
        }
        return list;
    }

    public static void writeSubjects(File file, List<Subject> list) throws IOException {
        ensureParentDir(file);
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8))) {
            writer.write("SubjectId,SubjectName,Credits,TheoryHours,PracticeHours");
            writer.newLine();
            for (Subject s : list) {
                writer.write(String.format("%s,%s,%d,%d,%d", escapeCsv(s.getSubjectId()), escapeCsv(s.getSubjectName()), s.getCredits(), s.getTheoryHours(), s.getPracticeHours()));
                writer.newLine();
            }
        }
    }

    // Students
    public static List<Student> readStudents(File file) throws IOException {
        List<Student> list = new ArrayList<>();
        if (!file.exists()) return list;
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("Id")) continue;
                String[] p = line.split(",", -1);
                if (p.length >= 10) {
                    list.add(new Student(p[0].trim(), p[1].trim(), p[2].trim(), p[3].trim(), p[4].trim(), p[5].trim(), p[6].trim(), p[7].trim(), p[8].trim(), p[9].trim()));
                }
            }
        }
        return list;
    }

    public static void writeStudents(File file, List<Student> list) throws IOException {
        ensureParentDir(file);
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8))) {
            writer.write("Id,FullName,Gender,DateOfBirth,Email,Phone,StudentId,Hometown,ClassId,AdvisorId");
            writer.newLine();
            for (Student s : list) {
                writer.write(String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,%s",
                        escapeCsv(s.getId()), escapeCsv(s.getFullName()), escapeCsv(s.getGender()), escapeCsv(s.getDateOfBirth()),
                        escapeCsv(s.getEmail()), escapeCsv(s.getPhone()), escapeCsv(s.getStudentId()), escapeCsv(s.getHometown()),
                        escapeCsv(s.getClassId()), escapeCsv(s.getAdvisorId())));
                writer.newLine();
            }
        }
    }

    // Grades
    public static List<Grade> readGrades(File file) throws IOException {
        List<Grade> list = new ArrayList<>();
        if (!file.exists()) return list;
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("GradeId")) continue;
                String[] p = line.split(",", -1);
                if (p.length >= 6) {
                    list.add(new Grade(p[0].trim(), p[1].trim(), p[2].trim(), Double.parseDouble(p[3].trim()), Double.parseDouble(p[4].trim()), Double.parseDouble(p[5].trim())));
                }
            }
        }
        return list;
    }

    public static void writeGrades(File file, List<Grade> list) throws IOException {
        ensureParentDir(file);
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8))) {
            writer.write("GradeId,StudentId,SubjectId,AttendanceScore,MidtermScore,FinalScore");
            writer.newLine();
            for (Grade g : list) {
                writer.write(String.format("%s,%s,%s,%.2f,%.2f,%.2f",
                        escapeCsv(g.getGradeId()), escapeCsv(g.getStudentId()), escapeCsv(g.getSubjectId()),
                        g.getAttendanceScore(), g.getMidtermScore(), g.getFinalScore()));
                writer.newLine();
            }
        }
    }

    private static void ensureParentDir(File file) {
        File parent = file.getParentFile();
        if (parent != null && !parent.exists()) {
            parent.mkdirs();
        }
    }

    private static String escapeCsv(String value) {
        if (value == null) return "";
        if (value.contains(",") || value.contains("\"") || value.contains("\n")) {
            return "\"" + value.replace("\"", "\"\"") + "\"";
        }
        return value;
    }
}
