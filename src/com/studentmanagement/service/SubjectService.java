package com.studentmanagement.service;

import com.studentmanagement.exception.DuplicateEntityException;
import com.studentmanagement.exception.InvalidDataException;
import com.studentmanagement.model.Subject;
import com.studentmanagement.repository.DataStore;
import com.studentmanagement.util.ValidationUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service for Subject / Course Management (Video #7).
 */
public class SubjectService {

    private final DataStore dataStore = DataStore.getInstance();

    public List<Subject> getAllSubjects() {
        return new ArrayList<>(dataStore.getSubjects());
    }

    public void addSubject(Subject subject) throws InvalidDataException, DuplicateEntityException {
        validateSubject(subject);
        if (existsById(subject.getSubjectId())) {
            throw new DuplicateEntityException("Mã Môn Học '" + subject.getSubjectId() + "' đã tồn tại!");
        }
        dataStore.getSubjects().add(subject);
        dataStore.saveSubjects();
    }

    public void updateSubject(Subject subject) throws InvalidDataException {
        validateSubject(subject);
        List<Subject> list = dataStore.getSubjects();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getSubjectId().equalsIgnoreCase(subject.getSubjectId())) {
                list.set(i, subject);
                dataStore.saveSubjects();
                return;
            }
        }
        throw new InvalidDataException("Không tìm thấy Môn Học có mã: " + subject.getSubjectId());
    }

    public void deleteSubject(String subjectId) throws InvalidDataException {
        boolean removed = dataStore.getSubjects().removeIf(s -> s.getSubjectId().equalsIgnoreCase(subjectId));
        if (!removed) {
            throw new InvalidDataException("Không tìm thấy Môn Học có mã: " + subjectId);
        }
        dataStore.saveSubjects();
    }

    public List<Subject> searchSubjects(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) return getAllSubjects();
        String k = keyword.trim().toLowerCase();
        return getAllSubjects().stream()
                .filter(s -> s.getSubjectId().toLowerCase().contains(k) || s.getSubjectName().toLowerCase().contains(k))
                .collect(Collectors.toList());
    }

    public boolean existsById(String subjectId) {
        return dataStore.getSubjects().stream().anyMatch(s -> s.getSubjectId().equalsIgnoreCase(subjectId));
    }

    public Subject getSubjectById(String subjectId) {
        if (subjectId == null || subjectId.trim().isEmpty()) return null;
        return dataStore.getSubjects().stream()
                .filter(s -> s.getSubjectId().equalsIgnoreCase(subjectId.trim()))
                .findFirst()
                .orElse(null);
    }

    private void validateSubject(Subject s) throws InvalidDataException {
        if (s == null) throw new InvalidDataException("Thông tin Môn Học không được rỗng!");
        ValidationUtil.validateNotEmpty(s.getSubjectId(), "Mã Môn Học");
        ValidationUtil.validateNotEmpty(s.getSubjectName(), "Tên Môn Học");
        if (s.getCredits() <= 0) throw new InvalidDataException("Số tín chỉ phải lớn hơn 0!");
    }
}
