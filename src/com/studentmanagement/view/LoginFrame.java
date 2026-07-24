package com.studentmanagement.view;

import com.studentmanagement.exception.AuthenticationException;
import com.studentmanagement.model.User;
import com.studentmanagement.service.AuthService;

import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {

    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnLogin;
    private JButton btnExit;

    private final AuthService authService;
    private LoginListener loginListener;

    public interface LoginListener {
        void onLoginSuccess(User user);
    }

    public LoginFrame(AuthService authService) {
        super("ĐĂNG NHẬP HỆ THỐNG QUẢN LÝ SINH VIÊN");
        this.authService = authService;
        initComponents();
    }

    private void initComponents() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(450, 320);
        setResizable(false);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout());

        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(21, 101, 192));
        headerPanel.setPreferredSize(new Dimension(450, 60));

        JLabel titleLabel = new JLabel("ĐĂNG NHẬP HỆ THỐNG");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel);

        add(headerPanel, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Tên đăng nhập:"), gbc);
        gbc.gridx = 1;
        txtUsername = new JTextField(18);
        txtUsername.setText("admin");
        formPanel.add(txtUsername, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("Mật khẩu:"), gbc);
        gbc.gridx = 1;
        txtPassword = new JPasswordField(18);
        txtPassword.setText("admin");
        formPanel.add(txtPassword, gbc);

        add(formPanel, BorderLayout.CENTER);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 15));
        btnLogin = new JButton("Đăng Nhập");
        btnLogin.setFont(new Font("SansSerif", Font.BOLD, 13));
        btnLogin.setPreferredSize(new Dimension(110, 34));

        btnExit = new JButton("Thoát");
        btnExit.setPreferredSize(new Dimension(90, 34));

        btnPanel.add(btnLogin);
        btnPanel.add(btnExit);

        add(btnPanel, BorderLayout.SOUTH);

        btnLogin.addActionListener(e -> performLogin());
        txtPassword.addActionListener(e -> performLogin());
        btnExit.addActionListener(e -> System.exit(0));
    }

    private void performLogin() {
        String username = txtUsername.getText().trim();
        String password = new String(txtPassword.getPassword());

        try {
            User user = authService.login(username, password);
            JOptionPane.showMessageDialog(this,
                    "Đăng nhập thành công!\nXin chào: " + user.getFullName() + " (" + user.getRole() + ")",
                    "Thông Báo", JOptionPane.INFORMATION_MESSAGE);

            if (loginListener != null) {
                loginListener.onLoginSuccess(user);
            }
            dispose();
        } catch (AuthenticationException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Lỗi Đăng Nhập", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void setLoginListener(LoginListener listener) {
        this.loginListener = listener;
    }
}
