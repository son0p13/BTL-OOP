package com.studentmanagement.view;

import com.studentmanagement.exception.AppException;
import com.studentmanagement.model.Subject;
import com.studentmanagement.service.SubjectService;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Subject / Course Management Panel (Video #7).
 */
public class SubjectPanel extends JPanel {

    private JTextField txtSubjectId;
    private JTextField txtSubjectName;
    private JTextField txtCredits;
    private JTextField txtTheoryHours;
    private JTextField txtPracticeHours;

    private JTextField txtSearch;
    private JButton btnSearch;

    private JButton btnAdd;
    private JButton btnEdit;
    private JButton btnDelete;
    private JButton btnClear;

    private JTable table;
    private CustomTableModels.SubjectTableModel tableModel;

    private final SubjectService subjectService;

    public SubjectPanel(SubjectService subjectService) {
        this.subjectService = subjectService;
        initComponents();
        loadData();
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Form Panel (West)
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Thông Tin Môn Học"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        int row = 0;
        gbc.gridx = 0; gbc.gridy = row;
        formPanel.add(new JLabel("Mã Môn (*):"), gbc);
        gbc.gridx = 1;
        txtSubjectId = new JTextField(16);
        formPanel.add(txtSubjectId, gbc);

        row++;
        gbc.gridx = 0; gbc.gridy = row;
        formPanel.add(new JLabel("Tên Môn (*):"), gbc);
        gbc.gridx = 1;
        txtSubjectName = new JTextField(16);
        formPanel.add(txtSubjectName, gbc);

        row++;
        gbc.gridx = 0; gbc.gridy = row;
        formPanel.add(new JLabel("Số Tín Chỉ (*):"), gbc);
        gbc.gridx = 1;
        txtCredits = new JTextField("3", 16);
        formPanel.add(txtCredits, gbc);

        row++;
        gbc.gridx = 0; gbc.gridy = row;
        formPanel.add(new JLabel("Tiết Lý Thuyết:"), gbc);
        gbc.gridx = 1;
        txtTheoryHours = new JTextField("30", 16);
        formPanel.add(txtTheoryHours, gbc);

        row++;
        gbc.gridx = 0; gbc.gridy = row;
        formPanel.add(new JLabel("Tiết Thực Hành:"), gbc);
        gbc.gridx = 1;
        txtPracticeHours = new JTextField("15", 16);
        formPanel.add(txtPracticeHours, gbc);

        // Buttons
        row++;
        gbc.gridx = 0; gbc.gridy = row; gbc.gridwidth = 2;
        JPanel btnPanel = new JPanel(new GridLayout(2, 2, 8, 8));
        btnAdd = new JButton("Thêm Môn");
        btnEdit = new JButton("Sửa Môn");
        btnDelete = new JButton("Xóa Môn");
        btnClear = new JButton("Làm Mới");

        btnPanel.add(btnAdd);
        btnPanel.add(btnEdit);
        btnPanel.add(btnDelete);
        btnPanel.add(btnClear);
        formPanel.add(btnPanel, gbc);

        add(formPanel, BorderLayout.WEST);

        // Table & Search Panel (Center)
        JPanel centerPanel = new JPanel(new BorderLayout(5, 5));

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.setBorder(BorderFactory.createTitledBorder("Tìm Kiếm Môn Học"));
        searchPanel.add(new JLabel("Từ khóa:"));
        txtSearch = new JTextField(18);
        searchPanel.add(txtSearch);
        btnSearch = new JButton("Tìm Kiếm");
        searchPanel.add(btnSearch);
        centerPanel.add(searchPanel, BorderLayout.NORTH);

        tableModel = new CustomTableModels.SubjectTableModel();
        table = new JTable(tableModel);
        table.setRowHeight(24);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Danh Sách Môn Học"));
        centerPanel.add(scrollPane, BorderLayout.CENTER);

        add(centerPanel, BorderLayout.CENTER);

        // Listeners
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) populateForm();
        });

        btnAdd.addActionListener(e -> onAdd());
        btnEdit.addActionListener(e -> onEdit());
        btnDelete.addActionListener(e -> onDelete());
        btnClear.addActionListener(e -> clearForm());
        btnSearch.addActionListener(e -> onSearch());
    }

    private void loadData() {
        tableModel.setData(subjectService.getAllSubjects());
    }

    private void populateForm() {
        int row = table.getSelectedRow();
        if (row >= 0) {
            Subject s = tableModel.getAt(row);
            if (s != null) {
                txtSubjectId.setText(s.getSubjectId());
                txtSubjectId.setEditable(false);
                txtSubjectName.setText(s.getSubjectName());
                txtCredits.setText(String.valueOf(s.getCredits()));
                txtTheoryHours.setText(String.valueOf(s.getTheoryHours()));
                txtPracticeHours.setText(String.valueOf(s.getPracticeHours()));
            }
        }
    }

    private void clearForm() {
        txtSubjectId.setText("");
        txtSubjectId.setEditable(true);
        txtSubjectName.setText("");
        txtCredits.setText("3");
        txtTheoryHours.setText("30");
        txtPracticeHours.setText("15");
        table.clearSelection();
        loadData();
    }

    private void onAdd() {
        try {
            int cr = Integer.parseInt(txtCredits.getText().trim());
            int th = Integer.parseInt(txtTheoryHours.getText().trim());
            int pr = Integer.parseInt(txtPracticeHours.getText().trim());
            Subject s = new Subject(txtSubjectId.getText().trim(), txtSubjectName.getText().trim(), cr, th, pr);
            subjectService.addSubject(s);
            JOptionPane.showMessageDialog(this, "Thêm Môn thành công!", "Thông Báo", JOptionPane.INFORMATION_MESSAGE);
            clearForm();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập số hợp lệ cho tín chỉ và số tiết!", "Lỗi Định Dạng", JOptionPane.ERROR_MESSAGE);
        } catch (AppException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void onEdit() {
        try {
            int cr = Integer.parseInt(txtCredits.getText().trim());
            int th = Integer.parseInt(txtTheoryHours.getText().trim());
            int pr = Integer.parseInt(txtPracticeHours.getText().trim());
            Subject s = new Subject(txtSubjectId.getText().trim(), txtSubjectName.getText().trim(), cr, th, pr);
            subjectService.updateSubject(s);
            JOptionPane.showMessageDialog(this, "Cập nhật Môn thành công!", "Thông Báo", JOptionPane.INFORMATION_MESSAGE);
            clearForm();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập số hợp lệ cho tín chỉ và số tiết!", "Lỗi Định Dạng", JOptionPane.ERROR_MESSAGE);
        } catch (AppException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void onDelete() {
        String id = txtSubjectId.getText().trim();
        if (id.isEmpty()) return;
        int confirm = JOptionPane.showConfirmDialog(this, "Xác nhận xóa Môn Học " + id + "?", "Xác Nhận", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                subjectService.deleteSubject(id);
                JOptionPane.showMessageDialog(this, "Xóa Môn Học thành công!", "Thông Báo", JOptionPane.INFORMATION_MESSAGE);
                clearForm();
            } catch (AppException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void onSearch() {
        tableModel.setData(subjectService.searchSubjects(txtSearch.getText().trim()));
    }
}
