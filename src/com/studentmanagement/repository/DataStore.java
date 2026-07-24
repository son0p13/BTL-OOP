package com.studentmanagement.repository;

import com.studentmanagement.model.*;
import com.studentmanagement.util.CsvUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DataStore {

    private static DataStore instance;

    private final String dataDir = "data";
    private final File usersFile = new File(dataDir, "users.csv");
    private final File facultiesFile = new File(dataDir, "faculties.csv");
    private final File classesFile = new File(dataDir, "classes.csv");
    private final File advisorsFile = new File(dataDir, "advisors.csv");
    private final File subjectsFile = new File(dataDir, "subjects.csv");
    private final File studentsFile = new File(dataDir, "students.csv");
    private final File gradesFile = new File(dataDir, "grades.csv");

    private final List<User> users = new ArrayList<>();
    private final List<Faculty> faculties = new ArrayList<>();
    private final List<StudentClass> classes = new ArrayList<>();
    private final List<Advisor> advisors = new ArrayList<>();
    private final List<Subject> subjects = new ArrayList<>();
    private final List<Student> students = new ArrayList<>();
    private final List<Grade> grades = new ArrayList<>();

    private DataStore() {
        loadAllData();
    }

    public static synchronized DataStore getInstance() {
        if (instance == null) {
            instance = new DataStore();
        }
        return instance;
    }

    public void loadAllData() {
        try {
            users.clear();
            users.addAll(CsvUtil.readUsers(usersFile));
            if (users.isEmpty()) {
                users.add(new User("admin", "admin", "Quản Trị Viên Hệ Thống", "ADMIN"));
                users.add(new User("giangvien", "123456", "Giảng Viên Vũ Văn A", "GIANGVIEN"));
                saveUsers();
            }

            faculties.clear();
            faculties.addAll(CsvUtil.readFaculties(facultiesFile));
            if (faculties.isEmpty()) {
                faculties.add(new Faculty("CNTT", "Công Nghệ Thông Tin", "PGS.TS Nguyễn Văn A", "0988111222"));
                faculties.add(new Faculty("KT", "Kinh Tế & Quản Trị", "TS. Trần Thị B", "0977222333"));
                saveFaculties();
            }

            classes.clear();
            classes.addAll(CsvUtil.readClasses(classesFile));
            if (classes.isEmpty()) {
                classes.add(new StudentClass("CNTT01", "Công Nghệ Thông Tin 1", "CNTT", "2022-2026"));
                classes.add(new StudentClass("KT01", "Kinh Tế Đầu Tư 1", "KT", "2022-2026"));
                saveClasses();
            }

            advisors.clear();
            advisors.addAll(CsvUtil.readAdvisors(advisorsFile));
            if (advisors.isEmpty()) {
                advisors.add(new Advisor("GV001", "ThS. Lê Hoàng Cường", "Nam", "15/04/1985", "cuong.lh@uni.edu.vn", "0912345678", "GV001", "CNTT"));
                advisors.add(new Advisor("GV002", "TS. Phạm Minh Dung", "Nữ", "20/10/1988", "dung.pm@uni.edu.vn", "0934567890", "GV002", "KT"));
                saveAdvisors();
            }

            subjects.clear();
            subjects.addAll(CsvUtil.readSubjects(subjectsFile));
            if (subjects.isEmpty()) {
                subjects.add(new Subject("JAVA01", "Lập Trình Hướng Đối Tượng Java", 3, 30, 30));
                subjects.add(new Subject("CSDL01", "Cơ Sở Dữ Liệu", 3, 30, 15));
                subjects.add(new Subject("MKT01", "Nguyên Lý Marketing", 2, 30, 0));
                saveSubjects();
            }

            students.clear();
            students.addAll(CsvUtil.readStudents(studentsFile));
            if (students.isEmpty()) {
                students.add(new Student("SV001", "Nguyễn Văn An", "Nam", "12/05/2003", "an.nv@gmail.com", "0987654321", "SV001", "Hà Nội", "CNTT01", "GV001"));
                students.add(new Student("SV002", "Trần Thị Bích", "Nữ", "18/09/2003", "bich.tt@gmail.com", "0912345678", "SV002", "Đà Nẵng", "KT01", "GV002"));
                saveStudents();
            }

            grades.clear();
            grades.addAll(CsvUtil.readGrades(gradesFile));
            if (grades.isEmpty()) {
                grades.add(new Grade("G001", "SV001", "JAVA01", 9.0, 8.5, 9.5));
                grades.add(new Grade("G002", "SV001", "CSDL01", 8.0, 7.5, 8.0));
                grades.add(new Grade("G003", "SV002", "MKT01", 9.5, 9.0, 9.0));
                saveGrades();
            }
        } catch (IOException e) {
            System.err.println("Lỗi khi nạp dữ liệu: " + e.getMessage());
        }
    }

    public void saveUsers() { try { CsvUtil.writeUsers(usersFile, users); } catch (IOException ignored) {} }
    public void saveFaculties() { try { CsvUtil.writeFaculties(facultiesFile, faculties); } catch (IOException ignored) {} }
    public void saveClasses() { try { CsvUtil.writeClasses(classesFile, classes); } catch (IOException ignored) {} }
    public void saveAdvisors() { try { CsvUtil.writeAdvisors(advisorsFile, advisors); } catch (IOException ignored) {} }
    public void saveSubjects() { try { CsvUtil.writeSubjects(subjectsFile, subjects); } catch (IOException ignored) {} }
    public void saveStudents() { try { CsvUtil.writeStudents(studentsFile, students); } catch (IOException ignored) {} }
    public void saveGrades() { try { CsvUtil.writeGrades(gradesFile, grades); } catch (IOException ignored) {} }

    // Getters for entity lists
    public List<User> getUsers() { return users; }
    public List<Faculty> getFaculties() { return faculties; }
    public List<StudentClass> getClasses() { return classes; }
    public List<Advisor> getAdvisors() { return advisors; }
    public List<Subject> getSubjects() { return subjects; }
    public List<Student> getStudents() { return students; }
    public List<Grade> getGrades() { return grades; }
}
