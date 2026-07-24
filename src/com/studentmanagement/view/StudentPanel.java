package com.studentmanagement.view;

import com.studentmanagement.exception.AppException;
import com.studentmanagement.model.Advisor;
import com.studentmanagement.model.Student;
import com.studentmanagement.model.StudentClass;
import com.studentmanagement.service.AdvisorService;
import com.studentmanagement.service.ClassService;
import com.studentmanagement.service.StudentService;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Student Profile Management Panel (Video #8).
 */
public class StudentPanel extends JPanel {

    private JTextField txtStudentId;
    private JTextField txtFullName;
    private JComboBox<String> cbGender;
    private JTextField txtDob;
    private JTextField txtHometown;
    private JComboBox<String> cbClass;
    private JComboBox<String> cbAdvisor;
    private JTextField txtEmail;
    private JTextField txtPhone;

    private JTextField txtSearch;
    private JButton btnSearch;

    private JButton btnAdd;
    private JButton btnEdit;
    private JButton btnDelete;
    private JButton btnClear;

    private JTable table;
    private CustomTableModels.StudentTableModel tableModel;

    private final StudentService studentService;
    private final ClassService classService;
    private final AdvisorService advisorService;

    public StudentPanel(StudentService studentService, ClassService classService, AdvisorService advisorService) {
        this.studentService = studentService;
        this.classService = classService;
        this.advisorService = advisorService;
        initComponents();
        loadCombos();
        loadData();
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Form Panel (West)
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Thông Tin Sinh Viên"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 4, 4, 4);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        int row = 0;
        gbc.gridx = 0; gbc.gridy = row;
        formPanel.add(new JLabel("Mã SV (*):"), gbc);
        gbc.gridx = 1;
        txtStudentId = new JTextField(16);
        formPanel.add(txtStudentId, gbc);

        row++;
        gbc.gridx = 0; gbc.gridy = row;
        formPanel.add(new JLabel("Họ và Tên (*):"), gbc);
        gbc.gridx = 1;
        txtFullName = new JTextField(16);
        formPanel.add(txtFullName, gbc);

        row++;
        gbc.gridx = 0; gbc.gridy = row;
        formPanel.add(new JLabel("Giới Tính:"), gbc);
        gbc.gridx = 1;
        cbGender = new JComboBox<>(new String[]{"Nam", "Nữ", "Khác"});
        formPanel.add(cbGender, gbc);

        row++;
        gbc.gridx = 0; gbc.gridy = row;
        formPanel.add(new JLabel("Ngày Sinh:"), gbc);
        gbc.gridx = 1;
        txtDob = new JTextField("12/05/2003", 16);
        formPanel.add(txtDob, gbc);

        row++;
        gbc.gridx = 0; gbc.gridy = row;
        formPanel.add(new JLabel("Quê Quán:"), gbc);
        gbc.gridx = 1;
        txtHometown = new JTextField("Hà Nội", 16);
        formPanel.add(txtHometown, gbc);

        row++;
        gbc.gridx = 0; gbc.gridy = row;
        formPanel.add(new JLabel("Lớp Học:"), gbc);
        gbc.gridx = 1;
        cbClass = new JComboBox<>();
        formPanel.add(cbClass, gbc);

        row++;
        gbc.gridx = 0; gbc.gridy = row;
        formPanel.add(new JLabel("CVHT:"), gbc);
        gbc.gridx = 1;
        cbAdvisor = new JComboBox<>();
        formPanel.add(cbAdvisor, gbc);

        row++;
        gbc.gridx = 0; gbc.gridy = row;
        formPanel.add(new JLabel("Email (*):"), gbc);
        gbc.gridx = 1;
        txtEmail = new JTextField(16);
        formPanel.add(txtEmail, gbc);

        row++;
        gbc.gridx = 0; gbc.gridy = row;
        formPanel.add(new JLabel("Số ĐT (*):"), gbc);
        gbc.gridx = 1;
        txtPhone = new JTextField(16);
        formPanel.add(txtPhone, gbc);

        // Buttons
        row++;
        gbc.gridx = 0; gbc.gridy = row; gbc.gridwidth = 2;
        JPanel btnPanel = new JPanel(new GridLayout(2, 2, 8, 8));
        btnAdd = new JButton("Thêm SV");
        btnEdit = new JButton("Sửa SV");
        btnDelete = new JButton("Xóa SV");
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
        searchPanel.setBorder(BorderFactory.createTitledBorder("Tìm Kiếm Sinh Viên"));
        searchPanel.add(new JLabel("Từ khóa:"));
        txtSearch = new JTextField(18);
        searchPanel.add(txtSearch);
        btnSearch = new JButton("Tìm Kiếm");
        searchPanel.add(btnSearch);
        centerPanel.add(searchPanel, BorderLayout.NORTH);

        tableModel = new CustomTableModels.StudentTableModel();
        table = new JTable(tableModel);
        table.setRowHeight(24);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Danh Sách Sinh Viên"));
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

    public void loadCombos() {
        cbClass.removeAllItems();
        List<StudentClass> classes = classService.getAllClasses();
        for (StudentClass c : classes) cbClass.addItem(c.getClassId());

        cbAdvisor.removeAllItems();
        List<Advisor> advisors = advisorService.getAllAdvisors();
        for (Advisor a : advisors) cbAdvisor.addItem(a.getAdvisorId());
    }

    private void loadData() {
        tableModel.setData(studentService.getAllStudents());
    }

    private void populateForm() {
        int row = table.getSelectedRow();
        if (row >= 0) {
            Student s = tableModel.getAt(row);
            if (s != null) {
                txtStudentId.setText(s.getStudentId());
                txtStudentId.setEditable(false);
                txtFullName.setText(s.getFullName());
                cbGender.setSelectedItem(s.getGender());
                txtDob.setText(s.getDateOfBirth());
                txtHometown.setText(s.getHometown());
                cbClass.setSelectedItem(s.getClassId());
                cbAdvisor.setSelectedItem(s.getAdvisorId());
                txtEmail.setText(s.getEmail());
                txtPhone.setText(s.getPhone());
            }
        }
    }

    private void clearForm() {
        txtStudentId.setText("");
        txtStudentId.setEditable(true);
        txtFullName.setText("");
        txtDob.setText("12/05/2003");
        txtHometown.setText("Hà Nội");
        txtEmail.setText("");
        txtPhone.setText("");
        table.clearSelection();
        loadCombos();
        loadData();
    }

    private void onAdd() {
        try {
            String sId = txtStudentId.getText().trim();
            String name = txtFullName.getText().trim();
            String gender = (String) cbGender.getSelectedItem();
            String dob = txtDob.getText().trim();
            String home = txtHometown.getText().trim();
            String cls = (String) cbClass.getSelectedItem();
            String adv = (String) cbAdvisor.getSelectedItem();
            String email = txtEmail.getText().trim();
            String phone = txtPhone.getText().trim();

            Student s = new Student(sId, name, gender, dob, email, phone, sId, home, cls, adv);
            studentService.addStudent(s);
            JOptionPane.showMessageDialog(this, "Thêm Sinh Viên thành công!", "Thông Báo", JOptionPane.INFORMATION_MESSAGE);
            clearForm();
        } catch (AppException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void onEdit() {
        try {
            String sId = txtStudentId.getText().trim();
            String name = txtFullName.getText().trim();
            String gender = (String) cbGender.getSelectedItem();
            String dob = txtDob.getText().trim();
            String home = txtHometown.getText().trim();
            String cls = (String) cbClass.getSelectedItem();
            String adv = (String) cbAdvisor.getSelectedItem();
            String email = txtEmail.getText().trim();
            String phone = txtPhone.getText().trim();

            Student s = new Student(sId, name, gender, dob, email, phone, sId, home, cls, adv);
            studentService.updateStudent(s);
            JOptionPane.showMessageDialog(this, "Cập nhật Sinh Viên thành công!", "Thông Báo", JOptionPane.INFORMATION_MESSAGE);
            clearForm();
        } catch (AppException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void onDelete() {
        String id = txtStudentId.getText().trim();
        if (id.isEmpty()) return;
        int confirm = JOptionPane.showConfirmDialog(this, "Xác nhận xóa Sinh Viên " + id + "?", "Xác Nhận", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                studentService.deleteStudent(id);
                JOptionPane.showMessageDialog(this, "Xóa Sinh Viên thành công!", "Thông Báo", JOptionPane.INFORMATION_MESSAGE);
                clearForm();
            } catch (AppException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void onSearch() {
        tableModel.setData(studentService.searchStudents(txtSearch.getText().trim()));
    }
}
