package com.studentmanagement.service;

import com.studentmanagement.exception.DuplicateEntityException;
import com.studentmanagement.exception.InvalidDataException;
import com.studentmanagement.model.Grade;
import com.studentmanagement.repository.DataStore;
import com.studentmanagement.util.ValidationUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service for Grade Management (Video #9).
 */
public class GradeService {

    private final DataStore dataStore = DataStore.getInstance();

    public List<Grade> getAllGrades() {
        return new ArrayList<>(dataStore.getGrades());
    }

    public void addGrade(Grade grade) throws InvalidDataException, DuplicateEntityException {
        validateGrade(grade);
        if (existsById(grade.getGradeId())) {
            throw new DuplicateEntityException("Mã Điểm '" + grade.getGradeId() + "' đã tồn tại!");
        }
        // Check if student already has a grade for this subject
        boolean duplicateSubj = dataStore.getGrades().stream()
                .anyMatch(g -> g.getStudentId().equalsIgnoreCase(grade.getStudentId()) && g.getSubjectId().equalsIgnoreCase(grade.getSubjectId()));
        if (duplicateSubj) {
            throw new DuplicateEntityException("Sinh viên " + grade.getStudentId() + " đã có điểm cho môn " + grade.getSubjectId() + "!");
        }
        dataStore.getGrades().add(grade);
        dataStore.saveGrades();
    }

    public void updateGrade(Grade grade) throws InvalidDataException {
        validateGrade(grade);
        List<Grade> list = dataStore.getGrades();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getGradeId().equalsIgnoreCase(grade.getGradeId())) {
                list.set(i, grade);
                dataStore.saveGrades();
                return;
            }
        }
        throw new InvalidDataException("Không tìm thấy Bảng Điểm có mã: " + grade.getGradeId());
    }

    public void deleteGrade(String gradeId) throws InvalidDataException {
        boolean removed = dataStore.getGrades().removeIf(g -> g.getGradeId().equalsIgnoreCase(gradeId));
        if (!removed) {
            throw new InvalidDataException("Không tìm thấy Bảng Điểm có mã: " + gradeId);
        }
        dataStore.saveGrades();
    }

    public List<Grade> getGradesByStudentId(String studentId) {
        return getAllGrades().stream()
                .filter(g -> g.getStudentId().equalsIgnoreCase(studentId))
                .collect(Collectors.toList());
    }

    public List<Grade> getGradesBySubjectId(String subjectId) {
        return getAllGrades().stream()
                .filter(g -> g.getSubjectId().equalsIgnoreCase(subjectId))
                .collect(Collectors.toList());
    }

    public boolean existsById(String gradeId) {
        return dataStore.getGrades().stream().anyMatch(g -> g.getGradeId().equalsIgnoreCase(gradeId));
    }

    private void validateGrade(Grade g) throws InvalidDataException {
        if (g == null) throw new InvalidDataException("Thông tin Bảng Điểm không được rỗng!");
        ValidationUtil.validateNotEmpty(g.getGradeId(), "Mã Điểm");
        ValidationUtil.validateNotEmpty(g.getStudentId(), "Mã Sinh Viên");
        ValidationUtil.validateNotEmpty(g.getSubjectId(), "Mã Môn Học");
        ValidationUtil.validateScore(g.getAttendanceScore(), "Điểm Chuyên Cần");
        ValidationUtil.validateScore(g.getMidtermScore(), "Điểm Giữa Kỳ");
        ValidationUtil.validateScore(g.getFinalScore(), "Điểm Cuối Kỳ");
    }
}
