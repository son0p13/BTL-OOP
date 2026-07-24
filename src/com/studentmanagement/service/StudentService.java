package com.studentmanagement.service;

import com.studentmanagement.exception.DuplicateEntityException;
import com.studentmanagement.exception.InvalidDataException;
import com.studentmanagement.model.Student;
import com.studentmanagement.repository.DataStore;
import com.studentmanagement.util.ValidationUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service for Student Management (Video #8).
 */
public class StudentService {

    private final DataStore dataStore = DataStore.getInstance();

    public List<Student> getAllStudents() {
        return new ArrayList<>(dataStore.getStudents());
    }

    public void addStudent(Student student) throws InvalidDataException, DuplicateEntityException {
        validateStudent(student);
        if (existsById(student.getStudentId())) {
            throw new DuplicateEntityException("Mã Sinh Viên '" + student.getStudentId() + "' đã tồn tại!");
        }
        dataStore.getStudents().add(student);
        dataStore.saveStudents();
    }

    public void updateStudent(Student student) throws InvalidDataException {
        validateStudent(student);
        List<Student> list = dataStore.getStudents();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getStudentId().equalsIgnoreCase(student.getStudentId())) {
                list.set(i, student);
                dataStore.saveStudents();
                return;
            }
        }
        throw new InvalidDataException("Không tìm thấy Sinh Viên có mã: " + student.getStudentId());
    }

    public void deleteStudent(String studentId) throws InvalidDataException {
        boolean removed = dataStore.getStudents().removeIf(s -> s.getStudentId().equalsIgnoreCase(studentId));
        if (!removed) {
            throw new InvalidDataException("Không tìm thấy Sinh Viên có mã: " + studentId);
        }
        dataStore.saveStudents();
    }

    public List<Student> searchStudents(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) return getAllStudents();
        String k = keyword.trim().toLowerCase();
        return getAllStudents().stream()
                .filter(s -> s.getStudentId().toLowerCase().contains(k) ||
                        s.getFullName().toLowerCase().contains(k) ||
                        s.getClassId().toLowerCase().contains(k) ||
                        s.getHometown().toLowerCase().contains(k))
                .collect(Collectors.toList());
    }

    public boolean existsById(String studentId) {
        return dataStore.getStudents().stream().anyMatch(s -> s.getStudentId().equalsIgnoreCase(studentId));
    }

    private void validateStudent(Student s) throws InvalidDataException {
        if (s == null) throw new InvalidDataException("Thông tin Sinh Viên không được rỗng!");
        ValidationUtil.validateNotEmpty(s.getStudentId(), "Mã Sinh Viên");
        ValidationUtil.validateNotEmpty(s.getFullName(), "Họ và Tên Sinh Viên");
        ValidationUtil.validateEmail(s.getEmail());
        ValidationUtil.validatePhone(s.getPhone());
    }
}
