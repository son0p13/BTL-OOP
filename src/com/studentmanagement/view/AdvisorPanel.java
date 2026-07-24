package com.studentmanagement.view;

import com.studentmanagement.exception.AppException;
import com.studentmanagement.model.Advisor;
import com.studentmanagement.model.Faculty;
import com.studentmanagement.service.AdvisorService;
import com.studentmanagement.service.FacultyService;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Academic Advisor Management Panel (Video #6).
 */
public class AdvisorPanel extends JPanel {

    private JTextField txtAdvisorId;
    private JTextField txtFullName;
    private JComboBox<String> cbGender;
    private JTextField txtDob;
    private JTextField txtEmail;
    private JTextField txtPhone;
    private JComboBox<String> cbFaculty;

    private JTextField txtSearch;
    private JButton btnSearch;

    private JButton btnAdd;
    private JButton btnEdit;
    private JButton btnDelete;
    private JButton btnClear;

    private JTable table;
    private CustomTableModels.AdvisorTableModel tableModel;

    private final AdvisorService advisorService;
    private final FacultyService facultyService;

    public AdvisorPanel(AdvisorService advisorService, FacultyService facultyService) {
        this.advisorService = advisorService;
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
        formPanel.setBorder(BorderFactory.createTitledBorder("Thông Tin Cố Vấn Học Tập"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        int row = 0;
        gbc.gridx = 0; gbc.gridy = row;
        formPanel.add(new JLabel("Mã CVHT (*):"), gbc);
        gbc.gridx = 1;
        txtAdvisorId = new JTextField(16);
        formPanel.add(txtAdvisorId, gbc);

        row++;
        gbc.gridx = 0; gbc.gridy = row;
        formPanel.add(new JLabel("Họ và Tên (*):"), gbc);
        gbc.gridx = 1;
        txtFullName = new JTextField(16);
        formPanel.add(txtFullName, gbc);

        row++;
        gbc.gridx = 0; gbc.gridy = row;
        formPanel.add(new JLabel("Giới tính:"), gbc);
        gbc.gridx = 1;
        cbGender = new JComboBox<>(new String[]{"Nam", "Nữ", "Khác"});
        formPanel.add(cbGender, gbc);

        row++;
        gbc.gridx = 0; gbc.gridy = row;
        formPanel.add(new JLabel("Ngày sinh:"), gbc);
        gbc.gridx = 1;
        txtDob = new JTextField("15/04/1985", 16);
        formPanel.add(txtDob, gbc);

        row++;
        gbc.gridx = 0; gbc.gridy = row;
        formPanel.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1;
        txtEmail = new JTextField(16);
        formPanel.add(txtEmail, gbc);

        row++;
        gbc.gridx = 0; gbc.gridy = row;
        formPanel.add(new JLabel("Số Điện Thoại:"), gbc);
        gbc.gridx = 1;
        txtPhone = new JTextField(16);
        formPanel.add(txtPhone, gbc);

        row++;
        gbc.gridx = 0; gbc.gridy = row;
        formPanel.add(new JLabel("Khoa Trực Thuộc:"), gbc);
        gbc.gridx = 1;
        cbFaculty = new JComboBox<>();
        formPanel.add(cbFaculty, gbc);

        // Buttons
        row++;
        gbc.gridx = 0; gbc.gridy = row; gbc.gridwidth = 2;
        JPanel btnPanel = new JPanel(new GridLayout(2, 2, 8, 8));
        btnAdd = new JButton("Thêm CVHT");
        btnEdit = new JButton("Sửa CVHT");
        btnDelete = new JButton("Xóa CVHT");
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
        searchPanel.setBorder(BorderFactory.createTitledBorder("Tìm Kiếm Cố Vấn Học Tập"));
        searchPanel.add(new JLabel("Từ khóa:"));
        txtSearch = new JTextField(18);
        searchPanel.add(txtSearch);
        btnSearch = new JButton("Tìm Kiếm");
        searchPanel.add(btnSearch);
        centerPanel.add(searchPanel, BorderLayout.NORTH);

        tableModel = new CustomTableModels.AdvisorTableModel();
        table = new JTable(tableModel);
        table.setRowHeight(24);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Danh Sách Cố Vấn Học Tập"));
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
        tableModel.setData(advisorService.getAllAdvisors());
    }

    private void populateForm() {
        int row = table.getSelectedRow();
        if (row >= 0) {
            Advisor a = tableModel.getAt(row);
            if (a != null) {
                txtAdvisorId.setText(a.getAdvisorId());
                txtAdvisorId.setEditable(false);
                txtFullName.setText(a.getFullName());
                cbGender.setSelectedItem(a.getGender());
                txtDob.setText(a.getDateOfBirth());
                txtEmail.setText(a.getEmail());
                txtPhone.setText(a.getPhone());
                cbFaculty.setSelectedItem(a.getFacultyId());
            }
        }
    }

    private void clearForm() {
        txtAdvisorId.setText("");
        txtAdvisorId.setEditable(true);
        txtFullName.setText("");
        txtDob.setText("15/04/1985");
        txtEmail.setText("");
        txtPhone.setText("");
        table.clearSelection();
        loadFaculties();
        loadData();
    }

    private void onAdd() {
        try {
            String advId = txtAdvisorId.getText().trim();
            String name = txtFullName.getText().trim();
            String gender = (String) cbGender.getSelectedItem();
            String dob = txtDob.getText().trim();
            String email = txtEmail.getText().trim();
            String phone = txtPhone.getText().trim();
            String fac = (String) cbFaculty.getSelectedItem();

            Advisor a = new Advisor(advId, name, gender, dob, email, phone, advId, fac);
            advisorService.addAdvisor(a);
            JOptionPane.showMessageDialog(this, "Thêm Cố Vấn thành công!", "Thông Báo", JOptionPane.INFORMATION_MESSAGE);
            clearForm();
        } catch (AppException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void onEdit() {
        try {
            String advId = txtAdvisorId.getText().trim();
            String name = txtFullName.getText().trim();
            String gender = (String) cbGender.getSelectedItem();
            String dob = txtDob.getText().trim();
            String email = txtEmail.getText().trim();
            String phone = txtPhone.getText().trim();
            String fac = (String) cbFaculty.getSelectedItem();

            Advisor a = new Advisor(advId, name, gender, dob, email, phone, advId, fac);
            advisorService.updateAdvisor(a);
            JOptionPane.showMessageDialog(this, "Cập nhật Cố Vấn thành công!", "Thông Báo", JOptionPane.INFORMATION_MESSAGE);
            clearForm();
        } catch (AppException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void onDelete() {
        String id = txtAdvisorId.getText().trim();
        if (id.isEmpty()) return;
        int confirm = JOptionPane.showConfirmDialog(this, "Xác nhận xóa Cố Vấn " + id + "?", "Xác Nhận", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                advisorService.deleteAdvisor(id);
                JOptionPane.showMessageDialog(this, "Xóa Cố Vấn thành công!", "Thông Báo", JOptionPane.INFORMATION_MESSAGE);
                clearForm();
            } catch (AppException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void onSearch() {
        tableModel.setData(advisorService.searchAdvisors(txtSearch.getText().trim()));
    }
}
