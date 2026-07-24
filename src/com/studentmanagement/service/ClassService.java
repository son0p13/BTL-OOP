package com.studentmanagement.service;

import com.studentmanagement.exception.DuplicateEntityException;
import com.studentmanagement.exception.InvalidDataException;
import com.studentmanagement.model.StudentClass;
import com.studentmanagement.repository.DataStore;
import com.studentmanagement.util.ValidationUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service for Student Class Management (Video #5).
 */
public class ClassService {

    private final DataStore dataStore = DataStore.getInstance();

    public List<StudentClass> getAllClasses() {
        return new ArrayList<>(dataStore.getClasses());
    }

    public void addClass(StudentClass sc) throws InvalidDataException, DuplicateEntityException {
        validateClass(sc);
        if (existsById(sc.getClassId())) {
            throw new DuplicateEntityException("Mã Lớp '" + sc.getClassId() + "' đã tồn tại!");
        }
        dataStore.getClasses().add(sc);
        dataStore.saveClasses();
    }

    public void updateClass(StudentClass sc) throws InvalidDataException {
        validateClass(sc);
        List<StudentClass> list = dataStore.getClasses();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getClassId().equalsIgnoreCase(sc.getClassId())) {
                list.set(i, sc);
                dataStore.saveClasses();
                return;
            }
        }
        throw new InvalidDataException("Không tìm thấy Lớp có mã: " + sc.getClassId());
    }

    public void deleteClass(String classId) throws InvalidDataException {
        boolean removed = dataStore.getClasses().removeIf(c -> c.getClassId().equalsIgnoreCase(classId));
        if (!removed) {
            throw new InvalidDataException("Không tìm thấy Lớp có mã: " + classId);
        }
        dataStore.saveClasses();
    }

    public List<StudentClass> searchClasses(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) return getAllClasses();
        String k = keyword.trim().toLowerCase();
        return getAllClasses().stream()
                .filter(c -> c.getClassId().toLowerCase().contains(k) || c.getClassName().toLowerCase().contains(k) || c.getFacultyId().toLowerCase().contains(k))
                .collect(Collectors.toList());
    }

    public boolean existsById(String classId) {
        return dataStore.getClasses().stream().anyMatch(c -> c.getClassId().equalsIgnoreCase(classId));
    }

    private void validateClass(StudentClass c) throws InvalidDataException {
        if (c == null) throw new InvalidDataException("Thông tin Lớp không được rỗng!");
        ValidationUtil.validateNotEmpty(c.getClassId(), "Mã Lớp");
        ValidationUtil.validateNotEmpty(c.getClassName(), "Tên Lớp");
    }
}
