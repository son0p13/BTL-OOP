package com.studentmanagement.view;

import com.studentmanagement.model.User;
import com.studentmanagement.service.*;

import javax.swing.*;
import java.awt.*;

/**
 * Main Application Window integrating all 8 modules from CodeMap Studio.
 */
public class MainFrame extends JFrame {

    private final AuthService authService;
    private final FacultyService facultyService;
    private final ClassService classService;
    private final AdvisorService advisorService;
    private final SubjectService subjectService;
    private final StudentService studentService;
    private final GradeService gradeService;

    private JTabbedPane tabbedPane;
    private FacultyPanel facultyPanel;
    private ClassPanel classPanel;
    private AdvisorPanel advisorPanel;
    private SubjectPanel subjectPanel;
    private StudentPanel studentPanel;
    private GradePanel gradePanel;

    private JLabel lblUserInfo;
    private JButton btnChangePassword;
    private JButton btnLogout;

    public MainFrame(AuthService authService, FacultyService facultyService, ClassService classService,
                     AdvisorService advisorService, SubjectService subjectService, StudentService studentService,
                     GradeService gradeService) {
        super("PHẦN MỀM QUẢN LÝ SINH VIÊN (CodeMap Studio - Standard Architecture)");
        this.authService = authService;
        this.facultyService = facultyService;
        this.classService = classService;
        this.advisorService = advisorService;
        this.subjectService = subjectService;
        this.studentService = studentService;
        this.gradeService = gradeService;

        initComponents();
    }

    private void initComponents() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 750);
        setMinimumSize(new Dimension(1000, 650));
        setLocationRelativeTo(null);

        // Header Panel (Top)
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(25, 118, 210));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        JLabel titleLabel = new JLabel("HỆ THỐNG QUẢN LÝ SINH VIÊN TOÀN DIỆN");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel, BorderLayout.WEST);

        JPanel userPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        userPanel.setOpaque(false);

        User curUser = authService.getCurrentUser();
        String uText = (curUser != null) ? "Xin chào: " + curUser.getFullName() + " [" + curUser.getRole() + "]" : "";
        lblUserInfo = new JLabel(uText);
        lblUserInfo.setFont(new Font("SansSerif", Font.BOLD, 13));
        lblUserInfo.setForeground(Color.WHITE);

        btnChangePassword = new JButton("Đổi Mật Khẩu");
        btnLogout = new JButton("Đăng Xuất");

        userPanel.add(lblUserInfo);
        userPanel.add(btnChangePassword);
        userPanel.add(btnLogout);

        headerPanel.add(userPanel, BorderLayout.EAST);
        add(headerPanel, BorderLayout.NORTH);

        // Tabbed Pane (Center)
        tabbedPane = new JTabbedPane();

        facultyPanel = new FacultyPanel(facultyService);
        classPanel = new ClassPanel(classService, facultyService);
        advisorPanel = new AdvisorPanel(advisorService, facultyService);
        subjectPanel = new SubjectPanel(subjectService);
        studentPanel = new StudentPanel(studentService, classService, advisorService);
        gradePanel = new GradePanel(gradeService, studentService, subjectService);

        tabbedPane.addTab(" Quản Lý Khoa ", facultyPanel);
        tabbedPane.addTab(" Quản Lý Lớp ", classPanel);
        tabbedPane.addTab(" Quản Lý Cố Vấn ", advisorPanel);
        tabbedPane.addTab(" Quản Lý Môn Học ", subjectPanel);
        tabbedPane.addTab(" Quản Lý Sinh Viên ", studentPanel);
        tabbedPane.addTab(" Quản Lý Điểm ", gradePanel);

        add(tabbedPane, BorderLayout.CENTER);

        // Status Bar
        JPanel statusBar = new JPanel(new FlowLayout(FlowLayout.LEFT));
        statusBar.setBorder(BorderFactory.createEtchedBorder());
        statusBar.add(new JLabel("Hệ thống đã kết nối dữ liệu. Sẵn sàng làm việc."));
        add(statusBar, BorderLayout.SOUTH);

        // Tab change listener to auto-refresh dropdown combos
        tabbedPane.addChangeListener(e -> {
            int index = tabbedPane.getSelectedIndex();
            if (index == 1) classPanel.loadFaculties();
            if (index == 2) advisorPanel.loadFaculties();
            if (index == 4) studentPanel.loadCombos();
            if (index == 5) gradePanel.loadCombos();
        });

        btnChangePassword.addActionListener(e -> {
            ChangePasswordDialog dialog = new ChangePasswordDialog(this, authService);
            dialog.setVisible(true);
        });

        btnLogout.addActionListener(e -> {
            authService.logout();
            dispose();
            LoginFrame loginFrame = new LoginFrame(authService);
            loginFrame.setLoginListener(user -> {
                MainFrame mainFrame = new MainFrame(authService, facultyService, classService, advisorService, subjectService, studentService, gradeService);
                mainFrame.setVisible(true);
            });
            loginFrame.setVisible(true);
        });
    }
}
