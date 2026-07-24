package com.studentmanagement.view;

import com.studentmanagement.model.*;
import com.studentmanagement.service.StudentService;
import com.studentmanagement.service.SubjectService;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

/**
 * Custom JTable models for all 6 entities in the system.
 */
public class CustomTableModels {

    // Faculty Table Model
    public static class FacultyTableModel extends AbstractTableModel {
        private final String[] cols = {"Mã Khoa", "Tên Khoa", "Trưởng Khoa", "Số Điện Thoại"};
        private List<Faculty> list = new ArrayList<>();

        public void setData(List<Faculty> list) {
            this.list = new ArrayList<>(list);
            fireTableDataChanged();
        }

        public Faculty getAt(int row) {
            return (row >= 0 && row < list.size()) ? list.get(row) : null;
        }

        @Override public int getRowCount() { return list.size(); }
        @Override public int getColumnCount() { return cols.length; }
        @Override public String getColumnName(int c) { return cols[c]; }
        @Override public Object getValueAt(int r, int c) {
            Faculty f = list.get(r);
            switch (c) {
                case 0: return f.getFacultyId();
                case 1: return f.getFacultyName();
                case 2: return f.getDeanName();
                case 3: return f.getPhone();
                default: return null;
            }
        }
    }

    // Class Table Model
    public static class ClassTableModel extends AbstractTableModel {
        private final String[] cols = {"Mã Lớp", "Tên Lớp", "Mã Khoa", "Khóa Học"};
        private List<StudentClass> list = new ArrayList<>();

        public void setData(List<StudentClass> list) {
            this.list = new ArrayList<>(list);
            fireTableDataChanged();
        }

        public StudentClass getAt(int row) {
            return (row >= 0 && row < list.size()) ? list.get(row) : null;
        }

        @Override public int getRowCount() { return list.size(); }
        @Override public int getColumnCount() { return cols.length; }
        @Override public String getColumnName(int c) { return cols[c]; }
        @Override public Object getValueAt(int r, int c) {
            StudentClass sc = list.get(r);
            switch (c) {
                case 0: return sc.getClassId();
                case 1: return sc.getClassName();
                case 2: return sc.getFacultyId();
                case 3: return sc.getAcademicYear();
                default: return null;
            }
        }
    }

    // Advisor Table Model
    public static class AdvisorTableModel extends AbstractTableModel {
        private final String[] cols = {"Mã CVHT", "Họ và Tên", "Giới Tính", "Khoa Trực Thuộc", "Email", "Số Điện Thoại"};
        private List<Advisor> list = new ArrayList<>();

        public void setData(List<Advisor> list) {
            this.list = new ArrayList<>(list);
            fireTableDataChanged();
        }

        public Advisor getAt(int row) {
            return (row >= 0 && row < list.size()) ? list.get(row) : null;
        }

        @Override public int getRowCount() { return list.size(); }
        @Override public int getColumnCount() { return cols.length; }
        @Override public String getColumnName(int c) { return cols[c]; }
        @Override public Object getValueAt(int r, int c) {
            Advisor a = list.get(r);
            switch (c) {
                case 0: return a.getAdvisorId();
                case 1: return a.getFullName();
                case 2: return a.getGender();
                case 3: return a.getFacultyId();
                case 4: return a.getEmail();
                case 5: return a.getPhone();
                default: return null;
            }
        }
    }

    // Subject Table Model
    public static class SubjectTableModel extends AbstractTableModel {
        private final String[] cols = {"Mã Môn", "Tên Môn Học", "Số Tín Chỉ", "Tiết Lý Thuyết", "Tiết Thực Hành"};
        private List<Subject> list = new ArrayList<>();

        public void setData(List<Subject> list) {
            this.list = new ArrayList<>(list);
            fireTableDataChanged();
        }

        public Subject getAt(int row) {
            return (row >= 0 && row < list.size()) ? list.get(row) : null;
        }

        @Override public int getRowCount() { return list.size(); }
        @Override public int getColumnCount() { return cols.length; }
        @Override public String getColumnName(int c) { return cols[c]; }
        @Override public Object getValueAt(int r, int c) {
            Subject s = list.get(r);
            switch (c) {
                case 0: return s.getSubjectId();
                case 1: return s.getSubjectName();
                case 2: return s.getCredits();
                case 3: return s.getTheoryHours();
                case 4: return s.getPracticeHours();
                default: return null;
            }
        }
    }

    // Student Table Model
    public static class StudentTableModel extends AbstractTableModel {
        private final String[] cols = {"Mã SV", "Họ và Tên", "Giới Tính", "Ngày Sinh", "Quê Quán", "Mã Lớp", "Mã CVHT", "Email", "Số ĐT"};
        private List<Student> list = new ArrayList<>();

        public void setData(List<Student> list) {
            this.list = new ArrayList<>(list);
            fireTableDataChanged();
        }

        public Student getAt(int row) {
            return (row >= 0 && row < list.size()) ? list.get(row) : null;
        }

        @Override public int getRowCount() { return list.size(); }
        @Override public int getColumnCount() { return cols.length; }
        @Override public String getColumnName(int c) { return cols[c]; }
        @Override public Object getValueAt(int r, int c) {
            Student s = list.get(r);
            switch (c) {
                case 0: return s.getStudentId();
                case 1: return s.getFullName();
                case 2: return s.getGender();
                case 3: return s.getDateOfBirth();
                case 4: return s.getHometown();
                case 5: return s.getClassId();
                case 6: return s.getAdvisorId();
                case 7: return s.getEmail();
                case 8: return s.getPhone();
                default: return null;
            }
        }
    }

    // Grade Table Model
    public static class GradeTableModel extends AbstractTableModel {
        private final String[] cols = {"Mã Điểm", "Mã SV", "Họ và Tên SV", "Mã Môn", "Tên Môn Học", "C.Cần (10%)", "G.Kỳ (30%)", "C.Kỳ (60%)", "Tổng (Thang 10)", "Tổng (Thang 4)", "Điểm Chữ", "Xếp Loại"};
        private List<Grade> list = new ArrayList<>();
        private StudentService studentService;
        private SubjectService subjectService;

        public GradeTableModel() {}

        public GradeTableModel(StudentService studentService, SubjectService subjectService) {
            this.studentService = studentService;
            this.subjectService = subjectService;
        }

        public void setServices(StudentService studentService, SubjectService subjectService) {
            this.studentService = studentService;
            this.subjectService = subjectService;
        }

        public void setData(List<Grade> list) {
            this.list = new ArrayList<>(list);
            fireTableDataChanged();
        }

        public Grade getAt(int row) {
            return (row >= 0 && row < list.size()) ? list.get(row) : null;
        }

        @Override public int getRowCount() { return list.size(); }
        @Override public int getColumnCount() { return cols.length; }
        @Override public String getColumnName(int c) { return cols[c]; }
        @Override public Object getValueAt(int r, int c) {
            Grade g = list.get(r);
            switch (c) {
                case 0: return g.getGradeId();
                case 1: return g.getStudentId();
                case 2: {
                    if (studentService != null) {
                        Student s = studentService.getStudentById(g.getStudentId());
                        if (s != null) return s.getFullName();
                    }
                    return "";
                }
                case 3: return g.getSubjectId();
                case 4: {
                    if (subjectService != null) {
                        Subject sub = subjectService.getSubjectById(g.getSubjectId());
                        if (sub != null) return sub.getSubjectName();
                    }
                    return "";
                }
                case 5: return String.format("%.1f", g.getAttendanceScore());
                case 6: return String.format("%.1f", g.getMidtermScore());
                case 7: return String.format("%.1f", g.getFinalScore());
                case 8: return String.format("%.2f", g.calculateTotal10());
                case 9: return String.format("%.2f", g.calculateTotal4());
                case 10: return g.getLetterGrade();
                case 11: return g.getRank();
                default: return null;
            }
        }
    }
}
