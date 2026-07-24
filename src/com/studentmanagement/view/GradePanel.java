package com.studentmanagement.view;

import com.studentmanagement.exception.AppException;
import com.studentmanagement.model.Grade;
import com.studentmanagement.model.Student;
import com.studentmanagement.model.Subject;
import com.studentmanagement.service.GradeService;
import com.studentmanagement.service.StudentService;
import com.studentmanagement.service.SubjectService;
import com.studentmanagement.util.FileReportUtil;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Grade Management Panel (Video #9 - END).
 */
public class GradePanel extends JPanel {

    private JTextField txtGradeId;
    private JComboBox<String> cbStudent;
    private JComboBox<String> cbSubject;
    private JTextField txtAttendanceScore;
    private JTextField txtMidtermScore;
    private JTextField txtFinalScore;

    private JButton btnAdd;
    private JButton btnEdit;
    private JButton btnDelete;
    private JButton btnClear;
    private JButton btnPrintTranscript;

    private JTable table;
    private CustomTableModels.GradeTableModel tableModel;

    private final GradeService gradeService;
    private final StudentService studentService;
    private final SubjectService subjectService;

    public GradePanel(GradeService gradeService, StudentService studentService, SubjectService subjectService) {
        this.gradeService = gradeService;
        this.studentService = studentService;
        this.subjectService = subjectService;
        initComponents();
        loadCombos();
        loadData();
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Form Panel (West)
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Nhập Điểm Môn Học (Video #9)"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        int row = 0;
        gbc.gridx = 0; gbc.gridy = row;
        formPanel.add(new JLabel("Mã Bảng Điểm (*):"), gbc);
        gbc.gridx = 1;
        txtGradeId = new JTextField(16);
        formPanel.add(txtGradeId, gbc);

        row++;
        gbc.gridx = 0; gbc.gridy = row;
        formPanel.add(new JLabel("Sinh Viên:"), gbc);
        gbc.gridx = 1;
        cbStudent = new JComboBox<>();
        formPanel.add(cbStudent, gbc);

        row++;
        gbc.gridx = 0; gbc.gridy = row;
        formPanel.add(new JLabel("Môn Học:"), gbc);
        gbc.gridx = 1;
        cbSubject = new JComboBox<>();
        formPanel.add(cbSubject, gbc);

        row++;
        gbc.gridx = 0; gbc.gridy = row;
        formPanel.add(new JLabel("Đ.Chuyên Cần (10%):"), gbc);
        gbc.gridx = 1;
        txtAttendanceScore = new JTextField("9.0", 16);
        formPanel.add(txtAttendanceScore, gbc);

        row++;
        gbc.gridx = 0; gbc.gridy = row;
        formPanel.add(new JLabel("Đ.Giữa Kỳ (30%):"), gbc);
        gbc.gridx = 1;
        txtMidtermScore = new JTextField("8.5", 16);
        formPanel.add(txtMidtermScore, gbc);

        row++;
        gbc.gridx = 0; gbc.gridy = row;
        formPanel.add(new JLabel("Đ.Cuối Kỳ (60%):"), gbc);
        gbc.gridx = 1;
        txtFinalScore = new JTextField("9.0", 16);
        formPanel.add(txtFinalScore, gbc);

        // Buttons
        row++;
        gbc.gridx = 0; gbc.gridy = row; gbc.gridwidth = 2;
        JPanel btnPanel = new JPanel(new GridLayout(3, 2, 8, 8));
        btnAdd = new JButton("Thêm Điểm");
        btnEdit = new JButton("Sửa Điểm");
        btnDelete = new JButton("Xóa Điểm");
        btnClear = new JButton("Làm Mới");
        btnPrintTranscript = new JButton("In Bảng Điểm TXT");

        btnPanel.add(btnAdd);
        btnPanel.add(btnEdit);
        btnPanel.add(btnDelete);
        btnPanel.add(btnClear);
        btnPanel.add(btnPrintTranscript);
        formPanel.add(btnPanel, gbc);

        add(formPanel, BorderLayout.WEST);

        // Table Panel (Center)
        tableModel = new CustomTableModels.GradeTableModel();
        table = new JTable(tableModel);
        table.setRowHeight(24);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Bảng Tổng Hợp Điểm Sinh Viên"));
        add(scrollPane, BorderLayout.CENTER);

        // Listeners
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) populateForm();
        });

        btnAdd.addActionListener(e -> onAdd());
        btnEdit.addActionListener(e -> onEdit());
        btnDelete.addActionListener(e -> onDelete());
        btnClear.addActionListener(e -> clearForm());
        btnPrintTranscript.addActionListener(e -> onPrintTranscript());
    }

    public void loadCombos() {
        cbStudent.removeAllItems();
        List<Student> students = studentService.getAllStudents();
        for (Student s : students) cbStudent.addItem(s.getStudentId());

        cbSubject.removeAllItems();
        List<Subject> subjects = subjectService.getAllSubjects();
        for (Subject s : subjects) cbSubject.addItem(s.getSubjectId());
    }

    private void loadData() {
        tableModel.setData(gradeService.getAllGrades());
    }

    private void populateForm() {
        int row = table.getSelectedRow();
        if (row >= 0) {
            Grade g = tableModel.getAt(row);
            if (g != null) {
                txtGradeId.setText(g.getGradeId());
                txtGradeId.setEditable(false);
                cbStudent.setSelectedItem(g.getStudentId());
                cbSubject.setSelectedItem(g.getSubjectId());
                txtAttendanceScore.setText(String.valueOf(g.getAttendanceScore()));
                txtMidtermScore.setText(String.valueOf(g.getMidtermScore()));
                txtFinalScore.setText(String.valueOf(g.getFinalScore()));
            }
        }
    }

    private void clearForm() {
        txtGradeId.setText("");
        txtGradeId.setEditable(true);
        txtAttendanceScore.setText("9.0");
        txtMidtermScore.setText("8.5");
        txtFinalScore.setText("9.0");
        table.clearSelection();
        loadCombos();
        loadData();
    }

    private void onAdd() {
        try {
            String gId = txtGradeId.getText().trim();
            String sId = (String) cbStudent.getSelectedItem();
            String subId = (String) cbSubject.getSelectedItem();
            double att = Double.parseDouble(txtAttendanceScore.getText().trim());
            double mid = Double.parseDouble(txtMidtermScore.getText().trim());
            double fin = Double.parseDouble(txtFinalScore.getText().trim());

            Grade g = new Grade(gId, sId, subId, att, mid, fin);
            gradeService.addGrade(g);
            JOptionPane.showMessageDialog(this, "Thêm Điểm thành công!", "Thông Báo", JOptionPane.INFORMATION_MESSAGE);
            clearForm();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập điểm dạng số từ 0 đến 10!", "Lỗi Định Dạng", JOptionPane.ERROR_MESSAGE);
        } catch (AppException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void onEdit() {
        try {
            String gId = txtGradeId.getText().trim();
            String sId = (String) cbStudent.getSelectedItem();
            String subId = (String) cbSubject.getSelectedItem();
            double att = Double.parseDouble(txtAttendanceScore.getText().trim());
            double mid = Double.parseDouble(txtMidtermScore.getText().trim());
            double fin = Double.parseDouble(txtFinalScore.getText().trim());

            Grade g = new Grade(gId, sId, subId, att, mid, fin);
            gradeService.updateGrade(g);
            JOptionPane.showMessageDialog(this, "Cập nhật Điểm thành công!", "Thông Báo", JOptionPane.INFORMATION_MESSAGE);
            clearForm();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập điểm dạng số từ 0 đến 10!", "Lỗi Định Dạng", JOptionPane.ERROR_MESSAGE);
        } catch (AppException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void onDelete() {
        String id = txtGradeId.getText().trim();
        if (id.isEmpty()) return;
        int confirm = JOptionPane.showConfirmDialog(this, "Xác nhận xóa Điểm " + id + "?", "Xác Nhận", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                gradeService.deleteGrade(id);
                JOptionPane.showMessageDialog(this, "Xóa Điểm thành công!", "Thông Báo", JOptionPane.INFORMATION_MESSAGE);
                clearForm();
            } catch (AppException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void onPrintTranscript() {
        String studentId = (String) cbStudent.getSelectedItem();
        if (studentId == null || studentId.isEmpty()) return;

        try {
            Student s = studentService.getAllStudents().stream()
                    .filter(st -> st.getStudentId().equalsIgnoreCase(studentId))
                    .findFirst().orElse(null);

            if (s == null) {
                JOptionPane.showMessageDialog(this, "Không tìm thấy thông tin sinh viên!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            List<Grade> studentGrades = gradeService.getGradesByStudentId(studentId);

            JFileChooser chooser = new JFileChooser();
            chooser.setDialogTitle("In Bảng Điểm Sinh Viên " + studentId);
            chooser.setFileFilter(new FileNameExtensionFilter("Text Files (*.txt)", "txt"));
            chooser.setSelectedFile(new File("bang_diem_" + studentId + ".txt"));

            if (chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                File file = chooser.getSelectedFile();
                if (!file.getName().endsWith(".txt")) {
                    file = new File(file.getAbsolutePath() + ".txt");
                }
                FileReportUtil.exportStudentTranscript(file, s, studentGrades);
                JOptionPane.showMessageDialog(this, "In bảng điểm TXT thành công!\nĐường dẫn: " + file.getAbsolutePath(), "Thông Báo", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Lỗi in bảng điểm: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
}
