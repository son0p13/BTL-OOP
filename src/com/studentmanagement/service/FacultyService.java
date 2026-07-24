package com.studentmanagement.service;

import com.studentmanagement.exception.DuplicateEntityException;
import com.studentmanagement.exception.InvalidDataException;
import com.studentmanagement.model.Faculty;
import com.studentmanagement.repository.DataStore;
import com.studentmanagement.util.ValidationUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FacultyService {

    private final DataStore dataStore = DataStore.getInstance();

    public List<Faculty> getAllFaculties() {
        return new ArrayList<>(dataStore.getFaculties());
    }

    public void addFaculty(Faculty faculty) throws InvalidDataException, DuplicateEntityException {
        validateFaculty(faculty);
        if (existsById(faculty.getFacultyId())) {
            throw new DuplicateEntityException("Mã Khoa '" + faculty.getFacultyId() + "' đã tồn tại!");
        }
        dataStore.getFaculties().add(faculty);
        dataStore.saveFaculties();
    }

    public void updateFaculty(Faculty faculty) throws InvalidDataException {
        validateFaculty(faculty);
        List<Faculty> list = dataStore.getFaculties();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getFacultyId().equalsIgnoreCase(faculty.getFacultyId())) {
                list.set(i, faculty);
                dataStore.saveFaculties();
                return;
            }
        }
        throw new InvalidDataException("Không tìm thấy Khoa có mã: " + faculty.getFacultyId());
    }

    public void deleteFaculty(String facultyId) throws InvalidDataException {
        boolean removed = dataStore.getFaculties().removeIf(f -> f.getFacultyId().equalsIgnoreCase(facultyId));
        if (!removed) {
            throw new InvalidDataException("Không tìm thấy Khoa có mã: " + facultyId);
        }
        dataStore.saveFaculties();
    }

    public List<Faculty> searchFaculties(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) return getAllFaculties();
        String k = keyword.trim().toLowerCase();
        return getAllFaculties().stream()
                .filter(f -> f.getFacultyId().toLowerCase().contains(k) || f.getFacultyName().toLowerCase().contains(k) || f.getDeanName().toLowerCase().contains(k))
                .collect(Collectors.toList());
    }

    public boolean existsById(String facultyId) {
        return dataStore.getFaculties().stream().anyMatch(f -> f.getFacultyId().equalsIgnoreCase(facultyId));
    }

    private void validateFaculty(Faculty f) throws InvalidDataException {
        if (f == null) throw new InvalidDataException("Thông tin Khoa không được rỗng!");
        ValidationUtil.validateNotEmpty(f.getFacultyId(), "Mã Khoa");
        ValidationUtil.validateNotEmpty(f.getFacultyName(), "Tên Khoa");
    }
}
