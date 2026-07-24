package com.studentmanagement.view;

import com.studentmanagement.exception.AppException;
import com.studentmanagement.model.Faculty;
import com.studentmanagement.model.StudentClass;
import com.studentmanagement.service.ClassService;
import com.studentmanagement.service.FacultyService;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Class Management Panel (Video #5).
 */
public class ClassPanel extends JPanel {

    private JTextField txtClassId;
    private JTextField txtClassName;
    private JComboBox<String> cbFaculty;
    private JTextField txtAcademicYear;

    private JTextField txtSearch;
    private JButton btnSearch;

    private JButton btnAdd;
    private JButton btnEdit;
    private JButton btnDelete;
    private JButton btnClear;

    private JTable table;
    private CustomTableModels.ClassTableModel tableModel;

    private final ClassService classService;
    private final FacultyService facultyService;

    public ClassPanel(ClassService classService, FacultyService facultyService) {
        this.classService = classService;
        this.facultyService = facultyService;
        initComponents();
        loadFaculties();
        loadData();
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Form Panel (West)
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Thông Tin Lớp"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        int row = 0;
        gbc.gridx = 0; gbc.gridy = row;
        formPanel.add(new JLabel("Mã Lớp (*):"), gbc);
        gbc.gridx = 1;
        txtClassId = new JTextField(16);
        formPanel.add(txtClassId, gbc);

        row++;
        gbc.gridx = 0; gbc.gridy = row;
        formPanel.add(new JLabel("Tên Lớp (*):"), gbc);
        gbc.gridx = 1;
        txtClassName = new JTextField(16);
        formPanel.add(txtClassName, gbc);

        row++;
        gbc.gridx = 0; gbc.gridy = row;
        formPanel.add(new JLabel("Khoa Trực Thuộc:"), gbc);
        gbc.gridx = 1;
        cbFaculty = new JComboBox<>();
        formPanel.add(cbFaculty, gbc);

        row++;
        gbc.gridx = 0; gbc.gridy = row;
        formPanel.add(new JLabel("Khóa Học:"), gbc);
        gbc.gridx = 1;
        txtAcademicYear = new JTextField("2022-2026", 16);
        formPanel.add(txtAcademicYear, gbc);

        // Buttons
        row++;
        gbc.gridx = 0; gbc.gridy = row; gbc.gridwidth = 2;
        JPanel btnPanel = new JPanel(new GridLayout(2, 2, 8, 8));
        btnAdd = new JButton("Thêm Lớp");
        btnEdit = new JButton("Sửa Lớp");
        btnDelete = new JButton("Xóa Lớp");
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
        searchPanel.setBorder(BorderFactory.createTitledBorder("Tìm Kiếm Lớp Học"));
        searchPanel.add(new JLabel("Từ khóa:"));
        txtSearch = new JTextField(18);
        searchPanel.add(txtSearch);
        btnSearch = new JButton("Tìm Kiếm");
        searchPanel.add(btnSearch);
        centerPanel.add(searchPanel, BorderLayout.NORTH);

        tableModel = new CustomTableModels.ClassTableModel();
        table = new JTable(tableModel);
        table.setRowHeight(24);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Danh Sách Lớp Sinh Viên"));
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

    public void loadFaculties() {
        cbFaculty.removeAllItems();
        List<Faculty> faculties = facultyService.getAllFaculties();
        for (Faculty f : faculties) {
            cbFaculty.addItem(f.getFacultyId());
        }
    }

    private void loadData() {
        tableModel.setData(classService.getAllClasses());
    }

    private void populateForm() {
        int row = table.getSelectedRow();
        if (row >= 0) {
            StudentClass sc = tableModel.getAt(row);
            if (sc != null) {
                txtClassId.setText(sc.getClassId());
                txtClassId.setEditable(false);
                txtClassName.setText(sc.getClassName());
                cbFaculty.setSelectedItem(sc.getFacultyId());
                txtAcademicYear.setText(sc.getAcademicYear());
            }
        }
    }

    private void clearForm() {
        txtClassId.setText("");
        txtClassId.setEditable(true);
        txtClassName.setText("");
        txtAcademicYear.setText("2022-2026");
        table.clearSelection();
        loadFaculties();
        loadData();
    }

    private void onAdd() {
        try {
            String fac = (String) cbFaculty.getSelectedItem();
            StudentClass sc = new StudentClass(txtClassId.getText().trim(), txtClassName.getText().trim(), fac, txtAcademicYear.getText().trim());
            classService.addClass(sc);
            JOptionPane.showMessageDialog(this, "Thêm Lớp thành công!", "Thông Báo", JOptionPane.INFORMATION_MESSAGE);
            clearForm();
        } catch (AppException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void onEdit() {
        try {
            String fac = (String) cbFaculty.getSelectedItem();
            StudentClass sc = new StudentClass(txtClassId.getText().trim(), txtClassName.getText().trim(), fac, txtAcademicYear.getText().trim());
            classService.updateClass(sc);
            JOptionPane.showMessageDialog(this, "Cập nhật Lớp thành công!", "Thông Báo", JOptionPane.INFORMATION_MESSAGE);
            clearForm();
        } catch (AppException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void onDelete() {
        String id = txtClassId.getText().trim();
        if (id.isEmpty()) return;
        int confirm = JOptionPane.showConfirmDialog(this, "Xác nhận xóa Lớp " + id + "?", "Xác Nhận", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                classService.deleteClass(id);
                JOptionPane.showMessageDialog(this, "Xóa Lớp thành công!", "Thông Báo", JOptionPane.INFORMATION_MESSAGE);
                clearForm();
            } catch (AppException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void onSearch() {
        tableModel.setData(classService.searchClasses(txtSearch.getText().trim()));
    }
}
