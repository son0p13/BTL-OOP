package com.studentmanagement.view;

import com.studentmanagement.exception.AppException;
import com.studentmanagement.model.Faculty;
import com.studentmanagement.service.FacultyService;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Faculty Management Panel (Video #4).
 */
public class FacultyPanel extends JPanel {

    private JTextField txtId;
    private JTextField txtName;
    private JTextField txtDean;
    private JTextField txtPhone;

    private JTextField txtSearch;
    private JButton btnSearch;

    private JButton btnAdd;
    private JButton btnEdit;
    private JButton btnDelete;
    private JButton btnClear;

    private JTable table;
    private CustomTableModels.FacultyTableModel tableModel;

    private final FacultyService facultyService;

    public FacultyPanel(FacultyService facultyService) {
        this.facultyService = facultyService;
        initComponents();
        loadData();
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Form Panel (West)
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Thông Tin Khoa"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        int row = 0;
        gbc.gridx = 0; gbc.gridy = row;
        formPanel.add(new JLabel("Mã Khoa (*):"), gbc);
        gbc.gridx = 1;
        txtId = new JTextField(16);
        formPanel.add(txtId, gbc);

        row++;
        gbc.gridx = 0; gbc.gridy = row;
        formPanel.add(new JLabel("Tên Khoa (*):"), gbc);
        gbc.gridx = 1;
        txtName = new JTextField(16);
        formPanel.add(txtName, gbc);

        row++;
        gbc.gridx = 0; gbc.gridy = row;
        formPanel.add(new JLabel("Trưởng Khoa:"), gbc);
        gbc.gridx = 1;
        txtDean = new JTextField(16);
        formPanel.add(txtDean, gbc);

        row++;
        gbc.gridx = 0; gbc.gridy = row;
        formPanel.add(new JLabel("Số Điện Thoại:"), gbc);
        gbc.gridx = 1;
        txtPhone = new JTextField(16);
        formPanel.add(txtPhone, gbc);

        // Buttons
        row++;
        gbc.gridx = 0; gbc.gridy = row; gbc.gridwidth = 2;
        JPanel btnPanel = new JPanel(new GridLayout(2, 2, 8, 8));
        btnAdd = new JButton("Thêm Khoa");
        btnEdit = new JButton("Sửa Khoa");
        btnDelete = new JButton("Xóa Khoa");
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
        searchPanel.setBorder(BorderFactory.createTitledBorder("Tìm Kiếm Khoa"));
        searchPanel.add(new JLabel("Từ khóa:"));
        txtSearch = new JTextField(18);
        searchPanel.add(txtSearch);
        btnSearch = new JButton("Tìm Kiếm");
        searchPanel.add(btnSearch);
        centerPanel.add(searchPanel, BorderLayout.NORTH);

        tableModel = new CustomTableModels.FacultyTableModel();
        table = new JTable(tableModel);
        table.setRowHeight(24);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Danh Sách Khoa"));
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
        tableModel.setData(facultyService.getAllFaculties());
    }

    private void populateForm() {
        int row = table.getSelectedRow();
        if (row >= 0) {
            Faculty f = tableModel.getAt(row);
            if (f != null) {
                txtId.setText(f.getFacultyId());
                txtId.setEditable(false);
                txtName.setText(f.getFacultyName());
                txtDean.setText(f.getDeanName());
                txtPhone.setText(f.getPhone());
            }
        }
    }

    private void clearForm() {
        txtId.setText("");
        txtId.setEditable(true);
        txtName.setText("");
        txtDean.setText("");
        txtPhone.setText("");
        table.clearSelection();
        loadData();
    }

    private void onAdd() {
        try {
            Faculty f = new Faculty(txtId.getText().trim(), txtName.getText().trim(), txtDean.getText().trim(), txtPhone.getText().trim());
            facultyService.addFaculty(f);
            JOptionPane.showMessageDialog(this, "Thêm Khoa thành công!", "Thông Báo", JOptionPane.INFORMATION_MESSAGE);
            clearForm();
        } catch (AppException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void onEdit() {
        try {
            Faculty f = new Faculty(txtId.getText().trim(), txtName.getText().trim(), txtDean.getText().trim(), txtPhone.getText().trim());
            facultyService.updateFaculty(f);
            JOptionPane.showMessageDialog(this, "Cập nhật Khoa thành công!", "Thông Báo", JOptionPane.INFORMATION_MESSAGE);
            clearForm();
        } catch (AppException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void onDelete() {
        String id = txtId.getText().trim();
        if (id.isEmpty()) return;
        int confirm = JOptionPane.showConfirmDialog(this, "Xác nhận xóa Khoa " + id + "?", "Xác Nhận", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                facultyService.deleteFaculty(id);
                JOptionPane.showMessageDialog(this, "Xóa Khoa thành công!", "Thông Báo", JOptionPane.INFORMATION_MESSAGE);
                clearForm();
            } catch (AppException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void onSearch() {
        List<Faculty> res = facultyService.searchFaculties(txtSearch.getText().trim());
        tableModel.setData(res);
    }
}
